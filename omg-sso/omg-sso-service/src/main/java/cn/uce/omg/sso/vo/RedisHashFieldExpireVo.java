/** 
 * @项目名称: FSP
 * @文件名称: RedisHashFieldExpireVo implements Serializable
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.io.Serializable;

/**
 * 缓存
 * @author huangting
 * @date 2017年6月9日 下午6:15:45
 */
public class RedisHashFieldExpireVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 过期时间 */
	private Long expireTime;

	/**
	 * @return the expireTime
	 */
	public Long getExpireTime() {
		return expireTime;
	}
	
	/**
	 * @param expireTime the expireTime to set
	 */
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	
}
