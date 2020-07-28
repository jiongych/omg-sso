package cn.uce.omg.portal.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.core.log.server.buffer.LogBuffer;
import cn.uce.omg.portal.biz.impl.LogBiz;
import cn.uce.omg.portal.util.LogConstants;
import cn.uce.omg.portal.vo.LogVo;
import cn.uce.omg.portal.vo.RoleVo;
import cn.uce.utils.JsonUtil;
import cn.uce.utils.StringUtil;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.util.WebUtil;

import com.alibaba.fastjson.JSONObject;
@Service("operatorHandlerService")
public class OperatorHandlerService {
	private Logger LOG = LoggerFactory.getLogger(LogBiz.class);
	private static final String LOG_TYPE_SPLIT = "_";
	@Resource
	private LogBuffer logBuffer;
	@Autowired
	private HttpServletRequest request;
	/**
	 * 构建操作日志
	 */
	public void build(Object[] obj, String operatorType, String operatorTable) {
		CurrentUser user = WebUtil.getCurrentUser();
		if(null == user) {
			return;
		}
		String ipAddr = getRemoteHost(request);
		LogVo operatorLog = new LogVo();
		operatorLog.setCreatedOrg(String.valueOf(user.getOrgId()));
		operatorLog.setCreatedOrgName(String.valueOf(user.getOrgName()));
		operatorLog.setOperatorEmp(user.getEmpCode());
		operatorLog.setOperatorEmpName(user.getEmpName());
		operatorLog.setOperatorIp(ipAddr);
		operatorLog.setOperatorTable(operatorTable);
		handlerOperatorType(operatorLog,obj, operatorType,operatorTable);
		logBuffer.write(operatorLog);
	}
	
	/**
	 * 获取远程主机
	 * @param request
	 * @return
	 * @author huangting
	 * @date 2017年6月9日 下午6:17:40
	 */
	public String getRemoteHost(HttpServletRequest request) {
		String ip = null;
		try {
			ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			LOG.error("获取ip失败", e);
		}
		return ip;
	}

	/**
	 * 处理操作类型
	 * @param operatorLog
	 * @param operatorType
	 */
	private void handlerOperatorType(LogVo operatorLog,Object[] obj, String operatorType,String operatorTable) {
		if(StringUtil.isBlank(operatorType)) {
			return;
		}
		String[] types = buildOperatorLog(operatorLog, obj, operatorType, operatorTable);
		operatorLog.setOperatorMenu(types[0]);
		operatorLog.setOperatorType(types[1]);
		operatorLog.setCreatedTime(new Date());
	}

	/**
	 * 用户管理操作日志
	 * @param operatorLog
	 * @param obj
	 * @param operatorType
	 * @param operatorTable
	 * @return
	 */
	private String[] buildOperatorLog(LogVo operatorLog,
			Object[] obj, String operatorType, String operatorTable) {
		LogConstants keys = LogConstants.methodName(operatorType);
		String[] types = keys.logMsg().split(LOG_TYPE_SPLIT);
		if (obj != null && obj[0] instanceof RoleVo) {
			JSONObject object = (JSONObject) JSONObject.toJSON(obj[0]);
			object.remove("permissionMap");
			operatorLog.setOperatorAfter(object.toString());
		} else {
			operatorLog.setOperatorAfter(JsonUtil.toJson(obj));
		}
		
		
		return types;
	}

	/**
	 * 业务记录日志方法
	 * @param log
	 */
	public void wirteLog(LogVo log) {
		try {
			logBuffer.write(log);
		} catch (Exception e) {
			LOG.error("log notes exception : ", e.getMessage());
		}
	}
	
	public LogBuffer getLogBuffer() {
		return logBuffer;
	}

	public void setLogBuffer(LogBuffer logBuffer) {
		this.logBuffer = logBuffer;
	}
}
