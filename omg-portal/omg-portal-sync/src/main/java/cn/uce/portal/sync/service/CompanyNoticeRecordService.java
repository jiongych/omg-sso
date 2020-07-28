package cn.uce.portal.sync.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.core.db.IBaseDao;
import cn.uce.core.sync.service.AbstractReceivePushMsgListService;
import cn.uce.portal.sync.dao.ICompanyNoticeRecordDao;
import cn.uce.portal.sync.entity.PortalCompanyNoticeRecord;
import cn.uce.portal.sync.vo.PortalCompanyNoticeRecordVo;

/**
 * 公司公告用户读取service
 *<pre>
 * CompanyNoticeRecordService
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年8月22日下午4:31:54
 */
@Service("companyNoticeRecordService")
public class CompanyNoticeRecordService extends AbstractReceivePushMsgListService<PortalCompanyNoticeRecordVo, Integer, PortalCompanyNoticeRecord, Long> {

	@Resource
	private ICompanyNoticeRecordDao companyNoticeRecordDao;
	
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
	 * @date 2018年8月22日下午4:37:36
	 */
	public int saveNoticeRecord(PortalCompanyNoticeRecord portalCompanyNoticeRecord) {
		return companyNoticeRecordDao.insert(portalCompanyNoticeRecord);
	}
	
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
	 * @date 2018年8月22日下午4:50:59
	 */
	public int findNoticeRecord(PortalCompanyNoticeRecord portalCompanyNoticeRecord) {
		return companyNoticeRecordDao.findNoticeRecord(portalCompanyNoticeRecord);
	}
	
	@Override
	public IBaseDao<PortalCompanyNoticeRecord, Long> getDao() {
		return companyNoticeRecordDao;
	}

	@Override
	public Class<PortalCompanyNoticeRecord> getDbEntityClass() {
		return PortalCompanyNoticeRecord.class;
	}

	@Override
	public Class<PortalCompanyNoticeRecordVo> getMsgEntityClass() {
		return PortalCompanyNoticeRecordVo.class;
	}

	@Override
	public Long getDbPk(PortalCompanyNoticeRecord entity) {
		return entity.getNoticeId();
	}

}
