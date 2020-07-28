package cn.uce.omg.portal.biz;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.vo.CollectPermissionVo;

public interface ICollectPermissionBiz {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 分页查询
	 * </pre>
	 * @param collectPermissionVo
	 * @param page
	 * @return
	 * @return Pagination<CollectPermissionVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月15日下午3:40:22
	 */
	Pagination<CollectPermissionVo> findByCondition(CollectPermissionVo collectPermissionVo, Page page);
}
