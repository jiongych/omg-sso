package cn.uce.omg.portal.entity;

import java.util.Date;

import cn.uce.base.entity.BaseEntity;
import cn.uce.core.db.annotion.Table;

@Table("omg_user")
public class PortalUser extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 员工ID. */
    private Integer empId;

    /** 员工编号.员工工号 */
    private String empCode;

    /** 最后登录时间. */
    private Date lastLoginTime;

    /** 是否启用.0/1 默认为0 */
    private Boolean enabled;

    /** 机构组范围.管理多个组织机构，用英文逗号分开。 */
    private String roleOrgGroup;

    /** 备注. */
    private String remark;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private Integer createOrg;

    /** 更新机构. */
    private Integer updateOrg;

    /** 更新人. */
    private String updateEmp;



    /** 取得 员工ID. */
    public Integer getEmpId() {
        return this.empId;
    }

    /** 设置 员工ID. */
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
    /** 取得 员工编号.员工工号 */
    public String getEmpCode() {
        return this.empCode;
    }

    /** 设置 员工编号.员工工号 */
    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }
    
    /** 取得 最后登录时间. */
    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    /** 设置 最后登录时间. */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    /** 取得 是否启用.0/1 默认为0 */
    public Boolean getEnabled() {
        return this.enabled;
    }

    /** 设置 是否启用.0/1 默认为0 */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    /** 取得 机构组范围.管理多个组织机构，用英文逗号分开。 */
    public String getRoleOrgGroup() {
        return this.roleOrgGroup;
    }

    /** 设置 机构组范围.管理多个组织机构，用英文逗号分开。 */
    public void setRoleOrgGroup(String roleOrgGroup) {
        this.roleOrgGroup = roleOrgGroup;
    }
    /** 取得 备注. */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注. */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /** 取得 创建人. */
    public String getCreateEmp() {
        return this.createEmp;
    }

    /** 设置 创建人. */
    public void setCreateEmp(String createEmp) {
        this.createEmp = createEmp;
    }
    /** 取得 创建机构. */
    public Integer getCreateOrg() {
        return this.createOrg;
    }

    /** 设置 创建机构. */
    public void setCreateOrg(Integer createOrg) {
        this.createOrg = createOrg;
    }
    /** 取得 更新机构. */
    public Integer getUpdateOrg() {
        return this.updateOrg;
    }

    /** 设置 更新机构. */
    public void setUpdateOrg(Integer updateOrg) {
        this.updateOrg = updateOrg;
    }
    /** 取得 更新人. */
    public String getUpdateEmp() {
        return this.updateEmp;
    }

    /** 设置 更新人. */
    public void setUpdateEmp(String updateEmp) {
        this.updateEmp = updateEmp;
    }



}
