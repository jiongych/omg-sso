/** 
 * @项目名称: FSP
 * @文件名称: CodeUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.omg.sso.cache.HashRedisWithFieldExpireCache;
import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.vo.CodeVo;

/**
 * CodeUtil  
 * @Description: CodeUtil  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class CodeUtil {
	protected Log LOG = LogFactory.getLog(CodeUtil.class);
	private Random random = new Random();
	
	/** 验证码缓存 */
	private HashRedisWithFieldExpireCache<CodeVo> codeCache;
	/** 验证码超时时间：毫秒，默认10分钟 */
	private Integer codeTimeout = 600;
	/** 验证码验证次数 */
	private Integer verifyCount = 10;
	/** 验证码发送间隔时间 */
	private Integer intervalTime = 10;
	
	public final static String RESET_PWD_KEY="resetPwdKey";
	
	/**
	 * 生成6位数字验证码
	 * @param request
	 * @return
	 */
	public String createCode() {
		int max=999999;
        int min=110010;
    	int code = random.nextInt(max)%(max-min+1) + min;
		return code + "";
	}
	
	public String createResetPwdKey(String empCode) throws Exception {
		return SignUtils.sign(empCode, System.currentTimeMillis()+"", "utf-8");
	}

	/**
	 * 保存验证码到redis
	 * @param code 
	 * @param userName 
	 * @param systemCode 
	 * @param codeType
	 * @throws Exception
	 */
	public void saveCode(String code,String empCode, String codeType) throws Exception {
		if(code == null || empCode == null || codeType == null){
			return;
		}
		String key = empCode + codeType;
		CodeVo codeVo = codeCache.get(key);
		if(codeVo == null){
			codeVo = new CodeVo();
			Calendar cal = Calendar.getInstance();
			codeVo.setExpireTime(cal.getTime().getTime() + codeTimeout * 1000);
		}else{
			long currentnewIntervalTime = new Date().getTime() - codeVo.getSendTime().getTime();
			if(currentnewIntervalTime < intervalTime * 1000){
				throw new GatewayException(ErrorCode.CODE_INTERVALTIME_ERROR, ErrorCode.CODE_INTERVALTIME_ERROR_MESSGE);
			}
		}
		codeVo.setCode(code);
		codeVo.setEmpCode(empCode);
		codeVo.setSendTime(new Date());
		codeCache.put(key, codeVo);
	}
	
	/**
	 * 保存重置密码唯一key到redis
	 * @param empCode
	 * @param codeType
	 * @throws Exception
	 */
	public String saveResetPwdKey(String empCode) throws Exception {
		if(empCode == null){
			return null;
		}
		String key = RESET_PWD_KEY + empCode;
		CodeVo codeVo = codeCache.get(key);
		if(codeVo == null){
			codeVo = new CodeVo();
			Calendar cal = Calendar.getInstance();
			codeVo.setExpireTime(cal.getTime().getTime() + codeTimeout * 1000);
		}
		String resetPwdKey = createResetPwdKey(empCode);
		codeVo.setCode(resetPwdKey);
		codeVo.setEmpCode(empCode);
		codeVo.setSendTime(new Date());
		codeCache.put(key, codeVo);
		return resetPwdKey;
	}
	
	/**
	 * 校验验证码
	 * @param code 
	 * @param userName 
	 * @param systemCode 
	 * @param codeType
	 * @throws Exception
	 */
	public boolean checkCode(String code, String empCode, String codeType) throws Exception {
		if(code == null || empCode == null || codeType == null){
			throw new GatewayException(ErrorCode.CODE_PARAM_ERROR, ErrorCode.CODE_PARAM_ERROR_MESSGE);
		}
		String key = empCode + codeType;
		CodeVo codeVo = codeCache.get(key);
		if(codeVo == null){
			throw new GatewayException(ErrorCode.CODE_NOTFIND_ERROR, ErrorCode.CODE_NOTFIND_ERROR_MESSGE);
		}
		if(codeVo.getVerifyCount() != null && codeVo.getVerifyCount() > verifyCount){
			deleteCode(key);
			throw new GatewayException(ErrorCode.CODE_VERIFYCOUNT_ERROR, ErrorCode.CODE_VERIFYCOUNT_ERROR_MESSGE);
		}
		String saveCode = codeVo.getCode();
		if(!saveCode.equals(code)){
			codeVo.setVerifyCount(codeVo.getVerifyCount() == null ? 0 + 1 : codeVo.getVerifyCount() + 1);
			throw new GatewayException(ErrorCode.RTEPWD_CODE_ERROR, ErrorCode.RTEPWD_CODE_ERROR_MESSGE);
		}
		Date now = new Date();
		if(now.getTime() - codeVo.getExpireTime() > codeTimeout){
			throw new GatewayException(ErrorCode.CODE_TIMEOUT_ERROR, ErrorCode.CODE_TIMEOUT_ERROR_MESSGE);
		}
		return true;
	}
	
	/**
	 * 校验验证码重置密码唯一key 
	 * @param code
	 * @param empCode
	 * @param codeType
	 * @return
	 * @throws Exception
	 */
	public boolean checkResetPwdKey(String code, String empCode) throws Exception {
		if(code == null || empCode == null){
			throw new GatewayException(ErrorCode.CODE_PARAM_ERROR, ErrorCode.CODE_PARAM_ERROR_MESSGE);
		}
		String key = RESET_PWD_KEY + empCode;
		CodeVo codeVo = codeCache.get(key);
		if(codeVo == null 
				|| (!code.equals(codeVo.getCode()))){
			return false;
		}
		return true;
	}
	
	/**
	 * 根据key获取验证码
	 * @param key
	 * @return
	 */
	public String getCodeByKey(String key){
		if(key == null){
			return null;
		}
		CodeVo codeVo = codeCache.get(key);
		if(codeVo != null){
			return codeVo.getCode();
		}
		return null;
	}
	
	/**
	 * 删除验证码
	 * @param key
	 */
	public void deleteCode(String key){
		CodeVo codeVo = codeCache.get(key);
		if(codeVo == null){
			return;
		}
		codeCache.delete(key);
	}
	/**
	 * @param codeCache the codeCache to set
	 */
	public void setCodeCache(HashRedisWithFieldExpireCache<CodeVo> codeCache) {
		this.codeCache = codeCache;
	}

	/**
	 * @param codeTimeout the codeTimeout to set
	 */
	public void setCodeTimeout(Integer codeTimeout) {
		this.codeTimeout = codeTimeout;
	}

	/**
	 * @param verifyCount the verifyCount to set
	 */
	public void setVerifyCount(Integer verifyCount) {
		this.verifyCount = verifyCount;
	}

	/**
	 * @param intervalTime the intervalTime to set
	 */
	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}
}
