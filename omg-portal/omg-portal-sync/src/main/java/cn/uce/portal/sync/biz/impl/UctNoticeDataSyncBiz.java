package cn.uce.portal.sync.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.core.sync.biz.BaseReceivePushMsgBiz;
import cn.uce.core.sync.service.AbstractReceivePushMsgListService;
import cn.uce.portal.sync.biz.util.PortalTimeUtils;
import cn.uce.portal.sync.entity.UctNoticeData;
import cn.uce.portal.sync.service.UctNoticeDataService;
import cn.uce.portal.sync.service.UctNoticeFilesService;
import cn.uce.portal.sync.vo.UctNoticeDataVo;

/**
 * 优速通公告主数据Biz
 *<pre>
 * UctNoticeDataSyncBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午3:55:27
 */
@Service("uctNoticeDataSyncBiz")
public class UctNoticeDataSyncBiz extends BaseReceivePushMsgBiz<UctNoticeDataVo, Integer, UctNoticeData, Long> {

	@Resource(name="uctNoticeDataService")
	private UctNoticeDataService noticeDataService;
	@Resource(name="uctNoticeFilesService")
	private UctNoticeFilesService noticeFilesService;
	
	@Override
	public AbstractReceivePushMsgListService<UctNoticeDataVo, Integer, UctNoticeData, Long> getReceivePushMsgListService() {
		return noticeDataService;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 处理优速通的数据
	 * </pre>
	 * @param uctNoticeData
	 * @param dealType
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月24日下午5:46:03
	 */
	public void processPushNoticeDataMsg(UctNoticeData uctNoticeData, Integer dealType) {
		if (dealType == 1) {
			//增加
			uctNoticeData.setNoticeSource("UCT");
			noticeDataService.saveNoticeData(uctNoticeData);
		} else if (dealType == 2) {
			//删除
			noticeDataService.deleteNoticeData(uctNoticeData.getNoticeId());
			noticeFilesService.deleteNoticeFilesByNoticeId(uctNoticeData.getNoticeId());
		} else if (dealType == 3) {
			//修改
			UctNoticeData uctNoticeDataSql = noticeDataService.findNoticeDataById(uctNoticeData.getNoticeId());
			if (null == uctNoticeDataSql) {
				//增加
				noticeDataService.saveNoticeData(uctNoticeData);
			} else {
				//比对更新时间
				if (PortalTimeUtils.compareTime(uctNoticeDataSql.getUpdateTime(), uctNoticeData.getUpdateTime())) {
					noticeDataService.updateNoticeData(uctNoticeData);
				}
			}
		}
	}
	
}
