package cn.uce.omg.portal.control;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.IPermissionBiz;
import cn.uce.omg.portal.util.Constants;
import cn.uce.omg.portal.vo.PermissionTreeNewVo;
import cn.uce.omg.portal.vo.PermissionTreeVo;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.omg.portal.vo.PortalRoleLevelVo;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.i18n.Resources;
import cn.uce.web.common.util.WebUtil;

/**
 * @Description: (PermissionController) 
 * @author XJ
 * @date 2017年7月28日 下午2:17:24
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController{
	@Resource
	private IPermissionBiz permissionBiz;
	
	/**
	 * @Description: (权限页面跳转) 
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月28日 下午2:18:11
	 */
	@RequestMapping(value = "/forward")
	public String forward()throws Exception{
		return "portal/permission";
	}
	
	/**
	 * @Description: (分页查询权限信息) 
	 * @param permissionVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 下午4:24:58
	 */
	@RequestMapping(value ="findPermissionByPage")
	@ResponseBody
	private Map<String, Object> findPermissionByPage(PermissionVo permissionVo, Page page) {
		permissionVo.setEmptyFlag(isEmpty(permissionVo));
		permissionVo.setFilterSystemCode(Constants.SYSTEM_CODE_PORTAL_MENU);
		Pagination<PermissionVo> permissionVos = permissionBiz.findPermissionByCondition(permissionVo, page);
		return returnSuccess(permissionVos);
	}
	
	/**
	 * @Description: (查询权限树) 
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 下午4:25:38
	 */
	@RequestMapping(value = "/findPermissionTree")
	@ResponseBody
	public List<PermissionTreeVo> findPermissionTree() {
		PermissionVo permissionVo = new PermissionVo();
		permissionVo.setFilterSystemCode(Constants.SYSTEM_CODE_PORTAL_MENU);
		permissionVo.setControlType(Constants.PERMISSION_CONTROL_TYPE_MENU);
		permissionVo.setDeleteFlag(0);
		List<PermissionTreeVo> permissionTreeList = permissionBiz.findPermissionTree(permissionVo);
		return permissionTreeList;
	}
	
	/**
	 * @Description: (新增权限) 
	 * @param permissionVo
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 下午4:26:32
	 */
	@RequestMapping(value ="addPermission")
	@ResponseBody
	public Map<String, Object> addPermission(PermissionVo permissionVo) {
		permissionVo.setLeafFlag(true);
		permissionVo.setDeleteFlag(0);
		permissionVo.setCreateEmp(WebUtil.getCurrentUser().getEmpCode());//TODO
		permissionVo.setCreateOrg(WebUtil.getCurrentUser().getCmsOrgId());//TODO
		permissionVo.setCreateTime(new Date());
		permissionVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());//TODO
		permissionVo.setUpdateOrg(WebUtil.getCurrentUser().getCmsOrgId());//TODO
		permissionVo.setUpdateTime(new Date());
		int res = permissionBiz.addPermission(permissionVo);
		if (res > 0 || res == -100) {
			if (res == -100) {
				return returnSuccess(res);
			}
	      return returnSuccess(Resources.getMessage("common.save.success"));
	    } else {
	      return returnError(Resources.getMessage("common.save.fail"));
	    }
	}
	
	/**
	 * @Description: (修改权限) 
	 * @param permissionVo
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 下午4:26:51
	 */
	@RequestMapping(value ="updatePermission")
	@ResponseBody
	public Map<String, Object> updatePermission(PermissionVo permissionVo) {
		if (permissionVo.getPermissionLevels() == null || permissionVo.getPermissionLevels().length() == 0) {
			return returnError("权限级别不能为空");
		}
		permissionVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		permissionVo.setUpdateTime(new Date());
		permissionVo.setDeleteFlag(0);
		permissionVo.setUpdateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		int res = permissionBiz.updatePermission(permissionVo);
		if (res > 0) {
	      return returnSuccess(Resources.getMessage("common.update.success"));
	    } else {
	      return returnError(Resources.getMessage("common.update.fail"));
	    }
	}
	
	/**
	 * @Description: (删除权限) 
	 * @param permissionCode
	 * @param parentPermission
	 * @param systemCode:数据同步时使用
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 下午4:27:05
	 */
	@RequestMapping(value ="deletePermission")
	@ResponseBody
	public Map<String, Object> deletePermission(String permissionCode, Long parentPermission, String systemCode, Long id) {
		int res = permissionBiz.deletePermission(permissionCode, parentPermission, systemCode, id);
		if (res > 0) {
	      return returnSuccess(Resources.getMessage("common.delete.success"));
	    } else {
	      return returnError(Resources.getMessage("common.delete.fail"));
	    }
	}
	
	/**
	 * @Description: (查询父级权限级别) 
	 * @param id
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 下午4:36:56
	 */
	@RequestMapping(value ="findParentPermissionLevels")
	@ResponseBody
	public List<PortalRoleLevelVo> findParentPermissionLevels(Long id){
		return permissionBiz.findParentPermissionLevels(id);
	}
	
	/**
	 * @Description: (按级别查询权限树) 
	 * @param userVo
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 下午4:36:36
	 */
	@RequestMapping(value ="findPermissionTreeByLevel")
	@ResponseBody
	public List<PermissionTreeVo> findPermissionTreeByLevel(Integer roleLevel){
		return permissionBiz.findPermissionTreeByLevel(roleLevel);
	}
	
	
	/**
	 * @Description: (按级别查询权限树) 
	 * @param userVo
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 下午4:36:36
	 */
	@RequestMapping(value ="findPermissionTreeByLevelNew")
	@ResponseBody
	public List<PermissionTreeNewVo> findPermissionTreeByLevelNew(Integer roleLevel){
		return permissionBiz.findPermissionTreeByLevelNew(roleLevel);
	}
	
	/**
	 * @Description: (按权限码查询权限) 
	 * @param permissionCode
	 * @return
	 * @author XJ
	 * @date 2017年7月28日 下午5:41:11
	 */
	@RequestMapping(value ="findByPermissionCode")
	@ResponseBody
	private Boolean findByPermissionCode(String permissionCode) {
		PermissionVo permissionVo = permissionBiz.findByPermissionCode(permissionCode);
		return permissionVo == null ? true : false;
	}
	
	/**
	 * @Description: (查询角色的权限ID) 
	 * @param roleCode
	 * @param request
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月29日 下午3:29:14
	 */
	@RequestMapping(value = "/findPermissionIdByRoleCode")
	@ResponseBody
	public Map<String, Object> findPermissionByRoleCode(String roleCode) throws Exception{
		List<PermissionVo> permissionVoList = permissionBiz.findPermissionByRoleCode(roleCode);
		List<Long> permissionIdList = new ArrayList<Long>();
		if(permissionVoList != null){
			for(PermissionVo permissionVo : permissionVoList){
				permissionIdList.add(permissionVo.getId());
			}
		}
		return returnSuccess(permissionIdList);
	}
	
	/**
	 * 根据角色编码查询portal权限
	 * @param roleCode
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2018年8月21日 下午3:31:06
	 */
	@RequestMapping(value = "/findPortalMenuByRoleCode")
	@ResponseBody
	public Map<String, Object> findPortalMenuByRoleCode(String roleCode) throws Exception{
		List<PermissionVo> permissionVoList = permissionBiz.findPortalMenuByRoleCode(roleCode);
		//-------------------寻找新菜单的一二级菜单start-----------------------------
		//寻找二级菜单
		//获取菜单的ID
		List<Long> ids = new ArrayList<Long>();
		if (permissionVoList != null && !permissionVoList.isEmpty()) {
			for (PermissionVo permission : permissionVoList) {
				ids.add(permission.getId());
			}
		}
		List<Long> noParent = processPermission(permissionVoList, ids);
		if (null != noParent && noParent.size() > 0) {
			List<PermissionVo> parentPermisson = permissionBiz.findPermissionByIds(noParent);
			if (null != parentPermisson && parentPermisson.size() > 0) {
				permissionVoList.addAll(parentPermisson);
				//寻找一级菜单
				List<Long> firstNoParent = processPermission(parentPermisson, ids);
				if (null != firstNoParent && firstNoParent.size() > 0) {
					List<PermissionVo> firstPermisson = permissionBiz.findPermissionByIds(firstNoParent);
					if (null != firstPermisson && firstPermisson.size() > 0) {
						permissionVoList.addAll(firstPermisson);
					}
				}
			}
		}
		//-------------------寻找新菜单的一二级菜单end----------------------------------
		List<Long> permissionIdList = new ArrayList<Long>();
		if(permissionVoList != null){
			for(PermissionVo permissionVo : permissionVoList){
				permissionIdList.add(permissionVo.getId());
			}
		}
		return returnSuccess(permissionIdList);
	}
	public List<Long> processPermission(List<PermissionVo> portalPermissionList, List<Long> ids) {
		//获取菜单的父级菜单ID
		Set<Long> parentIds = new HashSet<Long>();
		
		if (portalPermissionList != null && !portalPermissionList.isEmpty()) {
			for (PermissionVo permission : portalPermissionList) {
				if (null != permission.getControlType() && permission.getControlType() == 1) {
					parentIds.add(permission.getParentPermission());
				}
			}
		}
		
		List<Long> noParent = new ArrayList<>();
		if (null != parentIds && parentIds.size() > 0) {
			for (Long parentId : parentIds) {
				if (!ids.contains(parentId)) {
					noParent.add(parentId);
				}
			}
		}
		return noParent;
	}
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 判断实体类是否为空
	 * </pre>
	 * @param obj
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月27日下午4:01:01
	 */
	public static boolean isEmpty(Object obj) {
		try {
			int i = 0;
			Field[] fs = obj.getClass().getDeclaredFields();
			for (Field field : fs) {
				field.setAccessible(true);
				if (!field.getName().equals("serialVersionUID") && !field.getName().equals("isHide")) {
					if (field.get(obj) != null && !field.get(obj).equals("")) {
						System.out.println(field.getName());
						i ++;
					}
				}
			}
			if (i == 0) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
