package cn.uce.omg.portal.control;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.omg.portal.biz.IFileInfoBiz;
import cn.uce.omg.portal.vo.FileTypeVo;
import cn.uce.uct.app.entity.ConfigureResult;
import cn.uce.uct.app.entity.OmgEmpResult;
import cn.uce.uct.app.vo.AdministrationVo;
import cn.uce.uct.app.vo.ConfigureVo;
import cn.uce.uct.app.vo.ResultData;
import cn.uce.uct.app.vo.UserCollectVo;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.base.CurrentUser;
import cn.uce.web.common.util.WebUtil;

@Controller
@RequestMapping("/file")
public class FileViewController extends BaseController {

	private Log log = LogFactory.getLog(getClass());
	/**
	 * 文件映射地址
	 */
	@Value("${file.down.mapping.path}")
    private String fileDownMappingPath;
	@Value("${uct.referer}")
	private String uctReferer;
	/**
	 * 优速通请求appCode
	 */
	@Value("${uct.file.appCode}")
    private String appCode;
	
	@Autowired
	private IFileInfoBiz fileInfoBiz;
	
	@RequestMapping(value = "/forward")
	public String get() throws IOException {
		return "portal/pdfFileList";
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据受众群体查询文件类型获取
	 * </pre>
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月13日上午11:30:02
	 */
	@RequestMapping("getFileType")
	@ResponseBody
	public List<FileTypeVo> getFileType(HttpServletRequest request, HttpServletResponse response, String oneTypeName) { 
		String head = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", head);
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		
		//如查受众群体为空，先查询当前受众群体
		if (null == oneTypeName || oneTypeName.length() == 0) {
			ConfigureVo configureVo = new ConfigureVo();
			CurrentUser currentUser = WebUtil.getCurrentUser();
			String empCode = currentUser.getEmpCode();
			configureVo.setEmpCode(empCode);
			OmgEmpResult omgEmpResult = fileInfoBiz.findEmpIdentity(configureVo);
			oneTypeName = omgEmpResult == null ? "" : omgEmpResult.getIdentityType();
		}

		//暂时只有一个一级树结构
		List<FileTypeVo> fileTypeList = new ArrayList<>();
		FileTypeVo fileTypeVo = new FileTypeVo();
		fileTypeVo.setTwoTypeName("公司文件");
		List<ConfigureResult> configureResults = fileInfoBiz.getFileType(oneTypeName);
		fileTypeVo.setFileTypeVoList(configureResults);
		fileTypeVo.setId(1l);
		fileTypeList.add(fileTypeVo);
		return fileTypeList;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 获取最新文件列表
	 * </pre>
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月13日上午11:31:06
	 */
	@RequestMapping("getFileListByWeb")
	@ResponseBody
	public Map<String, Object> getFileListByWeb(HttpServletRequest request, HttpServletResponse response, AdministrationVo administrationVo, String typeId) {
		String head = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", head);
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		if (null == administrationVo) {
			administrationVo = new AdministrationVo();
		}
		CurrentUser currentUser = WebUtil.getCurrentUser();
		String empCode = currentUser.getEmpCode();
		administrationVo.setEmpNo(empCode);
		//二级
		String twoTypeName = administrationVo.getTwoTypeName();
		if (null == twoTypeName || twoTypeName.length() == 0) {
			administrationVo.setTwoTypeName("最新文件");
		}
		//页数
		administrationVo.setPage(1);
		//条数
		administrationVo.setRows(10);
		administrationVo.setFileName(null);
		//先查询当前受众群体
		ConfigureVo configureVo = new ConfigureVo();
		configureVo.setEmpCode(empCode);
		OmgEmpResult omgEmpResult = fileInfoBiz.findEmpIdentity(configureVo);
		String identityType = omgEmpResult.getIdentityType();
		administrationVo.setIdentityType(identityType);
		if (null != omgEmpResult && null != omgEmpResult.getIdentityType() && !"最新文件".equals(administrationVo.getTwoTypeName())) {
			if ("网点".equals(identityType)) {
				administrationVo.setDotId(typeId);;
			}
			if ("总部".equals(identityType)) {
				administrationVo.setHeadquartersId(typeId);
			}
			if ("省区".equals(identityType)) {
				administrationVo.setProvinceId(typeId);
			}
			if ("快递员".equals(identityType)) {
				administrationVo.setCourierId(typeId);
			}
		}

		Map<String, Object> fileInfoList = fileInfoBiz.getNewFileList(administrationVo);
		return fileInfoList;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 受众群体
	 * </pre>
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月13日上午11:30:15
	 */
	@RequestMapping("getUserViewScope")
	@ResponseBody
	public List<String> getUserViewScope() {
		CurrentUser currentUser = WebUtil.getCurrentUser();
		String empCode = currentUser.getEmpCode();
		List<String> FileUserViewScopeVos = fileInfoBiz.getUserViewScope(empCode);
		return FileUserViewScopeVos;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 获取最新文件列表
	 * </pre>
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月13日上午11:31:06
	 */
	@RequestMapping("getNewFileList")
	@ResponseBody
	public Map<String, Object> getNewFileList(AdministrationVo administrationVo, String typeId) {
		CurrentUser currentUser = WebUtil.getCurrentUser();
		String empCode = currentUser.getEmpCode();
		administrationVo.setEmpNo(empCode);
		if (null != administrationVo.getFileName() && administrationVo.getFileName().length() == 0) {
			administrationVo.setFileName(null);
		}
		String identityType = administrationVo.getIdentityType();
		String twoTypeName = administrationVo.getTwoTypeName();
		if (null != identityType && !"最新文件".equals(twoTypeName)) {
			if ("网点".equals(identityType)) {
				administrationVo.setDotId(typeId);
			}
			if ("总部".equals(identityType)) {
				administrationVo.setHeadquartersId(typeId);
			}
			if ("省区".equals(identityType)) {
				administrationVo.setProvinceId(typeId);
			}
			if ("快递员".equals(identityType)) {
				administrationVo.setCourierId(typeId);
			}
		}
		Map<String, Object> fileInfoList = fileInfoBiz.getNewFileList(administrationVo);
		return fileInfoList;
	}
	
	/**
	 * 我的收藏
	 * 方法的描述：
	 * <pre>
	 * 我的收藏
	 * </pre>
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月13日上午11:31:57
	 */
	@RequestMapping("getMyCollectFile")
	@ResponseBody
	public Map<String, Object> getMyCollectFile(Integer currentPage, Integer pageSize) {
		CurrentUser currentUser = WebUtil.getCurrentUser();
		String empCode = currentUser.getEmpCode();
		UserCollectVo userCollectVo = new UserCollectVo();
		userCollectVo.setEmpCode(empCode);
		userCollectVo.setAppCode(appCode);
		userCollectVo.setType(1);
		//userCollectVo.setFromSource("公司文件");
		Map<String, Object> fileMyCollectFileVos = fileInfoBiz.getCollectFile(userCollectVo, currentPage, pageSize);
		return fileMyCollectFileVos;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 文件收藏
	 * </pre>
	 * @param primaryKey
	 * @return
	 * @return ResultData<UserCollectVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午6:51:36
	 */
	@RequestMapping("addCollect")
	@ResponseBody
	public ResultData<UserCollectVo> addCollect(UserCollectVo userCollectVo) {
		//addCollect 收藏接口：primaryKey：文件ID  title：标题  detailUrl：地址
		CurrentUser currentUser = WebUtil.getCurrentUser();
		String empCode = currentUser.getEmpCode();
		userCollectVo.setAppCode(appCode);
		userCollectVo.setType(1);
		//userCollectVo.setFromSource("公司文件");
		//工号
		userCollectVo.setEmpCode(empCode);
		ResultData<UserCollectVo> omgEmpResult = fileInfoBiz.addCollect(userCollectVo);
		return omgEmpResult;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 取消收藏
	 * </pre>
	 * @param primaryKey
	 * @return
	 * @return ResultData<UserCollectVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午6:51:44
	 */
	@RequestMapping("cancelCollect")
	@ResponseBody
	public ResultData<UserCollectVo> cancelCollect(String primaryKey) {
		CurrentUser currentUser = WebUtil.getCurrentUser();
		String empCode = currentUser.getEmpCode();
		UserCollectVo userCollectVo = new UserCollectVo();
		userCollectVo.setAppCode(appCode);
		//工号
		userCollectVo.setEmpCode(empCode);
		//取消收藏的文件id
		userCollectVo.setPrimaryKey(primaryKey);
		ResultData<UserCollectVo> omgEmpResult = fileInfoBiz.cancelCollect(userCollectVo);
		return omgEmpResult;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 更新阅读量
	 * </pre>
	 * @param primaryKey
	 * @return
	 * @return ResultData<UserCollectVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午7:42:39
	 */
	@RequestMapping("updateBrowse")
	@ResponseBody
	public int updateBrowse(HttpServletRequest request, HttpServletResponse response, Long primaryKey) {
		String head = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", head);
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		CurrentUser currentUser = WebUtil.getCurrentUser();
		String empCode = currentUser.getEmpCode();
		AdministrationVo administrationVo = new AdministrationVo();
		//工号
		administrationVo.setEmpNo(empCode);
		//文件ID
		administrationVo.setId(primaryKey);
		administrationVo.setOrgId(currentUser.getOrgId());
		int updateResult = fileInfoBiz.updateBrowse(administrationVo);
		return updateResult;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 预览文件
	 * </pre>
	 * @param fileUrl
	 * @param urlFlag
	 * @param response
	 * @param request
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午7:49:14
	 */
	@RequestMapping("getPdfFile")
	@ResponseBody
	public void getRemoteFile(String fileUrl, HttpServletResponse response, HttpServletRequest request) {
		String strUrl = fileUrl.trim();
		String pdfPath = fileDownMappingPath + strUrl.substring(strUrl.indexOf("/update/"), strUrl.length());
		InputStream stream = findFdf(response, request, pdfPath);
		if (null == stream) {
			findFdf(response, request, strUrl);
		}
	}
	
	public InputStream findFdf(HttpServletResponse response, HttpServletRequest request, String filePath) {
		InputStream inputStream = null;
		try {
			String head = request.getHeader("Origin");
			URL url = new URL(filePath);
			// 打开请求连接
			URLConnection connection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
			httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			httpURLConnection.setRequestProperty("referer", uctReferer);
			// 取得输入流，并使用Reader读取
			inputStream = httpURLConnection.getInputStream();
			int byteread = 0;
			byte[] buffer = new byte[1024];
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String("cbzm.pdf".getBytes()));
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			
			byteread = 0;
			buffer = new byte[1024];
			while ((byteread = inputStream.read(buffer)) != -1) {
				toClient.write(buffer, 0, byteread);
			}
			toClient.flush();
			inputStream.close();
	
			toClient.close();
		} catch (Exception e) {
			log.error("没有获取到优速通的文件内容：" + filePath);
			inputStream = null;
		}
		return inputStream;
	}


}
