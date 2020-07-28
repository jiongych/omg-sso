package cn.uce.portal.sync.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.core.db.IBaseDao;
import cn.uce.core.sync.service.AbstractReceivePushMsgListService;
import cn.uce.portal.sync.dao.IUctNoticeDataDao;
import cn.uce.portal.sync.entity.UctNoticeData;
import cn.uce.portal.sync.vo.UctNoticeDataVo;

/**
 * 优速通公告主数据Service
 *<pre>
 * UctNoticeDataService
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午3:41:09
 */
@Service("uctNoticeDataService")
public class UctNoticeDataService extends AbstractReceivePushMsgListService<UctNoticeDataVo, Integer, UctNoticeData, Long> {

	@Resource
	private IUctNoticeDataDao uctNoticeDataDao;
	
	public int saveNoticeData(UctNoticeData uctNoticeData) {
		return uctNoticeDataDao.insert(uctNoticeData);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据noticeId更新
	 * </pre>
	 * @param uctNoticeData
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午5:12:26
	 */
	public int updateNoticeData(UctNoticeData uctNoticeData) {
		return uctNoticeDataDao.updateNoticeDataById(uctNoticeData);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID删除
	 * </pre>
	 * @param id
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午5:12:15
	 */
	public int deleteNoticeData(Long id) {
		return uctNoticeDataDao.deleteNoticeDataById(id);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID查询
	 * </pre>
	 * @param id
	 * @return
	 * @return UctNoticeData
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午5:13:11
	 */
	public UctNoticeData findNoticeDataById(Long id) {
		return uctNoticeDataDao.findNoticeDataById(id);
	}
	
	@Override
	public IBaseDao<UctNoticeData, Long> getDao() {
		return uctNoticeDataDao;
	}

	@Override
	public Class<UctNoticeData> getDbEntityClass() {
		return UctNoticeData.class;
	}

	@Override
	public Class<UctNoticeDataVo> getMsgEntityClass() {
		return UctNoticeDataVo.class;
	}

	@Override
	public Long getDbPk(UctNoticeData entity) {
		return entity.getNoticeId();
	}

}
