/** 
 * @项目名称: FSP
 * @文件名称: HashFieldCache extends BoundZSetRedisSupport<String> 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.cache;

import java.util.Set;

import cn.uce.omg.sso.util.redis.BoundZSetRedisSupport;

/**
 * 通过bound封装指定的key，进行一系列的操作而无须“显式”的再次指定Key 不会主动删除
 * @author zhangfh
 *
 * @param <T> redis中存放的对象
 */
public class HashFieldCache extends BoundZSetRedisSupport<String> {

	/**
	 * key默认值为“存放的对象的简单类名:hash”,可在之类中覆盖默认key
	 */
	public HashFieldCache() {
		super.hkey = "hashFeild:zset";
	}
	
  /***
	* 排序
	* @param min
	* @param max
	* @return 
	* @see cn.uce.omg.sso.util.redis.HashFieldCache#rangeByScore(double, double)
	*/
	public Set<String> rangeByScore(double min, double max) {
		return super.rangeByScore(min, max);
	}

}
