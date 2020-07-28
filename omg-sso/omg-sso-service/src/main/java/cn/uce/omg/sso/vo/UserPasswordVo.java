package cn.uce.omg.sso.vo;

import java.util.Date;

import cn.uce.base.vo.BaseVo;

public class UserPasswordVo extends BaseVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * hr表中的主键id
     */
    private Long primevalId;
    /**
     * hr对应的人员工号
     */
    private String workNum;
    /**
     * 修改后的密码
     */
    private String passwordStr;
    /**
     * 密码修改时间
     */
    private Date updatePasswordTime;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改部门编码
     */
    private String orgCode;
    /**
     * 修改部门名称
     */
    private String orgName;
    /**
     * 企业编码
     */
    private String compCode;
    
	public Long getPrimevalId() {
		return primevalId;
	}

	public void setPrimevalId(Long primevalId) {
		this.primevalId = primevalId;
	}

	public String getPasswordStr() {
		return passwordStr;
	}

	public void setPasswordStr(String passwordStr) {
		this.passwordStr = passwordStr;
	}

	public Date getUpdatePasswordTime() {
		return updatePasswordTime;
	}

	public void setUpdatePasswordTime(Date updatePasswordTime) {
		this.updatePasswordTime = updatePasswordTime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getWorkNum() {
		return workNum;
	}

	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
}
