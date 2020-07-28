package cn.uce.omg.portal.entity;

import cn.uce.base.entity.BaseEntity;

public class PortalEmp extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 员工ID. */
    private Integer empId;

    /** 钉钉用户ID.临时字段，方便二期上线期间mysql实时同步到oracle，成功上线后，废除该字段 */
    private String tempDingUserId;

    /** 员工编号.同一机构员工编号唯一 */
    private String empCode;

    /** 员工姓名.同一机构员工姓名唯一 */
    private String empName;

    /** 组织ID. */
    private Integer orgId;

    /** 是否负责人.是否负责人. */
    private Integer principalFlag;

    /** 性别. */
    private Integer sex;

    /** 直接上级. */
    private Integer immediateSuperior;

    /** 职级. */
    private Integer positionLevel;

    /** 固定电话. */
    private String telephone;

    /** 手机号码. */
    private String mobile;

    /** 邮箱. */
    private String email;

    /** 是否启用.0/1 默认为0 */
    private Boolean enabled;

    /** 员工状态. */
    private Integer status;

    /** 备注. */
    private String remark;

    /** 删除标识.1表示已删除，默认为0 */
    private Boolean deleteFlag;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private Integer createOrg;

    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private Integer updateOrg;



    /** 取得 员工ID. */
    public Integer getEmpId() {
        return this.empId;
    }

    /** 设置 员工ID. */
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
    /** 取得 钉钉用户ID.临时字段，方便二期上线期间mysql实时同步到oracle，成功上线后，废除该字段 */
    public String getTempDingUserId() {
        return this.tempDingUserId;
    }

    /** 设置 钉钉用户ID.临时字段，方便二期上线期间mysql实时同步到oracle，成功上线后，废除该字段 */
    public void setTempDingUserId(String tempDingUserId) {
        this.tempDingUserId = tempDingUserId;
    }
    /** 取得 员工编号.同一机构员工编号唯一 */
    public String getEmpCode() {
        return this.empCode;
    }

    /** 设置 员工编号.同一机构员工编号唯一 */
    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }
    /** 取得 员工姓名.同一机构员工姓名唯一 */
    public String getEmpName() {
        return this.empName;
    }

    /** 设置 员工姓名.同一机构员工姓名唯一 */
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    /** 取得 组织ID. */
    public Integer getOrgId() {
        return this.orgId;
    }

    /** 设置 组织ID. */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
    /** 取得 是否负责人.是否负责人. */
    public Integer getPrincipalFlag() {
        return this.principalFlag;
    }

    /** 设置 是否负责人.是否负责人. */
    public void setPrincipalFlag(Integer principalFlag) {
        this.principalFlag = principalFlag;
    }
    /** 取得 性别. */
    public Integer getSex() {
        return this.sex;
    }

    /** 设置 性别. */
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    /** 取得 直接上级. */
    public Integer getImmediateSuperior() {
        return this.immediateSuperior;
    }

    /** 设置 直接上级. */
    public void setImmediateSuperior(Integer immediateSuperior) {
        this.immediateSuperior = immediateSuperior;
    }
    /** 取得 职级. */
    public Integer getPositionLevel() {
        return this.positionLevel;
    }

    /** 设置 职级. */
    public void setPositionLevel(Integer positionLevel) {
        this.positionLevel = positionLevel;
    }
    /** 取得 固定电话. */
    public String getTelephone() {
        return this.telephone;
    }

    /** 设置 固定电话. */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    /** 取得 手机号码. */
    public String getMobile() {
        return this.mobile;
    }

    /** 设置 手机号码. */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    /** 取得 邮箱. */
    public String getEmail() {
        return this.email;
    }

    /** 设置 邮箱. */
    public void setEmail(String email) {
        this.email = email;
    }
    /** 取得 是否启用.0/1 默认为0 */
    public Boolean getEnabled() {
        return this.enabled;
    }

    /** 设置 是否启用.0/1 默认为0 */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    /** 取得 员工状态. */
    public Integer getStatus() {
        return this.status;
    }

    /** 设置 员工状态. */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /** 取得 备注. */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注. */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /** 取得 删除标识.1表示已删除，默认为0 */
    public Boolean getDeleteFlag() {
        return this.deleteFlag;
    }

    /** 设置 删除标识.1表示已删除，默认为0 */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
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
    /** 取得 更新人. */
    public String getUpdateEmp() {
        return this.updateEmp;
    }

    /** 设置 更新人. */
    public void setUpdateEmp(String updateEmp) {
        this.updateEmp = updateEmp;
    }
    /** 取得 更新机构. */
    public Integer getUpdateOrg() {
        return this.updateOrg;
    }

    /** 设置 更新机构. */
    public void setUpdateOrg(Integer updateOrg) {
        this.updateOrg = updateOrg;
    }



}
