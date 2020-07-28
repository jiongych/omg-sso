package cn.uce.omg.portal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.omg.portal.dao.IRoleDao;
import cn.uce.omg.portal.dao.IRolePermissionRelDao;
import cn.uce.omg.portal.dao.IUserRoleRelDao;
import cn.uce.omg.portal.entity.Role;
import cn.uce.omg.portal.entity.RolePermissionRel;
import cn.uce.omg.portal.exception.RoleException;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.omg.portal.vo.PortalRolePermissionRelVo;
import cn.uce.omg.portal.vo.RoleVo;
import cn.uce.omg.portal.vo.UserVo;
import cn.uce.utils.StringUtil;
import cn.uce.web.common.util.ObjectConvertUtil;

/**
 * @Description: (RoleService) 
 * @author XJ
 * @date 2017年7月29日 下午4:13:20
 */
@Service("roleService")
public class RoleService {
	@Resource
	private IRoleDao roleDao;
	
	@Resource
	private IUserRoleRelDao fspUserRoleRelDao;
	
	@Resource
	private IRolePermissionRelDao fspRolePermissionRelDao;
	
	/**
	 * @Description:(走DB分页查询角色) 
	 * @param roleVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年5月11日 上午9:44:38
	 */
	public List<RoleVo> findRoleByPage(RoleVo roleVo,Integer startIndex,Integer pageSize){
		return roleDao.findRoleByPage(roleVo, startIndex, pageSize);
	}
	public Integer findRoleCountByPage(RoleVo roleVo,Integer startIndex,Integer pageSize){
		return roleDao.findRoleCountByPage(roleVo, startIndex, pageSize);
	}
	
	/**
	 * @Description: (根据角色码查询角色) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:15:11
	 */
	public RoleVo findRoleByCode(String roleCode) {
		return roleDao.findRoleByCode(roleCode);
	}
	
	/**
	 * @Description: (增加角色) 
	 * @param roleVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:15:59
	 */
	public int addRole(RoleVo roleVo) {
		Role role = ObjectConvertUtil.convertObject(roleVo, Role.class);
		List<RolePermissionRel> rprelList = convertPermissionCodeToRel(roleVo);
		if (rprelList != null && rprelList.size() > 0) {
			fspRolePermissionRelDao.insert(rprelList);
		}
		return roleDao.insert(role);
	}
	
	/**
	 * @Description: (转换RoleVo为RolePermissionRel) 
	 * @param roleVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:16:12
	 */
	private List<RolePermissionRel> convertPermissionCodeToRel(RoleVo roleVo) {
		List<String> permissionCodeList = roleVo.getPermissionCodeList();
		List<String> systemCodeList = roleVo.getSystemCodeList();
		List<RolePermissionRel> rprelList = new ArrayList<RolePermissionRel>();
		if (permissionCodeList != null && permissionCodeList.size() > 0) {
			for (int i = 0; i < permissionCodeList.size(); i++) {
				RolePermissionRel rprel = new RolePermissionRel();
				rprel.setCreateEmp(StringUtil.isBlank(roleVo.getCreateEmp()) ? roleVo.getUpdateEmp() : roleVo.getCreateEmp());
				rprel.setCreateOrg(roleVo.getCreateOrg() == null ? roleVo.getUpdateOrg() : roleVo.getCreateOrg());
				rprel.setCreateTime(new Date());
				rprel.setUpdateTime(new Date());
				rprel.setUpdateEmp(rprel.getCreateEmp());
				rprel.setPermissionCode(permissionCodeList.get(i));
				rprel.setSystemCode(systemCodeList.get(i));
				rprel.setRoleCode(roleVo.getRoleCode());
				rprel.setRoleLevel(roleVo.getRoleLevel());
				rprelList.add(rprel);
			}
		}
		return rprelList;
	}
	
	/**
	 * @Description: (根据角色码删除角色) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:19:10
	 */
	public int deleteRole(String roleCode) {
		int count = fspUserRoleRelDao.findCountByRoleCode(roleCode);
		if (count > 0) {
			throw new RoleException("error.biz.role.roleusedbyuser");
		}
		fspRolePermissionRelDao.deleteByRoleCode(roleCode);
		return roleDao.deleteByRoleCode(roleCode);
	}

	/**
	 * @Description: (查询用户已分配的角色) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:19:37
	 */
	public List<RoleVo> findRoleByUser(String empCode) {
		return roleDao.findRoleByUser(empCode);
	}
	
	/**
	 * 
	 * @Description: (查询当前用户可分配但还未分配的角色) 
	 * @param roleVo
	 * @return
	 * @author XJ
	 * @date 2017年6月22日 下午2:57:45
	 */
	public List<RoleVo> findNotRoleByUser(String empCode,String baseOrgCode,Integer roleLevel, Integer cmsOrgType) {
		return roleDao.findNotRoleByUser(empCode, baseOrgCode, roleLevel, cmsOrgType);
	}
	
	/**
	 * @Description: (根据角色码删除角色) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:20:34
	 */
	public int deleteByRoleCode(String roleCode) {
		fspRolePermissionRelDao.deleteByRoleCode(roleCode);
		return roleDao.deleteByRoleCode(roleCode);
	}
	
	/**
	 * @Description: (新增角色) 
	 * @param roleVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:20:53
	 */
	public int insert(RoleVo roleVo) {
		Role role = ObjectConvertUtil.convertObject(roleVo, Role.class);
		List<RolePermissionRel> rprelList = convertPermissionCodeToRel(roleVo);
		if (rprelList != null && rprelList.size() > 0) {
			fspRolePermissionRelDao.insert(rprelList);
		}
		return roleDao.insert(role);
	}
	
	/**
	 * @Description: (根据角色码更新角色) 
	 * @param roleVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:21:39
	 */
	public int updateRoleByRoleCode(RoleVo roleVo){
		roleVo.setRoleCode(roleVo.getRoleCode());
		List<RolePermissionRel> rprelList = convertPermissionCodeToRel(roleVo);
		fspRolePermissionRelDao.deleteByRoleCode(roleVo.getRoleCode());
		int i=0;
		if (rprelList != null && rprelList.size() > 0) {
			i= i+fspRolePermissionRelDao.insert(rprelList);
		}
		roleDao.updateRoleByCode(roleVo);
		return i;
	}
	
	public int updateById(Role role) {
		return roleDao.updateById(role);
	}
	/**
	 * @Description: (插入角色权限关系) 
	 * @param rprelList
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 上午11:00:49
	 */
	public int insert(List<RolePermissionRel> rprelList) {
		if (rprelList != null && rprelList.size() > 0) {
			return fspRolePermissionRelDao.insert(rprelList);
		}
		return 0;
	}
	
	/**
	 * @Description: (查询使用此角色的用户) 
	 * @param userVo
	 * @return
	 * @author liyi
	 * @param i 
	 * @param startIndex 
	 * @date 2018年04月19日 上午9:47:52
	 */
	public List<UserVo> findUserByRole(String roleCode, Integer startIndex, Integer pageSize) {
		return fspUserRoleRelDao.findUserByRole(roleCode,startIndex,pageSize);
	}
	
	public Integer findUserCountByRole(String roleCode, Integer startIndex,
			int pageSize) {
		return fspUserRoleRelDao.findUserCountByRole(roleCode,startIndex,pageSize);
	}
	
	/**
	 * @Description: (查询时间范围内变更的角色) 
	 * @param userVo
	 * @return
	 * @author liyi
	 * @date 2018年04月19日 上午9:47:52
	 */
	public List<String> findRoleByDate(PortalDataPushVo dataPushVo) {
		return roleDao.findRoleByDate(dataPushVo);
	}
	
	/**
	 * findByID
	 * @param id
	 * @return
	 */
	public Role findById(Long id) {
		return roleDao.findById(id);
	}
	
	/**
	 * 同步重推使用
	 * @param roleCode
	 * @return
	 */
	public RoleVo findRoleSyncByCode(String roleCode) {
		return roleDao.findRoleSyncByCode(roleCode);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据员工工号查询信息
	 * </pre>
	 * @param empCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月23日下午4:55:21
	 */
	public List<String> findRoleCodeByUser(String empCode) {
		return fspUserRoleRelDao.findRoleByUser(empCode);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据角色编号查询用户
	 * </pre>
	 * @param roleCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月23日下午5:25:38
	 */
	public List<String> findUserListByRole(String roleCode) {
		return fspUserRoleRelDao.findUserListByRole(roleCode);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据角色编号查询权限编码、权限名称、系统编码
	 * </pre>
	 * @param roleCode
	 * @return
	 * @return List<PortalRolePermissionRelVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年8月13日下午5:53:44
	 */
	public List<PortalRolePermissionRelVo> findRolePermissionCodeByRoleCode(String roleCode) {
		return fspRolePermissionRelDao.findRolePermissionCodeByRoleCode(roleCode);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据角色编号查询权限编码集合
	 * </pre>
	 * @param roleCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年8月13日下午6:47:34
	 */
	public List<String> findRolePermissionSCodeByRoleCode(String roleCode) {
		return fspRolePermissionRelDao.findRolePermissionSCodeByRoleCode(roleCode);
	}
}
