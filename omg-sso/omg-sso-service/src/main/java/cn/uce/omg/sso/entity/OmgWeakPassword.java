package cn.uce.omg.sso.entity;

import java.util.Date;

import cn.uce.base.entity.BaseEntity;

public class OmgWeakPassword  extends BaseEntity{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
    /** 
	 * 主键ID 
	 */
	private Long id;
    /** 
	 * 弱的明文密码 
	 */
	private String weakPlaintextPassword;
    /** 
	 * 弱的加密密码 
	 */
	private String weakEncryptPassword;
    /** 
	 * 创建时间 
	 */
	private Date createTime;
	
	
	
	/**
	 * 获取 主键ID
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * 设置 主键ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	
	/**
	 * 获取 弱的明文密码
	 */
	public String getWeakPlaintextPassword() {
		return weakPlaintextPassword;
	}
	
	/**
	 * 设置 弱的明文密码
	 */
	public void setWeakPlaintextPassword(String weakPlaintextPassword) {
		this.weakPlaintextPassword = weakPlaintextPassword;
	}

	
	/**
	 * 获取 弱的加密密码
	 */
	public String getWeakEncryptPassword() {
		return weakEncryptPassword;
	}
	
	/**
	 * 设置 弱的加密密码
	 */
	public void setWeakEncryptPassword(String weakEncryptPassword) {
		this.weakEncryptPassword = weakEncryptPassword;
	}

	
	/**
	 * 获取 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
