package cn.uce.omg.portal.control;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.util.StringUtils;
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
import cn.uce.omg.portal.entity.jira.Add;
import cn.uce.omg.portal.entity.jira.Assignee;
import cn.uce.omg.portal.entity.jira.Attachment;
import cn.uce.omg.portal.entity.jira.Comment;
import cn.uce.omg.portal.entity.jira.CreateIssue;
import cn.uce.omg.portal.entity.jira.Customfield11211;
import cn.uce.omg.portal.entity.jira.Customfield11213;
import cn.uce.omg.portal.entity.jira.Customfield11214;
import cn.uce.omg.portal.entity.jira.Fields;
import cn.uce.omg.portal.entity.jira.Issue;
import cn.uce.omg.portal.entity.jira.IssueDetail;
import cn.uce.omg.portal.entity.jira.IssueTypeforJira;
import cn.uce.omg.portal.entity.jira.JiraHandle;
import cn.uce.omg.portal.entity.jira.JiraSet;
import cn.uce.omg.portal.entity.jira.JsonRootBean;
import cn.uce.omg.portal.entity.jira.Transition;
import cn.uce.omg.portal.entity.jira.Update;
import cn.uce.omg.portal.util.Constant;
import cn.uce.omg.portal.util.HttpClientUtilforJira;
import cn.uce.omg.portal.vo.EmpVo;
import cn.uce.omg.portal.vo.OrgVo;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.util.WebUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * portal对接jira
 * @author Ji Yongchao
 *
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping({"/jira"})
public class JiraControl extends BaseController {
	
	private Log log = LogFactory.getLog(getClass());
	
	private static HttpClientUtilforJira httpClientUtilforJira;
	
	@Value("${omg.portal.jiraPath:http://jira.uce.cn/}")
	private String jiraAuthUrl;

	@Autowired
	private IOrgBiz orgBiz;
	@Autowired
	private IEmpBiz empBiz;
	@Value("${omg.portal.jiraProjectType:ITSD}")
	private String jiraProjectType;
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
	public static void main(String[] args) {
		//String path = "http://jira.uce.cn/";
		/*HttpClientUtilforJira http = new HttpClientUtilforJira();
		Page page = new Page();
		//String empCode = WebUtil.getCurrentUser().getEmpCode();
		String jql = null;
		//判断查询全部问题还是分类查询
		jql = "project = ITSD AND 钉钉工号~\"" + "014221" + "\" ";
		jql += " AND description ~ 二手房屋请问v";
		jql += " ORDER BY priority DESC, created DESC";
		Map<String, Object> map = new HashMap<>();
		map.put("jql", jql);
		System.out.println(jql);
		map.put("startAt", Integer.valueOf((page.getCurrentPage() - 1) * page.getPageSize()));
		map.put("maxResults", Integer.valueOf(page.getPageSize()));
	
		List<String> fieldList = new ArrayList<>();
		fieldList.add("*all");
		map.put("fields", fieldList);
		String project = http.sendHttpPost(path + "rest/api/2/search", map);
		System.out.println(project);*/
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
	public Map<String, Object> createIssue(@RequestParam(value = "file", required = false) MultipartFile[] file, Issue issue) {
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
		/**
		 * 问题类型
		 * {"id":"10306","name":"咨询问题"},{"id":"10308","name":"业务需求"},{"id":"10100","name":"设备故障"}
		 */
		//问题描述
		String description = issue.getDescription();
		if (!StringUtils.isEmpty(description) && description.length() > 2) {
			if (description.substring(0, 2).equals("无,")) {
				description = description.substring(2, description.length());
			} else {
				description = description.substring(0, description.length() - 2);
			}
		}
		
		/*if (userTel.length() > 11) {
			return returnError("S0001", "手机号不能超过11位");
		}*/
		//标题
		String summary = null;
		//标题取描述的前10个字
		if ((!StringUtils.isEmpty(description)) && (description.length() >= 10)) {
			summary = description.substring(0, 10);
		} else if ((!StringUtils.isEmpty(description)) && (description.length() < 10)) {
			summary = description.substring(0, description.length());
		} else {
			summary = "无标题";
		}
		Map<String, Object> parent = new HashMap<>();
		Map<String, Object> son = new HashMap<>();
		Map<String, Object> project = new HashMap<>();
		Map<String, Object> issuetype = new HashMap<>();
		Map<String, Object> sourceType = new HashMap<>();
		project.put("key", jiraProjectType);
		issuetype.put("id", issue.getIssueTypeId());
		son.put("project", project);
		son.put("issuetype", issuetype);
		son.put("summary", summary);
		son.put("description", description);

		String fields = httpClientUtilforJira.sendHttpGet(jiraAuthUrl + "rest/api/2/field");
		if (!StringUtils.isEmpty(fields)) {
			JSONArray fieldsJson = JSONObject.parseArray(fields);
			for (int i = 0; i < fieldsJson.size(); i++) {
				String filedJson = fieldsJson.get(i).toString();
				Fields fldsbean = JSON.parseObject(filedJson, Fields.class);
				String name = fldsbean.getName();
				if (name.contains("工号")) {
					son.put(fldsbean.getId(), empCode);
				}
				if (name.contains("姓名")) {
					son.put(fldsbean.getId(), empName);
				}
				if (name.contains("电话")) {
					son.put(fldsbean.getId(), userTel);
				}
				if (name.equals("部门")) {
					son.put(fldsbean.getId(), orgName);
				}
				if (name.equals("来源")) {
					sourceType.put("id", "11711");
					son.put(fldsbean.getId(), sourceType);
				}
			}
			parent.put("fields", son);

			String result = httpClientUtilforJira.sendHttpPost(jiraAuthUrl + "rest/api/2/issue", parent);
			if ((result != null) && (!result.contains("errorMessages"))) {
				try {
					if (null != issue.getImageBase() && issue.getImageBase().length > 0) {
						String[] imageBase = issue.getImageBase();
						if (issue.getImageBase().length == 2 && issue.getImageBaseName().length == 1) {
							String imageResult = imageBase[0] + "," + imageBase[1];
							imageBase = new String[1];
							imageBase[0] = imageResult;
						}
						CreateIssue createIssue = JSON.parseObject(result, CreateIssue.class);
						httpClientUtilforJira.sendJiraHttpPost(jiraAuthUrl + "rest/api/2/issue/" + createIssue.getKey() + "/attachments", 
								null, imageBase, issue.getImageBaseName());
					}	
				} catch (Exception e) {
					log.warn("上传失败", e);
					return returnError("S9999", "服务器异常");
				}
				return returnSuccess("工单已提交成功，请等待工程师处理！");
			}
			return returnError("S9999", "创建失败，传入参数有误");
		}
		return returnError("S9999", "创建失败");
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
			String issuetype, Page page, String issueId, String startDate, String endDate, String descriptionSearch) {
		try {
			CurrentUser currentUser = WebUtil.getCurrentUser();
			String empCode = currentUser.getEmpCode();
			if (null == page) {
				page = new Page();
			}
			//String empCode = WebUtil.getCurrentUser().getEmpCode();
			String jql = null;
			String allJal = null;
			//判断查询全部问题还是分类查询
			if (null == issuetype || issuetype.length() == 0 || "全部".equals(issuetype)) {
				jql = "project = " + jiraProjectType + " AND 钉钉工号~\"" + empCode + "\" ";
			} else {
				jql = "project = " + jiraProjectType + " AND issuetype in (" + issuetype + ") AND 钉钉工号~\"" + empCode + "\" ";
			}
			if (null != issueId && issueId.length() > 0) {
				issueId = issueId.replaceAll("\\s*", ""); 
				jql += " AND key = " + issueId;
			}
			if (null != startDate && startDate.length() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				jql += " AND created >= '" + sdf.format(sdf.parse(startDate)) + "' ";
			}
			if (null != endDate && endDate.length() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				jql += " AND created <= '" + sdf.format(sdf.parse(endDate)) + "' ";
			}
			if (null != descriptionSearch && descriptionSearch.length() > 0) {
				descriptionSearch = descriptionSearch.replaceAll("\\s*", ""); 
				jql += " AND description ~ " + descriptionSearch;
			}
			allJal = jql;
			switch (statusParam) {
				case 1:
					break;
				case 2:
					jql += " AND status = 待受理";
					break;
				case 3:
					jql += " AND status != 关闭 AND status != 处理完成 AND status != 待受理";
					break;
				case 4:
					jql += " AND status = 处理完成";
					break;
				case 5:
					jql += " AND status = 关闭";
					break;
				default:
					break;
			}
			jql += " ORDER BY priority DESC, created DESC";
			Map<String, Object> map = new HashMap<>();
			map.put("jql", jql);
			map.put("startAt", Integer.valueOf((page.getCurrentPage() - 1) * page.getPageSize()));
			map.put("maxResults", Integer.valueOf(page.getPageSize()));

			List<String> fieldList = new ArrayList<>();
			fieldList.add("*all");
			map.put("fields", fieldList);
			
			String project = httpClientUtilforJira.sendHttpPost(jiraAuthUrl + "rest/api/2/search", map);

			/** ---------------获取各个类型的总数----------------*/
			int confirmStatus = 0;	//待用户确认
			Map<String, Integer> issueTypeTotal = new HashMap<String, Integer>();
			/*int waitStatus = 0;		//待处理
			int processStatus = 0;	//处理中
			int confirmStatus = 0;	//待用户确认
			int overStatus = 0;		//已完成
			
			//--------------待处理---------------//
			map.put("jql", allJal + " AND status = 待受理  ORDER BY priority DESC, created DESC");
			waitStatus = issueTypeTotal(map);
			issueTypeTotal.put("waitStatus", waitStatus);
			//--------------处理中---------------//
			map.put("jql", allJal + " AND status != 关闭 AND status != 处理完成 AND status != 待受理  ORDER BY priority DESC, created DESC");
			processStatus = issueTypeTotal(map);
			issueTypeTotal.put("processStatus", processStatus);
			//--------------待用户确认---------------//
			map.put("jql", allJal + " AND status = 处理完成  ORDER BY priority DESC, created DESC");
			confirmStatus = issueTypeTotal(map);
			issueTypeTotal.put("confirmStatus", confirmStatus);
			//--------------已完成---------------//
			map.put("jql", allJal + " AND status = 关闭  ORDER BY priority DESC, created DESC");
			overStatus  = issueTypeTotal(map);
			issueTypeTotal.put("overStatus", overStatus);*/
			map.put("jql", allJal + " AND status = 处理完成  ORDER BY priority DESC, created DESC");
			confirmStatus = issueTypeTotal(map);
			issueTypeTotal.put("confirmStatus", confirmStatus);
			issueTypeTotal.put("searchIssueType", statusParam);
			List<Issue> issues = new ArrayList<>();
			if (!StringUtils.isEmpty(project)) {
				JSONObject projectsJson = JSONObject.parseObject(project);
				JSONArray projectsArray = (JSONArray) projectsJson.get("issues");
				int total = projectsJson.getIntValue("total");
				if (null != projectsArray) {
					int size = projectsArray.size();
					for (int j = 0; j < size; j++) {
						JSONObject projectJson = (JSONObject) projectsArray.get(j);
						String key = projectJson.get("key").toString();
						JSONObject fields = (JSONObject) projectJson.get("fields");
						String description = fields.get("description") == null ? null : fields.get("description").toString();
						String issuetypeStr = fields.get("issuetype") == null ? null : fields.get("issuetype").toString();
						IssueTypeforJira issueType = JSON.parseObject(issuetypeStr, IssueTypeforJira.class);

						String assigneeStr = null;
						Assignee assignee = new Assignee();
						if (fields.get("assignee") != null) {
							assigneeStr = fields.get("assignee").toString();
							assignee = JSON.parseObject(assigneeStr, Assignee.class);
						} else {
							assignee.setDisplayName("未分配");
						}
						String solution = fields.get("customfield_11102") == null ? null : fields.get("customfield_11102").toString();

						String addPromble = fields.get("customfield_11214") == null ? null : fields.get("customfield_11214").toString();
						if (addPromble != null) {
							addPromble = addPromble.substring(addPromble.indexOf(":") + 1, addPromble.length());
						}
						String createTime = fields.get("created") == null ? null : dealTimeFormat(fields.get("created").toString());

						String comment = fields.get("customfield_11213") == null ? null : fields.get("customfield_11213").toString();
						
						String endTime = fields.get("customfield_10502") == null ? null : fields.get("customfield_10502").toString();
						//查询当前处理人
						JSONObject jiraComment = fields.getJSONObject("customfield_10337");
						String dispayName = "";
						if (jiraComment != null) {
							dispayName = jiraComment.getString("displayName");
						}
						JiraSet createIssue = new JiraSet();
						if (fields.get("customfield_11211") != null) {
							createIssue = JSON.parseObject(fields.get("customfield_11211").toString(), JiraSet.class);
						}
						
						Issue issue = new Issue();
						issue.setScore(createIssue == null ? null : createIssue.getValue());
						//获取状态数据
						JSONObject status = fields.getJSONObject("status");
						issue.setStatus(status == null ? null : (String)status.get("name"));
						//注释
						issue.setAddPromble(addPromble);
						//工单类型名称
						issue.setIssueType(issueType.getName());
						//工单类型编号
						issue.setIssueTypeId(issueType.getId());
						issue.setCreateTimeStr(createTime);
						//问题描述
						issue.setDescription(description);
						issue.setDisplayName(dispayName);
						issue.setKey(key);
						//解决方案
						issue.setSolution(solution);
						issue.setComment(comment);
						issue.setEndTime(endTime);
						//issue.setIssueTypeTotal(issueTypeTotal);
						issue.setSearchIssueType(statusParam);
						issues.add(issue);
					}
				}
				Pagination<Issue> pagination = new Pagination<Issue>();
				page.setTotal(total);
				pagination.setData(issues);
				pagination.setPage(page);
				return returnSuccess(pagination, issueTypeTotal);
				//return returnSuccess(pagination);
			}
			return returnError("S9999", "查询问题为空");
		} catch (Exception e) {
			log.warn("获取列表失败：", e);
		}
		return returnError("S9999", "查询失败");
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
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据条件查询各问题类型的总数
	 * </pre>
	 * @param sql
	 * @param map
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月28日下午2:59:43
	 */
	public int issueTypeTotal(Map<String, Object> map) {
		String project = httpClientUtilforJira.sendHttpPost(jiraAuthUrl + "rest/api/2/search", map);
		if (!StringUtils.isEmpty(project)) {
			JSONObject projectsJson = JSONObject.parseObject(project);
			return projectsJson.getIntValue("total");
		}
		return 0;
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
		String issueStr = httpClientUtilforJira.sendHttpGet(jiraAuthUrl + "rest/api/2/issue/" + issueId);
		if (!StringUtils.isEmpty(issueStr)) {
			CurrentUser currentUser = WebUtil.getCurrentUser();
			JSONObject projectJson = JSONObject.parseObject(issueStr);
			String key = projectJson.get("key") == null ? null : projectJson.get("key").toString();
			JSONObject fields = (JSONObject) projectJson.get("fields");

			String description = fields.get("description") == null ? null : fields.get("description").toString();
			String issuetypeStr = fields.get("issuetype") == null ? null : fields.get("issuetype").toString();
			IssueTypeforJira issueType = JSON.parseObject(issuetypeStr, IssueTypeforJira.class);

			//查询当前处理人
			JSONObject jiraCurrent = fields.getJSONObject("customfield_10337");
			String displayName = "";
			if (jiraCurrent != null) {
				displayName = jiraCurrent.getString("displayName");
			}
			String solution = fields.get("customfield_11102") == null ? null : fields.get("customfield_11102").toString();

			String addPromble = fields.get("customfield_11214") == null ? null : fields.get("customfield_11214").toString();
			if (addPromble != null) {
				addPromble = addPromble.substring(addPromble.indexOf(":") + 1, addPromble.length());
			}
			String createTime = fields.get("created") == null ? null : dealTimeFormat(fields.get("created").toString());
			String tel = fields.get("customfield_10343") == null ? "" : fields.get("customfield_10343").toString();
			String empName = fields.get("customfield_10338") == null ? "" : fields.get("customfield_10338").toString();
			String empCode = fields.get("customfield_10350") == null ? "" : fields.get("customfield_10350").toString();
			String attachmentStr = fields.get("attachment") == null ? null :fields.get("attachment").toString();
			List<Attachment> attachments = JSON.parseArray(attachmentStr, Attachment.class);

			//附件列表
			List<String> picUrl = new ArrayList<>();
			if (attachments != null) {
				for (int i = 0; i < attachments.size(); i++) {
					picUrl.add(attachments.get(i).getContent());
					/*String fileName = attachments.get(i).getContent();
					if (null != fileName) {
						String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
						if ("bmp".equals(fileType) || "jpg".equals(fileType) 
								|| "png".equals(fileType) || "jpeg".equals(fileType) 
								|| "JPG".equals(fileType) || "PNG".equals(fileType)
								|| "JPEG".equals(fileType) || "gif".equals(fileType)) {
							attachments.get(i).setBase64File(getJiraFile(attachments.get(i).getContent()));
						}
					}*/
				}
			}
			//注释
			String comment = fields.get("customfield_11213") == null ? null : fields.get("customfield_11213").toString();
			//创建人机构名字
			String orgName = fields.get("customfield_10349") == null ? null : fields.get("customfield_10349").toString();
			//String orgName = currentUser.getCmsOrgName();
			IssueDetail issue = new IssueDetail();
			/**
			 * 解析处理状态
			 */
			JSONObject jiraStatus = fields.getJSONObject("status");
			List<JiraHandle> jiraHandles = new ArrayList<>();
			if (jiraStatus != null) {
				String statusName = jiraStatus.getString("name");
				issue.setStatus(statusName);
				if (null != statusName && statusName.equals("关闭") && null != displayName && displayName.length() > 0) {
					JiraHandle jiraHandle = new JiraHandle();
					jiraHandle.setBody(solution);
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						jiraHandle.setCreated(format.parse(createTime));
					} catch (ParseException e) {
						jiraHandle.setCreated(new Date());
					}
					jiraHandle.setDisplayName(displayName);
					jiraHandle.setActive(true);
					jiraHandles.add(jiraHandle);
				}
				
			}
			/**
			 * 解析处理进度列表
			 */
			JSONObject jiraComment = fields.getJSONObject("comment");
			if (jiraComment != null) {
				
				JSONArray comments = jiraComment.getJSONArray("comments");
				if (null != comments && comments.size() > 0) {
					for (int i = comments.size() - 1; i >= 0; i--) {
						JSONObject commentJira = (JSONObject) comments.get(i);
						JiraHandle jiraHandle = commentJira.getJSONObject("author").toJavaObject(JiraHandle.class);
						jiraHandle.setBody(commentJira.getString("body"));
						jiraHandle.setCreated(commentJira.getDate("created"));
						jiraHandle.setUpdated(commentJira.getDate("updated"));
						jiraHandles.add(jiraHandle);
					}
				}
				issue.setJiraHandles(jiraHandles);
			}
			
			issue.setReportEmpName(currentUser.getEmpName());
			EmpVo empVo = empBiz.findByEmpCode(currentUser.getEmpCode());
			issue.setReportTel(empVo.getMobile());
			issue.setAddPromble(addPromble);
			issue.setIssueType(issueType.getName());
			issue.setCreateTimeStr(createTime);
			issue.setDescription(description);
			issue.setDisplayName(displayName);
			issue.setKey(key);
			issue.setSolution(solution);
			issue.setEmpName(empName);
			issue.setEmpCode(empCode);
			issue.setOrgName(orgName);
			issue.setEmpTel(tel);
			issue.setComment(comment);
			issue.setPicUrl(picUrl);
			issue.setAttachments(attachments);
			return returnSuccess(issue);
		}
		return returnError("S9999", "未获取到此问题");
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
			if (content == null || key == null) {
				return returnError("S0001", "请求参数不正确");
			}
			//评论内容
			//评分
			JsonRootBean jsonRootBean = new JsonRootBean();
			JiraSet set = new JiraSet();
			String allowedValueId = null;
			String allowedValue = null;
			switch (score) {
				case "1":
					allowedValueId = Constant.JiraScore.One.ids();
					allowedValue = Constant.JiraScore.One.scores();
					break;
				case "2":
					allowedValueId = Constant.JiraScore.Two.ids();
					allowedValue = Constant.JiraScore.Two.scores();
					break;
				case "3":
					allowedValueId = Constant.JiraScore.Three.ids();
					allowedValue = Constant.JiraScore.Three.scores();
					break;
				case "4":
					allowedValueId = Constant.JiraScore.Four.ids();
					allowedValue = Constant.JiraScore.Four.scores();
					break;
				case "5":
					allowedValueId = Constant.JiraScore.Five.ids();
					allowedValue = Constant.JiraScore.Five.scores();
					break;
				default:
					allowedValueId = Constant.JiraScore.Five.ids();
					allowedValue = Constant.JiraScore.Five.scores();
			}
			set.setId(allowedValueId);
			set.setValue(allowedValue);

			Customfield11211 customfield11211 = new Customfield11211();
			customfield11211.setSet(set);

			Customfield11213 customfield11213 = new Customfield11213();
			customfield11213.setSet(content);

			Update update = new Update();
			List<Customfield11211> customfield11211s = new ArrayList<>();
			customfield11211s.add(customfield11211);
			update.setCustomfield_11211(customfield11211s);

			List<Customfield11213> customfield11213s = new ArrayList<>();
			customfield11213s.add(customfield11213);
			update.setCustomfield_11213(customfield11213s);
			jsonRootBean.setUpdate(update);
			String result = httpClientUtilforJira.sendHttpPut(jiraAuthUrl + "rest/api/2/issue/" + key, jsonRootBean);
			if (result == null) {
				return returnSuccess("追加评论成功");
			}
			return returnError("S9999", result);
		} catch (Exception e) {
			log.warn("评论异常", e);
		}
		return returnError("S9999", "添加失败");
	}

	@RequestMapping(value = {"getProjectByKey"}, method = {RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> getProjectByKey() {
		try {
			String result = httpClientUtilforJira.sendHttpGet(jiraAuthUrl + "rest/api/2/project/" + jiraProjectType + "/statuses");
			if (!StringUtils.isEmpty(result)) {
				JSONArray projectsJson = JSONArray.parseArray(result);
				int size = projectsJson.size();
				List<IssueTypeforJira> issueTypes = new ArrayList<>();
				for (int j = 0; j < size; j++) {
					JSONObject projectJson = (JSONObject) projectsJson.get(j);
					String id = (String) projectJson.get("id");
					String name = (String) projectJson.get("name");
					IssueTypeforJira isTypeforJira = new IssueTypeforJira();
					isTypeforJira.setId(id);
					isTypeforJira.setName(name);
					issueTypes.add(isTypeforJira);
				}
				return returnSuccess(issueTypes);
			}
			return returnError("S9999", "获取问题状态为空");
		} catch (Exception e) {
			log.warn("获取问题类型失败", e);
		}
		return returnError("S9999", "服务器异常");
	}

	//审批
	@RequestMapping(value = {"addComment"}, method = {RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> addComment(String key, String content, Integer isSuccessFlag, String score) {
		try {
			if ((key == null) || (content == null) || (isSuccessFlag == null)) {
				return returnError("S0001", "请求参数不正确");
			}
			
			boolean isSuccess = false;
			if (isSuccessFlag == 1) {
				isSuccess = true;
			}
			String result = httpClientUtilforJira.sendHttpGet(jiraAuthUrl + "rest/api/2/issue/" + key + "/transitions?expand=transitions.fields");

			JsonRootBean jsonRootBean = new JsonRootBean();
			if (!StringUtils.isEmpty(result)) {
				JSONObject json = JSON.parseObject(result);
				String transitionId = null;
				String allowedValueId = null;
				String allowedValue = null;
				if (json != null) {
					JSONArray jsonArray = (JSONArray) json.get("transitions");
					int size = jsonArray.size();
					for (int j = 0; j < size; j++) {
						JSONObject projectJson = (JSONObject) jsonArray.get(j);
						String name = (String) projectJson.get("name");
						JSONObject fields = (JSONObject) projectJson.get("fields");
						if ((fields == null) || (fields.size() == 0)) {
							return returnError("S9999", "未获取到按钮权限");
						}
						if (isSuccess) {
							if (name.contains("验收通过")) {
								transitionId = (String) projectJson.get("id");
								JSONObject scoreJson = (JSONObject) fields.get("customfield_11211");
								JSONArray allowedValues = (JSONArray) scoreJson.get("allowedValues");
								int allowSize = allowedValues.size();
								for (int k = 0; k < allowSize; k++) {
									JSONObject allowe = (JSONObject) allowedValues.get(k);
									if (allowe.get("value").equals(score)) {
										allowedValueId = allowe.get("id").toString();
										allowedValue = allowe.get("value").toString();
									}
								}
								JiraSet set = new JiraSet();
								set.setId(allowedValueId);
								set.setValue(allowedValue);
								Customfield11211 customfield11211 = new Customfield11211();
								customfield11211.setSet(set);

								Customfield11213 customfield11213 = new Customfield11213();

								customfield11213.setSet(content);

								Update update = new Update();
								List<Customfield11211> customfield11211s = new ArrayList<>();
								customfield11211s.add(customfield11211);
								update.setCustomfield_11211(customfield11211s);

								List<Customfield11213> customfield11213s = new ArrayList<>();
								customfield11213s.add(customfield11213);
								update.setCustomfield_11213(customfield11213s);

								Transition transition = new Transition();
								transition.setId(transitionId);

								jsonRootBean.setTransition(transition);
								jsonRootBean.setUpdate(update);
							}
						} else if (name.contains("验收失败")) {
							transitionId = (String) projectJson.get("id");

							Transition transition = new Transition();
							transition.setId(transitionId);

							Customfield11214 customfield11214 = new Customfield11214();
							customfield11214.setSet(content);
							Update update = new Update();
							List<Customfield11214> customfield11214s = new ArrayList<>();
							customfield11214s.add(customfield11214);
							update.setCustomfield_11214(customfield11214s);

							Add add = new Add();
							//empName
							EmpVo empVo = empBiz.findByEmpCode(WebUtil.getCurrentUser().getEmpCode());
							String empName = empVo == null ? "" : empVo.getEmpName();
							add.setBody(empName + ":" + content);
							Comment comment = new Comment();
							comment.setAdd(add);

							List<Comment> comments = new ArrayList<>();
							comments.add(comment);

							update.setComment(comments);
							jsonRootBean.setTransition(transition);
							jsonRootBean.setUpdate(update);
						}
					}
				} else {
					return returnError("S9999", "当前用户无权限审批");
				}
				log.warn("请求参数" + JSON.toJSONString(jsonRootBean));
				result = httpClientUtilforJira.sendHttpPost(jiraAuthUrl + "rest/api/2/issue/" + key + "/transitions", jsonRootBean);
				if (result == null) {
					return returnSuccess("感谢您的反馈！");
				}
				return returnError(result);
			}
			return returnError("S9999", "服务器异常");
		} catch (Exception e) {
			log.warn("评分失败", e);
		}
		return returnError("S9999", "服务器异常");
	}

	@RequestMapping(value = {"getPicture"}, method = {RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> getPicture(String files) {
		try {
			List<Attachment> base64FileList = new ArrayList<>();
			if(null != files && files.length() > 0) {
				String[] fileArr = files.split(",");
				for (String string : fileArr) {
					if (null != string && string.length() > 0) {
						Attachment attachment = new Attachment();
						InputStream inputStream = httpClientUtilforJira.getInputStream(string);
						byte[] data = toByteArray(inputStream);
						BASE64Encoder encoder = new BASE64Encoder();
						String fileName = string.substring(string.lastIndexOf("/") + 1);
						attachment.setFileName(URLDecoder.decode(fileName,"UTF-8"));
						attachment.setContent(encoder.encode(data));
						base64FileList.add(attachment);
					}
				}
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
	 * 获取文件base64
	 * </pre>
	 * @param path
	 * @return
	 * @return String
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月1日上午11:45:23
	 */
	public String getJiraFile(String path) {
		try {
			InputStream inputStream = httpClientUtilforJira.getInputStream(path);
			byte[] data = toByteArray(inputStream);
			BASE64Encoder encoder = new BASE64Encoder();
			String str = encoder.encode(data);
			return str;
		} catch (Exception e) {
			log.warn("获取文件异常", e);
			return "";
		}
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
	public void downJiraFile(String filePath, HttpServletResponse response) {
		try {
			InputStream inputStream = httpClientUtilforJira.getInputStream(filePath);
			if (null != inputStream) {
				byte[] data = toByteArray(inputStream);
				response.setContentType("application/octet-stream");
				// 通知浏览器下载文件而不是打开
				String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
				
				response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8")
		                + ";filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "No-cache");
				response.setDateHeader("Expires", 0);
				response.getOutputStream().write(data);
				response.getOutputStream().flush();
			}
		} catch (Exception e) {
			log.warn("获取文件异常", e);
		}
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
