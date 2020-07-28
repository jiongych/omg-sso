package cn.uce.omg.portal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalUser;
import cn.uce.omg.portal.vo.OrgVo;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.omg.portal.vo.UserSearchVo;
import cn.uce.omg.portal.vo.UserVo;
import cn.uce.web.common.base.CurrentUser;

/**
 * @Description: (IUserDao) 
 * @author XJ
 * @date 2017年7月29日 下午5:05:25
 */
@Repository("fspUserDao")
public interface IUserDao extends IBaseDao<PortalUser, Long> {
    
	/**
	 * 根据条件查询，返回员工用户信息
	 * @param empUserVo
	 * @param page
	 * @param sorts
	 * @return
	 * @author raowb 2017年3月11日
	 */
	public List<UserVo> findUserByPage(@Param("userVo")UserVo userVo,@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
	public Integer findUserCountByPage(@Param("userVo")UserVo userVo,@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);

	
	/**
	 * 根据条件查询，返回员工用户信息
	 * @param empUserVo
	 * @param page
	 * @param sorts
	 * @return
	 * @author raowb 2017年3月11日
	 */
	public Pagination<UserVo> findUserByPageNew(UserSearchVo userVo,Page page);
	/**
	 * 获取角色权限列表
	 * 上级权限传空返回全部权限; 上级权限传0返回顶层菜单
	 * @param roles 人资角色列表
	 * @param parentPermission 上级权限
	 * @return
	 * @auth raowb 2017年3月22日
	 */
	public List<String> findPermissionByRoleCodes(List<String> roles) throws Exception;
	
	/**
	 * @Description: (根据用户名查询角色) 
	 * @param username
	 * @return
	 * @author XJ
	 * @date 2017年7月29日 下午5:06:37
	 */
	public List<String> findRoleByUserName(String username);
	
	/**
	 * @Description: (根据empCode查询用户) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年7月12日 上午10:42:03
	 */
	UserVo findUserByEmpCode(String empCode);
	
	/**
	 * @Description: (查询用户绑定的机构) 
	 * @param empId
	 * @return
	 * @author XJ
	 * @date 2017年7月26日 下午5:51:41
	 */
	List<OrgVo> findUserBindOrg(String empId);
	
	/**
	 * @Description: (查询当前登陆用户) 
	 * @return
	 * @author XJ
	 * @date 2018年4月18日 下午7:15:15
	 */
	CurrentUser findCurrentUser(String empCode);
	
	/**
	 * @Description: 查询时间范围内更新的用户
	 * @return
	 * @author liyi
	 * @date 2018年5月31日 下午7:16:54
	 */
	public List<String> findUserByDate(PortalDataPushVo dataPushVo);
	
}
