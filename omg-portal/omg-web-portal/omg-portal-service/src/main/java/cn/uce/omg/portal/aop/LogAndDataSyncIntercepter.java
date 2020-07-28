package cn.uce.omg.portal.aop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.uce.core.mq.rocket.RocketTemplate;
import cn.uce.ols.log.vo.OperateLogVo;
import cn.uce.omg.portal.biz.impl.DataPushBiz;
import cn.uce.omg.portal.biz.impl.UserBiz;
import cn.uce.omg.portal.service.DictDataService;
import cn.uce.omg.portal.service.OperatorHandlerService;
import cn.uce.omg.portal.service.RoleService;
import cn.uce.omg.portal.util.LogConstants;
import cn.uce.omg.portal.vo.PortalDictDataVo;
import cn.uce.omg.portal.vo.PortalUserRoleRelVo;
import cn.uce.omg.portal.vo.RoleVo;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.util.WebUtil;

/**
 * 处理操作日志及推送切面类
 * @author uce
 */
public class LogAndDataSyncIntercepter {
	Logger LOG = LoggerFactory.getLogger(LogAndDataSyncIntercepter.class);
	@Autowired
	private OperatorHandlerService operatorHandlerService;
	@Autowired
	private DataPushBiz dataPushBiz;
	@Resource
	private DictDataService fspDictDataService;
	//向日志中心发送消息
	@Resource
	private RocketTemplate olsPushTemplate;
	@Resource
	private RoleService roleService;
	@Resource
	private UserBiz userBiz;
	
	
	/**
	 * 前置通知
	 * @param joinPoint
	 */
    public Object doInvoke(JoinPoint joinPoint, Object value){  
    	Signature signature = joinPoint.getSignature();
        if(null != signature) {
        	// 目标类名
            String targetName = signature.getDeclaringTypeName();
            // 目标方法名称
            String methodName = signature.getName();
            // 调用参数
            Object[] params = joinPoint.getArgs();
            // 根据类名及方法名判断具体处理逻辑
            handler(targetName,methodName,params);
        }
        return value;
    }

    public void before(JoinPoint joinPoint){  
    	Signature signature = joinPoint.getSignature();
        if(null != signature) {
        	// 目标类名
            String targetName = signature.getDeclaringTypeName();
            // 目标方法名称
            String methodName = signature.getName();
            // 调用参数
            Object[] params = joinPoint.getArgs();
            //向日志中中心发送日志消息
            try {
            	sendOlsOperateLog(targetName, methodName, params);
            } catch(Exception e) {
            	LOG.error("向日志中心发送消息失败，可以忽略" + e.getMessage());
            }
			
        }
    }
    /**
     * 根据类名及方法名判断具体处理逻辑
     * @param targetName
     * @param methodName
     * @param params
     */
	private void handler(String targetName, String methodName, Object[] params) {
		LogConstants keys = LogConstants.methodName(targetName + "." + methodName);
		try {
			List<PortalDictDataVo> rdtv = fspDictDataService.findByTypeClassCode("SYS_SWITCHS");
			if(null != rdtv && !rdtv.isEmpty() && Boolean.valueOf(rdtv.get(0).getTypeCode())) {
				// 0为处理角色，1为处理权限，2为处理数据字典，3为处理用户管理
				String tableName = keys.tableName();
				// 异步处理操作日志信息
				operatorHandlerService.build(params, keys.logCode(), tableName);
			}
		} catch (Exception e) {
			LOG.error("write log have exception" + e.getMessage());
		}
		// 推送数据
		dataPushBiz.push(params, keys);
	}  
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 向日志中心发送操作日志
	 * </pre>
	 * @param targetName
	 * @param methodName
	 * @param params
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年8月13日下午4:15:29
	 */
	private void sendOlsOperateLog(String targetName, String methodName, Object[] params) {
		try {
			CurrentUser user = WebUtil.getCurrentUser();
			if(null == user) {
				return;
			}
			LogConstants keys = LogConstants.methodName(targetName + "." + methodName);
			
			if (null == keys || null == params) {
				return;
			}
			String operateType = keys.name();
			if (null == operateType) {
				return;
			}
			OperateLogVo operateLog = new OperateLogVo();
			
			//角色分配权限
			if ("ROLE_UPDATE".equals(operateType)) {
				RoleVo roleVo = (RoleVo) params[0];
				List<String> oldPermissionList = roleVo.getPermissionCodeList();
				List<String> newPermissionList = roleService.findRolePermissionSCodeByRoleCode(roleVo.getRoleCode());
				//获取新增的数据
				List<String> addPermissionList = oldAndNewDataCompare(oldPermissionList, newPermissionList, 1);
				//获取删除的数据
				List<String> deletePermissionList = oldAndNewDataCompare(oldPermissionList, newPermissionList, 2);
				
				/** 经过对比后减少的数据 **/
				Map<String, List<String>> beforeValue = new HashMap<>();
				beforeValue.put("permission_code", deletePermissionList);
				operateLog.setBefVal(beforeValue);
				
				/** 经过对比后增加的数据 **/
				Map<String, List<String>> afterValue = new HashMap<>();
				afterValue.put("permission_code", addPermissionList);
				operateLog.setAftVal(afterValue);
				//查询条件
				Map<String, List<String>> keyFields =  new HashMap<>();
				keyFields.put("role_code", Arrays.asList(roleVo.getRoleCode()));
				operateLog.setKeys(keyFields);
				operateLog.setOptType(cn.uce.ols.log.constants.LogConstants.OPERATE_TYPE_UPDATE);
				operateLog.setPrimaryKey(roleVo.getRoleCode());
				operateLog.setDbName("omg");
		        operateLog.setSystemCode("omg");
		        operateLog.setTableName("omg_portal_role_permission_rel");
		        
		        operateLog.setUpdateEmp(user.getEmpCode());
		        operateLog.setUpdateEmpName(user.getEmpName());
		        operateLog.setUpdateOrg(user.getCmsOrgId());
		        operateLog.setUpdateOrgName(user.getCmsOrgName());
		        operateLog.setUpdateAccOrg(user.getCmsBaseOrgCode());
		        operateLog.setUpdateAccOrgName(user.getCmsOrgName());
		        operateLog.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
		        operateLog.setUpdateTime(new Date());
		        if(LOG.isInfoEnabled()){
					LOG.info("角色分配权限发送到日志中心："+JSON.toJSONString(operateLog));
				}
		        olsPushTemplate.send(operateLog, operateLog.getPrimaryKey());
		        return;
			}
		
			//用户分配角色
			if ("USER_ROLE_ASSIGNED".equals(operateType)) {
				PortalUserRoleRelVo userRoleVo =  (PortalUserRoleRelVo) params[0];
				List<String> oldUserRoleList = userRoleVo.getRoleCodeList();
				List<String> newUserRoleList = userBiz.findRoleByUserName(userRoleVo.getEmpCode());
				//获取删除的数据
				List<String> deleteUserRoleList = oldAndNewDataCompare(oldUserRoleList, newUserRoleList, 2);
				//获取新增的数据
				List<String> addUserRoleList = oldAndNewDataCompare(oldUserRoleList, newUserRoleList, 1);
				
				/** 经过对比后减少的数据 **/
				Map<String, List<String>> beforeValue = new HashMap<>();
				beforeValue.put("role_code", deleteUserRoleList);
				operateLog.setBefVal(beforeValue);
				
				/** 经过对比后增加的数据 **/
				Map<String, List<String>> afterValue = new HashMap<>();
				afterValue.put("role_code", addUserRoleList);
				operateLog.setAftVal(afterValue);
				//查询条件
				Map<String, List<String>> keyFields =  new HashMap<>();
				keyFields.put("emp_code", Arrays.asList(userRoleVo.getEmpCode()));
				operateLog.setKeys(keyFields);
				operateLog.setOptType(cn.uce.ols.log.constants.LogConstants.OPERATE_TYPE_UPDATE);
				operateLog.setPrimaryKey(user.getEmpCode());
				operateLog.setDbName("omg");
		        operateLog.setSystemCode("omg");
		        operateLog.setTableName("omg_portal_user_role_rel");
		        operateLog.setUpdateEmp(user.getEmpCode());
		        operateLog.setUpdateEmpName(user.getEmpName());
		        operateLog.setUpdateOrg(user.getCmsOrgId());
		        operateLog.setUpdateOrgName(user.getCmsOrgName());
		        operateLog.setUpdateAccOrg(user.getCmsBaseOrgCode());
		        operateLog.setUpdateAccOrgName(user.getCmsOrgName());
		        operateLog.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
		        operateLog.setUpdateTime(new Date());
		        if(LOG.isInfoEnabled()){
					LOG.info("用户分配角色发送到日志中心："+JSON.toJSONString(operateLog));
				}
		        olsPushTemplate.send(operateLog, operateLog.getPrimaryKey());
		        return;
			}
			
		} catch (Exception e) {
			LOG.error("send ols message error:" + e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 比较两个集合的参数，
	 * 如果resultType = 1：则返回oldString多出来的数据
	 * 如果resultType = 2：则返回newString多出来的数据
	 * </pre>
	 * @param oldString
	 * @param newString
	 * @param resultType
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年8月13日下午6:50:55
	 */
	private List<String> oldAndNewDataCompare(List<String> oldString, List<String> newString, int resultType) {
		List<String> resultList = new ArrayList<String>();
		StringBuffer paramSb = new StringBuffer();
		if (1 == resultType) {
			//返回oldString多出来的数据
			if (null == oldString || oldString.size() == 0) {
				return null;
			}
			if (null == newString || newString.size() == 0) {
				return oldString;
			}
			for (String old : oldString) {
				boolean flag = false;
				for (String newS : newString) {
					if (old.equals(newS)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					paramSb.append(old).append(",");
				}
			}
			resultList.add(paramSb.toString());
			return resultList;
		} else {
			//返回newString多出来的数据
			if (null == oldString || oldString.size() == 0) {
				return newString;
			}
			if (null == newString || newString.size() == 0) {
				return null;
			}
			
			for (String newS : newString) {
				boolean flag = false;
				for (String old : oldString) {
					if (newS.equals(old)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					paramSb.append(newS).append(",");
				}
			}
			resultList.add(paramSb.toString());
			return resultList;
		}
		
	}

}
