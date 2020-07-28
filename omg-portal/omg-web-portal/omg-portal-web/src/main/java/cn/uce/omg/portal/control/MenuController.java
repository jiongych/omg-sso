package cn.uce.omg.portal.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.omg.portal.biz.IMenuBiz;
import cn.uce.omg.portal.entity.PortalUserMenu;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.util.WebUtil;
/**
 * 
 * @Description:(UserMenuController)
 * @author Ji Yongchao
 * @date 2017年8月4日 上午9:40:41
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController{

	@Resource
	private IMenuBiz portalMenuBiz;
	
	/**
	 * @Description: 常用菜单的移入和移除操作
	 * @param ids
	 * @param empCode
	 * @param cmsBaseOrgCode
	 * @param way：in为移入操作，out为移除操作
	 * @return
	 * @throws Exception
	 * @author JYC
	 * @date 2017年08月02日 上午9:43:25
	 */
	@RequestMapping(value="saveUserMenu")
	@ResponseBody
	public Map<String, Object> saveUserMenu(Long[] ids, String empCode, String cmsBaseOrgCode, Long[] oldUserMenu) throws Exception {
		if (!checkValue(ids)) {
			if (!compareLongArr(ids, oldUserMenu)) {
				Long[] sorts = new Long[ids.length];
				for (int i = 0; i < ids.length; i++) {
					sorts[i] = (long) (i + 1);
				}
				//通过permissionId集合、empCode、orgCode、sorts集合加工成List<UserMenu>
				List<PortalUserMenu> userMenuList = portalMenuBiz.processByParam(ids, empCode, cmsBaseOrgCode, sorts);
				//常用菜单初始化为空时
				if (!checkValue(oldUserMenu)) {
					portalMenuBiz.deleteUserMenuByEmpAndOrgAndPerId(empCode, cmsBaseOrgCode, oldUserMenu);
				}
				
				if (null != userMenuList && !userMenuList.isEmpty()) {
					portalMenuBiz.saveUserMenu(userMenuList);
				}
			}
			
		} else {
			portalMenuBiz.deleteUserMenuByEmpAndOrg(empCode, cmsBaseOrgCode);
		}
		/*if (null != way) {
			if ("in".equals(way)) {
				//常用菜单移入操作
				List<UserMenu> userMenuList = fspUserMenuBiz.processByParam(ids, empCode, cmsBaseOrgCode);
				if (null != userMenuList && !userMenuList.isEmpty()) {
					fspUserMenuBiz.saveUserMenu(userMenuList);
				}
			} else {
				//常用菜单移出操作
				fspUserMenuBiz.deleteUserMenuByEmpAndOrgAndPerId(empCode, cmsBaseOrgCode, ids);
			}
		}*/
		return null;
	}
	
	/**
	 * @Description: 根据UserMenu的empCode、orgCode查询，
	 * 获取UserMenu的permissionId集合，用于前端常用菜单展示
	 * @return
	 * @throws Exception
	 * @author JYC
	 * @date 2017年08月02日 上午9:43:25
	 */
	@RequestMapping(value="getUserMenu")
	@ResponseBody
	public List<Long> getUserMenu() throws Exception {
		PortalUserMenu userMenu = new PortalUserMenu();
		userMenu.setEmpCode(WebUtil.getCurrentUser().getEmpCode());//TODO
		userMenu.setOrgCode(WebUtil.getCurrentUser().getCmsBaseOrgCode());//TODO
		
		return portalMenuBiz.findUserMenuByEmpCodeAndOrgCode(userMenu);
	}
	/**
	 * @Description: 比较两个Long[]是否相同，包含元素和顺序
	 * @return boolean
	 * @param arr1
	 * @param arr2
	 * @author JYC
	 * @date 2017年08月02日 上午9:43:25
	 */
	public static boolean compareLongArr(Long[] arr1, Long[] arr2) {
		//检查这两个数组是否为空
		if (checkValue(arr1) && checkValue(arr2)) {
			return true;
		}
		if (!checkValue(arr1) || !checkValue(arr2)) {
			if (arr1.length == arr2.length) {
				for (int i = 0; i < arr1.length; i++) {
					//如果这两个数组长度、每个数组位置上的元素相等，则这两个数组为相等
					if (arr1[i].intValue() != arr2[i].intValue()) {
						return false;
					}
				}
			}
		}
		return false;
	}
	/**
	 * @Description: 判断Long[]是否为空
	 * @return boolean
	 * @param arr
	 * @author JYC
	 * @date 2017年08月02日 上午9:43:25
	 */
	public static boolean checkValue(Long[] arr) {
		if (null == arr) {
			return true;
		}
		if (arr.length == 0) {
			return true;
		}
		return false;
	}

}
