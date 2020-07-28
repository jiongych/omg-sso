package cn.uce.omg.portal.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.omg.portal.biz.IPortalMenuBiz;
import cn.uce.omg.portal.entity.PortalMenu;
import cn.uce.omg.portal.service.PermissionService;
import cn.uce.omg.portal.service.PortalMenuService;
import cn.uce.omg.portal.util.Constants;
import cn.uce.omg.portal.vo.MenuTreeNodeVo;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.utils.StringUtil;

import com.alibaba.fastjson.JSON;

@Service("fspPortalMenuBiz")
@Transactional(readOnly= true, propagation=Propagation.SUPPORTS)
public class PortalMenuBiz implements IPortalMenuBiz {

	@Resource
	PortalMenuService portalMenuService;
	
	@Resource 
	PermissionService permissionService;

	/**
	 * 查询portal菜单机构
	 * @return
	 * @author huangting
	 * @date 2018年8月15日 下午5:43:03
	 */
	@Override
	public List<MenuTreeNodeVo> findPortalMenuForZtree(PermissionVo search) {
		search.setDeleteFlag(0);
		List<PermissionVo> list = portalMenuService.findPortalMenuForZtree(search);
		if (list == null || list.size() == 0) {
			return new ArrayList<MenuTreeNodeVo>();
		}
		return buildMenuZtree(list);
	}
	
	private List<MenuTreeNodeVo> buildMenuZtree(List<PermissionVo> list) {
		List<MenuTreeNodeVo> rootTreeList = new ArrayList<MenuTreeNodeVo>();
		List<MenuTreeNodeVo> treeNodeList = new ArrayList<MenuTreeNodeVo>(list.size());
		Map<String /* 父级id */, List<MenuTreeNodeVo> /* 子节点 */> childTreeNodeMap = new HashMap<String, List<MenuTreeNodeVo>>();
		for (PermissionVo permissionVo : list) {
			MenuTreeNodeVo treeNode = convertMenuTreeNodeVo(permissionVo);
			if (StringUtil.isBlank(treeNode.getPid())) {
				rootTreeList.add(treeNode);
			}
			treeNodeList.add(treeNode);
			if (childTreeNodeMap.get(treeNode.getPid()) != null) {
				childTreeNodeMap.get(treeNode.getPid()).add(treeNode);
			} else {
				List<MenuTreeNodeVo> childTreeNodeList = new ArrayList<MenuTreeNodeVo>();
				childTreeNodeList.add(treeNode);
				childTreeNodeMap.put(treeNode.getPid() + "", childTreeNodeList);
			}
		}
		List<MenuTreeNodeVo> rootNodeBuilders = new ArrayList<MenuTreeNodeVo>();
		for (MenuTreeNodeVo builder : rootTreeList) {
			if (childTreeNodeMap.get(builder.getId()) != null) {
				builder.setIsParent(true);
				builder.setChildren(
						setChildTreeNodeVo(childTreeNodeMap.get(builder.getId()), childTreeNodeMap));
			}
			rootNodeBuilders.add(builder);
		}
		return rootNodeBuilders;
	}
	
	
	private MenuTreeNodeVo convertMenuTreeNodeVo(PermissionVo permissionVo) {
		MenuTreeNodeVo treeNode = new MenuTreeNodeVo();
		treeNode.setId(permissionVo.getId() +"");
		if (permissionVo.getParentPermission() != null) {
			treeNode.setPid(permissionVo.getParentPermission() + "");
		}
		treeNode.setName(permissionVo.getPermissionName());
		treeNode.setPermissionCode(permissionVo.getPermissionCode());
		treeNode.setSystemCode(permissionVo.getSystemCode());
		treeNode.setPermissionUrl(permissionVo.getPermissionUrl());
		return treeNode;
	}
	
	private List<MenuTreeNodeVo> setChildTreeNodeVo(List<MenuTreeNodeVo> orgTreeNodeList,
			Map<String, List<MenuTreeNodeVo>> childTreeNodeMap) {
		List<MenuTreeNodeVo> childTreeNodes = new ArrayList<MenuTreeNodeVo>();
		for (MenuTreeNodeVo orgTreeNodeBuilder : orgTreeNodeList) {
			if (childTreeNodeMap.get(orgTreeNodeBuilder.getId()) != null) {
				orgTreeNodeBuilder.setIsParent(true);
				orgTreeNodeBuilder.setChildren(setChildTreeNodeVo(
						childTreeNodeMap.get(orgTreeNodeBuilder.getId()), childTreeNodeMap));
			}
			childTreeNodes.add(orgTreeNodeBuilder);
		}
		return childTreeNodes;
	}

	/**
	 * 查询权限
	 * @return
	 * @author huangting
	 * @date 2018年8月15日 下午5:43:03
	 */
	@Override
	public List<MenuTreeNodeVo> findBaseMenuForZtree(PermissionVo search) {
		search.setDeleteFlag(0);
		search.setControlType(Constants.PERMISSION_CONTROL_TYPE_MENU);
		List<PermissionVo> list = portalMenuService.findBaseMenuForZtree(search);
		if (list == null || list.size() == 0) {
			return new ArrayList<MenuTreeNodeVo>();
		}
		return buildMenuZtree(list);
	}
	
	/**
	 * 更新portal菜单
	 * @param dataMap
	 * @return
	 * @author huangting
	 * @date 2018年8月16日 下午2:28:27
	 */
	@Override
	public int saveProtalMenu(String addStr,String sortStr,String updateStr,String deleteStr,PortalMenu menuVo) {
		int result =0;
		if (StringUtil.isNotEmpty(addStr)) {
			List<PortalMenu> portalMenuList = JSON.parseArray(addStr, PortalMenu.class);
			for(PortalMenu menu: portalMenuList) {
				menu.setId(null);
				menu.setCreateEmp(menuVo.getUpdateEmp());
				menu.setCreateOrg(menuVo.getUpdateOrg());
				menu.setCreateTime(menuVo.getUpdateTime());
				menu.setUpdateEmp(menuVo.getUpdateEmp());
				menu.setUpdateOrg(menuVo.getUpdateOrg());
				menu.setUpdateTime(menuVo.getUpdateTime());
				menu.setSort(1);
				result += portalMenuService.insert(menu);
			}
		}
		
		if (StringUtil.isNotEmpty(updateStr)) {
			List<PortalMenu> updateMenuList = JSON.parseArray(updateStr, PortalMenu.class);
			for (PortalMenu menu : updateMenuList) {
				menu.setUpdateEmp(menuVo.getUpdateEmp());
				menu.setUpdateOrg(menuVo.getUpdateOrg());
				menu.setUpdateTime(menuVo.getUpdateTime());
				result += portalMenuService.updateByUniqueKey(menu);
			}
		}
		
		if(StringUtil.isNotEmpty(sortStr)) {
			String[] moveNode = sortStr.split(",A");
			Integer nodeSort = 0;
			PortalMenu sortMenu = new PortalMenu();
			for (String nodeStr : moveNode) {
				if (nodeStr == "" || "".equals(nodeStr)) {
					continue;
				}
				String[] nodeIds = nodeStr.split(",");
				List<PortalMenu> deletePortalMenuList = new ArrayList<PortalMenu>();
				nodeSort = 0;
				if(nodeIds.length > 0) {
					for (String str : nodeIds) {
						String[] menuStr = str.split("\\+");
						sortMenu = new PortalMenu();
						sortMenu.setPermissionCode(menuStr[0]);
						sortMenu.setSystemCode(menuStr[1]);
						deletePortalMenuList.add(sortMenu);
					}
				}
				for (PortalMenu menu : deletePortalMenuList) {
					nodeSort++;
					menu.setUpdateEmp(menuVo.getUpdateEmp());
					menu.setUpdateOrg(menuVo.getUpdateOrg());
					menu.setUpdateTime(menuVo.getUpdateTime());
					menu.setSort(nodeSort);
					result += portalMenuService.updateSortByUniqueKey(menu); 
				}
			}
		}
		
		if (StringUtil.isNotEmpty(deleteStr)) {
			List<PortalMenu> delPortalMenuList = JSON.parseArray(deleteStr, PortalMenu.class);
			for(PortalMenu menu: delPortalMenuList) {
				result += portalMenuService.deleteByUniqueKey(menu);
			}
		}
		return result;
	}
}
