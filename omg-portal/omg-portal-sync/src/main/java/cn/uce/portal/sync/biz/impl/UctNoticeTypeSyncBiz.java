package cn.uce.portal.sync.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.core.sync.biz.BaseReceivePushMsgBiz;
import cn.uce.core.sync.service.AbstractReceivePushMsgListService;
import cn.uce.portal.sync.biz.util.PortalTimeUtils;
import cn.uce.portal.sync.entity.UctNoticeType;
import cn.uce.portal.sync.service.UctNoticeTypeService;
import cn.uce.portal.sync.vo.UctNoticeTypeVo;

/**
 * 接收优速通公告类型BIZ
 *<pre>
 * UctNoticeTypeSyncBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午4:01:03
 */
@Service("uctNoticeTypeSyncBiz")
public class UctNoticeTypeSyncBiz extends BaseReceivePushMsgBiz<UctNoticeTypeVo, Integer, UctNoticeType, Long> {

	@Resource(name="uctNoticeTypeService")
	private UctNoticeTypeService noticeTypeService;
	
	@Override
	public AbstractReceivePushMsgListService<UctNoticeTypeVo, Integer, UctNoticeType, Long> getReceivePushMsgListService() {
		return noticeTypeService;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 处理优速通的数据
	 * </pre>
	 * @param uctNoticeType
	 * @param dealType
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月24日下午5:46:45
	 */
	public void processPushNoticeTypeMsg(UctNoticeType uctNoticeType, Integer dealType) {
		if (dealType == 1) {
			//增加
			noticeTypeService.saveNoticeType(uctNoticeType);
		} else if (dealType == 2) {
			//删除
			noticeTypeService.deleteNoticeType(uctNoticeType.getTypeId());
		} else if (dealType == 3) {
			//修改
			UctNoticeType uctNoticeTypeSql = noticeTypeService.findNoticeTypeById(uctNoticeType.getTypeId());
			if (null == uctNoticeTypeSql) {
				//增加
				noticeTypeService.saveNoticeType(uctNoticeType);
			} else {
				if (PortalTimeUtils.compareTime(uctNoticeTypeSql.getUpdateTime(), uctNoticeType.getUpdateTime())) {
					noticeTypeService.updateNoticeType(uctNoticeType);
				}
			}
		}
	}
}
