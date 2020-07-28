/** 
 * @项目名称: FSP
 * @文件名称: SystemInfo extends BaseEntity 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import cn.uce.base.entity.BaseEntity;

/**
 * SystemInfo extends BaseEntity  
 * @Description: SystemInfo extends BaseEntity  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class SystemInfo extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 系统名称. */
    private String systemName;

    /** 系统编码. */
    private String systemCode;

    /** 系统类型.B/S，C/S */
    private String systemType;

    /** 系统说明. */
    private String systemDeclare;

    /** 是否绑定机器码. */
    private Integer boundMachine;

    /** 是否启用. */
    private Integer enable;

    /** 是否强制密码强度.设置为true，表示修改密码时，强制密码强度为高 */
    private Boolean pwForceIntensity;

    /** 加密类型.MD5，RSA，SHA */
    private String encryptType;

    /** 密钥. */
    private String secretKey;

    /** 是否主动踢出.只允许在同一台电脑上面进行登录。不同地址不允许登录 */
    private String activeKickOut;

    /** WEB主页地址.主页web地址，当web相应没有相应跳转地址时，跳转到此地址 */
    private String webIndexUrl;

    /** 认证跳转地址.认证成功后，需要回写信息跳转地址 */
    private String casUrl;

    /** 超时时间.配置的默认超时时间，单位为秒，不配置默认30分钟 */
    private Integer timeOut;

    /** 备注. */
    private String remark;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private Integer createOrg;

    /** 修改人. */
    private String updateEmp;

    /** 修改机构. */
    private Integer updateOrg;

    /** 是否启用统一角色 */
    private Boolean unifyRoleFlag;
    
    /**
     * 是否校验员工绑定关系  --  崔仁军   2012-10-16
     */
    private Boolean relCheckFlag;
    /**
     * 是否校验员工绑定关系  --  崔仁军   2012-10-16
     */
	public Boolean getRelCheckFlag() {
		return relCheckFlag;
	}
	/**
     * 是否校验员工绑定关系  --  崔仁军   2012-10-16
     */
	public void setRelCheckFlag(Boolean relCheckFlag) {
		this.relCheckFlag = relCheckFlag;
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @return the systemType
	 */
	public String getSystemType() {
		return systemType;
	}

	/**
	 * @return the systemDeclare
	 */
	public String getSystemDeclare() {
		return systemDeclare;
	}

	/**
	 * @return the boundMachine
	 */
	public Integer getBoundMachine() {
		return boundMachine;
	}

	/**
	 * @return the enable
	 */
	public Integer getEnable() {
		return enable;
	}

	/**
	 * @return the pwForceIntensity
	 */
	public Boolean getPwForceIntensity() {
		return pwForceIntensity;
	}

	/**
	 * @return the encryptType
	 */
	public String getEncryptType() {
		return encryptType;
	}

	/**
	 * @return the secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * @return the activeKickOut
	 */
	public String getActiveKickOut() {
		return activeKickOut;
	}

	/**
	 * @return the webIndexUrl
	 */
	public String getWebIndexUrl() {
		return webIndexUrl;
	}

	/**
	 * @return the casUrl
	 */
	public String getCasUrl() {
		return casUrl;
	}

	/**
	 * @return the timeOut
	 */
	public Integer getTimeOut() {
		return timeOut;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @return the createEmp
	 */
	public String getCreateEmp() {
		return createEmp;
	}

	/**
	 * @return the createOrg
	 */
	public Integer getCreateOrg() {
		return createOrg;
	}

	/**
	 * @return the updateEmp
	 */
	public String getUpdateEmp() {
		return updateEmp;
	}

	/**
	 * @return the updateOrg
	 */
	public Integer getUpdateOrg() {
		return updateOrg;
	}

	/**
	 * @return the unifyRoleFlag
	 */
	public Boolean getUnifyRoleFlag() {
		return unifyRoleFlag;
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * @param systemType the systemType to set
	 */
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	/**
	 * @param systemDeclare the systemDeclare to set
	 */
	public void setSystemDeclare(String systemDeclare) {
		this.systemDeclare = systemDeclare;
	}

	/**
	 * @param boundMachine the boundMachine to set
	 */
	public void setBoundMachine(Integer boundMachine) {
		this.boundMachine = boundMachine;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	/**
	 * @param pwForceIntensity the pwForceIntensity to set
	 */
	public void setPwForceIntensity(Boolean pwForceIntensity) {
		this.pwForceIntensity = pwForceIntensity;
	}

	/**
	 * @param encryptType the encryptType to set
	 */
	public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}

	/**
	 * @param secretKey the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * @param activeKickOut the activeKickOut to set
	 */
	public void setActiveKickOut(String activeKickOut) {
		this.activeKickOut = activeKickOut;
	}

	/**
	 * @param webIndexUrl the webIndexUrl to set
	 */
	public void setWebIndexUrl(String webIndexUrl) {
		this.webIndexUrl = webIndexUrl;
	}

	/**
	 * @param casUrl the casUrl to set
	 */
	public void setCasUrl(String casUrl) {
		this.casUrl = casUrl;
	}

	/**
	 * @param timeOut the timeOut to set
	 */
	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @param createEmp the createEmp to set
	 */
	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	/**
	 * @param createOrg the createOrg to set
	 */
	public void setCreateOrg(Integer createOrg) {
		this.createOrg = createOrg;
	}

	/**
	 * @param updateEmp the updateEmp to set
	 */
	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}

	/**
	 * @param updateOrg the updateOrg to set
	 */
	public void setUpdateOrg(Integer updateOrg) {
		this.updateOrg = updateOrg;
	}

	/**
	 * @param unifyRoleFlag the unifyRoleFlag to set
	 */
	public void setUnifyRoleFlag(Boolean unifyRoleFlag) {
		this.unifyRoleFlag = unifyRoleFlag;
	}
}
