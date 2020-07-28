/** 
 * @项目名称: FSP
 * @文件名称: CodeUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;

import cn.uce.omg.sso.cache.HashRedisWithFieldExpireCache;
import cn.uce.omg.sso.constant.AuthConstants;
import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.exception.TimeoutException;
import cn.uce.omg.sso.vo.LoginCodeVo;

/**
 * 扫码登录工具类
 * @author majun
 * @date 2019年6月24日 上午11:18:22
 *
 */
public class LoginCodeUtil {
	protected Log log = LogFactory.getLog(LoginCodeUtil.class);
	private String prefix = "LoginCode:";
	
	/** 锁管理对象 */
	private LockManager lockManager;
	
	/** 验证码缓存 */
	private HashRedisWithFieldExpireCache<LoginCodeVo> codeCache;
	/** 验证码超时时间：秒，默认2分钟 */
	private Integer codeTimeout = 120;
	/** 验证码验证次数 */
	private Integer verifyCount = 10;
	/** 验证码发送间隔时间 */
	private Integer intervalTime = 10;
	
	public final static String RESET_PWD_KEY="resetPwdKey";
	
	/**
	 * 生成登录码
	 * @param request
	 * @return
	 */
	private String createCode() {
		String code = UUID.randomUUID().toString().replaceAll("-", "");
		return code;
	}
	
	/**
	 * 保存登录验证码到redis
	 * @param code 
	 * @param userName 
	 * @param systemCode 
	 * @param codeType
	 * @throws GatewayException 
	 */
	public LoginCodeVo saveCode(String systemCode) throws GatewayException {
		if(systemCode == null){
			return new LoginCodeVo();
		}
		String code = systemCode + createCode();
		String key = prefix + code;
		LoginCodeVo codeVo = codeCache.get(key);
		if(codeVo != null){
			// 如果能够查到，说明生成了相同的code，提示错误。
			throw new GatewayException(ErrorCode.CREATE_CODE_ERROR, ErrorCode.CREATE_CODE_ERROR_MESSGE);
		}
		codeVo = new LoginCodeVo();
		codeVo.setCode(code);
		codeVo.setSystemCode(systemCode);
		codeVo.setSendTime(new Date());
		codeVo.setExpireTime(System.currentTimeMillis() + codeTimeout * 1000);
		codeCache.put(key, codeVo);
		
		return codeVo;
	}
	
	/**
	 * 获取验证码
	 * @param code 
	 * @param userName 
	 * @param systemCode 
	 * @param codeType
	 * @throws GatewayException 
	 * @throws Exception
	 */
	public LoginCodeVo getLoginCode(LoginCodeVo loginCode) throws GatewayException {
//		if(StringUtils.isEmpty(loginCode.getCode()) || StringUtils.isEmpty(loginCode.getSystemCode())){
//			throw new GatewayException(ErrorCode.CODE_PARAM_ERROR, ErrorCode.CODE_PARAM_ERROR_MESSGE);
//		}
		String key =prefix + loginCode.getCode();
		LoginCodeVo codeVo = codeCache.get(key);
//		if(codeVo == null){
//			throw new GatewayException(ErrorCode.CODE_NOTFIND_ERROR, ErrorCode.CODE_NOTFIND_ERROR_MESSGE);
//		}
		return codeVo;
	}
	
	public boolean updateLoginCode(LoginCodeVo loginCode) throws Exception {
		if(log.isInfoEnabled()){
			log.info("手机端扫码登录,更新缓存唯一码,入参:"+ JSON.toJSONString(loginCode));
		}
		if(StringUtils.isEmpty(loginCode.getCode()) || StringUtils.isEmpty(loginCode.getScanSystemCode())){
			throw new GatewayException(ErrorCode.CODE_PARAM_ERROR, ErrorCode.CODE_PARAM_ERROR_MESSGE);
		}
		String key =prefix + loginCode.getCode();
		try {
			lockManager.getLock(AuthConstants.LOGIN_FAIL, key, 5000);
		} catch (TimeoutException e) {
			  log.error("LockManager: "+e.getMessage(),e);
	    }
		try {
			LoginCodeVo codeVo = codeCache.get(key);
			if(codeVo == null){
				throw new GatewayException(ErrorCode.CODE_NOTFIND_ERROR, ErrorCode.CODE_NOTFIND_ERROR_MESSGE);
			} else if (StringUtils.isEmpty(codeVo.getEmpCode())) {
				if (StringUtils.isEmpty(loginCode.getEmpCode()) || StringUtils.isEmpty(loginCode.getScanSystemCode())) {
					return false;
				} else {
					codeVo.setEmpCode(loginCode.getEmpCode());
					codeVo.setScanSystemCode(loginCode.getScanSystemCode());
					codeVo.setTokenId(loginCode.getTokenId());
					if(log.isInfoEnabled()){
						log.info("手机端扫码登录,首次更新缓存唯一码相关登录信息成功,参数:"+ JSON.toJSONString(loginCode));
					}
				}
			} else if (!org.apache.commons.lang.StringUtils.equals(codeVo.getEmpCode(), loginCode.getEmpCode())) {
				throw new GatewayException(ErrorCode.CODE_OCCUPIED_ERROR, ErrorCode.CODE_OCCUPIED_ERROR_MESSGE);
			} else if (codeVo.getFailureFlag() != null && codeVo.getFailureFlag()){
				throw new GatewayException(ErrorCode.CODE_CANCEL_ERROR, ErrorCode.CODE_CANCEL_ERROR_MESSGE);
			} else if (StringUtils.isEmpty(codeVo.getTokenId())) {
				if ((!StringUtils.isEmpty(loginCode.getTokenId()) || null != loginCode.getFailureFlag())
						&& org.apache.commons.lang.StringUtils.equals(codeVo.getScanSystemCode(), loginCode.getScanSystemCode())) {
					codeVo.setTokenId(loginCode.getTokenId());
					codeVo.setFailureFlag(loginCode.getFailureFlag());
					if(log.isInfoEnabled()){
						log.info("手机端扫码登录,更新缓存token或失效标识成功,参数:"+ JSON.toJSONString(loginCode));
					}
				} else {
					return false;
				}
			}
			codeCache.put(key, codeVo);
		} finally {
			lockManager.releaseLock(AuthConstants.LOGIN_FAIL, key);
		}
		return true;
	}
	

	public void setLockManager(LockManager lockManager) {
		this.lockManager = lockManager;
	}

	public void setCodeCache(HashRedisWithFieldExpireCache<LoginCodeVo> codeCache) {
		this.codeCache = codeCache;
	}

	public void setCodeTimeout(Integer codeTimeout) {
		this.codeTimeout = codeTimeout;
	}

	public void setVerifyCount(Integer verifyCount) {
		this.verifyCount = verifyCount;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}
	
}
