package cn.uce.omg.sso.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.uce.omg.sso.dao.portal.IPortalCompanyNoticeDao;
import cn.uce.omg.sso.dao.portal.IPortalCompanyNoticeRecordDao;
import cn.uce.omg.sso.dao.portal.IPortalCurrentUserDao;
import cn.uce.omg.sso.dao.portal.IPortalDictDataDao;
import cn.uce.omg.sso.dao.portal.IPortalHomePageInfoDao;
import cn.uce.omg.sso.dao.portal.IPortalPermissionDao;
import cn.uce.omg.sso.dao.portal.IPortalUserMenuDao;
import cn.uce.omg.sso.entity.portal.PortalCompanyNoticeRecord;
import cn.uce.omg.sso.entity.portal.PortalUserMenu;
import cn.uce.omg.sso.vo.NoticeParentInfoVo;
import cn.uce.omg.sso.vo.portal.PortalBrowserVo;
import cn.uce.omg.sso.vo.portal.PortalCompanyNoticeVo;
import cn.uce.omg.sso.vo.portal.PortalCurrentUser;
import cn.uce.omg.sso.vo.portal.PortalDictDataVo;
import cn.uce.omg.sso.vo.portal.PortalHomePageInfoVo;
import cn.uce.omg.sso.vo.portal.PortalPermissionVo;

/**
 * @Description: (PortalService) 
 * @author XJ
 * @date 2018年3月28日 下午8:39:16
 */
public class PortalService {
	
	private IPortalPermissionDao portalPermissionDao;
	
	//公司公告Dao
	private IPortalCompanyNoticeDao portalCompanyNoticeDao;
	//常用菜单设置Dao
	private IPortalUserMenuDao portalUserMenuDao;
	//公司公告查看dao
	private IPortalCompanyNoticeRecordDao portalCompanyNoticeRecordDao;
	//各系统首页跳转信息Dao
	private IPortalHomePageInfoDao portalHomePageInfoDao;
	
	private IPortalCurrentUserDao portalCurrentUserDao;
	
	private IPortalDictDataDao portalDictDataDao;
	
	/**
	 * @Description: (findPortalPermissionByUser) 
	 * @param empCode
	 * @param controlType
	 * @return
	 * @author XJ
	 * @date 2018年3月28日 下午8:39:43
	 */
	public List<PortalPermissionVo> findPortalPermissionByUser(String empCode,Integer controlType){
		return portalPermissionDao.findPortalPermissionByUser(empCode, controlType);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询当前登录人的所有权限
	 * </pre>
	 * @param empCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年12月24日下午1:13:31
	 */
	public List<String> findPortalRolePermissionCodeByUser(String empCode){
		return portalPermissionDao.findPortalRolePermissionCodeByUser(empCode);
	}
	
	/**
	 * @Description: (findCurrentUser) 
	 * @param empId
	 * @return
	 * @author XJ
	 * @date 2018年4月2日 下午3:39:06
	 */
	public PortalCurrentUser findCurrentUser(String empCode){
		return portalCurrentUserDao.findCurrentUser(empCode);
	}
	/**
	 * 
	 * @Description: 查询员工信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月24日 下午5:58:02
	 */
	public PortalCurrentUser findCurrentUserInfo(String empCode) {
		return portalCurrentUserDao.findCurrentUserInfo(empCode);
	}
	
	/**
	 * 
	 * @Description: 查询员工信息
	 * @param empCode
	 * @return
	 * @author huangting
	 * @date 2019年9月24日 下午5:58:02
	 */
	public PortalCurrentUser findCurrentUserNoEmpRelation(String empCode){
		return portalCurrentUserDao.findCurrentUserNoEmpRelation(empCode);
	}
	
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
	 * @date 2018年3月30日下午2:29:38
	 */
	public List<PortalCompanyNoticeVo> findNoticeTopicTen(String empCode, String typeId, String orgId) {
		List<PortalCompanyNoticeVo> portalCompanyNoticeVos = new ArrayList<>();
		try {
			String fullOrdId = portalCompanyNoticeDao.findFullOrgIdList(orgId);
			List<String> orgList = new ArrayList<>();
			if (null != fullOrdId) {
				String[] orgArr = fullOrdId.split(",");
				for (String orgFullId : orgArr) {
					orgList.add(orgFullId);
				}
			}
			portalCompanyNoticeVos = portalCompanyNoticeDao.findNoticeTopicTenNew(empCode, typeId, orgId, orgList);
		} catch (Exception e) {
			portalCompanyNoticeVos = portalCompanyNoticeDao.findNoticeTopicTen(empCode, typeId, orgId);
		}
		
		return portalCompanyNoticeVos;
	}
	
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
	 * @date 2018年3月30日下午2:30:40
	 */
	public PortalCompanyNoticeVo findById(Long id) {
		PortalCompanyNoticeVo portalCompanyNoticeVo = portalCompanyNoticeDao.findNoticeById(id);
		List<Map<String,Object>> noticeFiles = portalCompanyNoticeVo.getNoticeFiles();
		if (null != noticeFiles && !noticeFiles.isEmpty()) {
			StringBuffer noticeContent = new StringBuffer();
			noticeContent.append(portalCompanyNoticeVo.getContent() == null ? "" : portalCompanyNoticeVo.getContent());
			noticeContent.append("</br><hr />");
			for (Map<String, Object> map : noticeFiles) {
				noticeContent.append("<p>附件：");
				noticeContent.append("<a href='").append(map.get("filePath")).append("' target='_blank'><span style='color:#003399;'>");
				noticeContent.append(map.get("fileName")).append("</span></a></p>");
			}
			portalCompanyNoticeVo.setContent(noticeContent.toString());
		}
		
		return portalCompanyNoticeVo;
	}
	
	/**
	 * @Description:通过UserMenu的empCode和orgCode条件查询UserMenur的permissionId集合
	 * @author Ji Yongchao
	 * @param userMenu
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	public List<Long> findUserMenuByEmpCodeAndOrgCode(PortalUserMenu userMenu) {
		return portalUserMenuDao.findUserMenuByEmpCodeAndOrgCode(userMenu);
	}
	
	/**
	 * @Description:通过permissionId集合、empCode、orgCode删除该UserMenu
	 * @author Ji Yongchao
	 * @param ids：permissionId
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	public int deleteUserMenuByEmpAndOrgAndPerId(String empCode, String orgCode, List<Long> permissionIds) {
		return portalUserMenuDao.deleteUserMenuByEmpAndOrgAndPerId(empCode, orgCode, permissionIds);
	}
	/**
	 * @Description:批量保存常用菜单数据
	 * @author Ji Yongchao
	 * @param userMenuList
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	public int saveUserMenu(List<PortalUserMenu> userMenuList) {
		return portalUserMenuDao.insert(userMenuList);
	}
	
	/**
	 * @Description:empCode、orgCode删除该UserMenu
	 * @author Ji Yongchao
	 * @param empCode
	 * @param orgCode
	 * @return
	 * @date 2017年8月4日 上午9:44:47
	 */
	public int deleteUserMenuByEmpAndOrg(String empCode, String orgCode) {
		return portalUserMenuDao.deleteUserMenuByEmpAndOrg(empCode, orgCode);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存公司公告查看记录
	 * </pre>
	 * @param portalCompanyNoticeRecord
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月31日下午2:05:12
	 */
	public int saveNoticeRecord(PortalCompanyNoticeRecord portalCompanyNoticeRecord) {
		return portalCompanyNoticeRecordDao.saveNoticeRecord(portalCompanyNoticeRecord);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据公告ID和查看人查询信息
	 * </pre>
	 * @param portalCompanyNoticeRecord
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月31日下午2:07:42
	 */
	public int findRecordByNoticeId(PortalCompanyNoticeRecord portalCompanyNoticeRecord) {
		return portalCompanyNoticeRecordDao.findRecordByNoticeId(portalCompanyNoticeRecord);
	}
	
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
	 * @date 2018年4月2日下午3:52:06
	 */
	public List<PortalHomePageInfoVo> findHomePageInfo() {
		return portalHomePageInfoDao.findHomePageInfo();
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存浏览器信息
	 * </pre>
	 * @return
	 * @return List<PortalHomePageInfoVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午3:52:06
	 */
	public void saveBrowserInfo(PortalBrowserVo browserVo) {
		portalHomePageInfoDao.saveBrowserInfo(browserVo);
	}
	
	/**
	 * 批量保存浏览器信息
	 * @param msg
	 */
	public void saveBatchBrowserInfo(List<Object> msg) {
		portalHomePageInfoDao.saveBatchBrowserInfo(msg);
	}
	
	/**
	 * @Description: (findDictDataByTypeClassCode) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2018年5月3日 下午6:48:51
	 */
	public List<PortalDictDataVo> findDictDataByTypeClassCode(String typeClassCode){
		return portalDictDataDao.findDictDataByTypeClassCode(typeClassCode);
	}
	
	public void setPortalPermissionDao(IPortalPermissionDao portalPermissionDao) {
		this.portalPermissionDao = portalPermissionDao;
	}

	public void setPortalCompanyNoticeDao(IPortalCompanyNoticeDao portalCompanyNoticeDao) {
		this.portalCompanyNoticeDao = portalCompanyNoticeDao;
	}

	public void setPortalUserMenuDao(IPortalUserMenuDao portalUserMenuDao) {
		this.portalUserMenuDao = portalUserMenuDao;
	}

	public void setPortalCompanyNoticeRecordDao(
			IPortalCompanyNoticeRecordDao portalCompanyNoticeRecordDao) {
		this.portalCompanyNoticeRecordDao = portalCompanyNoticeRecordDao;
	}

	public void setPortalHomePageInfoDao(IPortalHomePageInfoDao portalHomePageInfoDao) {
		this.portalHomePageInfoDao = portalHomePageInfoDao;
	}

	public void setPortalCurrentUserDao(IPortalCurrentUserDao portalCurrentUserDao) {
		this.portalCurrentUserDao = portalCurrentUserDao;
	}

	public void setPortalDictDataDao(IPortalDictDataDao portalDictDataDao) {
		this.portalDictDataDao = portalDictDataDao;
	}

	/**
	 * 根据orgId获取上级级联
	 * @param orgId
	 * @return
	 */
	public String findFullOrgIdList(String orgId) {
		return portalCompanyNoticeDao.findFullOrgIdList(orgId);
	}
	
	public List<PortalCompanyNoticeVo> findNoticeAlert(String empCode, Date limitDate, String orgId) {
		String fullOrdId = portalCompanyNoticeDao.findFullOrgIdList(orgId);
		List<String> orgList = new ArrayList<>();
		if (null != fullOrdId) {
			String[] orgArr = fullOrdId.split(",");
			for (String orgFullId : orgArr) {
				orgList.add(orgFullId);
			}
		}
		return portalCompanyNoticeDao.findNoticeAlert(empCode, limitDate, orgList);
	}

	/**
	 * 根据permissionCode和systemCode查询菜单
	 * @param permissionCode
	 * @param systemCode
	 * @return
	 */
	public PortalPermissionVo findPermissionAndSystemCode(String permissionCode, String systemCode) {
		return portalPermissionDao.findPermissionAndSystemCode(permissionCode, systemCode);
	}
	
	/**
	 * 根据empCode和systemCode校验菜单权限
	 * @param empCode
	 * @param systemCode
	 * @return
	 */
	public int menuPermissionCheck(String empCode, String systemCode){
		return portalPermissionDao.menuPermissionCheck(empCode, systemCode);
	}
	
	public List<PortalPermissionVo> findPermissionByIds(List<Long> ids) {
		return portalPermissionDao.findPermissionByIds(ids);
	}
	
	/**
	 * 单条保存常用菜单数据
	 * @param userMenu
	 * @return
	 */
	public int saveUserMenu(PortalUserMenu userMenu) {
		return portalUserMenuDao.insert(userMenu);
	}
	/**
	 * 根据三个条件删除常用菜单
	 * @param empCode
	 * @param orgCode
	 * @param permissionId
	 * @return
	 */
	public int deleteUserMenuByParam(String empCode, String orgCode, Long permissionId) {
		
		return portalUserMenuDao.deleteUserMenuByParam(empCode, orgCode, permissionId);
	}
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID查询阅读数
	 * </pre>
	 * @param noticeId
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月19日上午11:21:38
	 */
	public int findCompanyViewNum(Long noticeId) {
		PortalCompanyNoticeRecord portalCompanyNoticeRecord = new PortalCompanyNoticeRecord();
		portalCompanyNoticeRecord.setNoticeId(noticeId);
		return portalCompanyNoticeRecordDao.findRecordByNoticeId(portalCompanyNoticeRecord);
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
	public List<NoticeParentInfoVo> findNoticeType() {
		
		return portalCompanyNoticeDao.findNoticeType();
	}
}
