package cn.uce.omg.main.sso.vo;

import java.util.Date;

import cn.uce.omg.sso.vo.RedisHashFieldExpireVo;

/**
 * 
  * <p>Title : ImageCodeVo</p>
  * <p>Description : 图片验证码类</p>
  * @author : crj
  * @date : 2017年10月27日上午9:44:03
 */
public class ImageCodeVo extends RedisHashFieldExpireVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 类型，登录时的验证码code,  找回密码或设置密码   phone_code
	 */
	private String codeType;
	/**
	 * 图片验证码
	 */
	private String code;
	/**
	 * 用户名。如果是登录时的图片验证码存的是员工工号， 如果是找回密码或设置密码是的图片验证码存放手机号。
	 */
	private String userName;
	/** 验证码发送间隔时间 */
	private Date sendTime;
	/**
	 * 类型，登录时的验证码code,  找回密码或设置密码   phone_code
	 */
	public String getCodeType() {
		return codeType;
	}
	/**
	 * 类型，登录时的验证码code,  找回密码或设置密码   phone_code
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	/**
	 * 图片验证码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 图片验证码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 用户名。如果是登录时的图片验证码存的是员工工号， 如果是找回密码或设置密码是的图片验证码存放手机号。
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 用户名。如果是登录时的图片验证码存的是员工工号， 如果是找回密码或设置密码是的图片验证码存放手机号。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/** 验证码发送间隔时间 */
	public Date getSendTime() {
		return sendTime;
	}
	/** 验证码发送间隔时间 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
}
