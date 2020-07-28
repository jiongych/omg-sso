package cn.uce.omg.portal.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.uce.omg.portal.entity.PortalMenu;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.core.db.IBaseDao;

@Repository("portalMenuDao")
public interface IPortalMenuDao extends IBaseDao<PortalMenu,Long> {

	/**
	 * 查询portal菜单
	 * @param permissionVo
	 * @return
	 * @author huangting
	 * @date 2018年8月16日 上午10:33:05
	 */
	List<PermissionVo> findPortalMenuForZtree(PermissionVo permissionVo);
	
	/**
	 * 查询portal菜单
	 * @param permissionVo
	 * @return
	 * @author huangting
	 * @date 2018年8月16日 上午10:33:05
	 */
	List<PermissionVo> findBaseMenuForZtree(PermissionVo permissionVo);

	/**
	 * 根据唯一键删除portal菜单
	 * @param menu
	 * @return
	 * @author huangting
	 * @date 2018年8月23日 下午2:22:23
	 */
	int deleteByUniqueKey(PortalMenu menu);
	
	/**
	 * 根据唯一键更新portal菜单
	 * @param menu
	 * @return
	 * @author huangting
	 * @date 2018年8月23日 下午2:22:06
	 */
	int updateSortByUniqueKey(PortalMenu menu);
	
	/**
	 * 根据唯一键更新portal菜单
	 * @param menu
	 * @return
	 * @author huangting
	 * @date 2018年8月23日 下午2:22:06
	 */
	int updateByUniqueKey(PortalMenu menu);
}
