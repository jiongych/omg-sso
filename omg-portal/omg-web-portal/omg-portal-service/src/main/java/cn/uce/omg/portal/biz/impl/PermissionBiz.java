package cn.uce.omg.portal.biz.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.cache.CacheManager;
import cn.uce.core.cache.base.ICache;
import cn.uce.omg.portal.biz.IPermissionBiz;
import cn.uce.omg.portal.entity.PortalPermission;
import cn.uce.omg.portal.exception.PermissionException;
import cn.uce.omg.portal.service.PermissionService;
import cn.uce.omg.portal.service.PortalMenuService;
import cn.uce.omg.portal.vo.PermissionTreeNewVo;
import cn.uce.omg.portal.vo.PermissionTreeVo;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.omg.portal.vo.PortalDictDataVo;
import cn.uce.omg.portal.vo.PortalRoleLevelVo;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.util.WebUtil;

/**
 * @Description: (PermissionBiz) 
 * @author XJ
 * @date 2017年7月29日 上午9:19:11
 */
@Service("permissionBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class PermissionBiz implements IPermissionBiz {
	
	private final Log log = LogFactory.getLog(this.getClass());
	@Resource
	private PermissionService permissionService;
	
	@Resource
	private PortalMenuService portalMenuService;
	
	/**
	 * 	(非 Javadoc) 
	* <p>Title: addPermission</p> 
	* <p>Description: 新增权限</p> 
	* @param permissionVo
	* @return 
	* @see cn.uce.web.authorg.biz.IPermissionBiz#addPermission(cn.uce.PermissionVo.authorg.vo.PermissionVo)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int addPermission(PermissionVo permissionVo) {
		PermissionVo permission = permissionService.findByPermissionCode(permissionVo.getPermissionCode());
		if(permission!=null){
			return -100;
		}
		try {
			int insert= permissionService.insert(permissionVo);
			/*if (permissionVo.getIsHide() == 1) {
				permissionVo.setDeleteFlag(1);
			}*/
			CacheManager.getInstance().getCache("PermissionTreeCache").refresh("permissionTree");
			return insert;
		} catch (Exception e) {
			return -200;
		}
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: updatePermission</p> 
	* <p>Description: 修改权限</p> 
	* @param permissionVo
	* @return 
	* @see cn.uce.web.authorg.biz.IPermissionBiz#updatePermission(cn.uce.PermissionVo.authorg.vo.PermissionVo)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int updatePermission(PermissionVo permissionVo) {
		//比较新旧权限级别
		String oldPermissionLevels = permissionVo.getOldPermissionLevels();
		String permissionLevels = permissionVo.getPermissionLevels();
		String[] oldPermissionLevelArr = oldPermissionLevels.split(",");
		String[] permissionLevelArr = permissionLevels.split(",");
		List<String> deleteList = new ArrayList<String>();
		for(String oldPermissionLevel : oldPermissionLevelArr){
			boolean flag = false;
			for(String permissionLevel : permissionLevelArr){
				if(oldPermissionLevel.equals(permissionLevel)){
					flag=true;
					break;
				}
			}
			if(!flag){
				deleteList.add(oldPermissionLevel);
			}
		}
		if(!deleteList.isEmpty()){
			ICache<String, List<PermissionTreeVo>> permissionTreeCache = CacheManager.getInstance().getCache("PermissionTreeCache");
			List<PermissionTreeVo> permissionTree = permissionTreeCache.get("permissionTree");
			//查询所有子权限码
			List<PermissionTreeVo> childPermissionTree = findPermissionChildrenInTree(permissionTree,permissionVo.getPermissionCode());
			List<String> permissionCodeList = new ArrayList<String>();
			permissionCodeList.add(permissionVo.getPermissionCode());
			if(childPermissionTree !=null){
				List<PermissionVo> permissionVoList = convertTreeToList(childPermissionTree,null);
				for(PermissionVo permission : permissionVoList){
					permissionCodeList.add(permission.getPermissionCode());
					for(String str : deleteList){
						String newStr = null;
						if(permission.getPermissionLevels()!=null){
							newStr = permission.getPermissionLevels().replaceAll(","+str+"|"+str+"|"+str+",", "");
						}
						permission.setPermissionLevels("".equals(newStr) ? null : newStr);
					}
					permissionService.updateLevelsById(permission);
				}
				CacheManager.getInstance().getCache("PermissionTreeCache").refresh("permissionTree");
			}
			
			//删除角色权限关系
			permissionService.deleteByPermissionLevelAndPermissionCode(deleteList, permissionCodeList);
		}
		int i = permissionService.updateById(permissionVo);
		PortalPermission permissionSync = permissionService.findById(permissionVo.getId());
		permissionVo.setCreateEmp(permissionSync.getCreateEmp());
		permissionVo.setCreateTime(permissionSync.getCreateTime());
		permissionVo.setCreateOrg(permissionSync.getCreateOrg());
		/*if (permissionVo.getIsHide() == 1) {
			permissionVo.setDeleteFlag(1);
		}*/
		return i;
	}
	
	/**
	 * @Description: (把树形节点转换为List) 
	 * @param permissionTree
	 * @param list
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:12:32
	 */
	private List<PermissionVo> convertTreeToList(List<PermissionTreeVo> permissionTree,List<PermissionVo> list){
		if(list==null){
			list = new ArrayList<PermissionVo>();
		}
		for(PermissionTreeVo permissionTreeVo : permissionTree){
			PermissionVo permissionVo = new PermissionVo();
			permissionVo.setId(Long.valueOf(permissionTreeVo.getId()));
			permissionVo.setPermissionCode(permissionTreeVo.getPermissionCode());
			permissionVo.setPermissionLevels(permissionTreeVo.getPermissionLevels());
			list.add(permissionVo);
			if(permissionTreeVo.getChildren()!=null){
				convertTreeToList(permissionTreeVo.getChildren(),list);
			}
		}
		return list;
	}
	/**
	 * 递归查询权限的子节点
	 * @param permissionTreeList
	 * @param orgId
	 * @return
	 */
	private List<PermissionTreeVo> findPermissionChildrenInTree(List<PermissionTreeVo> permissionTreeList, String permissionCode) {
		for (PermissionTreeVo permissionTreeVo : permissionTreeList) {
			if (permissionTreeVo.getPermissionCode().equals(permissionCode)) {
				return permissionTreeVo.getChildren();
			} else {
				if (permissionTreeVo.getChildren() != null) {
					List<PermissionTreeVo> childrenTree = findPermissionChildrenInTree(permissionTreeVo.getChildren(), permissionCode);
					if (childrenTree != null) {
						return childrenTree;
					}
				}
			}
		}
		return null;
	}

	/**
	 * (非 Javadoc) 
	* <p>Title: deletePermission</p> 
	* <p>Description:删除权限 </p> 
	* @param permissionCode
	* @param parentPermission
	*  systemCode:数据同步时使用
	* @return 
	* @see cn.uce.web.authorg.biz.IPermissionBiz#deletePermission(java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int deletePermission(String permissionCode, Long parentPermission, String systemCode, Long id) {
		int count = permissionService.findCountByPermissionCode(permissionCode, systemCode);
		if (count > 0) {
			throw new  PermissionException("error.biz.permission.permissionusedbyrole");
		}
		
		try {
			/**  对接日志系统，物理删除前先更新删除人员 by:jiyongchao 20190814*/
			PortalPermission portalPermission = permissionService.findById(id);
			portalPermission.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
			portalPermission.setUpdateOrg(WebUtil.getCurrentUser().getOrgCode());
			portalPermission.setUpdateTime(new Date());
			permissionService.updateById(portalPermission);
		} catch(Exception e) {
			log.error("向日志中心发送消息失败，可以忽略" + e.getMessage());
		}
		
		
		int result = permissionService.deleteByPermissionCode(permissionCode, systemCode);
		if (result > 0 && parentPermission != null) {
			PermissionVo searchPermission = new PermissionVo();
			searchPermission.setParentPermission(parentPermission);
			searchPermission.setDeleteFlag(0);
			List<PermissionVo> permissionList= this.findByCondition(searchPermission);
			if (permissionList == null || permissionList.size() == 0) {
				PermissionVo parentPermissionVo = new PermissionVo();
				parentPermissionVo.setId(parentPermission);
				parentPermissionVo.setLeafFlag(true);
				permissionService.updateById(parentPermissionVo);
			}
		}
		return result;
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findPermissionByCondition</p> 
	* <p>Description: 分页查询权限</p> 
	* @param permissionVo
	* @param page
	* @return 
	
	* @see cn.uce.web.authorg.biz.IPermissionBiz#findPermissionByCondition(cn.uce.PermissionVo.authorg.vo.PermissionVo, cn.uce.base.page.Page)
	 */
	@Override
	public Pagination<PermissionVo> findPermissionByCondition(
			PermissionVo permissionVo, Page page) {
		return permissionService.findByCondition(permissionVo, page);
	}

	/**
	 * (非 Javadoc) 
	* <p>Title: findPermissionTree</p> 
	* <p>Description: 查询权限树</p> 
	* @param permissionVo
	* @return 
	* @see cn.uce.web.authorg.biz.IPermissionBiz#findPermissionTree(cn.uce.PermissionVo.authorg.vo.PermissionVo)
	 */
	public List<PermissionTreeVo> findPermissionTree(PermissionVo permissionVo) {
		return permissionService.findPermissionTree(permissionVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findByPermissionCode</p> 
	* <p>Description: 按权限码查询权限</p> 
	* @param permissionCode
	* @return 
	* @see cn.uce.web.authorg.biz.IPermissionBiz#findByPermissionCode(java.lang.String)
	 */
	@Override
	public PermissionVo findByPermissionCode(String permissionCode) {
		return permissionService.findByPermissionCode(permissionCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findByCondition</p> 
	* <p>Description: 条件查询权限</p> 
	* @param permissionVo
	* @return 
	* @see cn.uce.web.authorg.biz.IPermissionBiz#findByCondition(cn.uce.PermissionVo.authorg.vo.PermissionVo)
	 */
	@Override
	public List<PermissionVo> findByCondition(PermissionVo permissionVo) {
		return permissionService.findByCondition(permissionVo);
	}

	/**
	 * (非 Javadoc) 
	* <p>Title: findParentPermissionLevels</p> 
	* <p>Description: 查询父级权限的权限级别</p> 
	* @param id
	* @return 
	* @see cn.uce.web.authorg.biz.IPermissionBiz#findParentPermissionLevels(java.lang.Long)
	 */
	@Override
	public List<PortalRoleLevelVo> findParentPermissionLevels(Long id) {
		List<PortalRoleLevelVo> roleLevelVoList = new ArrayList<PortalRoleLevelVo>();
		Map<String,String> roleLevelToName =  getRoleLevelToName();
		if(id==null){//表示增加模块
			Set<String> roleCodeSet =roleLevelToName.keySet();
			List<String> roleCodeList = new ArrayList<String>(roleCodeSet);
			Collections.sort(roleCodeList);
			for(String roleCode : roleCodeList){
				PortalRoleLevelVo roleLevelVo = new PortalRoleLevelVo(roleCode,roleLevelToName.get(roleCode));
				roleLevelVoList.add(roleLevelVo);
			}
		}else{//表示增加菜单或者按钮
			PermissionVo permissionVo = permissionService.findPermissionById(id);
			String[] roleLevelStr={};
			if(permissionVo.getPermissionLevels() != null){
				roleLevelStr = permissionVo.getPermissionLevels().split(",");
			}
			for(String str : roleLevelStr){
				PortalRoleLevelVo roleLevelVo = new PortalRoleLevelVo(str,roleLevelToName.get(str));
				roleLevelVoList.add(roleLevelVo);
			}
		}
		return roleLevelVoList;
	}
	
	/**
	 * @Description: (获取角色级别-名称的对应关系) 
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 上午9:16:41
	 */
	private Map<String,String> getRoleLevelToName(){
		Map<String,String> roleLevelToName = new HashMap<String,String>();
		ICache<String, List<PortalDictDataVo>> dictDataCache = CacheManager.getInstance().getCache("DictDataCache");
		List<PortalDictDataVo> roleLevelList = dictDataCache.get("ROLE_LEVEL");
		for(PortalDictDataVo roleLevelVo : roleLevelList){
			roleLevelToName.put(roleLevelVo.getTypeCode(), roleLevelVo.getTypeName());
		}
		return roleLevelToName;
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findPermissionTreeByLevel</p> 
	* <p>Description: 按级别查询权限树</p> 
	* @param userVo
	* @return 
	* @see cn.uce.web.authorg.biz.IPermissionBiz#findPermissionTreeByLevel(cn.uce.UserVo.authorg.vo.UserVo)
	 */
	@Override
	public List<PermissionTreeVo> findPermissionTreeByLevel(Integer roleLevel) {
		//获取超级管理员列表
		ICache<String, List<PortalDictDataVo>> dictDataCache = CacheManager.getInstance().getCache("DictDataCache");
		List<PortalDictDataVo> superAdminList = dictDataCache.get("SUPER_ADMIN");
		//获取当前登录用户
		CurrentUser currentUser = WebUtil.getCurrentUser();
		//判断当前用户是否是超级管理员
		boolean flag=false;
		for(PortalDictDataVo dictDataVo : superAdminList){
			if(currentUser.getEmpCode().equals(dictDataVo.getTypeCode())){
				flag=true;
			}
		}
		String empCode = null;
		if(!flag){
			empCode = currentUser.getEmpCode();
		}
		List<PermissionTreeVo> permissionVoList = permissionService.findPermissionTreeByLevel(empCode, roleLevel);
		List<PermissionTreeVo> rootTree = new ArrayList<PermissionTreeVo>();
		Map<String,PermissionTreeVo> idTreeNodeRef = new HashMap<String,PermissionTreeVo>();
		for(PermissionTreeVo treeNodeVo : permissionVoList){
			idTreeNodeRef.put(treeNodeVo.getId(), treeNodeVo);
		}
		for(PermissionTreeVo treeNodeVo : permissionVoList){
			if(treeNodeVo.getParentId() != null && idTreeNodeRef.get(treeNodeVo.getParentId()) != null){
				if(idTreeNodeRef.get(treeNodeVo.getParentId()).getChildren() == null){
					idTreeNodeRef.get(treeNodeVo.getParentId()).setChildren(new ArrayList<PermissionTreeVo>());
				}
				idTreeNodeRef.get(treeNodeVo.getParentId()).getChildren().add(treeNodeVo);
			}else{
				rootTree.add(treeNodeVo);
			}
		}
		return rootTree;
	}
	
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findPermissionTreeByLevel</p> 
	* <p>Description: 按级别查询权限树</p> 
	* @param userVo
	* @return 
	* @see cn.uce.web.authorg.biz.IPermissionBiz#findPermissionTreeByLevel(cn.uce.UserVo.authorg.vo.UserVo)
	 */
	@Override
	public List<PermissionTreeNewVo> findPermissionTreeByLevelNew(Integer roleLevel) {

		//判断当前用户是否是超级管理员
/*		boolean flag=false;
		for(PortalDictDataVo dictDataVo : superAdminList){
			if(currentUser.getEmpCode().equals(dictDataVo.getTypeCode())){
				flag=true;
			}
		}*/
		String empCode = null;
/*		if(!flag){
			empCode = currentUser.getEmpCode();
		}*/
		List<PermissionTreeNewVo> permissionVoList = permissionService.findPermissionTreeByLevelNew(empCode, roleLevel);
		List<PermissionTreeNewVo> rootTree = new ArrayList<PermissionTreeNewVo>();
		Map<String,PermissionTreeNewVo> idTreeNodeRef = new HashMap<String,PermissionTreeNewVo>();
		for(PermissionTreeNewVo treeNodeVo : permissionVoList){
			if (null != treeNodeVo && null != treeNodeVo.getControlType() && "1".equals(treeNodeVo.getControlType())) {
				treeNodeVo.setName(treeNodeVo.getSystemCode() + "-" + treeNodeVo.getName());
			}
			idTreeNodeRef.put(treeNodeVo.getId(), treeNodeVo);
		}
		for(PermissionTreeNewVo treeNodeVo : permissionVoList){
			if(treeNodeVo.getParentId() != null && idTreeNodeRef.get(treeNodeVo.getParentId()) != null){
				if(idTreeNodeRef.get(treeNodeVo.getParentId()).getChildren() == null){
					idTreeNodeRef.get(treeNodeVo.getParentId()).setChildren(new ArrayList<PermissionTreeNewVo>());
				}
				idTreeNodeRef.get(treeNodeVo.getParentId()).getChildren().add(treeNodeVo);
			}else{
				if(!StringUtils.equals(treeNodeVo.getControlType(), "2")){
					rootTree.add(treeNodeVo);
				}
			}
		}
		return rootTree;
	}

	@Override
	public List<String> findPermissionCodeByUser(String empCode, Integer controlType) {
		List<PermissionVo> permissionVoList = permissionService.findPermissionByUser(empCode, controlType);
		List<String> permissionCodeList = new ArrayList<String>();
		if(permissionVoList != null){
			for(PermissionVo permissionVo : permissionVoList){
				permissionCodeList.add(permissionVo.getPermissionCode());
			}
		}
		return permissionCodeList;
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findPermissionByRoleCode</p> 
	* <p>Description: 根据角色码查询权限</p> 
	* @param roleCode
	* @return 
	* @see cn.uce.web.authorg.biz.IRoleBiz#findPermissionIdByRoleCode(java.lang.String)
	 */
	@Override
	public List<PermissionVo> findPermissionByRoleCode(String roleCode) {
		return permissionService.findPermissionByRoleCode(roleCode);
	}

	/**
	 * @Description: (查询时间范围内更新的权限) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:05:02
	 */
	@Override
	public List<String> findPermissionCodeByDate(PortalDataPushVo dataPushVo) {
		return permissionService.findPermissionCodeByDate(dataPushVo);
	}

	/**
	 * 根据角色编码查询portal编码
	 * @param roleCode
	 * @return
	 * @author huangting
	 * @date 2018年8月21日 下午3:32:03
	 */
	@Override
	public List<PermissionVo> findPortalMenuByRoleCode(String roleCode) {
		return permissionService.findPortalMenuByRoleCode(roleCode);
	}

	@Override
	public List<PermissionVo> findPermissionByIds(List<Long> ids) {
		
		return permissionService.findPermissionByIds(ids);
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
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月20日上午10:57:06
	 */
	@Override
	public int findCountByUserAndPerCodeAndSysCode(String empCode, String permissionCode, String systemCode) {
		return permissionService.findCountByUserAndPerCodeAndSysCode(empCode, permissionCode, systemCode);
	}

}
