package cn.uce.omg.portal.util;

/**
 * 操作日志常量类
 * @author uce
 */
public enum LogConstants {
	// 角色操作日志操作类型
	ROLE_ADD("cn.uce.omg.portal.biz.impl.RoleBiz.addRole","13_10","OMG_PORTAL_ROLE_PERMISSION_REL|OMG_PORTAL_ROLE","1","rolePushTemplate"),
	ROLE_UPDATE("cn.uce.omg.portal.biz.impl.RoleBiz.updateRoleByRoleCode","13_12","OMG_PORTAL_ROLE_PERMISSION_REL|OMG_PORTAL_ROLE","3","rolePushTemplate"),
	ROLE_DELETE("cn.uce.omg.portal.biz.impl.RoleBiz.deleteRole","13_11","OMG_PORTAL_ROLE_PERMISSION_REL|OMG_PORTAL_ROLE","2","rolePushTemplate"),
	// 数据字典操作日志操作类型
	DICTDATA_ADD("cn.uce.omg.portal.biz.impl.DictDataBiz.addDictData","12_10","OMG_PORTAL_DICT_DATA","1","dictDataPushTemplate"),
	DICTDATA_UPDATE("cn.uce.omg.portal.biz.impl.DictDataBiz.updateDictData","12_12","OMG_PORTAL_DICT_DATA","3","dictDataPushTemplate"),
	DICTDATA_DELETE("cn.uce.omg.portal.biz.impl.DictDataBiz.deleteDictData","12_11","OMG_PORTAL_DICT_DATA","2","dictDataPushTemplate"),
	DICTDATA_SYS_ADD("cn.uce.omg.portal.biz.impl.DictDataBiz.addDictType","12_15","OMG_PORTAL_DICT_TYPE","4","dictDataPushTemplate"),
	DICTDATA_SYS_UPDATE("cn.uce.omg.portal.biz.impl.DictDataBiz.updateDictType","12_17","OMG_PORTAL_DICT_TYPE","6","dictDataPushTemplate"),
	DICTDATA_SYS_DELETE("cn.uce.omg.portal.biz.impl.DictDataBiz.deleteDictType","12_16","OMG_PORTAL_DICT_TYPE","5","dictDataPushTemplate"),
	
	// 数据权限操作日志操作类型
	PERMISSION_ADD("cn.uce.omg.portal.biz.impl.PermissionBiz.addPermission","11_10","OMG_PORTAL_PERMISSION","1","permissionPushTemplate"),
	PERMISSION_UPDATE("cn.uce.omg.portal.biz.impl.PermissionBiz.updatePermission","11_12","OMG_PORTAL_PERMISSION","3","permissionPushTemplate"),
	PERMISSION_DELETE("cn.uce.omg.portal.biz.impl.PermissionBiz.deletePermission","11_11","OMG_PORTAL_PERMISSION","2","permissionPushTemplate"),
	
	// 用户管理操作日志操作类型
	USER_DATA("cn.uce.omg.portal.biz.impl.UserBiz.updateUserOrg","10_14","OMG_PORTAL_USER_ORG","1","userPushTemplate"),
	USER_ROLE_ASSIGNED("cn.uce.omg.portal.biz.impl.UserBiz.saveUserRoleRel","10_13","OMG_PORTAL_USER_ROLE_REL","2","userPushTemplate");
	
	String logCode;
	String logMsg;
	String methodName;
	String tableName;
	String mqTemplate;
	String operatorType;
	
	private LogConstants(String logCode, String logMsg) {
		this.logCode = logCode;
		this.logMsg = logMsg;
	}
    private LogConstants(String logCode,String logMsg,String tableName,String operatorType, String mqTemplate) {
    	this.logCode = logCode;
    	this.logMsg = logMsg;
    	this.tableName = tableName;
    	this.operatorType = operatorType;
    	this.mqTemplate = mqTemplate;
    }
	public String logCode() {
		return logCode;
	}
	
	public String logMsg() {
		return logMsg;
	}
	
	public String operatorType() {
		return operatorType;
	}
	
	public String mqTemplate() {
		return mqTemplate;
	}
	
	public String tableName() {
		return tableName;
	}
	
	public static String message(String code) {
		LogConstants[] constans = LogConstants.values();
		for (LogConstants constant : constans) {
			if (constant.logCode().equals(code)) {
				return constant.logMsg();
			}
		}
		return null;
	}
	
	public static LogConstants methodName(String code) {
		LogConstants[] constans = LogConstants.values();
		for (LogConstants constant : constans) {
			if (constant.logCode().equals(code)) {
				return constant;
			}
		}
		return null;
	}
}
