package cn.uce.omg.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.dao.IPermissionDao;
import cn.uce.omg.portal.dao.IRolePermissionRelDao;
import cn.uce.omg.portal.entity.PortalPermission;
import cn.uce.omg.portal.vo.PermissionTreeNewVo;
import cn.uce.omg.portal.vo.PermissionTreeVo;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.web.common.util.ObjectConvertUtil;

/**
 * @Description: (PermissionService) 
 * @author XJ
 * @date 2017年7月29日 上午9:19:31
 */
@Service("fspPermissionService")
public class PermissionService {
	
	@Resource
	private IPermissionDao permissionDao;
	@Resource
	private IRolePermissionRelDao rolePermissionRelDao;
	
	/**
	 * @Description: (插入) 
	 * @param permissionVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:19:49
	 */
	public int insert(PermissionVo permissionVo) {
		return permissionDao.insert(permissionVo);
	}
	
	/**
	 * @Description: (按ID更新) 
	 * @param permissionVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:20:03
	 */
	public int updateById(PermissionVo permissionVo) {
		PortalPermission permission = ObjectConvertUtil.convertObject(permissionVo, PortalPermission.class);
		return permissionDao.updateById(permission);
	}
	
	public int updateById(PortalPermission permission) {
		return permissionDao.updateById(permission);
	}
	
	/**
	 * @Description:(按ID更新权限级别) 
	 * @param permissionVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:20:36
	 */
	public int updateLevelsById(PermissionVo permissionVo) {
		return permissionDao.updateLevelsById(permissionVo);
	}
	
	/**
	 * @Description: (按ID删除) 
	 * @param id
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:20:57
	 */
	public int deleteById(Long id) {
		return permissionDao.deleteById(id);
	}
	
	/**
	 * @Description: (分页查询权限) 
	 * @param permissionVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:21:26
	 */
	public Pagination<PermissionVo> findByCondition(
			PermissionVo permissionVo, Page page) {
		return permissionDao.findByCondition(permissionVo, page);
	}
	
	/**
	 * 
	 * @Description: (查询权限树) 
	 * @param permissionVo
	 * @return
	 * @author XJ
	 * @date 2017年6月10日 下午3:44:23
	 */
	public List<PermissionTreeVo> findPermissionTree(PermissionVo permissionVo) {
		List<PermissionVo> permissionList = permissionDao.findByConditionForTree(permissionVo);
		return getPermissionTree(permissionList);
	}
	
	/**
	 * @Description: (获取权限树) 
	 * @param permissionList
	 * @return
	 * @author XJ
	 * @date 2017年6月10日 下午5:58:34
	 */
	private List<PermissionTreeVo> getPermissionTree(List<PermissionVo> permissionList) {
		Map<String /* 父级id */,PermissionTreeVo /* 子节点 */> idNodeRef = new HashMap<String, PermissionTreeVo>();
		List<PermissionTreeVo> rootTreeList = new ArrayList<PermissionTreeVo>();
		for (PermissionVo permissionVo : permissionList) {
			PermissionTreeVo treeNode = convertToTreeNode(permissionVo);
			idNodeRef.put(treeNode.getId(), treeNode);
		}
		for (PermissionVo permissionVo : permissionList) {
			if(permissionVo.getParentPermission() != null && idNodeRef.get(permissionVo.getParentPermission().toString()) != null){
				if(idNodeRef.get(permissionVo.getParentPermission().toString()).getChildren() ==null){
					idNodeRef.get(permissionVo.getParentPermission().toString()).setChildren(new ArrayList<PermissionTreeVo>());
				}
				idNodeRef.get(permissionVo.getParentPermission().toString()).getChildren().add(idNodeRef.get(permissionVo.getId().toString()));
			}else{
				rootTreeList.add(idNodeRef.get(permissionVo.getId().toString()));
			}
		}
		return rootTreeList;
	}
	
	/**
	 * @Description: (转换Vo为树形节点) 
	 * @param permissionVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:22:19
	 */
	private PermissionTreeVo convertToTreeNode(PermissionVo permissionVo) {
		PermissionTreeVo treeNode = new PermissionTreeVo();
		treeNode.setId(permissionVo.getId().toString());
		treeNode.setText(permissionVo.getPermissionName());
		treeNode.setPermissionCode(permissionVo.getPermissionCode());
		treeNode.setPermissionLevels(permissionVo.getPermissionLevels());
		return treeNode;
	}
	
	/**
	 * @Description: (按权限码查询) 
	 * @param permissionCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:22:49
	 */
	public PermissionVo findByPermissionCode(String permissionCode) {
		return permissionDao.findByPermissionCode(permissionCode);
	}
	
	/**
	 * @Description: (按条件查询权限) 
	 * @param permissionVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:23:11
	 */
	public List<PermissionVo> findByCondition(PermissionVo permissionVo) {
		return permissionDao.findByCondition(permissionVo);
	}
	
	/**
	 * @Description: (按权限码删除) 
	 * @param permissionCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:23:27
	 */
	public int deleteByPermissionCode(String permissionCode, String systemCode) {
		return permissionDao.deleteByPermissionCode(permissionCode, systemCode);
	}
	
	/**
	 * @Description: (查询用户所有的权限码) 
	 * @param roles
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:24:10
	 */
	public List<String> findUserAllPermissionCode(List<String> roles){
		return permissionDao.findUserAllPermissionCode(roles);
	}
	
	/**
	 * @Description: (查询用户所有权限) 
	 * @param roles
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月29日 下午5:03:23
	 */
	public List<PermissionVo> findUserAllPermission(List<String> roles){
		return permissionDao.findUserAllPermission(roles);
	}
	
	/**
	 * @Description: (按级别查询所有权限) 
	 * @param userVo
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:31:51
	 */

	public List<PermissionTreeVo> findPermissionTreeByLevel(String empCode,Integer roleLevel){
		return permissionDao.findPermissionTreeByLevel(empCode,roleLevel);
	}
	/**
	 * @Description: (按ID查询权限) 
	 * @param id
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:33:13
	 */
	public PermissionVo findPermissionById(Long id){
		PortalPermission permission = permissionDao.findById(id);
		return ObjectConvertUtil.convertObject(permission, PermissionVo.class);
	}
	
	/**
	 * @Description: (findPermissionByUser) 
	 * @param userVo
	 * @return
	 * @author XJ
	 * @date 2018年3月26日 下午3:07:59
	 */
	public List<PermissionVo> findPermissionByUser(String empCode,Integer controlType){
		return permissionDao.findPermissionByUser(empCode,controlType);
	}
	
	/**
	 * @Description:(根据角色码查询权限) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:15:31
	 */
	public List<PermissionVo> findPermissionByRoleCode(String roleCode) {
		return permissionDao.findPermissionByRoleCode(roleCode);
	}
	
	/**
	 * @Description: (根据权限级别和权限码删除角色权限关系) 
	 * @param permissionLevelList
	 * @param permissionCodeList
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 上午11:01:46
	 */
	public int deleteByPermissionLevelAndPermissionCode(List<String> permissionLevelList,List<String> permissionCodeList){
		return rolePermissionRelDao.deleteByRoleLevelAndPermissionCode(permissionLevelList,permissionCodeList);
	}
	
	/**
	 * @Description: (按权限码查询角色权限关系数量) 
	 * @param permissionCode
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 上午11:01:25
	 */
	public int findCountByPermissionCode(String permissionCode, String systemCode) {
		return rolePermissionRelDao.findCountByPermissionCode(permissionCode, systemCode);
	}

	/**
	 * @Description: (查询时间范围内更新的权限) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:05:02
	 */
	public List<String> findPermissionCodeByDate(PortalDataPushVo dataPushVo) {
		return permissionDao.findPermissionCodeByDate(dataPushVo);
	}

	public List<PermissionTreeNewVo> findPermissionTreeByLevelNew(
			String empCode, Integer roleLevel) {
		return permissionDao.findPermissionTreeByLevelNew(empCode,roleLevel);
	}
	
	/**
	 * 根据级别查询portal权限
	 * @param empCode
	 * @param roleLevel
	 * @return
	 * @author huangting
	 * @date 2018年8月21日 下午3:33:48
	 */
	public List<PermissionTreeNewVo> findPortalMenuTreeByLevel(
			String empCode, Integer roleLevel) {
		return permissionDao.findPortalMenuTreeByLevel(empCode,roleLevel);
	}
	/**
	 * findByID
	 * @param id
	 * @return
	 */
	public PortalPermission findById(Long id) {
		return permissionDao.findById(id);
	}
	
	/**
	 * 根据角色编码查询portal编码
	 * @param roleCode
	 * @return
	 * @author huangting
	 * @date 2018年8月21日 下午3:32:03
	 */
	public List<PermissionVo> findPortalMenuByRoleCode(String roleCode) {
		return permissionDao.findPortalMenuByRoleCode(roleCode);
	}
	
	/**
	 * 根据角色编码查询portal编码
	 * @param roleCode
	 * @return
	 * @author huangting
	 * @date 2018年8月21日 下午3:32:03
	 */
	public List<PermissionVo> findParentPermissionById(List<Long> id) {
		return permissionDao.findParentPermissionById(id);
	}
	public List<PermissionVo> findPermissionByIds(List<Long> ids) {
		
		return permissionDao.findPermissionByIds(ids);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据用户、权限code、systemCode查询该用户是否有此权限
	 * </pre>
	 * @param empCode
	 * @param permissionCode
	 * @param systemCode
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月20日上午10:56:58
	 */
	public int findCountByUserAndPerCodeAndSysCode(String empCode, String permissionCode, String systemCode) {
		// TODO Auto-generated method stub
		return permissionDao.findCountByUserAndPerCodeAndSysCode(empCode, permissionCode, systemCode);
	}
}
