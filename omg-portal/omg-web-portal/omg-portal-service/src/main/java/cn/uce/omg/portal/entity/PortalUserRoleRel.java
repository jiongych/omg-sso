package cn.uce.omg.portal.entity;

import java.util.Date;

import cn.uce.base.entity.BaseEntity;
import cn.uce.core.db.annotion.Table;

@Table("fsp_user_role_rel")
public class PortalUserRoleRel extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private Long Id;
    
    /** 员工编号. */
    private String empCode;

    /** 角色编码. */
    private String roleCode;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private Integer createOrg;
    
    /** 创建时间. */
    private Date createTime;
    
    /** 版本. */
    private Integer version;
    
    /** 更新时间. */
    private Date updateTime;
    


    /** 取得 员工编号. */
    public String getEmpCode() {
        return this.empCode;
    }

    /** 设置 员工编号. */
    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }
    /** 取得 角色编码. */
    public String getRoleCode() {
        return this.roleCode;
    }

    /** 设置 角色编码. */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
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

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
