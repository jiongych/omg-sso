package cn.uce.omg.portal.control;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.IPermissionBiz;
import cn.uce.omg.portal.biz.IPortalMenuBiz;
import cn.uce.omg.portal.entity.PortalMenu;
import cn.uce.omg.portal.vo.MenuTreeNodeVo;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.util.WebUtil;


@Controller
@RequestMapping("/portalMenu")
public class PortalMenuController extends BaseController{
	@Resource
	IPortalMenuBiz fspPortalMenuBiz;
	
	@Resource
	private IPermissionBiz permissionBiz;
	
	
	/**
	 * 页面跳转
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2018年8月15日 上午11:28:36
	 */
	@RequestMapping(value = "/forward")
	public String forward()throws Exception{
		return "portal/portalMenu";
	}
	
	/**
	 * 查询简单数据的Ztree机构数
	 * @author zhangRb
	 * @throws Exception
	 * @date 2015年9月2日
	 */
	@RequestMapping(value = "findBaseMenuForZtree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findBaseMenuForZtree(PermissionVo search) throws Exception {
		List<MenuTreeNodeVo> treeNodeList = fspPortalMenuBiz.findBaseMenuForZtree(search);
		Pagination<MenuTreeNodeVo> pagination = new Pagination<MenuTreeNodeVo>();	 
		pagination.setData(treeNodeList);
		return returnSuccess(pagination);
	}
	
	/**
	 * 查询简单数据的Ztree机构数
	 * @author zhangRb
	 * @throws Exception
	 * @date 2015年9月2日
	 */
	@RequestMapping(value = "findPortalMenuForZtree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findPortalMenuForZtree(PermissionVo search) throws Exception {
		List<MenuTreeNodeVo> treeNodeList = fspPortalMenuBiz.findPortalMenuForZtree(search);
		Pagination<MenuTreeNodeVo> pagination = new Pagination<MenuTreeNodeVo>();	 
		pagination.setData(treeNodeList);
		return returnSuccess(pagination);
	}
	
	
	/**
	 * 保存Portal菜单
	 * @param data
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2018年8月16日 下午2:22:58
	 */
	@RequestMapping(value = "saveProtalMenuTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveProtalMenuTree(String addStr,String sortStr,String updateStr,String deleteStr) throws Exception {
        PortalMenu menuVo = new PortalMenu();
		menuVo.setCreateEmp(WebUtil.getCurrentUser().getEmpCode());
		menuVo.setCreateOrg(WebUtil.getCurrentUser().getOrgId());
		menuVo.setCreateTime(new Date());
		menuVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		menuVo.setUpdateOrg(WebUtil.getCurrentUser().getOrgId());
		menuVo.setUpdateTime(new Date());
		fspPortalMenuBiz.saveProtalMenu(addStr,sortStr,updateStr,deleteStr,menuVo);
		return returnSuccess(1, "操作成功！");
	}
	
	
	
}
