package cn.uce.omg.portal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.dao.IUserDao;
import cn.uce.omg.portal.dao.IUserOrgDao;
import cn.uce.omg.portal.dao.IUserOrgManagerDao;
import cn.uce.omg.portal.dao.IUserRoleRelDao;
import cn.uce.omg.portal.entity.PortalUserOrg;
import cn.uce.omg.portal.entity.PortalUserOrgManager;
import cn.uce.omg.portal.vo.OrgVo;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.omg.portal.vo.PortalUserRoleRelVo;
import cn.uce.omg.portal.vo.UserSearchVo;
import cn.uce.omg.portal.vo.UserVo;
import cn.uce.utils.StringUtil;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.util.WebUtil;

import com.alibaba.dubbo.common.utils.CollectionUtils;

/**
 * @Description: (UserService) 
 * @author XJ
 * @date 2017年7月29日 下午4:48:32
 */
@Service("userService")
public class UserService {
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUserRoleRelDao userRoleRelDao;
	@Autowired
	private IUserOrgDao userOrgDao;
	
	@Autowired
	private IUserOrgManagerDao userOrgManagerDao;
	
	/**
	 * @Description: (findUserByPage) 
	 * @param empUserVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:49:06
	 */
	public List<UserVo> findUserByPage(UserVo userVo,Integer startIndex,Integer pageSize) {
		return userDao.findUserByPage(userVo, startIndex, pageSize);
	}
	public Integer findUserCountByPage(UserVo userVo,Integer startIndex,Integer pageSize) {
		return userDao.findUserCountByPage(userVo, startIndex, pageSize);
	}
	
	public Pagination<UserVo> findUserByPageNew(UserSearchVo userVo,Page page) {
		CurrentUser userInfo = WebUtil.getCurrentUser();
		String orgCode = userOrgManagerDao.findDataManagerByEmpCode(userInfo.getEmpCode());
		if(!StringUtil.isEmpty(orgCode)){
			String[] list = orgCode.split(",");
			List<String> idList = Arrays.asList(list);
			if(CollectionUtils.isNotEmpty(idList)){
				userVo.setIdList(idList);
				userVo.setOrgId(null);
			}
		}
		return userDao.findUserByPageNew(userVo, page);
	}
	
	/**
	 * @Description: (保存用户角色关系) 
	 * @param userRoleRel
	 * @param roleCodeListStr
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月29日 下午4:49:38
	 */
	public int saveUserRoleRel(PortalUserRoleRelVo userRoleRelVo){
		if (userRoleRelVo == null) {
			return -1;
		}
		userRoleRelDao.deleteRelByEmpCode(userRoleRelVo.getEmpCode());
		if(userRoleRelVo.getRoleCodeList() != null && !userRoleRelVo.getRoleCodeList().isEmpty()){
			return userRoleRelDao.insertRel(userRoleRelVo);
		}
		return 0;
	}
	
	/**
	 * 获取角色权限列表
	 * 上级权限为空，返回全部权限
	 * @param roles 人资角色列表
	 * @param parentPermission 上级权限
	 * @return
	 * @throws Exception
	 * @auth raowb 2017年3月22日
	 */
	public List<String> findPermissionByRoleCodes(List<String> roles) throws Exception {
		if (roles == null || roles.isEmpty()) {
			return new ArrayList<String>();
		}
		return userDao.findPermissionByRoleCodes(roles);
	}
	
	/**
	 * @Description: (查询用户所有角色) 
	 * @param username
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:54:30
	 */
	public List<String> findRoleByUserName(String username){
		return userDao.findRoleByUserName(username);
	}
	
	
	/**
	 * @Description: (查询用户绑定的机构) 
	 * @param empId
	 * @return
	 * @author XJ
	 * @date 2017年7月26日 下午5:54:01
	 */
	public List<OrgVo> findUserBindOrg(String empId){
		return userDao.findUserBindOrg(empId);
	}
	
	/**
	 * @Description: (根据empCode查询用户) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年7月12日 上午10:43:20
	 */
	public UserVo findUserByEmpCode(String empCode){
		return userDao.findUserByEmpCode(empCode);
	}
	
	/**
	 * @Description: (新增用户组织绑定关系) 
	 * @param userOrgList
	 * @return
	 * @author XJ
	 * @date 2017年8月1日 下午4:17:32
	 */
	public int addUserOrg(List<PortalUserOrg> userOrgList){
		if(userOrgList != null && !userOrgList.isEmpty()){
			return userOrgDao.insert(userOrgList);
		}
		return 0;
	}
	
	/**
	 * @Description: (新增用户机构关系) 
	 * @param userOrg
	 * @return
	 * @author XJ
	 * @date 2017年8月10日 下午4:45:20
	 */
	public int addUserOrg(PortalUserOrg userOrg){
		return userOrgDao.insert(userOrg);
	}
	
	/**
	 * @Description: (新增用户管理机构机构范围) 
	 * @param userOrg
	 * @return
	 * @author XJ
	 * @date 2017年8月10日 下午4:45:20
	 */
	public int addUserOrgManager(PortalUserOrgManager userOrg){
		return userOrgManagerDao.insert(userOrg);
	}
	
	/**
	 * @Description: (根据工号删除用户组织管理范围) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年8月1日 下午4:18:16
	 */
	public int deleteUserOrgManagerByEmpCode(String empCode){
		return userOrgManagerDao.deleteUserOrgManagerByEmpCode(empCode);
	}
	
	/**
	 * @Description: (根据工号删除用户组织绑定关系) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年8月1日 下午4:18:16
	 */
	public int deleteUserOrgByEmpCode(String empCode){
		return userOrgDao.deleteUserOrgByEmpCode(empCode);
	}
	
	/**
	 * @Description: (按角色码查询用户角色关系数量) 
	 * @param roleCode
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 上午11:04:18
	 */
	public int findCountByRoleCode(String roleCode) {
		return userRoleRelDao.findCountByRoleCode(roleCode);
	}
	
	/**
	 * @Description: (查询用户绑定的机构码) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年8月3日 上午9:02:02
	 */
	public String findDataAuthByEmpCode(String empCode){
		return userOrgDao.findDataAuthByEmpCode(empCode);
	}
	
	/**
	 * @Description: (查询用户绑定的机构码) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年8月3日 上午9:02:02
	 */
	public String findDataManagerByEmpCode(String empCode){
		return userOrgManagerDao.findDataManagerByEmpCode(empCode);
	}
	
	/**
	 * @Description: (findCurrentUser) 
	 * @return
	 * @author XJ
	 * @date 2018年4月18日 下午7:16:18
	 */
	public CurrentUser findCurrentUser(String empCode){
		return userDao.findCurrentUser(empCode);
	}
	
	/**
	 * @Description: 查询时间范围内更新的用户
	 * @return
	 * @author liyi
	 * @date 2018年5月31日 下午7:16:54
	 */
	public List<String> findUserByDate(PortalDataPushVo dataPushVo) {
		return userDao.findUserByDate(dataPushVo);
	}
	
}
