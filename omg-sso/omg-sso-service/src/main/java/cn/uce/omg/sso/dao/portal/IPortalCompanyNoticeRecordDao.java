package cn.uce.omg.sso.dao.portal;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.portal.PortalCompanyNoticeRecord;

/**
 * 公司公告查看DAO
 *<pre>
 * IPortalCompanyNoticeRecordDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月31日下午2:02:25
 */
public interface IPortalCompanyNoticeRecordDao extends IBaseDao<PortalCompanyNoticeRecord, Long> {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据公告ID和查看人查询信息查询数量
	 * </pre>
	 * @param portalCompanyNoticeRecord
	 * @return
	 * @return PortalCompanyNoticeRecord
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月31日下午2:07:42
	 */
	int findRecordByNoticeId(PortalCompanyNoticeRecord portalCompanyNoticeRecord);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存查看记录
	 * </pre>
	 * @param portalCompanyNoticeRecord
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月31日下午2:34:38
	 */
	int saveNoticeRecord(PortalCompanyNoticeRecord portalCompanyNoticeRecord);
}
