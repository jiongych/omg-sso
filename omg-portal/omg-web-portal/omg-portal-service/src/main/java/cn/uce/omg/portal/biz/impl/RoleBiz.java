package cn.uce.omg.portal.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.IRoleBiz;
import cn.uce.omg.portal.entity.Role;
import cn.uce.omg.portal.exception.RoleException;
import cn.uce.omg.portal.service.OrgService;
import cn.uce.omg.portal.service.PermissionService;
import cn.uce.omg.portal.service.RoleService;
import cn.uce.omg.portal.service.UserService;
import cn.uce.omg.portal.util.CommonUtil;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.omg.portal.vo.RoleVo;
import cn.uce.omg.portal.vo.UserVo;
import cn.uce.utils.StringUtil;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.util.ObjectConvertUtil;
import cn.uce.web.common.util.WebUtil;

/**
 * @Description:(RoleBiz) 
 * @author XJ
 * @date 2017年7月29日 下午4:09:47
 */
@Service("fspRoleBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class RoleBiz implements IRoleBiz {
	
	private final Log log = LogFactory.getLog(this.getClass());
	@Resource
	private RoleService roleService;
	@Resource
	private UserService fspUserService;
	@Resource
	private OrgService fspOrgService;
	@Resource
	private PermissionService permissionService;
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findRoleByPage</p> 
	* <p>Description: 分页查询角色信息</p> 
	* @param roleVo
	* @param page
	* @return 
	* @see cn.uce.web.authorg.biz.IRoleBiz#findRoleByPage(cn.uce.RoleVo.authorg.vo.RoleVo, cn.uce.base.page.Page)
	 */
	@Override
	public Pagination<RoleVo> findRoleByPage(RoleVo roleVo, Integer currentPage, Integer pageSize) {
		//获取当前用户
		CurrentUser useInfo = WebUtil.getCurrentUser();
		//判断当前用户是否是超级管理员
		boolean flag = CommonUtil.isSuperAdmin(useInfo.getEmpCode());
		//不是超级管理员，组装查询条件查询
		if(!flag){
			//roleVo.setRoleLevel(useInfo.getCmsOrgType());
			roleVo.setContainRoleLevel(useInfo.getCmsOrgType());
			roleVo.setRoleScope(useInfo.getCmsBaseOrgCode());
		}
		Integer startIndex = (currentPage - 1)*pageSize;
		
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		page.setTotal(roleService.findRoleCountByPage(roleVo, startIndex, pageSize));
		
		Pagination<RoleVo> pagination = new Pagination<RoleVo>();
		pagination.setPage(page);
		pagination.setData(roleService.findRoleByPage(roleVo, startIndex, pageSize));
		return pagination;
	}
	/**
	 * (非 Javadoc) 
	* <p>Title: addRole</p> 
	* <p>Description: 新增角色</p> 
	* @param roleVo
	* @return 
	* @see cn.uce.web.authorg.biz.IRoleBiz#addRole(cn.uce.RoleVo.authorg.vo.RoleVo)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int addRole(RoleVo roleVo) {
		if (roleVo == null || StringUtil.isBlank(roleVo.getRoleCode()) || StringUtil.isBlank(roleVo.getRoleName())) {
			throw new RoleException("error.biz.role.illegalparam");
		}
		RoleVo dbRoleVo = roleService.findRoleByCode(roleVo.getRoleCode());
		if (dbRoleVo != null) {
			throw new RoleException("error.biz.role.rolecodeexists");
		}
		buildParentPermission(roleVo.getPermissionIdList(),roleVo);
		removeDuplicatePermission(roleVo);
		return roleService.insert(roleVo);
	}
	
	/**
	 * 去除重复权限
	 * @param roleVo
	 * @author huangting
	 * @date 2018年8月27日 下午2:49:28
	 */
	public void removeDuplicatePermission(RoleVo roleVo) {
		Set<Long> set = new HashSet<Long>();
		if (roleVo.getPermissionIdList() != null && roleVo.getPermissionIdList().size() > 0) {
			/** 权限码 */
			List<String> permissionCodeList = new ArrayList<String>();
		    /** 权限码对应的系统编码*/
		    List<String> systemCodeList = new ArrayList<String>();
			for (int i = 0; i < roleVo.getPermissionIdList().size(); i++) {
				if (!set.contains(roleVo.getPermissionIdList().get(i))) {
					set.add(roleVo.getPermissionIdList().get(i));
					permissionCodeList.add(roleVo.getPermissionCodeList().get(i));
					systemCodeList.add(roleVo.getSystemCodeList().get(i));
				}
			}
			roleVo.setPermissionCodeList(permissionCodeList);
			roleVo.setSystemCodeList(systemCodeList);
		}
	}
	
	/**
	 * 查询三级菜单原父级
	 * @param ids
	 * @param roleVo
	 * @author huangting
	 * @date 2018年8月27日 下午2:48:12
	 */
	private void buildParentPermission(List<Long> ids,RoleVo roleVo) {
		if (ids == null || ids.size() == 0) {
			return;
		}
		List<PermissionVo> list = permissionService.findParentPermissionById(ids);
		List<Long> parentIds = new ArrayList<Long>();
		if (list != null && list.size() > 0) {
			for(PermissionVo permission : list) {
				if (permission.getParentPermission() != null) {
					parentIds.add(permission.getParentPermission());
				}
				roleVo.getSystemCodeList().add(permission.getSystemCode());
				roleVo.getPermissionCodeList().add(permission.getPermissionCode());
				roleVo.getPermissionIdList().add(permission.getId());
			}
			if (parentIds.size() > 0) {
				buildParentPermission(parentIds,roleVo);
			}
		}
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: deleteRole</p> 
	* <p>Description: 删除角色</p> 
	* @param roleCode
	* @return 
	* @see cn.uce.web.authorg.biz.IRoleBiz#deleteRole(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int deleteRole(String roleCode) {
		int count = fspUserService.findCountByRoleCode(roleCode);
		if (count > 0) {
			throw new RoleException("error.biz.role.roleusedbyuser");
		}
		try {
			/**  对接日志系统，物理删除前先更新删除人员 by:jiyongchao 20190814*/
			RoleVo roleVo = roleService.findRoleByCode(roleCode);
			roleVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
			roleVo.setUpdateOrg(WebUtil.getCurrentUser().getOrgCode());
			roleVo.setUpdateTime(new Date());
			Role role = ObjectConvertUtil.convertObject(roleVo, Role.class);
			roleService.updateById(role);
		} catch (Exception e) {
			log.error("向日志中心发送消息失败，可以忽略" + e.getMessage());
		}
		
		return roleService.deleteByRoleCode(roleCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: updateRoleByRoleCode</p> 
	* <p>Description: 根据角色码更新角色</p> 
	* @param roleVo
	* @return 
	* @see cn.uce.web.authorg.biz.IRoleBiz#updateRoleByRoleCode(cn.uce.RoleVo.authorg.vo.RoleVo)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int updateRoleByRoleCode(RoleVo roleVo) {
		buildParentPermission(roleVo.getPermissionIdList(),roleVo);
		removeDuplicatePermission(roleVo);
		int i = roleService.updateRoleByRoleCode(roleVo);
		//同步使用
		Role role = roleService.findById(roleVo.getId());
		roleVo.setCreateEmp(role.getCreateEmp());
		roleVo.setCreateOrg(role.getCreateOrg());
		roleVo.setCreateTime(role.getCreateTime());
		roleVo.setRoleScope(role.getRoleScope());
		return i;
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findByRoleCode</p> 
	* <p>Description:根据角色码查询角色 </p> 
	* @param roleCode
	* @return 
	* @see cn.uce.web.authorg.biz.IRoleBiz#findByRoleCode(java.lang.String)
	 */
	@Override
	public RoleVo findRoleByCode(String roleCode) {
		return roleService.findRoleByCode(roleCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findRoleByUser</p> 
	* <p>Description: 查询用户已分配的角色</p> 
	* @param empCode
	* @return 
	* @see cn.uce.web.authorg.biz.IRoleBiz#findRoleByUser(java.lang.String)
	 */
	@Override
	public List<RoleVo> findRoleByUser(String empCode) {
		return roleService.findRoleByUser(empCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findNotRoleByUser</p> 
	* <p>Description: 查询用户未分配的角色</p> 
	* @param userVo
	* @return 
	* @see cn.uce.web.authorg.biz.IRoleBiz#findNotRoleByUser(cn.uce.UserVo.authorg.vo.UserVo)
	 */
	@Override
	public List<RoleVo> findNotRoleByUser(String empCode,String baseOrgCode,Integer roleLevel, Integer cmsOrgType) {
		return roleService.findNotRoleByUser(empCode, baseOrgCode, roleLevel, cmsOrgType);
	}
	
	/**
	 * @Description: (查询使用此角色的用户) 
	 * @param userVo
	 * @return
	 * @author liyi
	 * @date 2018年04月19日 上午9:47:52
	 */
	@Override
	public Pagination<UserVo> findUserByRole(String roleCode, Page p) {
        Integer startIndex = (p.getCurrentPage() - 1) * p.getPageSize();
		p.setTotal(roleService.findUserCountByRole(roleCode, startIndex, p.getPageSize()));
		
		Pagination<UserVo> pagination = new Pagination<UserVo>();
		pagination.setPage(p);
		pagination.setData(roleService.findUserByRole(roleCode, startIndex, p.getPageSize()));
		return pagination;
	}
	
	/**
	 * @Description: (查询时间范围内变更的角色) 
	 * @param userVo
	 * @return
	 * @author liyi
	 * @date 2018年04月19日 上午9:47:52
	 */
	@Override
	public List<String> findRoleByDate(PortalDataPushVo dataPushVo) {
		return roleService.findRoleByDate(dataPushVo);
	}
	/**
	 * 同步重推使用
	 */
	@Override
	public RoleVo findRoleSyncByCode(String roleCode) {
		
		return roleService.findRoleSyncByCode(roleCode);
	}
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据员工工号查询信息
	 * </pre>
	 * @param empCode
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月23日下午4:59:04
	 */
	@Override
	public List<String> findRoleCodeByUser(String empCode) {
		return roleService.findRoleCodeByUser(empCode);
	}
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据角色编号查询用户
	 * </pre>
	 * @param roleCode
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月23日下午5:30:30
	 */
	@Override
	public List<String> findUserListByRole(String roleCode) {
		return roleService.findUserListByRole(roleCode);
	}
}
