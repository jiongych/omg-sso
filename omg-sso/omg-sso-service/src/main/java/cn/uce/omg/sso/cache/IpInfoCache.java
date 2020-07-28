/** 
 * @项目名称: FSP
 * @文件名称: IpInfoCache extends BoundHashRedisSupport<IpInfoVo> implements InitializingBean 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.cache;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import cn.uce.omg.sso.util.redis.BoundHashRedisSupport;
import cn.uce.omg.sso.vo.IpInfoVo;

/**
 * ip信息缓存
 * @author tanchong
 */
public class IpInfoCache extends BoundHashRedisSupport<IpInfoVo> implements InitializingBean {
	protected HashFieldCache hashFieldCache;
	private String splitChar = "#";

	@Override
	public void afterPropertiesSet() throws Exception {
		if (hashFieldCache == null) {
			throw new IllegalArgumentException("HashFieldCache cann't be null");
		}
	}

	/**
	 * 加入到缓存中
	 * @param key
	 * @param value
	 * @param sectionNum ip段
	 */
	public void put(final String key, IpInfoVo value, Long sectionNum) {
		if (key == null || value == null || sectionNum == null) {
			return;
		}
		hashFieldCache.add(generateZsetMember(key), sectionNum);
		super.put(key, value);
	}

	/**
	 * (非 Javadoc) 
	* <p>Title: get</p> 
	* <p>Description: </p> 
	* @param key
	* @return 
	* @see cn.uce.omg.sso.util.redis.IpInfoCache#get(java.lang.String)
	 */
	public IpInfoVo get(String key) {
		IpInfoVo value = super.get(key);
		return value;
	}
	
  /**
	* 遍历
	* @return 
	*/
	@Override
	public Map<String, IpInfoVo> entries() {
		return super.entries();
	}

 /**
	* 删除缓存
	* @param key 
	* @see cn.uce.omg.sso.util.redis.IpInfoCache#delete(java.lang.String)
	*/
	public void delete(String key) {
		super.delete(key);
		hashFieldCache.remove(generateZsetMember(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @date 2017年6月9日 下午6:12:24
	 */
	public String generateZsetMember(String key) {
		String member = this.getHkey() + splitChar + key;
		return member;
	}

	public void setHashFieldCache(HashFieldCache hashFieldCache) {
		this.hashFieldCache = hashFieldCache;
	}

}
