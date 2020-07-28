package cn.uce.omg.portal.control;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Encoder;
import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.IEmpBiz;
import cn.uce.omg.portal.biz.IOrgBiz;
import cn.uce.omg.portal.entity.jira.Attachment;
import cn.uce.omg.portal.entity.jira.Issue;
import cn.uce.omg.portal.entity.jira.IssueDetail;
import cn.uce.omg.portal.entity.jira.JiraHandle;
import cn.uce.omg.portal.util.HttpClientUtilforJira;
import cn.uce.omg.portal.vo.EmpVo;
import cn.uce.omg.portal.vo.OrgVo;
import cn.uce.utils.StringUtil;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.util.WebUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * portal对接ITSM
 * @author 004425
 *
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping({"/itsm"})
public class ITSMControl extends BaseController {
	
	private Log log = LogFactory.getLog(getClass());
	
	private static HttpClientUtilforJira httpClientUtilforJira;
	
	@Value("${omg.portal.itsmPath}")
	private String itsmUrl;

	@Autowired
	private IOrgBiz orgBiz;
	@Autowired
	private IEmpBiz empBiz;
	
	static {
		if (httpClientUtilforJira == null) {
			httpClientUtilforJira = new HttpClientUtilforJira();
		}
	}

	@RequestMapping(value ="/forward")
	public String forward() throws Exception {
		return "portal/jiraList";
	}
	@RequestMapping(value ="/createIssue")
	public String createIssue() throws Exception {
		return "portal/createIssue";
	}
	@RequestMapping(value ="/jiraDetail")
	public String jiraDetail(String issueId, HttpServletRequest request) throws Exception {
		request.setAttribute("issueId", issueId);
		return "portal/jiraDetail";
	}
	
	@RequestMapping(value = {"findCurrentUserInfo"}, method = {RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> findCurrentUserInfo() {
		CurrentUser currentUser = WebUtil.getCurrentUser();
		String empCode = currentUser.getEmpCode();
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("empCode", empCode);
		userInfo.put("empName", currentUser.getEmpName());
		OrgVo orgVo = orgBiz.findByOrgId(currentUser.getOrgId());
		userInfo.put("orgName", orgVo == null ? "无机构" : orgVo.getOrgFullName());
		EmpVo empVo = empBiz.findByEmpCode(empCode);
		userInfo.put("userTel", empVo == null ? "" : empVo.getMobile());
		return userInfo;
	}
	
	/**
	 * 创建问题
	 * @param file
	 * @param issue
	 * @return
	 */
	@RequestMapping(value = {"createIssue"}, method = {RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> createIssue(@RequestParam(value = "file[]", required = false) MultipartFile[] files, Issue issue) {
		//empCode
		String empCode = issue.getEmpCode();
		CurrentUser currentUser = WebUtil.getCurrentUser();
		if (null == empCode || empCode.length() == 0) {
			empCode = currentUser.getEmpCode();
		}
		//empName
		String empName = issue.getEmpName();
		if (null == empName || empName.length() == 0) {
			empName = currentUser.getEmpName();
		}
		
		//机构名称
		String orgName = issue.getOrgName();
		log.info("创建工单请求参数,机构:" + orgName +",当前机构:" + currentUser.getOrgId());
		if (null == orgName || orgName.length() == 0) {
			OrgVo orgVo = orgBiz.findByOrgId(currentUser.getOrgId());
			orgName = orgVo == null ? "" : orgVo.getOrgFullName();
		}
		
		//手机号码
		String userTel = issue.getTelephone();
		if (null == userTel || userTel.length() == 0) {
			EmpVo empVo = empBiz.findByEmpCode(empCode);
			userTel = empVo == null ? "" : empVo.getMobile();
		}
		Map<String, Object> son = new HashMap<>();
		son.put("incTopic", issue.getTitle());
		son.put("incContent", issue.getDescription());
		son.put("incType", issue.getIssueTypeId());
		son.put("serviceType", issue.getBizType());
		son.put("incHappenDate", new Date());
		son.put("contactorName", issue.getEmpName());
		son.put("contactorPhone", issue.getTelephone());
		son.put("userNo", empCode);
		son.put("reqOrgName", orgName);
		String param = HttpClientUtilforJira.convertJSONParamter(son);
		String result = HttpClientUtilforJira.sendItsmHttpPostJson(itsmUrl + "/incidentManageForThirdAction/addAndProcess",param);
		if(log.isInfoEnabled()) {
			log.info("创建工单请求参数:" + param + ",返回结果:" + result);
		}
		if (result == null || !(result.contains("success"))) {
			log.error("创建工单请求参数:" + param + ",返回结果:" + result);
			return returnError("S9999", "提交失败,请稍后重试");
		}
		JSONObject obj = JSONObject.parseObject(result);
		if(StringUtil.equals(obj.getString("success"),"false")) {
			log.error("请求参数:" + param + ",返回结果:" + obj);
			if (StringUtil.equals(obj.getString("msg"), "员工在ITSM系统不存在")) {
				return returnError("S9999", "用户在ITSM系统不存在，请由其他人代替上报或联系省区IT");
			}
			return returnError("S9999", "提交失败," + obj.getString("msg"));
		}
		JSONObject resultObj = obj.getJSONObject("data");
		String fileResult = null;
		if (null != issue.getImageBase() && issue.getImageBase().length > 0) {
			String[] imageBase = issue.getImageBase();
			if (issue.getImageBase().length == 2 && issue.getImageBaseName().length == 1) {
				String imageResult = imageBase[0] + "," + imageBase[1];
				imageBase = new String[1];
				imageBase[0] = imageResult;
			}
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < imageBase.length; i++) {
				fileResult = HttpClientUtilforJira.uploadFileItsmHttpPost(itsmUrl + "/incidentManageForThirdAction/uploadFile",resultObj.getString("id"),empCode, imageBase[i], issue.getImageBaseName()[i]);
	      		if (fileResult == null || !(fileResult.contains("success"))) {
	  				log.error("上传附件请求参数:" + empCode + ",返回结果:" + fileResult);
	  			}
	      		JSONObject fileObj = JSONObject.parseObject(result);
	      		if(StringUtil.equals(fileObj.getString("success"),"false")) {
					log.error("上传附件请求参数:" + empCode + ",返回结果:" + fileResult);
					return returnError("S9999", "上传附件出错");
				}
			}
			long endTime1 = System.currentTimeMillis();
			if(log.isInfoEnabled()) {
				log.info("上传附件耗时：" + (endTime1 - startTime) +"ms");
			}
		}	
		return returnSuccess("工单已提交成功，请等待工程师处理！");
	}

	/**
	 * 根据条件查询jira列表和详情
	 * @param statusParam
	 * @param pageSize
	 * @param currentPage
	 * @param empCode
	 * @param issuetype
	 * @return
	 */
	@RequestMapping(value = {"getIssueList"}, method = {RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getIssueList(@RequestParam(value="statusParam", defaultValue="1") int statusParam, 
			String issuetype, Page page, String issueId, Date startDate, Date endDate, String descriptionSearch) {
		try {
			CurrentUser currentUser = WebUtil.getCurrentUser();
			String empCode = currentUser.getEmpCode();
			if (null == page) {
				page = new Page();
			}
			Map<String, Object> map = new HashMap<>();
			if (statusParam > 1) {
				map.put("queryStatus", statusParam);
			}
			if (StringUtil.isNotBlank(descriptionSearch)) {
				
			}
			map.put("myself", "true");
			map.put("userNo", empCode);
			map.put("incNo", issueId);
			map.put("luceneKey", descriptionSearch);
			map.put("incCreateDateBegin", startDate);
			map.put("incCreateDateEnd", endDate);
			map.put("start", Integer.valueOf((page.getCurrentPage() - 1) * page.getPageSize()));
			map.put("limit", Integer.valueOf(page.getPageSize()));
			List<Issue> issues = new ArrayList<>();
			String param = HttpClientUtilforJira.convertJSONParamter(map);
			String result = HttpClientUtilforJira.sendItsmHttpPostJson(itsmUrl + "/incidentManageForThirdAction/incidentList", param);
			if (result == null || !(result.contains("success"))) {
				log.error("请求参数:" + param + ",返回结果:" + result);
				return returnError("S9999", "查询出错");
			}
			JSONObject resultObj = JSONObject.parseObject(result);
			if(StringUtil.equals(resultObj.getString("success"),"false")) {
				log.error("请求参数:" + param + ",返回结果:" + result);
				if (StringUtil.equals(resultObj.getString("msg"), "员工在ITSM系统不存在")) {
					return returnError("S9999", "用户在ITSM系统不存在，请由其他人代替上报或联系省区IT");
				}
				return returnError("S9999", "查询出错");
			}
			JSONArray root = resultObj.getJSONArray("root");
			Issue issue = null;
			if (root != null && root.size() > 0) {
				for (int i = 0; i < root.size(); i++) {
					issue = new Issue();
					issue.setId(root.getJSONObject(i).getString("id"));
					issue.setKey(root.getJSONObject(i).getString("incNo"));
					//问题描述
					issue.setDescription(root.getJSONObject(i).getString("incContent"));
					//解决方案
					issue.setSolution(root.getJSONObject(i).getString("incSolution"));
					issue.setDisplayName(root.getJSONObject(i).getString("flowMangerUserrealname"));
					//获取状态数据
					issue.setStatus(convertStatus(root.getJSONObject(i).getString("incStatus")));
					issue.setCreateTimeStr(root.getJSONObject(i).getString("incCreateDate"));
					issue.setEndTime(root.getJSONObject(i).getString("incClosedDate"));
					issue.setSearchIssueType(statusParam);
				    issue.setIncSatisfaction(root.getJSONObject(i).getString("incSatisfaction"));
					issues.add(issue);
				}
			}
			Map<String, Integer> issueTypeTotal = new HashMap<String, Integer>();
			issueTypeTotal.put("confirmStatus", 0);
			if (StringUtil.isNotBlank(resultObj.getString("pendingCnt"))) {
				issueTypeTotal.put("confirmStatus", Integer.valueOf(resultObj.getString("pendingCnt")));
			}
			issueTypeTotal.put("searchIssueType", statusParam);
			Pagination<Issue> pagination = new Pagination<Issue>();
			page.setTotal(resultObj.getInteger("totalProperty"));
			pagination.setData(issues);
			pagination.setPage(page);
			return returnSuccess(pagination, issueTypeTotal);
		} catch (Exception e) {
			log.warn("获取列表失败：", e);
		}
		return returnError("S9999", "查询失败");
	}
	
	public String convertStatus(String status) {
		String statusName = "待受理";
		switch (status) {
			case "30":
				statusName = "处理中";
				break;
			case "50":
				statusName = "处理完成";
				break;
			case "60":
				statusName = "关闭";
				break;
		}
	    return statusName;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 重写returnSuccess方法
	 * </pre>
	 * @param data
	 * @param otherParam
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月3日下午7:26:03
	 */
	public Map<String, Object> returnSuccess(Object data, Map<String, Integer> otherParam) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("otherParam", otherParam);
        map.put("success", true);
        if(data != null) {
        	if(data instanceof Pagination<?>) {
        		Pagination<?> page = (Pagination<?>) data;
        		map.put("rows", page.getData());
        		if(null!=page.getPage()){
        			map.put("total", page.getPage().getTotal());
        		}
        	}else if(data instanceof List<?>) {
        		map.put("data", data);
        	}else {
        		map.put("data", data);
        	}
        }
        return map;
    }

	/**
	 * 根据key[issueId]查询详情
	 * @param issueId
	 * @return
	 */
	@RequestMapping(value = {"getIssueById"}, method = {RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> getIssueById(String issueId) {
		if (issueId == null) {
			return returnError("S0001", "请求参数不正确");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("id", issueId);
		String param = HttpClientUtilforJira.convertJSONParamter(map);
		String result = HttpClientUtilforJira.sendItsmHttpPostJson(itsmUrl + "/incidentManageForThirdAction/view", param);
		if (result == null || !(result.contains("success"))) {
			log.error("查询工单详情请求参数:" + param + ",返回结果:" + result);
			return returnError("S9999", "查询出错");
		}
		JSONObject resultObj = JSONObject.parseObject(result);
		if(StringUtil.equals(resultObj.getString("success"),"false")) {
			log.error("查询工单详情请求参数:" + param + ",返回结果:" + result);
			return returnError("S9999", "查询出错");
		}
		JSONObject data = resultObj.getJSONObject("data");
		IssueDetail issue = new IssueDetail();
		CurrentUser currentUser = WebUtil.getCurrentUser();
		EmpVo empVo = empBiz.findByEmpCode(currentUser.getEmpCode());
		if (empVo == null) {
			log.error("查询员工信息失败,empCode:" + currentUser.getEmpCode());
			return returnError("S9999", "查询出错");
		}
		//判断当前登录人和工单提交人是否匹配
		//reqOmgEmpCode，submitOmgEmpCode对应请求人工号和提交人工号 待定
		String reqUsername = data.getString("reqUsername");
		if (null != issue && !reqUsername.contains(currentUser.getEmpCode())) {
			log.error("当前登录人：" + currentUser.getEmpCode() + "，工单提交人：" + reqUsername);
			return returnError("S9999", "当前登录人与工单提交人不一致。");
		}
		issue.setKey(data.getString("incNo"));
		issue.setStatus(convertStatus(data.getString("incStatus")));
		issue.setEmpTel(data.getString("contactorPhone"));
		issue.setEmpName(data.getString("contactorName"));
		issue.setEmpCode(currentUser.getEmpCode());
		issue.setReportEmpName(currentUser.getEmpName());
		issue.setReportTel(empVo.getMobile());
		issue.setOrgName(data.getString("reqOrgName"));
		issue.setDescription(data.getString("incContent"));
		issue.setSolution(data.getString("incSolution"));
		issue.setCreateTimeStr(data.getString("incCreateDate"));
		issue.setDisplayName(data.getString("flowMangerUserrealname"));
		issue.setTitle(data.getString("incTopic"));
		issue.setBizTypeName("快递");
		if (StringUtil.isNotBlank(data.getString("serviceType")) && "20".equals(data.getString("serviceType"))) {
			issue.setBizTypeName("快运");
		}
		//查询附件
		Map<String, Object> fileMap = new HashMap<>();
		fileMap.put("id", issueId);
		String fileParam = HttpClientUtilforJira.convertJSONParamter(fileMap);
		String fileResult = HttpClientUtilforJira.sendItsmHttpPostJson(itsmUrl + "/incidentManageForThirdAction/listAttachment", fileParam);
		if (fileResult == null || !(fileResult.contains("success"))) {
			log.error("附件查询请求参数:" + fileParam + ",返回结果:" + fileResult);
			return returnError("S9999", "查询出错");
		}
		JSONObject fileObj = JSONObject.parseObject(fileResult);
		if(StringUtil.equals(fileObj.getString("success"),"false")) {
			log.error("附件查询请求参数:" + fileParam + ",返回结果:" + fileResult);
			return returnError("S9999", "查询出错");
		}
		JSONArray root = fileObj.getJSONArray("root");
		List<Attachment> attachments = new ArrayList<Attachment>();
		Attachment attachment = null;
		if (root != null && root.size() > 0) {
			for (int i = 0; i < root.size(); i++) {
				attachment = new Attachment();
				attachment.setId(root.getJSONObject(i).getString("id"));
				attachment.setFileName(root.getJSONObject(i).getString("originalName"));
				attachment.setContent(root.getJSONObject(i).getString("originalName"));
				attachment.setFilePath(itsmUrl + "/incidentManageForThirdAction/viewFile?filePath=" + root.getJSONObject(i).getString("filePath"));
				attachments.add(attachment);
			}
			issue.setAttachments(attachments);
		}
		//查询工单操作日志
		Map<String, Object> logMap = new HashMap<>();
		logMap.put("id", issueId);
		String logParam = HttpClientUtilforJira.convertJSONParamter(logMap);
		String logResult = HttpClientUtilforJira.sendItsmHttpPostJson(itsmUrl + "/incidentManageForThirdAction/processLogList", logParam);
		if (logResult == null || !(logResult.contains("success"))) {
			log.error("操作日志查询请求参数:" + logParam + ",返回结果:" + logResult);
			return returnError("S9999", "查询出错");
		}
		JSONObject logObj = JSONObject.parseObject(logResult);
		if(StringUtil.equals(logObj.getString("success"),"false")) {
			log.error("操作日志请求参数:" + logParam + ",返回结果:" + logObj);
			return returnError("S9999", "查询出错");
		}
		JSONArray logRoot = logObj.getJSONArray("root");
		List<JiraHandle> jiraHandles = new ArrayList<>();
		JiraHandle jiraHandle = null;
		if (null != logRoot && logRoot.size() > 0) {
			for (int i = 0; i < logRoot.size(); i++) {
				jiraHandle = new JiraHandle();
				JSONObject obj = logRoot.getJSONObject(i);
				jiraHandle.setDisplayName(obj.getString("operatorName"));
				jiraHandle.setBody(obj.getString("userlog"));
				jiraHandle.setCreated(obj.getDate("operateDate"));
				jiraHandle.setStatus(obj.getString("incStatusName"));
				jiraHandles.add(jiraHandle);
			}
			issue.setJiraHandles(jiraHandles);
		}
		return returnSuccess(issue);
	}


	//审批
	@RequestMapping(value = {"addComment"}, method = {RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> addComment(String key, String content, Integer isSuccessFlag, String score) {
		try {
			if ((key == null) || (content == null) || (isSuccessFlag == null)) {
				return returnError("S0001", "请求参数不正确");
			}
			Map<String, Object> map = new HashMap<>();
			CurrentUser currentUser = WebUtil.getCurrentUser();
			map.put("id", key);
			map.put("userNo", currentUser.getEmpCode());
			String result = null;
			String param = null;
			//未解决
			if (isSuccessFlag == 2) {
				map.put("notFinishedReason", content);
				param = HttpClientUtilforJira.convertJSONParamter(map);
				result = HttpClientUtilforJira.sendItsmHttpPostJson(itsmUrl + "/incidentManageForThirdAction/notFinishedProcess", param);
			} else {
				//确认解决并评分
				map.put("incSatisfaction",score);
				map.put("incSatisfactionContent", content);
				param = HttpClientUtilforJira.convertJSONParamter(map);
				result = HttpClientUtilforJira.sendItsmHttpPostJson(itsmUrl + "/incidentManageForThirdAction/completedProcess", param);
			}
			if (result == null || !(result.contains("success"))) {
				log.error("评论工单请求参数:" + param + ",返回结果:" + result);
				return returnError("S9999", "反馈失败");
			}
			JSONObject resultObj = JSONObject.parseObject(result);
			if(StringUtil.equals(resultObj.getString("success"),"false")) {
				log.error("评论工单请求参数:" + param + ",返回结果:" + result);
				return returnError("S9999", resultObj.getString("msg"));
			}
			return returnSuccess("感谢您的反馈！");
		} catch (Exception e) {
			log.error("评分失败", e);
		}
		return returnError("S9999", "反馈失败");
	}
	
	/**
	 * 追加评论
	 * @param p
	 * @return
	 */
	@RequestMapping(value = {"insertComment"}, method = {RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> insertComment(String content, String key, String score) {
		try {
			if ((key == null) || (content == null)) {
				return returnError("S0001", "请求参数不正确");
			}
			Map<String, Object> map = new HashMap<>();
			CurrentUser currentUser = WebUtil.getCurrentUser();
			map.put("id", key);
			map.put("userNo", currentUser.getEmpCode());
			String result = null;
			String param = null;
			//确认解决并评分
			map.put("incSatisfaction",score);
			map.put("incSatisfactionContent", content);
			param = HttpClientUtilforJira.convertJSONParamter(map);
			result = HttpClientUtilforJira.sendItsmHttpPostJson(itsmUrl + "/incidentManageForThirdAction/additionalRating", param);
			if (result == null || !(result.contains("success"))) {
				log.error("评论工单请求参数:" + param + ",返回结果:" + result);
				return returnError("S9999", "反馈失败");
			}
			JSONObject resultObj = JSONObject.parseObject(result);
			if(StringUtil.equals(resultObj.getString("success"),"false")) {
				log.error("评论工单请求参数:" + param + ",返回结果:" + result);
				return returnError("S9999", resultObj.getString("msg"));
			}
			return returnSuccess("感谢您的反馈！");
		} catch (Exception e) {
			log.error("评分失败", e);
		}
		return returnError("S9999", "反馈失败");
	}


	@RequestMapping(value = {"getPicture"}, method = {RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> getPicture(String files,String fileNames) {
		try {
			long startTime = System.currentTimeMillis();
			List<Attachment> base64FileList = new ArrayList<>();
			if(null != files && files.length() > 0) {
				String[] fileArr = files.split(",");
				String[] fileNameArr = fileNames.split(",");
				for (int i =0; i < fileArr.length;i++) {
					if (null != fileArr[i] && fileArr[i].length() > 0) {
						Attachment attachment = new Attachment();
						InputStream inputStream = httpClientUtilforJira.getInputStream(itsmUrl + "/incidentManageForThirdAction/downloadFile?id=" + fileArr[i]);
						byte[] data = toByteArray(inputStream);
						BASE64Encoder encoder = new BASE64Encoder();
						attachment.setFileName(fileNameArr[i]);
						attachment.setContent(encoder.encode(data));
						base64FileList.add(attachment);
					}
				}
			}
			long endTime1 = System.currentTimeMillis();
			if(log.isInfoEnabled()) {
				log.info("获取图片耗时：" + (endTime1 - startTime) +"ms");
			}
			return returnSuccess(base64FileList);
		} catch (Exception e) {
			log.warn("获取图片异常", e);
		}
		return returnError("S9999", "服务器异常");
	}
	

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过文件路径下载
	 * </pre>
	 * @param filePath
	 * @param response
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月1日下午2:25:17
	 */
	@RequestMapping(value = "/downJiraFile.do")
	@ResponseBody
	public void downJiraFile(String issueId, String id,String fileName, HttpServletResponse response) {
		try {
			boolean issueEmpCheck = issueCompareCurrent(issueId);
			if (issueEmpCheck) {
				InputStream inputStream = httpClientUtilforJira.getInputStream(itsmUrl + "/incidentManageForThirdAction/downloadFile?id=" + id);
				if (null != inputStream) {
					byte[] data = toByteArray(inputStream);
					response.setContentType("application/octet-stream");
					response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8")
			                + ";filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
					response.setHeader("Pragma", "No-cache");
					response.setHeader("Cache-Control", "No-cache");
					response.setDateHeader("Expires", 0);
					response.getOutputStream().write(data);
					response.getOutputStream().flush();
				}
			} else {
				response.setContentType("application/octet-stream");
				response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("获取文件异常.txt", "UTF-8")
		                + ";filename*=UTF-8''" + URLEncoder.encode("获取文件异常.txt", "UTF-8"));
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "No-cache");
				response.setDateHeader("Expires", 0);
				response.getOutputStream().write(0);
				response.getOutputStream().flush();
			}
			
		} catch (Exception e) {
			log.warn("获取文件异常", e);
		}
	}
	
	public boolean issueCompareCurrent(String issueId) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", issueId);
		String param = HttpClientUtilforJira.convertJSONParamter(map);
		String result = HttpClientUtilforJira.sendItsmHttpPostJson(itsmUrl + "/incidentManageForThirdAction/view", param);
		if (result == null || !(result.contains("success"))) {
			log.error("查询工单详情请求参数:" + param + ",返回结果:" + result);
			return false;
		}
		JSONObject resultObj = JSONObject.parseObject(result);
		if(StringUtil.equals(resultObj.getString("success"),"false")) {
			log.error("查询工单详情请求参数:" + param + ",返回结果:" + result);
			return false;
		}
		JSONObject data = resultObj.getJSONObject("data");
		IssueDetail issue = new IssueDetail();
		CurrentUser currentUser = WebUtil.getCurrentUser();
		//判断当前登录人和工单提交人是否匹配
		//reqOmgEmpCode，submitOmgEmpCode对应请求人工号和提交人工号 待定
		String reqUsername = data.getString("reqUsername");
		if (null != issue && !reqUsername.contains(currentUser.getEmpCode())) {
			log.error("当前登录人：" + currentUser.getEmpCode() + "，工单提交人：" + reqUsername);
			return false;
		}
		return true;
	}
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte['?'];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}

	public static String dealTimeFormat(String time) {
		time = time.substring(0, time.length() - 9);
		return time.substring(0, 10) + " " + time.substring(11, time.length());
	}

}
