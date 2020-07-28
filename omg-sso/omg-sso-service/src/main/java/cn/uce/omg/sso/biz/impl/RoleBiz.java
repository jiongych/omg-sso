/** 
 * @项目名称: FSP
 * @文件名称: RoleBiz implements IRoleBiz, IRoleApi 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.omg.sso.biz.IRoleBiz;
import cn.uce.omg.sso.service.RoleService;
import cn.uce.omg.vo.RoleVo;
import cn.uce.omg.vo.UserRoleRelVo;

import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * 角色BIZ实现类
 * @author huangting
 * @date 2017年6月9日 下午4:09:41
 */
public class RoleBiz implements IRoleBiz {

	private RoleService roleService;
	private Log LOG = LogFactory.getLog(RoleBiz.class);
	
	/**
	 * 查询所有可用角色
	 * @return
	 */
	@Override
	public List<RoleVo> findAllAvailableRole() {
		return this.roleService.findAllAvailableRole();
	}
	
	/**
	 * 根据条件查询角色
	 * @param roleVo 查询条件
	 * @return 角色列表
	 */
	@Override
	public List<RoleVo> findRoleByCondition(RoleVo roleVo) {
		return this.roleService.findRoleByCondition(roleVo);
	}

	/**
	 * 根据员工编号查询角色信息
	 * @param empId
	 * @return
	 */
	@Override
	public List<RoleVo> findRoleByEmpCode(String empCode){
		if (StringUtils.isBlank(empCode)) {
			LOG.warn("findRoleByEmpCode error. illegal parameter");
			return null;
		}
		return this.roleService.findRoleByEmpCode(empCode);
	}
	
	/**
	 * 查询用户角色关系
	 * @param userRoleRelVo
	 * @return
	 */
	@Override
	public List<UserRoleRelVo> findAllUserRoleRel() {
		return this.roleService.findAllUserRoleRel();
	}
	
	/**
	 * 根据条件查询用户角色关系
	 * @param userRoleRelVo
	 * @return
	 */
	@Override
	public List<UserRoleRelVo> findUserRoleRelByCondition(
			UserRoleRelVo userRoleRelVo) {
		return this.roleService.findUserRoleRelByCondition(userRoleRelVo);
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
