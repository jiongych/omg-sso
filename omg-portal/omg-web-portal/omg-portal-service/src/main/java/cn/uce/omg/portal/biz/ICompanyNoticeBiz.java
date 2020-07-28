package cn.uce.omg.portal.biz;

import java.util.List;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.vo.NoticeParentInfoVo;
import cn.uce.omg.portal.vo.PortalCompanyNoticeRecordVo;
import cn.uce.omg.portal.vo.PortalCompanyNoticeVo;

/**
 * 公司公告BIZ
 *<pre>
 * ICompanyNoticeBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月29日下午7:37:09
 */
public interface ICompanyNoticeBiz {

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
	 * @date 2018年3月29日下午7:34:55
	 */
	Pagination<PortalCompanyNoticeVo> findCompanyNoticeByPage(PortalCompanyNoticeVo portalCompanyNoticeVo, Page page);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 更新公司公告
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月29日下午7:36:13
	 */
	int updateCompanyNotice(PortalCompanyNoticeVo portalCompanyNoticeVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 新增公司公告
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月29日下午7:36:18
	 */
	int insertCompanyNotice(PortalCompanyNoticeVo portalCompanyNoticeVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 删除公司公告
	 * </pre>
	 * @param id
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月29日下午7:36:22
	 */
	int deleteCompanyNotice(Long id);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param id
	 * @param empCode
	 * @return
	 * @return PortalCompanyNoticeVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月2日下午12:49:45
	 */
	PortalCompanyNoticeVo findById(Long id, String empCode);
	
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
	 * @date 2018年8月2日下午1:40:36
	 */
	int findRecordByNoticeId(PortalCompanyNoticeRecordVo portalCompanyNoticeRecordVo);
	
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
	 * @date 2018年8月2日下午1:40:41
	 */
	int saveNoticeRecord(PortalCompanyNoticeRecordVo portalCompanyNoticeRecordVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询公告级别类型
	 * </pre>
	 * @return
	 * @return NoticeParentInfoVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月19日下午3:13:45
	 */
	List<NoticeParentInfoVo> findNoticeType();
}
