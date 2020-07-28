package cn.uce.omg.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.dao.ICollectPermissionDao;
import cn.uce.omg.portal.vo.CollectPermissionVo;
/**
 * 菜单操作日志服务处理层
 * @author uce
 */
@Service("collectPermissionService")
public class CollectPermissionService {
	
	@Autowired
	private ICollectPermissionDao collectPermissionDao;
	
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
	 * @date 2019年4月15日下午3:40:51
	 */
	public Pagination<CollectPermissionVo> findByCondition(CollectPermissionVo collectPermissionVo, Page page) {
		return collectPermissionDao.findByCondition(collectPermissionVo, page);
	}
}
