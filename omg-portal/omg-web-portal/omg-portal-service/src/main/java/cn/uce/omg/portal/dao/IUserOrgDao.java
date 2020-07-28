package cn.uce.omg.portal.dao;

import org.springframework.stereotype.Repository;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalUserOrg;

@Repository("fspUserOrgDao")
public interface IUserOrgDao extends IBaseDao<PortalUserOrg, Long>{
	
	/**
	 * @Description: (删除用户绑定的机构码) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年8月3日 上午9:01:20
	 */
	int deleteUserOrgByEmpCode(String empCode);
	
	/**
	 * @Description: (查询用户绑定的机构码) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午9:01:42
	 */
	String findDataAuthByEmpCode(String empCode);
	
}
