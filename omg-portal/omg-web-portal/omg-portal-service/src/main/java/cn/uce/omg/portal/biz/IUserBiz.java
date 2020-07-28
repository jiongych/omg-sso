package cn.uce.omg.portal.biz;

import java.util.List;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.omg.portal.vo.PortalMenuTreeVo;
import cn.uce.omg.portal.vo.OrgVo;
import cn.uce.omg.portal.vo.PortalUserRoleRelVo;
import cn.uce.omg.portal.vo.UserSearchVo;
import cn.uce.omg.portal.vo.UserVo;
import cn.uce.web.common.base.CurrentUser;

/**
 * @Description: (IUserBiz) 
 * @author XJ
 * @date 2017年7月29日 下午4:38:24
 */
public interface IUserBiz {
    
	/**
	 * @Description: (查询菜单树) 
	 * @param roles
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月29日 下午4:41:56
	 */
	List<PortalMenuTreeVo> getMenuTree(List<String> roles);
	
	/**
	 * @Description: (查询菜单树) 
	 * @param roles
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月29日 下午4:41:56
	 */
	List<PortalMenuTreeVo> findMenuTree(List<String> roles);
	
	/**
	 * @Description: (分页查询用户员工信息) 
	 * @param empUserVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:42:44
	 */
	public Pagination<UserVo> findUserByPage(UserVo userVo, Integer currentPage,Integer pageSize);

	/**
	 * @Description: (saveUserRoleRel) 
	 * @param userRoleRel
	 * @param roleCodeListStr
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年7月29日 下午4:43:31
	 */
	public int saveUserRoleRel(PortalUserRoleRelVo userRoleRelVo);

	/**
	 * @Description: (查询用户所有的权限码) 
	 * @param roles
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:44:40
	 */
	List<String> findUserAllPermissionCode(List<String> roles);
	
	/**
	 * @Description: (查询用户绑定的机构) 
	 * @param empId
	 * @return
	 * @author XJ
	 * @date 2017年7月26日 下午5:55:17
	 */
	List<OrgVo> findUserBindOrg(String empId);
	
	/**
	 * @Description: (根据用户名查询角色) 
	 * @param username
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午4:57:49
	 */
	List<String> findRoleByUserName(String username);
	
	/**
	 * @Description: (根据empCode查询用户) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年7月12日 上午10:44:16
	 */
	UserVo findUserByEmpCode(String empCode);
	
	/**
	 * @Description: (更新用户组织绑定关系) 
	 * @param userVo
	 * @return
	 * @author XJ
	 * @date 2017年8月1日 下午4:11:43
	 */
	int updateUserOrg(UserVo userVo,String zTreeOrgCode);
	
	/**
	 * @Description: (查询用户绑定的机构码) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年8月3日 上午9:02:55
	 */
	String findDataAuthByEmpCode(String empCode);
	
	/**
	 * @Description: (findCurrentUser) 
	 * @return
	 * @author XJ
	 * @date 2018年4月18日 下午7:16:54
	 */
	CurrentUser findCurrentUser(String empCode);

	/**
	 * @Description: 查询时间范围内更新的用户
	 * @return
	 * @author liyi
	 * @date 2018年5月31日 下午7:16:54
	 */
	List<String> findUserByDate(PortalDataPushVo dataPushVo);

	Pagination<UserVo> findUserByPageNew(UserSearchVo userVo, Page page);

	int updateUserOrgManager(UserVo userVo, String zTreeOrgCode);

	String findDataManagerByEmpCode(String empCode);
	
}
