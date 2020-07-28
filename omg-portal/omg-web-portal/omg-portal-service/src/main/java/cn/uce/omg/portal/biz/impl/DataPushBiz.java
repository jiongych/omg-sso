package cn.uce.omg.portal.biz.impl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.mq.rocket.RocketTemplate;
import cn.uce.omg.gateway.exception.GatewayException;
import cn.uce.omg.portal.biz.IDataPushBiz;
import cn.uce.omg.portal.biz.IDictDataBiz;
import cn.uce.omg.portal.biz.IPermissionBiz;
import cn.uce.omg.portal.biz.IPushErrorLogBiz;
import cn.uce.omg.portal.biz.IRoleBiz;
import cn.uce.omg.portal.biz.IUserBiz;
import cn.uce.omg.portal.util.LogConstants;
import cn.uce.omg.portal.util.PushErrorLogConstants;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.omg.portal.vo.PortalDictDataVo;
import cn.uce.omg.portal.vo.PortalDictTypeVo;
import cn.uce.omg.portal.vo.PortalUserRoleRelVo;
import cn.uce.omg.portal.vo.PushErrorLogVo;
import cn.uce.omg.portal.vo.RoleVo;
import cn.uce.omg.portal.vo.UserVo;
import cn.uce.utils.JsonUtil;
import cn.uce.utils.StringUtil;
import cn.uce.web.common.util.GZIPHelper;
import cn.uce.web.common.util.WebUtil;

/**
 * 数据推送具体实现类
 * 
 * @author uce
 */
@Service("dataPushBiz")
public class DataPushBiz implements IDataPushBiz {
	Logger LOG = LoggerFactory.getLogger(DataPushBiz.class);
	@Autowired
	private IUserBiz userBiz;
	@Autowired
	private IRoleBiz roleBiz;
	@Autowired
	private IPermissionBiz permissionBiz;
	@Autowired
	private IDictDataBiz fspDictDataBiz;
	@Autowired
	private IPushErrorLogBiz pushErrorLogBiz;
	@Autowired
	private Map<String, RocketTemplate> mqTemplateMap;
	@Value("${pushErrorLog.whenFailProcessIntervalBase}")
	private int whenFailProcessIntervalBase;
	// 页面重推类型
	private final static String[] dataTypes = {"SYNC_USER_ROLE","SYNC_USER_DATA_PERMISSION","SYNC_PERMISSION","SYNC_ROLE","SYNC_DICT_TYPE","SYNC_DICT_DATA"};
    // 页面重推MQ模板
	private final static String[] mqTemplete = {"roleRepairPushTemplate","permissionRepairPushTemplate","userRoleRelRepairPushTemplate","dictDataRepairPushTemplate"};
	/**
	 * 根据LogConstants判断具体操作，实现具体细节
	 * 
	 * @throws IOException
	 */
	@Override
	public int push(Object[] obj, LogConstants constants) {
		// 角色管理操作同步數據
		// 角色管理操作同步數據
		try {
			if (LogConstants.ROLE_ADD.logCode().equals(constants.logCode())
					|| LogConstants.ROLE_UPDATE.logCode().equals(constants.logCode())
					|| LogConstants.USER_DATA.logCode().equals(constants.logCode())) {
				obj = new Object[]{GZIPHelper.zip(JsonUtil.toJson(obj))};
			}
			send(obj, constants.operatorType(), constants.mqTemplate());
		} catch (Exception e) {
			LOG.error("data sync error:" + e.getMessage());
			pushErrorLogBiz.savePushErrorLog(buildPushErrorLog(obj, constants.operatorType(), constants.mqTemplate()));
		} 
		return 1;
	}

	/**
	 * 构建数据同步异常日志
	 * @param obj
	 * @param operatorType
	 * @param mqTemplate
	 * @return
	 */
	private PushErrorLogVo buildPushErrorLog(Object[] obj, String operatorType,
			String mqTemplate) {
		PushErrorLogVo pushErrorLogVo = new PushErrorLogVo();
		pushErrorLogVo.setReqId(String.valueOf(System.currentTimeMillis()));
		pushErrorLogVo.setMqTemplete(mqTemplate);
		pushErrorLogVo.setOperatorType(operatorType);
		pushErrorLogVo.setProcessStatus(PushErrorLogConstants.PROCESS_STATUS_UNPROCESS);
		pushErrorLogVo.setNextProcessTime(new Date(System.currentTimeMillis() + whenFailProcessIntervalBase * 1000));
		pushErrorLogVo.setSyncContent(JsonUtil.toJson(obj));
		pushErrorLogVo.setTotalProcessNum(0);
		pushErrorLogVo.setProcessNum(0);
		pushErrorLogVo.setVersion("1.0");
		pushErrorLogVo.setCreateEmp(WebUtil.getCurrentUser().getEmpCode());
		pushErrorLogVo.setCreateTime(new Date());
		return pushErrorLogVo;
	}

	/**
	 * 通過MQ發送異步消息，同步數據
	 * 
	 * @param obj
	 * @param oType
	 * @param mqTemplate
	 */
	public void send(Object[] obj, String oType, String mqTemplate) {
		RocketTemplate template = mqTemplateMap.get(mqTemplate);
		template.send(JsonUtil.toJson(obj), oType);
	}

	/**
	 * 推送失败重新推送
	 */
	@Override
	public int resend(String obj, String otype, String mqTemplate) {
		RocketTemplate template = mqTemplateMap.get(mqTemplate);
		template.send(obj, otype);
		return 0;
	}

	/**
	 * 处理页面重推信息
	 * @param t
	 * @param constants
	 * @return
	 */
	@Override
	public Map<String, Object> processPushData(PortalDataPushVo dataPushVo) {
		Map<String, Object> result = null;
		try {
			if(null == dataPushVo || StringUtil.isBlank(dataPushVo.getDataType())){
				result = new HashMap<String,Object>();
				result.put("参数为空", dataPushVo);
			}
			if(StringUtil.equals(dataTypes[0], dataPushVo.getDataType())) {
				result = processUserRoleData(dataPushVo);
			} else if(StringUtil.equals(dataTypes[1], dataPushVo.getDataType())) {
				result = processUserPermissionData(dataPushVo);
			} else if(StringUtil.equals(dataTypes[2], dataPushVo.getDataType())) {
				result = processPermissionData(dataPushVo);
			} else if(StringUtil.equals(dataTypes[3], dataPushVo.getDataType())) {
				result = processRoleData(dataPushVo);
			} else if(StringUtil.equals(dataTypes[4], dataPushVo.getDataType())) {
				result = processDictTypeData(dataPushVo);
			} else if(StringUtil.equals(dataTypes[5], dataPushVo.getDataType())) {
				result = processDictData(dataPushVo);
			}
		} catch (Exception e) {
			result.put(dataPushVo.getReceiver(), e.getMessage());
		}
		return result;
	}
    
	/**
	 * 处理数据字典数据
	 * @param dataPushVo
	 * @return 
	 */
	private Map<String, Object> processDictData(PortalDataPushVo dataPushVo) {
		Map<String, Object> result = new HashMap<String,Object>();
		if(null == dataPushVo) {
			throw new NullPointerException("推送数据字典数据条件dataPushVo为null");
		}
		String[] dictDatas = dataPushVo.getPushRange().split(",");
		for(String dictData : dictDatas) {
			PortalDictDataVo param = new PortalDictDataVo();
			param.setTypeCode(dictData);
			PortalDictDataVo dictDataVo = fspDictDataBiz.findExitTypeCode(param);
			String[] receivers = dataPushVo.getReceiver().split(",");
			Object[] obj = new Object[]{dictDataVo};
			result.put(dataPushVo.getReceiver(), obj);
			RocketTemplate template = mqTemplateMap.get(mqTemplete[3]);
			// 循环处理系统推送
			for(String receiver : receivers) {
				template.send(JsonUtil.toJson(obj), receiver, LogConstants.DICTDATA_UPDATE.operatorType());
			}
		}
		return result;
	}

	/**
	 * 处理数据字典类型
	 * @param dataPushVo
	 * @return 
	 */
	@SuppressWarnings("null")
	private Map<String, Object> processDictTypeData(PortalDataPushVo dataPushVo) {
		Map<String, Object> result = new HashMap<String,Object>();
		if(null == dataPushVo && StringUtil.isBlank(dataPushVo.getPushRange())) {
			throw new NullPointerException("推送数据字典类型条件dataPushVo为null");
		}
		// 如果范围为空，则根据时间同步数据字典类型
		if(StringUtil.isBlank(dataPushVo.getPushRange())) {
			PortalDictTypeVo pddtv = new PortalDictTypeVo();
			pddtv.setCreateTime(dataPushVo.getStartTime());
			pddtv.setUpdateTime(dataPushVo.getEndTime());
			List<PortalDictTypeVo> list = fspDictDataBiz.findDictTypeList(pddtv);
			result.put(dataPushVo.getReceiver(), list);
			if(null != list && !list.isEmpty()) {
				for(PortalDictTypeVo pdtv : list) {
					sendDictType(dataPushVo, pdtv);
				}
			}
		} else {
			String[] dictTypes = dataPushVo.getPushRange().split(",");
			result.put(dataPushVo.getReceiver(), dictTypes);
			if(null != dictTypes && dictTypes.length > 0) {
				for(String dictType : dictTypes) {
					PortalDictTypeVo dictTypeVo = fspDictDataBiz.findByTypeClassCode(dictType);
					sendDictType(dataPushVo, dictTypeVo);
				}
			}
		}
		return result;
	}
    
	/**
	 * 同步发送数据字典类型
	 * @param dataPushVo
	 * @param dictTypeVo
	 */
	private void sendDictType(PortalDataPushVo dataPushVo,
			PortalDictTypeVo dictTypeVo) {
		String[] receivers = dataPushVo.getReceiver().split(",");
		Object[] obj = new Object[]{dictTypeVo};
		RocketTemplate template = mqTemplateMap.get(LogConstants.DICTDATA_SYS_UPDATE.mqTemplate());
		// 循环处理系统推送
		for(String receiver : receivers) {
			template.send(JsonUtil.toJson(obj), receiver, LogConstants.DICTDATA_SYS_UPDATE.operatorType());
			LOG.info("数据字典推送-主题：omgPortalDictDataRepairPush|接收系统：" + receiver + "|key:" + LogConstants.DICTDATA_SYS_UPDATE.operatorType() + "|推送数据：" + JsonUtil.toJson(obj));
		}
	}
	
	/**
	 * 处理用户角色
	 * @param dataPushVo
	 * @return 
	 */
	private Map<String, Object> processUserRoleData(PortalDataPushVo dataPushVo) {
		Map<String, Object> result = new HashMap<String,Object>();
		List<Object> resultList = new ArrayList<Object>();
		if(null == dataPushVo) {
			throw new NullPointerException("推送条件dataPushVo为null");
		}
		String[] empCodes = StringUtil.isBlank(dataPushVo.getPushRange())?null:dataPushVo.getPushRange().split(",");
		if(null == empCodes && null != dataPushVo.getStartTime()) {
			if(null == dataPushVo.getEndTime()) {
				dataPushVo.setEndTime(new Date());
			}
			List<String> list = userBiz.findUserByDate(dataPushVo);
			if(null != list && !list.isEmpty()) {
				empCodes = new String[list.size()];
				empCodes = list.toArray(empCodes);
			}
		}
		// 循环处理用户角色
		if(null != empCodes && empCodes.length > 0){
			for(String empCode : empCodes) {
				PortalUserRoleRelVo userRoleRelVo = new PortalUserRoleRelVo();
				List<RoleVo> roleList = roleBiz.findRoleByUser(empCode);
				List<String> list = new ArrayList<String>();
				// 处理用户对应角色编码
				if(null != roleList && !roleList.isEmpty()) {
					userRoleRelVo.setCreateEmp(roleList.get(0).getCreateEmp());
					userRoleRelVo.setCreateOrg(roleList.get(0).getCreateOrg());
					userRoleRelVo.setCreateTime(roleList.get(0).getCreateTime());
					userRoleRelVo.setUpdateTime(roleList.get(0).getUpdateTime());
					for(RoleVo roleVo : roleList) {
						list.add(roleVo.getRoleCode());
					}
				}
				userRoleRelVo.setRoleCodeList(list);
				userRoleRelVo.setEmpCode(empCode);
				String[] receivers = dataPushVo.getReceiver().split(",");
				Object[] obj = new Object[]{userRoleRelVo};
				RocketTemplate template = mqTemplateMap.get(mqTemplete[2]);
				// 循环处理系统推送
				for(String receiver : receivers) {
					template.send(JsonUtil.toJson(obj), receiver, LogConstants.USER_ROLE_ASSIGNED.operatorType());
					LOG.info("用户角色推送-主题：omgPortalUserRoleRelRepairPush|接收系统：" + receiver + "|key:" + LogConstants.USER_ROLE_ASSIGNED.operatorType() + "|推送数据：" + JsonUtil.toJson(obj));
					resultList.add(obj);
				}
			}
		}
		result.put(dataPushVo.getReceiver(), resultList);
		return result;
	}

	/**
	 * 处理同步权限数据
	 * @param dataPushVo
	 * @return 
	 */
	private Map<String, Object> processPermissionData(PortalDataPushVo dataPushVo) {
		Map<String, Object> result = new HashMap<String,Object>();
		List<Object> resultList = new ArrayList<Object>();
		if(null == dataPushVo) {
			throw new NullPointerException("推送权限条件dataPushVo为null");
		}
		String[] permissions = StringUtil.isBlank(dataPushVo.getPushRange())?null:dataPushVo.getPushRange().split(",");
		if(null == permissions && null != dataPushVo.getStartTime()) {
			if(null == dataPushVo.getEndTime()) {
				dataPushVo.setEndTime(new Date());
			}
			List<String> list = permissionBiz.findPermissionCodeByDate(dataPushVo);
			if(null != list && !list.isEmpty()) {
				permissions = new String[list.size()];
				permissions = list.toArray(permissions);
			}
		}
		// 循环处理权限数据
		if(null != permissions && permissions.length > 0) {
			for(String permission : permissions) {
				PermissionVo permissionVo = permissionBiz.findByPermissionCode(permission);
				permissionVo.setOldPermissionLevels(permissionVo.getPermissionLevels());
				String[] receivers = dataPushVo.getReceiver().split(",");
				RocketTemplate template = mqTemplateMap.get(mqTemplete[1]);
				for(String receiver : receivers) {
					template.send(JsonUtil.toJson(new Object[]{permissionVo}) , receiver, LogConstants.PERMISSION_UPDATE.operatorType());
					LOG.info("权限管理推送-主题：omgPortalPermissionRepairPush|接收系统：" + receiver + "|key:" + LogConstants.PERMISSION_UPDATE.operatorType() + "|推送数据：" + JsonUtil.toJson(permissionVo));
					resultList.add(permissionVo);
				}
			}
		}
		result.put(dataPushVo.getReceiver(), resultList);
		return result;
	}

	/**
	 * 处理用户权限问题
	 * @param dataPushVo
	 * @return 
	 */
	private Map<String, Object> processUserPermissionData(PortalDataPushVo dataPushVo) {
		Map<String, Object> result = new HashMap<String,Object>();
		List<Object> resultList = new ArrayList<Object>();
		if(null == dataPushVo) {
			throw new NullPointerException("推送条件dataPushVo为null");
		}
		try {
			String[] empCodes = StringUtil.isBlank(dataPushVo.getPushRange())?null:dataPushVo.getPushRange().split(",");
			if(null != empCodes && null != dataPushVo.getStartTime()) {
				if(null == dataPushVo.getEndTime()) {
					dataPushVo.setEndTime(new Date());
				}
				List<String> list = userBiz.findUserByDate(dataPushVo);
				if(null != list && !list.isEmpty()) {
					empCodes = new String[list.size()];
					empCodes = list.toArray(empCodes);
				}
			}
			// 循环处理用户数据权限
			if(null != empCodes && empCodes.length > 0) {
				for(String empCode : empCodes) {
					UserVo userVo = userBiz.findUserByEmpCode(empCode);
					String usrOrg = userBiz.findDataAuthByEmpCode(empCode);
					String[] receivers = dataPushVo.getReceiver().split(",");
					Object[] usrOrgs = new Object[]{userVo,usrOrg};
					Object[] obj = new Object[]{GZIPHelper.zip(JsonUtil.toJson(usrOrgs))};
					// 循环接收系统推送
					for(String receiver : receivers) {
						RocketTemplate template = mqTemplateMap.get(mqTemplete[2]);
						template.send(JsonUtil.toJson(obj), receiver, LogConstants.USER_DATA.operatorType());
						LOG.info("用户权限推送-主题：omgPortalUserRoleRelRepairPush|接收系统：" + receiver + "|key:" + LogConstants.USER_DATA.operatorType() + "|推送数据：" + JsonUtil.toJson(obj));
						resultList.add(usrOrgs);
					}
				}
			}
		} catch (IOException e) {
			LOG.error("用户权限推送失败-主题：omgPortalUserRoleRelRepairPush|接收系统：" + dataPushVo.getReceiver() + "|key:" + LogConstants.USER_DATA.operatorType() + "|推送数据：" + JsonUtil.toJson(dataPushVo));
		    throw new GatewayException(e.getMessage());
		}
		result.put(dataPushVo.getReceiver(), resultList);
		return result;
	}

	/**
	 * 处理角色同步数据
	 * @param dataPushVo
	 * @return 
	 */
	private Map<String, Object> processRoleData(PortalDataPushVo dataPushVo) {
		Map<String, Object> result = new HashMap<String,Object>();
		List<Object> resultList = new ArrayList<Object>();
		if(null == dataPushVo) {
			throw new NullPointerException("同步角色数据查询条件为空");
		}
		String[] roleCodes = StringUtil.isBlank(dataPushVo.getPushRange())?null:dataPushVo.getPushRange().split(",");
		if(null == roleCodes && null != dataPushVo.getStartTime()) {
			if(null == dataPushVo.getEndTime()) {
				dataPushVo.setEndTime(new Date());
			}
			List<String> roles = roleBiz.findRoleByDate(dataPushVo);
			if(null != roles && !roles.isEmpty()) {
				roleCodes = new String[roles.size()];
				roleCodes = roles.toArray(roleCodes);
			}
		}
		RocketTemplate template = mqTemplateMap.get(mqTemplete[0]);
		// 处理不同的角色
		if(null != roleCodes && roleCodes.length > 0) {
			for(String roleCode : roleCodes) {
				RoleVo dbRoleVo = roleBiz.findRoleSyncByCode(roleCode);
				List<PermissionVo> pvList = permissionBiz.findPermissionByRoleCode(roleCode);
				List<String> permissionList = new ArrayList<String>();
				List<String> systemCodeList = new ArrayList<>();
				for(PermissionVo pv : pvList) {
					permissionList.add(pv.getPermissionCode());
					systemCodeList.add(pv.getSystemCode());
				}
				dbRoleVo.setPermissionCodeList(permissionList);
				dbRoleVo.setSystemCodeList(systemCodeList);
				String[] receivers = dataPushVo.getReceiver().split(",");
				// 处理不同的系统
				for(String receiver : receivers) {
					try {
						template.send(JsonUtil.toJson(new Object[]{GZIPHelper.zip(JsonUtil.toJson(dbRoleVo))}), receiver, LogConstants.ROLE_UPDATE.operatorType());
					    LOG.info("角色推送-主题：omgPortalRoleRepairPush|接收系统：" + receiver + "|key:" + LogConstants.ROLE_UPDATE.operatorType() + "|推送数据：" + JsonUtil.toJson(dbRoleVo));
					    resultList.add(dbRoleVo);
					} catch (IOException e) {
					    LOG.info("角色推送失败-主题：omgPortalRoleRepairPush|接收系统：" + receiver + "|key:" + LogConstants.ROLE_UPDATE.operatorType() + "|推送数据：" + JsonUtil.toJson(dbRoleVo));
						LOG.error("推送管理页面role sync error:" + e.getMessage());
					    throw new GatewayException(e.getMessage());
					}
				}
			}
		}
		result.put(dataPushVo.getReceiver(), resultList);
		return result;
	}
    
	/**
	 * 查询推送的数据范围
	 * @param dataPushVo
	 * @return
	 */
	@Override
	public List<Object> findPushRange(PortalDataPushVo dataPushVo) {
		if(null == dataPushVo) {
			throw new NullPointerException("同步数据查询同步范围异常,dataPushVo is null");
		}
		List<Object> data = new ArrayList<Object>();
		String dataType = dataPushVo.getDataType();
		// 查询用户角色及用户权限类型范围用户
		if(StringUtil.equals(dataType, dataTypes[0]) || StringUtil.equals(dataType, dataTypes[1])) {
			UserVo userVo = new UserVo();
			userVo.setEmpName(dataPushVo.getPushRange());
			Pagination<UserVo> pagination = userBiz.findUserByPage(userVo, 1, 10);
			List<UserVo> list = pagination.getData();
			// 构建用户角色及用户权限范围
			for(UserVo vo : list) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", vo.getEmpCode());
				map.put("name", vo.getEmpName());
				data.add(map);
			}
	    // 查询权限类型菜单
		} else if(StringUtil.equals(dataType, dataTypes[2])) {
			PermissionVo permissionVo = new PermissionVo();
			permissionVo.setPermissionName(dataPushVo.getPushRange());
			Page page = new Page();
			page.setCurrentPage(1);
			page.setPageSize(10);
			Pagination<PermissionVo> permissionList = permissionBiz.findPermissionByCondition(permissionVo, page);
			List<PermissionVo> list = permissionList.getData();
			// 构建权限范围
			for(PermissionVo vo : list) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", vo.getPermissionCode());
				map.put("name", vo.getPermissionName());
				data.add(map);
			}
	    // 查询角色
		} else if(StringUtil.equals(dataType, dataTypes[3])) {
			RoleVo roleVo = new RoleVo();
			roleVo.setRoleName(dataPushVo.getPushRange());
			Pagination<RoleVo> roleList = roleBiz.findRoleByPage(roleVo, 1, 10);
			List<RoleVo> roles = roleList.getData();
			// 构建角色范围
			for(RoleVo vo : roles) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", vo.getRoleCode());
				map.put("name", vo.getRoleName());
				data.add(map);
			}
	    // 查询数据字典类型	
		} else if(StringUtil.equals(dataType, dataTypes[4])) {
			PortalDictTypeVo dictTypeVo = new PortalDictTypeVo();
			dictTypeVo.setTypeClassName(dataPushVo.getPushRange());
			List<PortalDictTypeVo> list = fspDictDataBiz.findDictTypeList(dictTypeVo);
			for(PortalDictTypeVo vo : list) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", vo.getTypeClassCode());
				map.put("name", vo.getTypeClassName());
				data.add(map);
			}
	    // 查询数据字典数据
		} else if(StringUtil.equals(dataType, dataTypes[5])) {
			PortalDictDataVo dictDataVo = new PortalDictDataVo();
			dictDataVo.setTypeClassName(dataPushVo.getPushRange());
			Page page = new Page();
			page.setCurrentPage(1);
			page.setPageSize(10);
			Pagination<PortalDictDataVo> pagination = fspDictDataBiz.findDictDataByPage(dictDataVo, page);
			List<PortalDictDataVo> roles = pagination.getData();
			// 构建数据字典数据范围
			for(PortalDictDataVo vo : roles) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", vo.getTypeCode());
				map.put("name", vo.getTypeName());
				data.add(map);
			}
		}
		return data;
	}
}
