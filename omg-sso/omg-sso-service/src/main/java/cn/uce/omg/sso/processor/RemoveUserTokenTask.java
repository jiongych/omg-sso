/** 
 * @项目名称: FSP
 * @文件名称: RemoveUserTokenTask extends Thread 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.omg.sso.cache.HashRedisWithFieldExpireCache;
import cn.uce.omg.sso.util.redis.BoundHashRedisSupport;
import cn.uce.omg.sso.vo.TokenVo;
import cn.uce.omg.sso.vo.portal.PortalCurrentUser;

/**
 * 移除用户token定时器
 * @author zhangfenghuang
 * @date 2017年6月8日 下午2:42:03
 */
public class RemoveUserTokenTask extends Thread {
	/** 
	 * 用户token信息缓存
	 */
	private BoundHashRedisSupport<List<String>> userTokenCache;

	/**
	 * 用户信息
	 */
	private BoundHashRedisSupport<PortalCurrentUser> tokenUserInfoCache;
	
	/** 
	 * token系统信息缓存
	 */
	private HashRedisWithFieldExpireCache<TokenVo> tokenSystemCache;
	
	/** 
	 * 默认5分钟 
	 */
	private int waitTime = 300;
	
	/** 
	 * 退出标识
	 */
	private boolean exitFlag;
	private Log LOG = LogFactory.getLog(this.getClass());


	/**
	 * 线程执行入口
	 * @author zhangfh
	 * @date 2017年6月14日 下午4:06:47
	 */
	public void run() {
		//等待时间
		waitTime = waitTime * 1000;
		while (!exitFlag) {
			try {
				//获取所有用户token缓存
				Map<String, List<String>> entries = userTokenCache.entries();
				for (String userName : entries.keySet()) {
					//根据用户名获取用户token
					List<String> oldTokenList = entries.get(userName);
					List<String> newTokenList = new ArrayList<String>(oldTokenList.size());
					List<String> delTokenList = new ArrayList<String>(oldTokenList.size());
					for (String token : oldTokenList) {
						if (tokenSystemCache.get(token) != null) {
							newTokenList.add(token);
						}else{
							//找不到
							delTokenList.add(token);
						}
					}
					//如果token没有被系统使用，则删除
					if (newTokenList.size() == 0) {
						userTokenCache.delete(userName);
						//清除用户信息
						for(String token : oldTokenList){
							tokenUserInfoCache.delete(token);
						}
					} else {
						//如果token被系统使用，重新放入
						userTokenCache.put(userName, newTokenList);
						//清除用户信息
						for(String token : delTokenList){
							tokenUserInfoCache.delete(token);
						}
					}
				}
			} catch (Exception e) {
				LOG.error("删除UserTokenCache失败，", e);
			}
			try {
				//线程等待
				Thread.sleep((waitTime));
			} catch (Exception e) {
				LOG.error(e);
			}
		}
	}

	/**
	 * @param userTokenCache the userTokenCache to set
	 */
	public void setUserTokenCache(BoundHashRedisSupport<List<String>> userTokenCache) {
		this.userTokenCache = userTokenCache;
	}

	/**
	 * @param tokenSystemCache the tokenSystemCache to set
	 */
	public void setTokenSystemCache(
			HashRedisWithFieldExpireCache<TokenVo> tokenSystemCache) {
		this.tokenSystemCache = tokenSystemCache;
	}
	/**
	 * @param tokenUserInfoCache the tokenUserInfoCache to set
	 */
	public void setTokenUserInfoCache(BoundHashRedisSupport<PortalCurrentUser> tokenUserInfoCache) {
		this.tokenUserInfoCache = tokenUserInfoCache;
	}
	
	/**
	 * @param waitTime the waitTime to set
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	/**
	* 线程销毁
	* @see java.lang.Thread#destroy()
	*/
	public void destroy() {
		this.exitFlag = true;
	}
}
