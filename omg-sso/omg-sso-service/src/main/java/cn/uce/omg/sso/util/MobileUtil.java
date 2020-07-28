/** 
 * @项目名称: FSP
 * @文件名称: CodeUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uce.omg.sso.cache.HashRedisWithFieldExpireCache;
import cn.uce.omg.sso.constant.AuthConstants;
import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.exception.TimeoutException;
import cn.uce.omg.sso.vo.CodeVo;

/**
 * MobileUtil  
 * @Description: MobileUtil  
 * @author yangjun 
 * @date 2017年9月13日 下午1:02:26 
 * @version 1.0 
 */
public class MobileUtil {
	protected Logger LOG = LoggerFactory.getLogger(MobileUtil.class);
	
	/** 手机号验证缓存 */
	private HashRedisWithFieldExpireCache<CodeVo> mobileCache;
	/** 锁管理对象 */
	private LockManager lockManager;
	/** 手机号验证超时时间：毫秒，默认12小时 */
	private Integer codeTimeout = 43200;
	/**手机号验证验证次数 */
	private Integer verifyCount = 3;
	/** 手机号验证发送间隔时间 */
	private Integer intervalTime = 10;
	
	/**
	 * 
		 * <pre>
		 *		描述:校验手机发送短信验证
		 * </pre>
		 * @param mobile
		 * @author yangjun
	 * @throws GatewayException 
		 * @date 2017-09-14
	 */
	public boolean checkMobile(String mobile) throws GatewayException{
		if (mobile == null) {
			throw new GatewayException(ErrorCode.MOBILE_NOTFIND_ERROR,
					ErrorCode.MOBILE_NOTFIND_ERROR_MESSGE);
		}
		String key = mobile;//手机号作为cache的key
		CodeVo codeVo = mobileCache.get(key);
		// 如果当前缓存没有手机号发送记录，则添加进去
		if (codeVo == null) {
			codeVo = new CodeVo();
			codeVo.setSendTime(new Date());// 当前发送时间
			codeVo.setExpireTime(System.currentTimeMillis()+codeTimeout*1000);// 记录当前key的失效时间
			codeVo.setVerifyCount(1);// 记录第一次发送
			increment(key, codeVo); // 添加到缓存
			return true;
		}		
		// 发送间隔时间差
		long currentnewIntervalTime = new Date().getTime()
				- codeVo.getSendTime().getTime();		
		// 验证频繁发送手机短信时间
		if (currentnewIntervalTime < intervalTime * 1000) {
			throw new GatewayException(ErrorCode.MOBILE_INTERVALTIME_ERROR,
					ErrorCode.MOBILE_INTERVALTIME_ERROR_MESSGE);
		}
		// 验证发送手机短信次数
		if (codeVo.getVerifyCount() != null
				&& codeVo.getVerifyCount() >= verifyCount) {
			// deleteCode(key);
			throw new GatewayException(ErrorCode.MOBILE_VERIFYCOUNT_ERROR,
					ErrorCode.MOBILE_VERIFYCOUNT_ERROR_MESSGE);
		}

		increment(key, codeVo);// 添加到缓存
		return true;
	}
	/**
	 *  
		 * <pre>
		 *		描述:通过管理锁的方式控制并发操作
		 * </pre>
		 * @param key 
		 * @param codeVo
		 * @author yangjun
	 * @throws GatewayException 
		 * @date 2017-09-14
	 */
	public void increment(String key, CodeVo codeVo) throws GatewayException {
	    try {
	      lockManager.getLock(AuthConstants.MOBILE_CODE, key, 5000);
	    } catch (TimeoutException e) {
	    	LOG.error("LockManager: "+e.getMessage(),e);      
	    }
	    try {
	      CodeVo oldCodeVo = mobileCache.get(key);
	      if (oldCodeVo == null) {
	        mobileCache.put(key, codeVo);
	      } else {
	    	codeVo.setSendTime(new Date());// 当前发送时间
	        codeVo.setVerifyCount(oldCodeVo.getVerifyCount() + 1);// 本次发送次数+1
	      }
	      mobileCache.put(key, codeVo);
	    } finally {
	      lockManager.releaseLock(AuthConstants.MOBILE_CODE, key);
	    }
	  }

	/**
	 * @param mobileCache the mobileCache to set
	 */
	public void setMobileCache(HashRedisWithFieldExpireCache<CodeVo> mobileCache) {
		this.mobileCache = mobileCache;
	}
	/**
	 * @param lockManager the lockManager to set
	 */
	public void setLockManager(LockManager lockManager) {
		this.lockManager = lockManager;
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
