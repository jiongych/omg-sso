package cn.uce.omg.portal.entity;

import java.util.Date;

import cn.uce.base.entity.BaseEntity;
import cn.uce.core.db.annotion.Table;

@Table("omg_portal_menu")
public class PortalMenu extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 权限编码. */
    private String permissionCode;

    /** 系统编码. */
    private String systemCode;

    /** 权限别名. */
    private String permissionAliasName;

    /** 父级权限.顶级菜单为NULL. */
    private Long parentPermission;

    /** 排序. */
    private Integer sort;

    /** 备注. */
    private String remark;

    /** 版本. */
    private Integer version;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private Integer createOrg;

    /** 创建时间. */
    private Date createTime;

    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private Integer updateOrg;

    /** 更新时间. */
    private Date updateTime;

    /** 取得 权限编码. */
    public String getPermissionCode() {
        return this.permissionCode;
    }

    /** 设置 权限编码. */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
    /** 取得 系统编码. */
    public String getSystemCode() {
        return this.systemCode;
    }

    /** 设置 系统编码. */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
    /** 取得 权限别名. */
    public String getPermissionAliasName() {
        return this.permissionAliasName;
    }

    /** 设置 权限别名. */
    public void setPermissionAliasName(String permissionAliasName) {
        this.permissionAliasName = permissionAliasName;
    }
    /** 取得 父级权限.顶级菜单为NULL. */
    public Long getParentPermission() {
        return this.parentPermission;
    }

    /** 设置 父级权限.顶级菜单为NULL. */
    public void setParentPermission(Long parentPermission) {
        this.parentPermission = parentPermission;
    }
    /** 取得 排序. */
    public Integer getSort() {
        return this.sort;
    }

    /** 设置 排序. */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    /** 取得 备注. */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注. */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /** 取得 版本. */
    public Integer getVersion() {
        return this.version;
    }

    /** 设置 版本. */
    public void setVersion(Integer version) {
        this.version = version;
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
    /** 取得 创建时间. */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间. */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
    /** 取得 更新时间. */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /** 设置 更新时间. */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }



}
