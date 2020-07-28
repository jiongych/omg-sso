package cn.uce.omg.portal.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.ICompanyNoticeBiz;
import cn.uce.omg.portal.service.CompanyNoticeService;
import cn.uce.omg.portal.vo.NoticeParentInfoVo;
import cn.uce.omg.portal.vo.PortalCompanyNoticeRecordVo;
import cn.uce.omg.portal.vo.PortalCompanyNoticeVo;

/**
 * 公司公告BIZ实现类
 *<pre>
 * CompanyNoticeBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月29日下午7:37:41
 */
@Service(value = "fspCompanyNoticeBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class CompanyNoticeBiz implements ICompanyNoticeBiz {

	@Resource
	private CompanyNoticeService fspCompanyNoticeService;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 分页查询公司公告
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @param page
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月29日下午7:41:40
	 */
	@Override
	public Pagination<PortalCompanyNoticeVo> findCompanyNoticeByPage(
			PortalCompanyNoticeVo portalCompanyNoticeVo, Page page) {
		return fspCompanyNoticeService.findNoticeByPage(portalCompanyNoticeVo, page);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 更新公司公告
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月29日下午7:41:43
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int updateCompanyNotice(PortalCompanyNoticeVo portalCompanyNoticeVo) {
		return fspCompanyNoticeService.updateCompanyNotice(portalCompanyNoticeVo);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 新增公司公告
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月29日下午7:41:47
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int insertCompanyNotice(PortalCompanyNoticeVo portalCompanyNoticeVo) {
		return fspCompanyNoticeService.insertCompanyNotice(portalCompanyNoticeVo);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 删除公司公告
	 * </pre>
	 * @param id
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月29日下午7:41:51
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int deleteCompanyNotice(Long id) {
		return fspCompanyNoticeService.deleteCompanyNotice(id);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param id
	 * @param empCode
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月2日下午1:03:11
	 */
	@Override
	public PortalCompanyNoticeVo findById(Long id, String empCode) {
		
		return fspCompanyNoticeService.findById(id);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param portalCompanyNoticeRecordVo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月2日下午1:43:01
	 */
	@Override
	public int findRecordByNoticeId(PortalCompanyNoticeRecordVo portalCompanyNoticeRecordVo) {
		
		return fspCompanyNoticeService.findRecordByNoticeId(portalCompanyNoticeRecordVo);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param portalCompanyNoticeRecordVo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月2日下午1:43:04
	 */
	@Override
	public int saveNoticeRecord(PortalCompanyNoticeRecordVo portalCompanyNoticeRecordVo) {
		
		return fspCompanyNoticeService.saveNoticeRecord(portalCompanyNoticeRecordVo);
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
	@Override
	public List<NoticeParentInfoVo> findNoticeType() {
		
		return fspCompanyNoticeService.findNoticeType();
	}

}
