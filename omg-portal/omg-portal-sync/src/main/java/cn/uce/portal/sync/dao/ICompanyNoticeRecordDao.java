package cn.uce.portal.sync.dao;

import org.springframework.stereotype.Repository;

import cn.uce.core.sync.dao.IBaseSyncDao;
import cn.uce.portal.sync.entity.PortalCompanyNoticeRecord;

/**
 * 公告读取
 *<pre>
 * ICompanyNoticeRecordDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年8月22日下午4:32:48
 */
@Repository("companyNoticeRecordDao")
public interface ICompanyNoticeRecordDao extends IBaseSyncDao<PortalCompanyNoticeRecord, Long> {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据条件查询
	 * </pre>
	 * @param portalCompanyNoticeRecord
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月22日下午4:48:50
	 */
	public int findNoticeRecord(PortalCompanyNoticeRecord portalCompanyNoticeRecord);
}
