package cn.uce.omg.portal.control;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.mq.rocket.RocketTemplate;
import cn.uce.omg.portal.biz.ICompanyNoticeBiz;
import cn.uce.omg.portal.biz.IOrgBiz;
import cn.uce.omg.portal.vo.OrgVo;
import cn.uce.omg.portal.vo.PortalCompanyNoticeRecordVo;
import cn.uce.omg.portal.vo.PortalCompanyNoticeVo;
import cn.uce.utils.FastJsonUtil;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.i18n.Resources;
import cn.uce.web.common.util.WebUtil;
import cn.uce.web.file.service.FastdfsService;
import cn.uce.web.file.vo.FileInfoVO;

/**
 * 公司公告控制类
 *<pre>
 * CompanyNoticeController
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月28日下午2:15:38
 */
@Controller
@RequestMapping("/notice")
public class CompanyNoticeController extends BaseController {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Resource(name = "fileService")
	private FastdfsService fastdfsService;
	@Resource
	private ICompanyNoticeBiz fspCompanyNoticeBiz;
	@Resource
	private RocketTemplate noticeReadTemplate;
	@Resource
	private IOrgBiz orgBiz;
	
	@RequestMapping(value ="/forward")
	public String forward() throws Exception {
		return "portal/notice";
	}
	
	@RequestMapping(value ="/noticeDetail")
	public String noticeDetail() throws Exception {
		return "portal/noticeDetail";
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 分页查询公司公告
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @param page
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月29日下午7:44:46
	 */
	@RequestMapping(value ="/findCompanyNoticeByPage")
	@ResponseBody
	public Map<String, Object> findCompanyNoticeByPage(PortalCompanyNoticeVo portalCompanyNoticeVo, Page page) {
		portalCompanyNoticeVo.setTypeId(portalCompanyNoticeVo.getTypeId() == null ? "0" : portalCompanyNoticeVo.getTypeId());
		portalCompanyNoticeVo.setOrgId(WebUtil.getCurrentUser().getOrgId() + "");
		portalCompanyNoticeVo.setEmpCode(WebUtil.getCurrentUser().getEmpCode());
		Pagination<PortalCompanyNoticeVo> paginations = fspCompanyNoticeBiz.findCompanyNoticeByPage(portalCompanyNoticeVo, page);
		return returnSuccess(paginations);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 新增公司公告
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月29日下午7:59:35
	 */
	@RequestMapping(value="insertCompanyNotice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertCompanyNotice(PortalCompanyNoticeVo portalCompanyNoticeVo) {
		portalCompanyNoticeVo.setCreateEmp(WebUtil.getCurrentUser().getEmpCode());
		portalCompanyNoticeVo.setCreateOrg(WebUtil.getCurrentUser().getCmsOrgId());//TODO
		portalCompanyNoticeVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		portalCompanyNoticeVo.setUpdateOrg(WebUtil.getCurrentUser().getCmsOrgId());//TODO
		portalCompanyNoticeVo.setUpdateTime(new Date());
		int result = fspCompanyNoticeBiz.insertCompanyNotice(portalCompanyNoticeVo);
		if (result > 0) {
			return returnSuccess(Resources.getMessage("common.save.success"));
	    } else {
	    	return returnError(Resources.getMessage("common.save.fail"));
	    }
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @param imgFile
	 * @return
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadFile(HttpServletRequest request,HttpServletResponse response,  
            @RequestParam MultipartFile[] imgFile) {
		//获取项目访问路径
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
		                request.getServerPort() + request.getContextPath()+"/";
		
    	String jpeg = "jpeg,tiff,raw,bmp,gif,png,jpg";
    	FileInfoVO fileInfoVo = new FileInfoVO();
    	fileInfoVo.setFileStatus("0");
    	fileInfoVo.setVersion(1);
    	fileInfoVo.setSubSys("PORTAL");
    	List<String> fileInfo = fastdfsService.uploadFile(imgFile, fileInfoVo);
    	fileInfoVo.setFileId(fileInfo.get(0));
    	Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("error", 0);
		if(jpeg.contains(fileInfo.get(0).substring(fileInfo.get(0).lastIndexOf(".")+1).toLowerCase())){
			msg.put("url", basePath.toString() + "notice/viewImage.do?fileId=" + fileInfo.get(0));
		}else {
			msg.put("url", basePath.toString() + "notice/downLoadFile.do?fileId=" + fileInfo.get(0));
		}
		msg.put("fileName", imgFile[0].getOriginalFilename());
		return msg;  
    }
    
	/**
	 * 查看图片
	 * @param fileId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/viewImage", method = RequestMethod.GET)
    @ResponseBody
    public void viewImage(String fileId, HttpServletResponse response) throws IOException {
    	FileInfoVO fileInfoVo = new FileInfoVO();
    	fileInfoVo.setFileStatus("0");
    	fileInfoVo.setVersion(1);
    	fileInfoVo.setSubSys("PORTAL");
    	fileInfoVo.setFileId(fileId);
    	byte[] imgbyte = fastdfsService.downLoadFile(fileInfoVo);
        response.setContentType("image/jpeg");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(imgbyte);
        outputStream.flush();
    }
    
    /**
	 * 下载文件
	 * 
	 * @param fileId
	 *            文件ID
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/downLoadFile.do")
	@ResponseBody
	public void downLoadFile(@RequestParam String fileId,
			HttpServletResponse response) throws IOException {
		logger.info("download file start ：");
		FileInfoVO info = new FileInfoVO();
		info.setFileId(fileId);
		byte[] file_id = fastdfsService.downLoadFile(info);
		response.setContentType("application/octet-stream");
		// 通知浏览器下载文件而不是打开
		response.addHeader("Content-Disposition", "attachment;filename="
				+ fileId.substring(fileId.lastIndexOf("/") + 1));
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		response.getOutputStream().write(file_id);
		response.getOutputStream().flush();
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 更新公司公告
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月29日下午7:57:49
	 */
	@RequestMapping(value="updateCompanyNotice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCompanyNotice(PortalCompanyNoticeVo portalCompanyNoticeVo) {
		portalCompanyNoticeVo.setUpdateTime(new Date());
		portalCompanyNoticeVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		portalCompanyNoticeVo.setUpdateOrg(WebUtil.getCurrentUser().getCmsOrgId());//TODO
		int result = fspCompanyNoticeBiz.updateCompanyNotice(portalCompanyNoticeVo);
		if (result > 0) {
			return returnSuccess(Resources.getMessage("common.update.success"));
	    } else {
	    	return returnError(Resources.getMessage("common.update.fail"));
	    }
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 删除公司公告
	 * </pre>
	 * @param id
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月29日下午7:59:23
	 */
	@RequestMapping(value="deleteCompanyNotice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCompanyNotice(Long id) {
		int result = fspCompanyNoticeBiz.deleteCompanyNotice(id);
		if (result > 0) {
			return returnSuccess(Resources.getMessage("common.delete.success"));
	    } else {
	    	return returnError(Resources.getMessage("common.delete.fail"));
	    }
	}
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据noticeID查看
	 * </pre>
	 * @param id
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月2日下午12:46:48
	 */
	@RequestMapping(value="findById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findById(Long id) {
		PortalCompanyNoticeVo portalCompanyNoticeVo = fspCompanyNoticeBiz.findById(id, WebUtil.getCurrentUser().getEmpCode());
		if (null == portalCompanyNoticeVo) {
			return returnSuccess(null);
		}
		OrgVo orgVo = orgBiz.findByOrgCode(portalCompanyNoticeVo.getCreateOrg());
		if (null != orgVo) {
			portalCompanyNoticeVo.setCreateOrgName(orgVo.getOrgName());
		}
		PortalCompanyNoticeRecordVo portalCompanyNoticeRecordVo = new PortalCompanyNoticeRecordVo();
		portalCompanyNoticeRecordVo.setNoticeId(id);
		int noticeViewNum = fspCompanyNoticeBiz.findRecordByNoticeId(portalCompanyNoticeRecordVo);
		portalCompanyNoticeVo.setViewCount(noticeViewNum);
		try {
			PortalCompanyNoticeRecordVo portalCompanyNoticeRecord = new PortalCompanyNoticeRecordVo();
			portalCompanyNoticeRecord.setNoticeId(id);
			portalCompanyNoticeRecord.setViewUser(WebUtil.getCurrentUser().getEmpCode());
			portalCompanyNoticeRecord.setViewTime(new Date());
			portalCompanyNoticeRecord.setViewFlag(true);
			portalCompanyNoticeRecord.setFromSource(2);
			portalCompanyNoticeRecord.setOrgCode(WebUtil.getCurrentUser().getOrgId() + "");
			portalCompanyNoticeRecord.setEmpName(WebUtil.getCurrentUser().getEmpName());
			portalCompanyNoticeRecord.setEmpCode(WebUtil.getCurrentUser().getEmpCode());
			portalCompanyNoticeRecord.setCreateTime(new Date());
			noticeReadTemplate.send(FastJsonUtil.toJsonString(portalCompanyNoticeRecord));
		} catch (Exception e) {
			log.error("发送用户查看公告消息失败", e);
		} 
		
		return returnSuccess(portalCompanyNoticeVo);
	}
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询公告类型
	 * </pre>
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月19日下午3:14:22
	 */
	@RequestMapping(value="findNoticeType", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findNoticeType() {
		
		return returnSuccess(fspCompanyNoticeBiz.findNoticeType());
	}
}
