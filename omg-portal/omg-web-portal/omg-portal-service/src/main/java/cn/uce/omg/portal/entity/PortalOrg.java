package cn.uce.omg.portal.entity;

import cn.uce.base.entity.BaseEntity;

public class PortalOrg extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 机构ID. */
    private Integer orgId;

    /** 钉钉ID.临时字段，方便二期上线期间mysql实时同步到oracle，成功上线后，废除该字段 */
    private Integer tempDingOrgId;

    /** 机构编号.机构编号唯一 */
    private String orgCode;

    /** 机构名称.同一机构下,机构名称唯一 */
    private String orgName;

    /** 机构全称.上级的机构全称加上本级机构名称 */
    private String orgFullName;

    /** 隶属机构全称.如果是部门，由上级机构的隶属机构全称加上本级机构名称，如果是机构，则是自身的机构名称 */
    private String affiliatedOrgName;

    /** 网点名称全拼. */
    private String namePy;

    /** 网点名称拼音缩写. */
    private String nameSpy;

    /** 机构排序. */
    private Integer orgSort;

    /** 机构类型.总部-HQ,财务中心-FC,操作中心-OC,网点-SITE,承包区-CONTRACT */
    private Integer orgType;

    /** 部门标识.0表示机构.,1表示部门 */
    private Boolean deptFlag;

    /** 标签. */
    private Integer orgLabel;

    /** 上一级机构ID.当前机构或者部门的上一级 */
    private Integer parentOrg;

    /** 隶属机构ID.如果是部门,则是该部门的机构ID,如果是机构,则是自身的ID */
    private Integer affiliatedOrg;

    /** 负责人. */
    private Integer principal;

    /** 分管总裁.主要是审批用 */
    private Integer chargePresident;

    /** 状态.新建-new */
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



    /** 取得 机构ID. */
    public Integer getOrgId() {
        return this.orgId;
    }

    /** 设置 机构ID. */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
    /** 取得 钉钉ID.临时字段，方便二期上线期间mysql实时同步到oracle，成功上线后，废除该字段 */
    public Integer getTempDingOrgId() {
        return this.tempDingOrgId;
    }

    /** 设置 钉钉ID.临时字段，方便二期上线期间mysql实时同步到oracle，成功上线后，废除该字段 */
    public void setTempDingOrgId(Integer tempDingOrgId) {
        this.tempDingOrgId = tempDingOrgId;
    }
    /** 取得 机构编号.机构编号唯一 */
    public String getOrgCode() {
        return this.orgCode;
    }

    /** 设置 机构编号.机构编号唯一 */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    /** 取得 机构名称.同一机构下,机构名称唯一 */
    public String getOrgName() {
        return this.orgName;
    }

    /** 设置 机构名称.同一机构下,机构名称唯一 */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    /** 取得 机构全称.上级的机构全称加上本级机构名称 */
    public String getOrgFullName() {
        return this.orgFullName;
    }

    /** 设置 机构全称.上级的机构全称加上本级机构名称 */
    public void setOrgFullName(String orgFullName) {
        this.orgFullName = orgFullName;
    }
    /** 取得 隶属机构全称.如果是部门，由上级机构的隶属机构全称加上本级机构名称，如果是机构，则是自身的机构名称 */
    public String getAffiliatedOrgName() {
        return this.affiliatedOrgName;
    }

    /** 设置 隶属机构全称.如果是部门，由上级机构的隶属机构全称加上本级机构名称，如果是机构，则是自身的机构名称 */
    public void setAffiliatedOrgName(String affiliatedOrgName) {
        this.affiliatedOrgName = affiliatedOrgName;
    }
    /** 取得 网点名称全拼. */
    public String getNamePy() {
        return this.namePy;
    }

    /** 设置 网点名称全拼. */
    public void setNamePy(String namePy) {
        this.namePy = namePy;
    }
    /** 取得 网点名称拼音缩写. */
    public String getNameSpy() {
        return this.nameSpy;
    }

    /** 设置 网点名称拼音缩写. */
    public void setNameSpy(String nameSpy) {
        this.nameSpy = nameSpy;
    }
    /** 取得 机构排序. */
    public Integer getOrgSort() {
        return this.orgSort;
    }

    /** 设置 机构排序. */
    public void setOrgSort(Integer orgSort) {
        this.orgSort = orgSort;
    }
    /** 取得 机构类型.总部-HQ,财务中心-FC,操作中心-OC,网点-SITE,承包区-CONTRACT */
    public Integer getOrgType() {
        return this.orgType;
    }

    /** 设置 机构类型.总部-HQ,财务中心-FC,操作中心-OC,网点-SITE,承包区-CONTRACT */
    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }
    /** 取得 部门标识.0表示机构.,1表示部门 */
    public Boolean getDeptFlag() {
        return this.deptFlag;
    }

    /** 设置 部门标识.0表示机构.,1表示部门 */
    public void setDeptFlag(Boolean deptFlag) {
        this.deptFlag = deptFlag;
    }
    /** 取得 标签. */
    public Integer getOrgLabel() {
        return this.orgLabel;
    }

    /** 设置 标签. */
    public void setOrgLabel(Integer orgLabel) {
        this.orgLabel = orgLabel;
    }
    /** 取得 上一级机构ID.当前机构或者部门的上一级 */
    public Integer getParentOrg() {
        return this.parentOrg;
    }

    /** 设置 上一级机构ID.当前机构或者部门的上一级 */
    public void setParentOrg(Integer parentOrg) {
        this.parentOrg = parentOrg;
    }
    /** 取得 隶属机构ID.如果是部门,则是该部门的机构ID,如果是机构,则是自身的ID */
    public Integer getAffiliatedOrg() {
        return this.affiliatedOrg;
    }

    /** 设置 隶属机构ID.如果是部门,则是该部门的机构ID,如果是机构,则是自身的ID */
    public void setAffiliatedOrg(Integer affiliatedOrg) {
        this.affiliatedOrg = affiliatedOrg;
    }
    /** 取得 负责人. */
    public Integer getPrincipal() {
        return this.principal;
    }

    /** 设置 负责人. */
    public void setPrincipal(Integer principal) {
        this.principal = principal;
    }
    /** 取得 分管总裁.主要是审批用 */
    public Integer getChargePresident() {
        return this.chargePresident;
    }

    /** 设置 分管总裁.主要是审批用 */
    public void setChargePresident(Integer chargePresident) {
        this.chargePresident = chargePresident;
    }
    /** 取得 状态.新建-new */
    public Integer getStatus() {
        return this.status;
    }

    /** 设置 状态.新建-new */
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
