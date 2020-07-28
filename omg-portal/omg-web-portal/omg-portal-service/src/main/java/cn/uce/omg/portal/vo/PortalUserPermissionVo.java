package cn.uce.omg.portal.vo;

import java.util.Set;

import cn.uce.base.vo.BaseVo;


/**
 * 用户权限信息
 * @author raowb
 *
 */
public class PortalUserPermissionVo extends BaseVo {
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private final String loginName;
    private Set<String> urlSet;
    private Set<String> roles;
    private Set<String> permissionCodeSet;
    
    public PortalUserPermissionVo(String loginName) {
    	this.loginName = loginName;
	}
    

    public Set<String> getUrlSet() {
        return urlSet;
    }

    public void setUrlSet(Set<String> urlSet) {
        this.urlSet = urlSet;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getLoginName() {
        return loginName;
    }

    public Set<String> getPermissionCodeSet() {
		return permissionCodeSet;
	}


	public void setPermissionCodeSet(Set<String> permissionCodeSet) {
		this.permissionCodeSet = permissionCodeSet;
	}


	/**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return loginName;
    }
}