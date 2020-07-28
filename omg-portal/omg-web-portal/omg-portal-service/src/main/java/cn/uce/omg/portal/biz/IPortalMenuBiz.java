package cn.uce.omg.portal.biz;

import java.util.List;

import cn.uce.omg.portal.entity.PortalMenu;
import cn.uce.omg.portal.vo.MenuTreeNodeVo;
import cn.uce.omg.portal.vo.PermissionVo;

public interface IPortalMenuBiz {
	/**
	 * 查询portal菜单机构
	 * @return
	 * @author huangting
	 * @date 2018年8月15日 下午5:43:03
	 */
	List<MenuTreeNodeVo> findPortalMenuForZtree(PermissionVo search);
	
	/**
	 * 查询权限
	 * @return
	 * @author huangting
	 * @date 2018年8月15日 下午5:43:03
	 */
	List<MenuTreeNodeVo> findBaseMenuForZtree(PermissionVo search);
	
	/**
	 * 更新portal菜单
	 * @param dataMap
	 * @return
	 * @author huangting
	 * @date 2018年8月16日 下午2:28:27
	 */
	int saveProtalMenu(String addStr,String sortStr,String updateStr,String deleteStr,PortalMenu menuVo);
}
