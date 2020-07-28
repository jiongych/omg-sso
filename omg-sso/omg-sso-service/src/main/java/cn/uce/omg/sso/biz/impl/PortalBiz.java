package cn.uce.omg.sso.biz.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.core.cache.CacheManager;
import cn.uce.core.cache.base.ICache;
import cn.uce.core.log.server.buffer.LogBuffer;
import cn.uce.core.mq.api.MqTemplate;
import cn.uce.omg.sso.biz.IPortalBiz;
import cn.uce.omg.sso.entity.portal.PortalCompanyNoticeRecord;
import cn.uce.omg.sso.entity.portal.PortalUserMenu;
import cn.uce.omg.sso.service.PortalService;
import cn.uce.omg.sso.vo.NoticeParentInfoVo;
import cn.uce.omg.sso.vo.portal.PortalBrowserVo;
import cn.uce.omg.sso.vo.portal.PortalCollectPermissionVo;
import cn.uce.omg.sso.vo.portal.PortalCompanyNoticeVo;
import cn.uce.omg.sso.vo.portal.PortalCurrentUser;
import cn.uce.omg.sso.vo.portal.PortalDictDataVo;
import cn.uce.omg.sso.vo.portal.PortalHomePageInfoVo;
import cn.uce.omg.sso.vo.portal.PortalMenuTreeVo;
import cn.uce.omg.sso.vo.portal.PortalMessageStatusVo;
import cn.uce.omg.sso.vo.portal.PortalPermissionVo;
import cn.uce.utils.FastJsonUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @Description: (PortalBiz) 
 * @author XJ
 * @date 2018年3月28日 下午8:44:17
 */
public class PortalBiz implements IPortalBiz{
	
	private Log log = LogFactory.getLog(this.getClass());
	private PortalService portalService;
	private LogBuffer logBuffer;
	private MqTemplate noticeReadTemplate;
	private MqTemplate openPermissionTemplate;
	private MqTemplate sendMessageStatusTemplate;
	/**
	 * (非 Javadoc) 
	* <p>Title: getMenuTree</p> 
	* <p>Description: 获取菜单树</p> 
	* @param empCode
	* @return 
	
	* @see cn.uce.omg.sso.biz.IPortalBiz#getMenuTree(java.lang.String)
	 */
	public List<PortalMenuTreeVo> getMenuTree(String empCode){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("empCode", empCode);
		params.put("controlType", 1);
		List<PortalPermissionVo> portalPermissionList = portalService.findPortalPermissionByUser(empCode,1);
		//-------------------寻找新菜单的一二级菜单start-----------------------------
		//寻找二级菜单
		//获取菜单的ID
		List<Long> ids = new ArrayList<Long>();
		if (portalPermissionList != null && !portalPermissionList.isEmpty()) {
			for (PortalPermissionVo permission : portalPermissionList) {
				ids.add(permission.getId());
			}
		}
		List<Long> noParent = processPermission(portalPermissionList, ids);
		if (null != noParent && noParent.size() > 0) {
			List<PortalPermissionVo> parentPermisson = portalService.findPermissionByIds(noParent);
			if (null != parentPermisson && parentPermisson.size() > 0) {
				portalPermissionList.addAll(parentPermisson);
				//寻找一级菜单
				List<Long> firstNoParent = processPermission(parentPermisson, ids);
				if (null != firstNoParent && firstNoParent.size() > 0) {
					List<PortalPermissionVo> firstPermisson = portalService.findPermissionByIds(firstNoParent);
					if (null != firstPermisson && firstPermisson.size() > 0) {
						portalPermissionList.addAll(firstPermisson);
					}
				}
			}
		}
		//-------------------寻找新菜单的一二级菜单end----------------------------------
		List<PortalMenuTreeVo> menuTree = new ArrayList<PortalMenuTreeVo>();
		if(portalPermissionList != null && !portalPermissionList.isEmpty()){
			for (PortalPermissionVo permission : portalPermissionList) {
				PortalMenuTreeVo treeNodeVo = new PortalMenuTreeVo();
				treeNodeVo.setId(permission.getId());
				treeNodeVo.setIsHide(permission.getIsHide());
				treeNodeVo.setText(permission.getPermissionName());
				treeNodeVo.setParentId(permission.getParentPermission());
				treeNodeVo.setPermissionCode(permission.getPermissionCode());
				treeNodeVo.setPermissionIcon(permission.getPermissionIcon());
				treeNodeVo.setSystemCode(permission.getSystemCode());
				
				if(permission.getPermissionUrl()==null || "".equals(permission.getPermissionUrl())){
					treeNodeVo.setAttributes("");
				}else{
					treeNodeVo.setAttributes((permission.getSysUrl()==null||"".equals(permission.getSysUrl())) ? permission.getPermissionUrl():permission.getSysUrl()+permission.getPermissionUrl());
				}
				menuTree.add(treeNodeVo);
			}
		}
		return menuTree;
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getExpandMenuTree</p> 
	* <p>Description: 查询展开的菜单树</p> 
	* @param empCode
	* @return 
	* @see cn.uce.omg.sso.biz.IPortalBiz#getExpandMenuTree(java.lang.String)
	 */
	@Override
	public List<PortalMenuTreeVo> getExpandMenuTree(String empCode) {
		List<PortalPermissionVo> portalPermissionList = portalService.findPortalPermissionByUser(empCode,1);
		List<PortalMenuTreeVo> menuTree = new ArrayList<PortalMenuTreeVo>();
		Map<Long,PortalMenuTreeVo> idNodeRef = new HashMap<Long,PortalMenuTreeVo>();
		if (portalPermissionList != null && !portalPermissionList.isEmpty()) {
			for (PortalPermissionVo permission : portalPermissionList) {
				PortalMenuTreeVo treeNodeVo = new PortalMenuTreeVo();
				treeNodeVo.setId(permission.getId());
				treeNodeVo.setText(permission.getPermissionName());
				treeNodeVo.setPermissionIcon(permission.getPermissionIcon());
				
				if(permission.getPermissionUrl()==null || "".equals(permission.getPermissionUrl())){
					treeNodeVo.setAttributes("");
				}else{
					treeNodeVo.setAttributes((permission.getSysUrl()==null||"".equals(permission.getSysUrl())) ? permission.getPermissionUrl():permission.getSysUrl()+permission.getPermissionUrl());
				}
				if(permission.getParentPermission() == null){
					
				}
				idNodeRef.put(treeNodeVo.getId(), treeNodeVo);
			}
			for(PortalPermissionVo permissionVo : portalPermissionList){
				if(permissionVo.getParentPermission() != null){
					if(idNodeRef.get(permissionVo.getParentPermission()).getChildren() ==null){
						idNodeRef.get(permissionVo.getParentPermission()).setChildren(new ArrayList<PortalMenuTreeVo>());
					}
					idNodeRef.get(permissionVo.getParentPermission()).getChildren().add(idNodeRef.get(permissionVo.getId()));
				}else{
					menuTree.add(idNodeRef.get(permissionVo.getId()));
				}
			}
		}
		return menuTree;
	}
	
	public List<Long> processPermission(List<PortalPermissionVo> portalPermissionList, List<Long> ids) {
		//获取菜单的父级菜单ID
		Set<Long> parentIds = new HashSet<Long>();
		
		if (portalPermissionList != null && !portalPermissionList.isEmpty()) {
			for (PortalPermissionVo permission : portalPermissionList) {
				parentIds.add(permission.getParentPermission());
			}
		}
		
		List<Long> noParent = new ArrayList<>();
		if (null != parentIds && parentIds.size() > 0) {
			for (Long parentId : parentIds) {
				if (!ids.contains(parentId)) {
					noParent.add(parentId);
				}
			}
		}
		return noParent;
	}
	
	
	
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findLoginEmpOrgRelByEmpId</p> 
	* <p>Description: 查询登陆员工机构关系</p> 
	* @param empId
	* @return 
	* @see cn.uce.omg.sso.biz.IPortalBiz#findLoginEmpOrgRelByEmpId(java.lang.Integer)
	 */
	@Override
	public PortalCurrentUser findCurrentUser(String empCode) {
		return portalService.findCurrentUser(empCode);
	}
	
	public PortalCurrentUser findCurrentUserInfo(String empCode) {
		return portalService.findCurrentUserInfo(empCode);
	}
	
	/**
	 * 
	 * @Description: 查询员工信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月24日 下午5:58:02
	 */
	@Override
	public PortalCurrentUser findCurrentUserNoEmpRelation(String empCode) {
		return portalService.findCurrentUserNoEmpRelation(empCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findPermissionCodeByCurrentUser</p> 
	* <p>Description: 查询当前用户的权限码</p> 
	* @param empCode
	* @return 
	* @see cn.uce.omg.sso.biz.IPortalBiz#findPermissionCodeByCurrentUser(java.lang.String)
	 */
	@Override
	public List<String> findPermissionCodeByCurrentUser(String empCode){
		List<String> portalPermissionList =  portalService.findPortalRolePermissionCodeByUser(empCode);
		return portalPermissionList;
	}

	public void setPortalService(PortalService portalService) {
		this.portalService = portalService;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * portal前台页面展示前十条公司公告
	 * </pre>
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月30日下午2:32:42
	 */
	@Override
	public List<PortalCompanyNoticeVo> findNoticeTopicTen(String empCode, String typeId, String orgId) {
		List<PortalCompanyNoticeVo> companyNoticList = portalService.findNoticeTopicTen(empCode, typeId, orgId);
		if (null != companyNoticList && !companyNoticList.isEmpty()) {
			for (PortalCompanyNoticeVo portalCompanyNoticeVo : companyNoticList) {
				portalCompanyNoticeVo.setNewNotice(isLatestWeek(portalCompanyNoticeVo.getCreateTime()));
			}
		}
		return companyNoticList;
	}
	
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * portal前台页面展示提示公司公告
	 * </pre>
	 * @return
	 * @author zb
	 * @date 2018年3月30日下午2:32:42
	 */
	@Override
	public List<PortalCompanyNoticeVo> findNoticeAlert(String empCode, String orgId) {
		int day = 30;
		try {ICache<String,List<PortalDictDataVo>> cache = CacheManager.getInstance().getCache("PortalDictDataCache");
			List<PortalDictDataVo> portalDictDataList = cache.get("PORTAL_INFO");
			if (null != portalDictDataList && portalDictDataList.size() > 0) {
				for (PortalDictDataVo portalDictDataVo : portalDictDataList) {
					if (null != portalDictDataVo && 
							null != portalDictDataVo.getTypeCode() &&
							null != portalDictDataVo.getTypeName() &&
									portalDictDataVo.getTypeName().equals("NOTICE_ALERT_TIME")) {
						day = Integer.parseInt(portalDictDataVo.getTypeCode());
					}
				}
			}
		} catch (Exception e) {
			day = 30;
		}
		
		Calendar c = Calendar.getInstance();
	    c.setTime(new Date());
	    c.add(Calendar.DAY_OF_MONTH, day * -1);
		List<PortalCompanyNoticeVo> companyNoticList = portalService.findNoticeAlert(empCode,c.getTime(), orgId);
		return companyNoticList;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 判断创建时间是否是三天内
	 * </pre>
	 * @param addtime
	 * @return
	 * @return boolean
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月25日上午10:11:13
	 */
	public boolean isLatestWeek(Date addtime){  
	    Calendar calendar = Calendar.getInstance();  	//得到日历  
	    calendar.setTime(new Date());					//把当前时间赋给日历  
	    calendar.add(Calendar.DAY_OF_MONTH, -3);  		//设置为3天前  
	    Date before3days = calendar.getTime();   		//得到3天前的时间  
	    if(null != addtime && before3days.getTime() < addtime.getTime()){  
	        return true;  
	    }else{  
	        return false;  
	    }  
	}  
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID查询数据
	 * </pre>
	 * @param id
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月30日下午2:32:53
	 */
	@Override
	public PortalCompanyNoticeVo saveAndFindById(Long id, String empCode, String orgCode, String empName) {
		try {
			PortalCompanyNoticeRecord portalCompanyNoticeRecord = new PortalCompanyNoticeRecord();
			portalCompanyNoticeRecord.setNoticeId(id);
			portalCompanyNoticeRecord.setViewUser(empCode);
			portalCompanyNoticeRecord.setViewTime(new Date());
			portalCompanyNoticeRecord.setViewFlag(true);
			portalCompanyNoticeRecord.setFromSource(2);
			portalCompanyNoticeRecord.setOrgCode(orgCode);
			portalCompanyNoticeRecord.setEmpName(empName);
			portalCompanyNoticeRecord.setEmpCode(empCode);
			portalCompanyNoticeRecord.setCreateTime(new Date());
			noticeReadTemplate.send(FastJsonUtil.toJsonString(portalCompanyNoticeRecord));
		} catch (Exception e){
			log.error("发送用户查看公告消息失败", e);
		}
		
		return portalService.findById(id);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过UserMenu的empCode和orgCode条件查询UserMenur的permissionId集合
	 * </pre>
	 * @param userMenu
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月31日上午9:34:07
	 */
	@Override
	public List<Long> findUserMenuByEmpCodeAndOrgCode(PortalUserMenu userMenu) {
		return portalService.findUserMenuByEmpCodeAndOrgCode(userMenu);
	}

	/**
	 * (非 Javadoc) 
	 * <p>Title: processByParam</p> 
	 * <p>Description: 通过permissionId集合、empCode、orgCode、sorts集合加工成List<UserMenu></p> 
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @param sorts
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	@Override
	public List<PortalUserMenu> processByParam(Long[] ids, String empCode, String orgCode, Long[] sorts) {
		List<PortalUserMenu> userMenuList = new ArrayList<>();
		if (null != ids && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				PortalUserMenu userMenu = new PortalUserMenu();
				userMenu.setPermissionId(ids[i]);
				userMenu.setEmpCode(empCode);
				userMenu.setOrgCode(orgCode);
				userMenu.setCreateTime(new Date());
				userMenu.setSort(sorts[i].intValue());
				userMenuList.add(userMenu);
			}
		}
		return userMenuList;
	}

	/**
	 * (非 Javadoc) 
	 * <p>Title: deleteUserMenuByEmpAndOrgAndPerId</p> 
	 * <p>Description: 通过permissionId集合、empCode、orgCode删除该UserMenu</p> 
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	@Override
	public int deleteUserMenuByEmpAndOrgAndPerId(String empCode, String orgCode, Long[] ids){
		
		return portalService.deleteUserMenuByEmpAndOrgAndPerId(empCode, orgCode, longArrToList(ids));
	}
	/**
	 * (非 Javadoc) 
	 * <p>Title: longArrToList</p> 
	 * <p>Description: 将Long[]转换为List<Long></p> 
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	public static List<Long> longArrToList(Long[] ids) {
		List<Long> longList = new ArrayList<>();
		if (null != ids && ids.length > 0) {
			for (Long id : ids) {
				longList.add(id);
			}
		} else {
			return null;
		}
		return longList;
	}
	
	/**
	 * (非 Javadoc) 
	 * <p>Title: saveUserRoleRel</p> 
	 * <p>Description: 批量保存常用菜单数据</p> 
	 * @author Ji Yongchao
	 * @param userMenuList
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	@Override
	public int saveUserMenu(List<PortalUserMenu> userMenuList) {
		
		return portalService.saveUserMenu(userMenuList);
	}

	/**
	 * (非 Javadoc) 
	 * <p>Title: deleteUserMenuByEmpAndOrg</p> 
	 * <p>Description: empCode、orgCode删除该UserMenu</p>
	 * @author Ji Yongchao
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	@Override
	public int deleteUserMenuByEmpAndOrg(String empCode, String orgCode) {
		
		return portalService.deleteUserMenuByEmpAndOrg(empCode, orgCode);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询各系统首页跳转信息
	 * </pre>
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午3:53:42
	 */
	@Override
	public List<PortalHomePageInfoVo> findHomePageInfo() {
		
		return portalService.findHomePageInfo();
	}
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存浏览器信息
	 * </pre>
	 * @return
	 */
	@Override
	public void saveBrowserInfo(String empCode, String remoteAddr, Integer orgId,String browserInfo) {
		JSONObject json = (JSONObject) JSON.parse(browserInfo);
		PortalBrowserVo browserVo = new PortalBrowserVo();
		browserVo.setBrowserName(json.getString("browserName") == null ? "未知浏览器" : json.getString("browserName"));
		String browserVersion = json.getString("browserVersion");
		if (null != browserVersion && browserVersion.length() > 200) {
			browserVo.setBrowserVersion(browserVersion.substring(0, 200));
		} else {
			browserVo.setBrowserVersion(browserVersion);
		}
		browserVo.setBrowserOperatingSystems(json.getString("osVersion"));
		browserVo.setBrowserScreen(json.getString("screen"));
		browserVo.setCreateDate(new Date());
		browserVo.setCreateEmp(empCode);
		browserVo.setOperatingIp(remoteAddr);
		browserVo.setCreateOrg(String.valueOf(orgId));
		logBuffer.write(browserVo);
	}
	
	//add by xj
	/**
	 * (非 Javadoc) 
	* <p>Title: findDictDataByTypeClassCode</p> 
	* <p>Description: 根据typeClassCode查询数据字典</p> 
	* @param typeClassCode
	* @return 
	* @see cn.uce.omg.sso.biz.IPortalBiz#findDictDataByTypeClassCode(java.lang.String)
	 */
	public List<PortalDictDataVo> findDictDataByTypeClassCode(String typeClassCode){
		return portalService.findDictDataByTypeClassCode(typeClassCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getDictDataByTypeClassCode</p> 
	* <p>Description: 获取数据字典缓存</p> 
	* @param typeClassCode
	* @return 
	* @see cn.uce.omg.sso.biz.IPortalBiz#getDictDataByTypeClassCode(java.lang.String)
	 */
	public List<PortalDictDataVo> getDictDataByTypeClassCode(String typeClassCode){
		ICache<String,List<PortalDictDataVo>> cache = CacheManager.getInstance().getCache("PortalDictDataCache");
		return cache.get(typeClassCode);
	}
	//add by xj

	public LogBuffer getLogBuffer() {
		return logBuffer;
	}

	public void setLogBuffer(LogBuffer logBuffer) {
		this.logBuffer = logBuffer;
	}

	public PortalService getPortalService() {
		return portalService;
	}


	/**
	 * 根据permissionCode和systemCode查询菜单
	 * @param permissionCode
	 * @param systemCode
	 * @return
	 */
	@Override
	public PortalPermissionVo findPermissionAndSystemCode(String permissionCode, String systemCode) {
		
		return portalService.findPermissionAndSystemCode(permissionCode, systemCode);
	}

	public void setNoticeReadTemplate(MqTemplate noticeReadTemplate) {
		this.noticeReadTemplate = noticeReadTemplate;
	}
	
	/**
	 * 根据empCode和systemCode校验菜单权限
	 * @param empCode
	 * @param systemCode
	 * @return
	 */
	@Override
	public boolean menuPermissionCheck(String empCode, String systemCode) {
		int count = portalService.menuPermissionCheck(empCode, systemCode);
		return count>0;
	}

	/**
	 * 单条保存常用菜单数据
	 */
	@Override
	public int saveUserMenu(PortalUserMenu userMenu) {
		
		return portalService.saveUserMenu(userMenu);
	}

	/**
	 * 根据三个条件删除常用菜单
	 * @param empCode
	 * @param orgCode
	 * @param permissionId
	 * @return
	 */
	@Override
	public int deleteUserMenuByParam(String empCode, String orgCode, Long permissionId) {
		
		return portalService.deleteUserMenuByParam(empCode, orgCode, permissionId);
	}

	public void setOpenPermissionTemplate(MqTemplate openPermissionTemplate) {
		this.openPermissionTemplate = openPermissionTemplate;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 菜单操作日志发送mq
	 * </pre>
	 * @param portalPermission
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月10日下午1:48:49
	 */
	@Override
	public void collectOpenPermissionInfo(PortalCollectPermissionVo portalPermission) {
		
		openPermissionTemplate.send(FastJsonUtil.toJsonString(portalPermission));
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询公告阅读数
	 * </pre>
	 * @param noticeId
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月19日上午11:19:11
	 */
	@Override
	public int findCompanyViewNum(Long noticeId) {
		
		return portalService.findCompanyViewNum(noticeId);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询公告类型
	 * </pre>
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月19日下午3:14:58
	 */
	@Override
	public List<NoticeParentInfoVo> findNoticeType() {
		
		return portalService.findNoticeType();
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 发送消息变更状态。
	 * </pre>
	 * @param portalPermission
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年5月13日下午4:43:32
	 */
	@Override
	public void updateMessageStatus(PortalMessageStatusVo porMessageStatusVo) {
		sendMessageStatusTemplate.send(FastJsonUtil.toJsonString(porMessageStatusVo));
	}

	public void setSendMessageStatusTemplate(MqTemplate sendMessageStatusTemplate) {
		this.sendMessageStatusTemplate = sendMessageStatusTemplate;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据orgid查询父级级联
	 * </pre>
	 * @param orgId
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年6月25日上午10:33:58
	 */
	@Override
	public String findFullOrgIdList(String orgId) {
		
		return portalService.findFullOrgIdList(orgId);
	}
	
}
