package cn.uce.omg.portal.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.ICollectPermissionBiz;
import cn.uce.omg.portal.service.CollectPermissionService;
import cn.uce.omg.portal.vo.CollectPermissionVo;

/**
 * 
 *<pre>
 * CollectPermissionBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年4月15日下午3:41:43
 */
@Service(value = "collectPermissionBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class CollectPermissionBiz implements ICollectPermissionBiz {

	@Autowired
	private CollectPermissionService collectPermissionService;
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 分页查询
	 * </pre>
	 * @param collectPermissionVo
	 * @param page
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月15日下午3:41:55
	 */
	@Override
	public Pagination<CollectPermissionVo> findByCondition(CollectPermissionVo collectPermissionVo, Page page) {
		
		return collectPermissionService.findByCondition(collectPermissionVo, page);
	}

}
