package cn.uce.portal.sync.biz.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.uce.mq.rocket.exp.base.MsgBodyListenerConcurrently;
import cn.uce.portal.sync.entity.UctNoticeData;
import cn.uce.portal.sync.entity.UctNoticeFiles;
import cn.uce.portal.sync.entity.UctNoticeMustReadScope;
import cn.uce.portal.sync.entity.UctNoticeType;
import cn.uce.portal.sync.vo.UctNoticeFlagVo;
import cn.uce.portal.sync.vo.UctNoticeMustReadScopeVo;
import cn.uce.web.common.util.GZIPHelper;

import com.alibaba.fastjson.JSONObject;

/**
 * 接收优速通公告biz入口
 *<pre>
 * UctNoticeSyncBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午4:02:30
 */
@Service("uctNoticeSyncBiz")
public class UctNoticeSyncBiz extends MsgBodyListenerConcurrently<Object> {

	protected static Logger Log = LoggerFactory.getLogger(UctNoticeSyncBiz.class);

	@Resource(name="uctNoticeDataSyncBiz")
	private UctNoticeDataSyncBiz uctNoticeDataSyncBiz;
	
	@Resource(name="uctNoticeFilesSyncBiz")
	private UctNoticeFilesSyncBiz uctNoticeFilesSyncBiz;
	
	@Resource(name="uctNoticeTypeSyncBiz")
	private UctNoticeTypeSyncBiz uctNoticeTypeSyncBiz;
	
	@Resource(name="uctNoticeMustReadScopeSyncBiz")
	private UctNoticeMustReadScopeSyncBiz uctNoticeMustReadScopeSyncBiz;
	
	@Override
	public void processMessageBody(Object body) {
		if (null != body) {
			String message = processNoticeParam(body);
			UctNoticeFlagVo uctNoticeFlagVo = JSONObject.parseObject(message, UctNoticeFlagVo.class);
			if (null != uctNoticeFlagVo && null != uctNoticeFlagVo.getTableCode() &&  null != uctNoticeFlagVo.getDealType()) {
				//1001DATA表 1002TYPE表 1003FILE表
				if (uctNoticeFlagVo.getTableCode() == 1001) {
					//DATA表
					UctNoticeData uctNoticeData = JSONObject.parseObject(message, UctNoticeData.class);
					uctNoticeDataSyncBiz.processPushNoticeDataMsg(uctNoticeData, uctNoticeFlagVo.getDealType());
				} else if (uctNoticeFlagVo.getTableCode() == 1002) {
					//TYPE表
					UctNoticeType uctNoticeType = JSONObject.parseObject(message, UctNoticeType.class);
					uctNoticeTypeSyncBiz.processPushNoticeTypeMsg(uctNoticeType, uctNoticeFlagVo.getDealType());
				} else if (uctNoticeFlagVo.getTableCode() == 1003) {
					//FILE表
					UctNoticeFiles uctNoticeFiles = JSONObject.parseObject(message, UctNoticeFiles.class);
					uctNoticeFilesSyncBiz.processPushNoticeFilesMsg(uctNoticeFiles, uctNoticeFlagVo.getDealType());
				} else if (uctNoticeFlagVo.getTableCode() == 1004) {
					//MUST_READ表
					UctNoticeMustReadScope uctNoticeMustReadScope = new UctNoticeMustReadScope();
					UctNoticeMustReadScopeVo uctNoticeMustReadScopeVo = JSONObject.parseObject(message, UctNoticeMustReadScopeVo.class);
					if (null != uctNoticeMustReadScopeVo) {
						uctNoticeMustReadScope.setNoticeId(uctNoticeMustReadScopeVo.getNoticeId());
						if (uctNoticeMustReadScopeVo.getMustReadEmpCodes() != null) {
							uctNoticeMustReadScope.setCreateTime(new Date());
							String result = uctNoticeMustReadScopeVo.getMustReadEmpCodes().toString().replaceAll(" ", "");
							uctNoticeMustReadScope.setCode(result.substring(1, result.length() - 1));
							uctNoticeMustReadScope.setName("暂不存储");
						}
					}
					uctNoticeMustReadScopeSyncBiz.processPushNoticeMustReadScopeMsg(uctNoticeMustReadScope, uctNoticeFlagVo.getDealType());
				}
			}
		}
	}
	
	private String processNoticeParam(Object body) {
		String message = "";
		try {
			message = GZIPHelper.unZip(body.toString());
			message = message.toString().replaceAll("@type", "null");
		} catch(Exception e) {
			message = body.toString().replaceAll("@type", "null");
		}
		return message;
	}

}
