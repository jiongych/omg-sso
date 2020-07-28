package cn.uce.omg.portal.control;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.ICollectPermissionBiz;
import cn.uce.omg.portal.vo.CollectPermissionVo;
import cn.uce.web.common.base.BaseController;

/**
 * 菜单收集
 *<pre>
 * CollectPermissionController
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年4月15日下午3:48:26
 */
@Controller
@RequestMapping("/collect")
public class CollectPermissionController extends BaseController {

	@Autowired
	private ICollectPermissionBiz collectPermissionBiz;
	
	@RequestMapping(value = "/forward")
	public String get() throws IOException {
		return "portal/collectPermission";
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 分页查询
	 * </pre>
	 * @param collectPermissionVo
	 * @param page
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月15日下午3:52:25
	 */
	@RequestMapping(value ="/findCollectPermissionByPage")
	@ResponseBody
	public Map<String, Object> findCollectPermissionByPage(CollectPermissionVo collectPermissionVo, Page page, String init) {
		//首次进入不查询
		if (null == init || init.equals("T")) {
			return returnSuccess();
		}
		Pagination<CollectPermissionVo> paginations = collectPermissionBiz.findByCondition(collectPermissionVo, page);
		return returnSuccess(paginations);
	}
}
