package cn.uce.portal.sync.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.core.sync.biz.BaseReceivePushMsgBiz;
import cn.uce.core.sync.service.AbstractReceivePushMsgListService;
import cn.uce.portal.sync.entity.UctNoticeMustReadScope;
import cn.uce.portal.sync.service.UctNoticeMustReadScopeService;
import cn.uce.portal.sync.vo.UctNoticeMustReadScopeVo;

/**
 * 优速通公告必读权限范围Biz
 *<pre>
 * UctNoticeMustReadScopeSyncBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @MustReadScope 2018年7月24日下午3:55:27
 */
@Service("uctNoticeMustReadScopeSyncBiz")
public class UctNoticeMustReadScopeSyncBiz extends BaseReceivePushMsgBiz<UctNoticeMustReadScopeVo, Integer, UctNoticeMustReadScope, Long> {

	@Resource(name="uctNoticeMustReadScopeService")
	private UctNoticeMustReadScopeService noticeMustReadScopeService;
	
	@Override
	public AbstractReceivePushMsgListService<UctNoticeMustReadScopeVo, Integer, UctNoticeMustReadScope, Long> getReceivePushMsgListService() {
		return noticeMustReadScopeService;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 处理优速通的数据
	 * </pre>
	 * @param uctNoticeMustReadScope
	 * @param dealType
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月24日下午5:46:03
	 */
	public void processPushNoticeMustReadScopeMsg(UctNoticeMustReadScope uctNoticeMustReadScope, Integer dealType) {
		if (dealType == 1) {
			//增加
			noticeMustReadScopeService.saveNoticeMustReadScope(uctNoticeMustReadScope);
		} else if (dealType == 2) {
			//删除
			noticeMustReadScopeService.deleteNoticeMustReadScope(uctNoticeMustReadScope.getNoticeId());
		} else if (dealType == 3) {
			//修改
			UctNoticeMustReadScope uctNoticeMustReadScopeSql = noticeMustReadScopeService.findNoticeMustReadScopeById(uctNoticeMustReadScope.getNoticeId());
			if (null == uctNoticeMustReadScopeSql) {
				//增加
				noticeMustReadScopeService.saveNoticeMustReadScope(uctNoticeMustReadScope);
			} else {
				noticeMustReadScopeService.updateNoticeMustReadScope(uctNoticeMustReadScope);
			}
		}
	}
	
}
