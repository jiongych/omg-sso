package cn.uce.omg.portal.dao;

import org.springframework.stereotype.Repository;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalCollectPermission;
import cn.uce.omg.portal.vo.CollectPermissionVo;

/**
 * 
 *<pre>
 * ICollectPermissionDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年4月15日下午3:00:16
 */
@Repository("collectPermissionDao")
public interface ICollectPermissionDao extends IBaseDao<PortalCollectPermission, Long> {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 分页查询
	 * </pre>
	 * @param permissionVo
	 * @param page
	 * @return
	 * @return Pagination<CollectPermissionVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月15日下午3:07:15
	 */
	Pagination<CollectPermissionVo> findByCondition(CollectPermissionVo collectPermissionVo, Page page);
}
