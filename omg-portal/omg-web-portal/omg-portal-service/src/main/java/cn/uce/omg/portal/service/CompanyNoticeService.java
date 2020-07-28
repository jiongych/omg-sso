package cn.uce.omg.portal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.dao.ICompanyNoticeDao;
import cn.uce.omg.portal.entity.PortalCompanyNotice;
import cn.uce.omg.portal.vo.NoticeParentInfoVo;
import cn.uce.omg.portal.vo.PortalCompanyNoticeRecordVo;
import cn.uce.omg.portal.vo.PortalCompanyNoticeVo;
import cn.uce.web.common.util.ObjectConvertUtil;

/**
 * 公司公告Service
 *<pre>
 * CompanyNoticeService
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月28日下午2:34:12
 */
@Service("fspCompanyNoticeService")
public class CompanyNoticeService {

	@Resource
	private ICompanyNoticeDao fspCompanyNoticeDao;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 分页查询公司公告
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @param page
	 * @return
	 * @return Pagination<PortalCompanyNoticeVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月28日下午2:33:57
	 */
	public Pagination<PortalCompanyNoticeVo> findNoticeByPage(PortalCompanyNoticeVo portalCompanyNoticeVo, Page page) {
		try {
			String fullOrdId = fspCompanyNoticeDao.findFullOrgIdList(portalCompanyNoticeVo.getOrgId());
			if (null != fullOrdId) {
				String[] orgArr = fullOrdId.split(",");
				StringBuffer orgListSb = new StringBuffer();
				for (String orgFullId : orgArr) {
					orgListSb.append("FIND_IN_SET(").append(orgFullId).append(",OMG_PORTAL_UCT_NOTICE_DATA.CODE_SCOPE) OR ");
				}
				orgListSb = orgListSb.delete(orgListSb.length() - 4, orgListSb.length());
				portalCompanyNoticeVo.setOrgListStr(orgListSb.toString());
			}
			Pagination<PortalCompanyNoticeVo> pages = fspCompanyNoticeDao.findNoticeByPageNew(portalCompanyNoticeVo, page);
			return pages;
		} catch (Exception e) {
			return fspCompanyNoticeDao.findNoticeByPage(portalCompanyNoticeVo, page);
		}
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 公司公告新增
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月28日下午2:53:55
	 */
	public int insertCompanyNotice(PortalCompanyNoticeVo portalCompanyNoticeVo) {
		PortalCompanyNotice portalCompanyNotice = ObjectConvertUtil.convertObject(portalCompanyNoticeVo, PortalCompanyNotice.class);
		return fspCompanyNoticeDao.insert(portalCompanyNotice);
	}
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 公司公告更新
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月28日下午2:52:21
	 */
	public int updateCompanyNotice(PortalCompanyNoticeVo portalCompanyNoticeVo) {
		PortalCompanyNotice portalCompanyNotice = 
				ObjectConvertUtil.convertObject(portalCompanyNoticeVo, PortalCompanyNotice.class);
		return fspCompanyNoticeDao.updateById(portalCompanyNotice);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 公司公告更新
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月28日下午2:52:21
	 */
	public int deleteCompanyNotice(Long id) {
		return fspCompanyNoticeDao.deleteById(id);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID获取公司公告
	 * </pre>
	 * @param id
	 * @return
	 * @return PortalCompanyNoticeVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月28日下午3:03:35
	 */
	public PortalCompanyNoticeVo findById(Long id) {
		PortalCompanyNoticeVo portalCompanyNoticeVo = fspCompanyNoticeDao.findNoticeById(id);
		if (null != portalCompanyNoticeVo) {
			List<Map<String,Object>> noticeFiles = portalCompanyNoticeVo.getNoticeFiles();
			if (null != noticeFiles && !noticeFiles.isEmpty()) {
				StringBuffer noticeContent = new StringBuffer();
				noticeContent.append(portalCompanyNoticeVo.getContent() == null ? "" : portalCompanyNoticeVo.getContent());
				noticeContent.append("</br><hr />");
				for (Map<String, Object> map : noticeFiles) {
					noticeContent.append("<p>附件：");
					noticeContent.append("<a href='").append(map.get("filePath")).append("' target='_blank'><span style='color:#003399;'>");
					noticeContent.append(map.get("fileName")).append("</span></a></p>");
				}
				portalCompanyNoticeVo.setContent(noticeContent.toString());
			}
		}
			
		return portalCompanyNoticeVo;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询有效的公司公告
	 * </pre>
	 * @return
	 * @return List<PortalCompanyNoticeVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月28日下午3:12:12
	 */
	public List<PortalCompanyNoticeVo> findByValidNotice() {
		return fspCompanyNoticeDao.findByValidNotice();
	}
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param portalCompanyNoticeRecordVo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月2日下午1:44:20
	 */
	public int saveNoticeRecord(PortalCompanyNoticeRecordVo portalCompanyNoticeRecordVo) {
		return fspCompanyNoticeDao.saveNoticeRecord(portalCompanyNoticeRecordVo);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param portalCompanyNoticeRecordVo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月2日下午1:44:23
	 */
	public int findRecordByNoticeId(PortalCompanyNoticeRecordVo portalCompanyNoticeRecordVo) {
		return fspCompanyNoticeDao.findRecordByNoticeId(portalCompanyNoticeRecordVo);
	}
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询公告类型
	 * </pre>
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月19日下午3:14:58
	 */
	public List<NoticeParentInfoVo> findNoticeType() {
		
		return fspCompanyNoticeDao.findNoticeType();
	}
}
