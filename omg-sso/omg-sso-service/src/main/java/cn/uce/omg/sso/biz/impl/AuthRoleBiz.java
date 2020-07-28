/** 
 * @项目名称: FSP
 * @文件名称: AuthRoleBiz implements IAuthRoleApi, IAuthRoleBiz 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import cn.uce.omg.sso.api.IAuthRoleApi;
import cn.uce.omg.sso.biz.IAuthRoleBiz;
import cn.uce.omg.sso.biz.IEmpBiz;
import cn.uce.omg.sso.biz.IRoleBiz;
import cn.uce.omg.sso.constant.AuthConstants;
import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.util.EmpInfoUtil;
import cn.uce.omg.sso.util.StringUtil;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.EmpInfoVo;
import cn.uce.omg.sso.vo.EmpRoleRelVo;
import cn.uce.omg.sso.vo.ResultRoleVo;
import cn.uce.omg.sso.vo.UserInfoVo;
import cn.uce.omg.sso.vo.UserRoleVo;
import cn.uce.omg.sso.vo.UserSearchVo;
import cn.uce.omg.vo.EmpVo;
import cn.uce.omg.vo.RoleVo;
import cn.uce.omg.vo.UserRoleRelVo;

/**
 * 权限相关接口
 * @author tanchong
 *
 */
public class AuthRoleBiz implements IAuthRoleApi, IAuthRoleBiz {

	/** 
	 * 用户biz
	 */
	private IRoleBiz roleBiz;
	/** 
	 * 员工biz
	 */
	private IEmpBiz empBiz;
	private Log log = LogFactory.getLog(this.getClass());
	/** 
	 * 认证工具类
	 */
	private EmpInfoUtil empInfoUtil;

	/**
	 * 查询用户相关信息
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	public UserInfoVo findUser(UserSearchVo userSearchVo) throws Exception {
		//校验请求参数
		if (userSearchVo == null || StringUtil.isEmpty(userSearchVo.getUserName())) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		UserInfoVo userInfoVo = null;
		try {
			//根据用户名查询员工信息
			EmpInfoVo empInfoVo = empInfoUtil.getEmpInfoByKey(userSearchVo.getUserName(), AuthConstants.LOGIN_TYPE_EMP_CODE);
			if (empInfoVo == null || empInfoVo.getEmpVo() == null) {
				//用户不存在
				throw new GatewayException(ErrorCode.USER_NONENTITY, ErrorCode.USER_NONENTITY_MESSGE);
			}
			EmpVo empVo = empInfoVo.getEmpVo();
			userInfoVo = new UserInfoVo();
			// 拷贝相同的数据
			BeanUtils.copyProperties(empVo,userInfoVo);
			
		} catch (GatewayException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error("查询用户相关信息错误", ex);
			throw new GatewayException(ErrorCode.SYSTEM_ERROR);
		}
		return userInfoVo;
	}
	
	
	/**
	 * 根据用户查询用户角色
	 * @param empcode
	 * @return
	 * @throws Exception
	 */
	public AuthResultVo findUserRole(UserSearchVo userSearchVo) throws Exception {
		//请求参数校验
		if (userSearchVo == null ) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		String empCode = userSearchVo.getEmpCode();
		//员工编码不存在则取用户名
		if (StringUtil.isEmpty(empCode)) {
			empCode = userSearchVo.getUserName();
		}
		//员工编码为空抛出异常
		if (StringUtil.isEmpty(empCode)) {
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		
		AuthResultVo authResultVo = new AuthResultVo();
		try {
			// 设置参数
			authResultVo.setEmpCode(empCode);
			//根据员工编码查询角色列表
			List<RoleVo> roleList = roleBiz.findRoleByEmpCode(authResultVo.getEmpCode());
			if (roleList != null && !roleList.isEmpty()) {
				authResultVo.setUserRoleList(getUserRoleRel(roleList));
			}
		} catch (GatewayException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error("根据用户查询用户角色错误", ex);
			throw new GatewayException(ErrorCode.SYSTEM_ERROR);
		}
		return authResultVo;
	}

	/**
	 * 获取需要发送给第三方系统的角色关系信息
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List<UserRoleVo> getUserRoleRel(List<RoleVo> roleList) throws Exception {
		//请求参数校验
		if (roleList == null || roleList.isEmpty()) {
			return null;
		}
		List<UserRoleVo> userRoleList = new ArrayList<UserRoleVo>();
		for (RoleVo roleVo : roleList) {
			UserRoleVo userRoleVo = new UserRoleVo();
			//设置角色编码
			userRoleVo.setRoleCode(roleVo.getRoleCode());
			//设置角色名称
			userRoleVo.setRoleName(roleVo.getRoleName());
			//角色对象添加到集合
			userRoleList.add(userRoleVo);
		}
		return userRoleList;
	}

	
	/**
	 * 根据角色名称或编号查询角色
	 * @param userSearchVo
	 * @return
	 * @throws Exception
	 */
	public ResultRoleVo findRole(UserSearchVo userSearchVo) throws Exception {
		ResultRoleVo resultRoleVo = new ResultRoleVo();
		List<UserRoleVo> userRoleList = new ArrayList<UserRoleVo>();
		try {
			List<RoleVo> roleList = null;
			//无查询参数查询所有数据
			if (userSearchVo == null || StringUtil.isEmpty(userSearchVo.getRoleName()) && StringUtil.isEmpty(userSearchVo.getRoleCode())) {
				roleList = roleBiz.findAllAvailableRole();
			} else {
				RoleVo roleVo = new RoleVo();
				//设置角色编码
				roleVo.setRoleCode(userSearchVo.getRoleCode());
				//设置角色名称
				roleVo.setRoleName(userSearchVo.getRoleName());
				//设置删除标识
				roleVo.setDeleteFlag(false);
				//根据条件查询
				roleList = roleBiz.findRoleByCondition(roleVo);
			}
			if (roleList != null && roleList.size() > 0) {
				for (RoleVo roleVo : roleList) {
					UserRoleVo userRoleVo = new UserRoleVo();
					//设置角色编码
					userRoleVo.setRoleCode(roleVo.getRoleCode());
					//设置角色名称
					userRoleVo.setRoleName(roleVo.getRoleName());
					//设置备注
					userRoleVo.setRemark(roleVo.getRemark());
					//角色对象添加到集合
					userRoleList.add(userRoleVo);
				}
				//设置用户角色集合
				resultRoleVo.setUserRoleList(userRoleList);;
			}
		} catch (Exception ex) {
			log.error("查询角色错误：", ex);
			throw new GatewayException(ErrorCode.SYSTEM_ERROR);
		}
		return resultRoleVo;
	}
	
	/**
	 * 根据员工编号或角色编号查询角色关系
	 * @param userSearchVo
	 * @return
	 * @throws Exception
	 */
	public ResultRoleVo findRoleRel(UserSearchVo userSearchVo) throws Exception {
		ResultRoleVo resultRoleVo = new ResultRoleVo();
		List<EmpRoleRelVo> roleRelList = new ArrayList<EmpRoleRelVo>();
		try {
			List<UserRoleRelVo> roleRels = null;
			//无查询参数查询所有数据
			if (userSearchVo == null || userSearchVo.getEmpCode() == null && StringUtil.isEmpty(userSearchVo.getRoleCode())) {
				roleRels = roleBiz.findAllUserRoleRel();
			} else {
				UserRoleRelVo roleRelVo = new UserRoleRelVo();
				//设置角色编码
				roleRelVo.setRoleCode(userSearchVo.getRoleCode());
				//roleRelVo.setEmpId(userSearchVo.getEmpId());
				//设置员工编码
				roleRelVo.setEmpCode(userSearchVo.getEmpCode());
				//根据条件查询
				roleRels = roleBiz.findUserRoleRelByCondition(roleRelVo);
			}
			if (roleRels != null && roleRels.size() > 0) {
				for (UserRoleRelVo roleRelVo : roleRels) {
					EmpRoleRelVo empRoleRelVo = new EmpRoleRelVo();
					//设置员工id
					empRoleRelVo.setEmpId(roleRelVo.getEmpId());
					//设置角色编码
					empRoleRelVo.setRoleCode(roleRelVo.getRoleCode());
					//员工角色关联对象添加到集合
					roleRelList.add(empRoleRelVo);
				}
				//设置角色关联集合
				resultRoleVo.setRoleRelList(roleRelList);
			}
		} catch (Exception ex) {
			log.error("查询角色关系错误：", ex);
			throw new GatewayException(ErrorCode.SYSTEM_ERROR);
		}
		return resultRoleVo;
	}
	/**
	 * @return the roleBiz
	 */
	public IRoleBiz getRoleBiz() {
		return roleBiz;
	}


	/**
	 * @return the empBiz
	 */
	public IEmpBiz getEmpBiz() {
		return empBiz;
	}


	/**
	 * @return the empInfoUtil
	 */
	public EmpInfoUtil getEmpInfoUtil() {
		return empInfoUtil;
	}


	/**
	 * @param roleBiz the roleBiz to set
	 */
	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
	}


	/**
	 * @param empBiz the empBiz to set
	 */
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}


	/**
	 * @param empInfoUtil the empInfoUtil to set
	 */
	public void setEmpInfoUtil(EmpInfoUtil empInfoUtil) {
		this.empInfoUtil = empInfoUtil;
	}
}
