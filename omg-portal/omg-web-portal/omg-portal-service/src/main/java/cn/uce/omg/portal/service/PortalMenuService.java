package cn.uce.omg.portal.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.omg.portal.dao.IPortalMenuDao;
import cn.uce.omg.portal.entity.PortalMenu;
import cn.uce.omg.portal.vo.PermissionVo;

@Service("portalMenuService")
public class PortalMenuService {

	@Resource
	IPortalMenuDao portalMenuDao;
	
	/**
	 * 查询portal菜单
	 * @param permissionVo
	 * @return
	 * @author huangting
	 * @date 2018年8月16日 上午10:33:05
	 */
	public List<PermissionVo> findPortalMenuForZtree(PermissionVo permissionVo) {
		return portalMenuDao.findPortalMenuForZtree(permissionVo);
	}
	
	
	/**
	 * 查询未分配到portal菜单的菜单
	 * @param permissionVo
	 * @return
	 * @author huangting
	 * @date 2018年8月16日 上午10:33:05
	 */
	public List<PermissionVo> findBaseMenuForZtree(PermissionVo permissionVo) {
		return portalMenuDao.findBaseMenuForZtree(permissionVo);
	}
	
	
	/**
	 * 新增portal菜单
	 * @param menu
	 * @return
	 * @author huangting
	 * @date 2018年8月16日 下午7:00:40
	 */
	public int insert(PortalMenu menu) {
		return portalMenuDao.insert(menu);
	}
	
	/**
	 * 更新portal菜单
	 * @param menu
	 * @return
	 * @author huangting
	 * @date 2018年8月16日 下午7:00:40
	 */
	public int updateById(PortalMenu menu) {
		return portalMenuDao.updateById(menu);
	}
	
	/**
	 * 根据唯一键删除portal菜单
	 * @param menu
	 * @return
	 * @author huangting
	 * @date 2018年8月23日 下午2:22:23
	 */
	public int deleteByUniqueKey(PortalMenu menu) {
		return portalMenuDao.deleteByUniqueKey(menu);
	}
	
	/**
	 * 根据唯一键更新portal菜单排序
	 * @param menu
	 * @return
	 * @author huangting
	 * @date 2018年8月23日 下午2:22:06
	 */
	public int updateSortByUniqueKey(PortalMenu menu) {
		return portalMenuDao.updateSortByUniqueKey(menu);
	}
	
	/**
	 * 根据唯一键更新portal菜单
	 * @param menu
	 * @return
	 * @author huangting
	 * @date 2018年8月23日 下午2:22:06
	 */
	public int updateByUniqueKey(PortalMenu menu) {
		return portalMenuDao.updateByUniqueKey(menu);
	}
}
