package cn.uce.omg.portal.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalCompanyNotice;
import cn.uce.omg.portal.vo.NoticeParentInfoVo;
import cn.uce.omg.portal.vo.PortalCompanyNoticeRecordVo;
import cn.uce.omg.portal.vo.PortalCompanyNoticeVo;

/**
 * 公司公告DAO
 *<pre>
 * ICompanyNoticeDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月28日下午2:20:43
 */
@Repository("fspCompanyNoticeDao")
public interface ICompanyNoticeDao extends IBaseDao<PortalCompanyNotice, Long> {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 分页查询
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @param page
	 * @return
	 * @return Pagination<PortalCompanyNoticeVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月28日下午2:22:26
	 */
	Pagination<PortalCompanyNoticeVo> findNoticeByPage(PortalCompanyNoticeVo portalCompanyNoticeVo, Page page);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param portalCompanyNoticeVo
	 * @param page
	 * @return
	 * @return Pagination<PortalCompanyNoticeVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月31日下午5:22:58
	 */
	Pagination<PortalCompanyNoticeVo> findNoticeByPageNew(PortalCompanyNoticeVo portalCompanyNoticeVo, Page page);
	
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
	 * @date 2018年3月28日下午3:10:55
	 */
	List<PortalCompanyNoticeVo> findByValidNotice();
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param id
	 * @return
	 * @return PortalCompanyNoticeVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月2日下午1:03:04
	 */
	PortalCompanyNoticeVo findNoticeById(Long id);
	
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
	 * @date 2018年8月2日下午1:43:42
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
	 * @date 2018年8月2日下午1:44:12
	 */
	int saveNoticeRecord(PortalCompanyNoticeRecordVo portalCompanyNoticeRecordVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param orgId
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月31日下午4:15:15
	 */
	String findFullOrgIdList(String orgId);
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
	List<NoticeParentInfoVo> findNoticeType();
}
