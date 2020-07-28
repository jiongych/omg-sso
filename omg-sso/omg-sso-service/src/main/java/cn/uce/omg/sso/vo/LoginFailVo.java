/** 
 * @项目名称: FSP
 * @文件名称: LoginFailVo implements Serializable 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.io.Serializable;

/**
 * 登录失败vo 1、用户输入N次失败之后，递增锁定用户，不允许登录认证系统。 2、用户登录成功后，将失败信息进行删除。
 * @author tanchong
 *
 */
public class LoginFailVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** 用户名 */
	private String userName;
	/** 失效时间：用户不允许操作的时间 */
	private Long userExpireTime;
	/** 失败次数:依次累加 */
	private Integer failCount;
	/** 锁定次数 */
	private Integer lockCount;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userExpireTime
	 */
	public Long getUserExpireTime() {
		return userExpireTime;
	}

	/**
	 * @param userExpireTime the userExpireTime to set
	 */
	public void setUserExpireTime(Long userExpireTime) {
		this.userExpireTime = userExpireTime;
	}

	/**
	 * @return the failCount
	 */
	public Integer getFailCount() {
		return failCount;
	}

	/**
	 * @param failCount the failCount to set
	 */
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	/**
	 * @return the lockCount
	 */
	public Integer getLockCount() {
		return lockCount;
	}

	/**
	 * @param lockCount the lockCount to set
	 */
	public void setLockCount(Integer lockCount) {
		this.lockCount = lockCount;
	}

}
