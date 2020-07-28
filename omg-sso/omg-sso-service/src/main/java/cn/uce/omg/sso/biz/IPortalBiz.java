package cn.uce.omg.sso.biz;

import java.util.List;

import cn.uce.omg.sso.entity.portal.PortalUserMenu;
import cn.uce.omg.sso.vo.NoticeParentInfoVo;
import cn.uce.omg.sso.vo.portal.PortalCollectPermissionVo;
import cn.uce.omg.sso.vo.portal.PortalCompanyNoticeVo;
import cn.uce.omg.sso.vo.portal.PortalCurrentUser;
import cn.uce.omg.sso.vo.portal.PortalDictDataVo;
import cn.uce.omg.sso.vo.portal.PortalHomePageInfoVo;
import cn.uce.omg.sso.vo.portal.PortalMenuTreeVo;
import cn.uce.omg.sso.vo.portal.PortalMessageStatusVo;
import cn.uce.omg.sso.vo.portal.PortalPermissionVo;

/**
 * @Description: (IPortalBiz) 
 * @author XJ
 * @date 2018年3月28日 下午8:42:11
 */
public interface IPortalBiz {
	
	/**
	 * @Description: (getExpandMenuTree) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年3月28日 下午8:42:23
	 */
	List<PortalMenuTreeVo> getMenuTree(String empCode);
	
	/**
	 * @Description: (getExpandMenuTree) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年3月28日 下午8:42:23
	 */
	List<PortalMenuTreeVo> getExpandMenuTree(String empCode);
	
	/**
	 * @Description: (findCurrentUser) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年4月2日 下午6:38:12
	 */
	PortalCurrentUser findCurrentUser(String empCode);
	
	PortalCurrentUser findCurrentUserInfo(String empCode);
	
	/**
	 * 
	 * @Description: 查询员工信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月24日 下午5:58:02
	 */
	PortalCurrentUser findCurrentUserNoEmpRelation(String empCode);
	
	/**
	 * @Description: (findPermissionCodeByCurrentUser) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年4月19日 下午4:16:41
	 */
	List<String> findPermissionCodeByCurrentUser(String empCode);
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * portal前台页面展示前十条公司公告
	 * </pre>
	 * @return
	 * @return List<PortalCompanyNoticeVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月30日下午2:31:43
	 */
	List<PortalCompanyNoticeVo> findNoticeTopicTen(String empCode, String typeId, String orgId);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID查询数据
	 * </pre>
	 * @param id
	 * @return
	 * @return PortalCompanyNotice
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月30日下午2:31:54
	 */
	PortalCompanyNoticeVo saveAndFindById(Long id, String empCode, String orgCode, String empName);
	
	/**
	 * @Description:通过UserMenu的empCode和orgCode条件查询UserMenur的permissionId集合
	 * @author Ji Yongchao
	 * @param userMenu
	 * @return
	 * 2017年8月4日 上午9:44:47
	 */
	List<Long> findUserMenuByEmpCodeAndOrgCode(PortalUserMenu userMenu);
	/**
	 * @Description:通过permissionId集合、empCode、orgCode、sorts集合加工成List<UserMenu>
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @param sorts
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	List<PortalUserMenu> processByParam(Long[] ids, String empCode, String orgCode, Long[] sorts);
	/**
	 * @Description:通过permissionId集合、empCode、orgCode删除该UserMenu
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	int deleteUserMenuByEmpAndOrgAndPerId(String empCode, String orgCode, Long[] ids);
	/**
	 * @Description:批量保存常用菜单数据
	 * @author Ji Yongchao
	 * @param userMenuList
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	int saveUserMenu(List<PortalUserMenu> userMenuList);
	/**
	 * @Description:empCode、orgCode删除该UserMenu
	 * @author Ji Yongchao
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	int deleteUserMenuByEmpAndOrg(String empCode, String orgCode);
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询各系统首页跳转信息
	 * </pre>
	 * @return
	 * @return List<PortalHomePageInfoVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午3:53:07
	 */
	List<PortalHomePageInfoVo> findHomePageInfo();

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存登录浏览器信息
	 * </pre>
	 * @return
	 * @return List<PortalHomePageInfoVo>
	 */
	void saveBrowserInfo(String empCode, String remoteAddr, Integer orgId,
			String browserInfo);
	
	/**
	 * @Description: (findDictDataByTypeClassCode) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2018年5月3日 下午6:55:07
	 */
	List<PortalDictDataVo> findDictDataByTypeClassCode(String typeClassCode);
	
	/**
	 * @Description: (getDictDataByTypeClassCode) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2018年5月3日 下午6:58:38
	 */
	List<PortalDictDataVo> getDictDataByTypeClassCode(String typeClassCode);

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * portal前台页面展示提示公司公告
	 * </pre>
	 * @return
	 * @author zb
	 * @date 2018年6月08日下午2:32:42
	 */
	List<PortalCompanyNoticeVo> findNoticeAlert(String empCode, String orgId);
	
	/**
	 * 根据permissionCode和systemCode查询菜单
	 * @param permissionCode
	 * @param systemCode
	 * @return
	 */
	PortalPermissionVo findPermissionAndSystemCode(String permissionCode, String systemCode);
	
	/**
	 * 根据empCode和systemCode校验菜单权限
	 * @param empCode
	 * @param systemCode
	 * @return
	 */
	boolean menuPermissionCheck(String empCode,String systemCode);
	/**
	 * 单条保存常用菜单数据
	 * @param userMenu
	 * @return
	 */
	int saveUserMenu(PortalUserMenu userMenu);
	
	/**
	 * 根据三个条件删除常用菜单
	 * @param empCode
	 * @param orgCode
	 * @param permissionId
	 * @return
	 */
	int deleteUserMenuByParam(String empCode, String orgCode, Long permissionId);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 *  菜单点击操作日志
	 * </pre>
	 * @param title
	 * @param permissionId
	 * @param systemCode
	 * @param inFlag
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月10日下午1:46:27
	 */
	void collectOpenPermissionInfo(PortalCollectPermissionVo portalPermission);
	
	/**
	 * 根据ID查询阅读数量
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param noticeId
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月19日上午11:18:29
	 */
	int findCompanyViewNum(Long noticeId);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询公告级别类型
	 * </pre>
	 * @return
	 * @return NoticeParentInfoVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月19日下午3:13:45
	 */
	List<NoticeParentInfoVo> findNoticeType();
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 发送消息变更状态
	 * </pre>
	 * @param porMessageStatusVo
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年5月13日下午4:53:57
	 */
	void updateMessageStatus(PortalMessageStatusVo porMessageStatusVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据orgId查询父级级联关系
	 * </pre>
	 * @param orgId
	 * @return
	 * @return String
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年6月25日上午10:33:25
	 */
	String findFullOrgIdList(String orgId);
}
