package cn.uce.portal.sync.biz.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.uce.mq.rocket.exp.base.MsgBodyListenerConcurrently;
import cn.uce.omg.sync.util.ObjectConvertUtil;
import cn.uce.portal.sync.entity.PortalCompanyNoticeRecord;
import cn.uce.portal.sync.service.CompanyNoticeRecordService;
import cn.uce.portal.sync.vo.PortalCompanyNoticeRecordVo;

import com.alibaba.fastjson.JSONObject;

/**
 * 接收用户公告读取信息
 *<pre>
 * CompanyNoticeRecordBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年8月22日下午4:40:33
 */
@Service("companyNoticeRecordSyncBiz")
public class CompanyNoticeRecordSyncBiz extends MsgBodyListenerConcurrently<Object> {

	protected static Logger Log = LoggerFactory.getLogger(CompanyNoticeRecordSyncBiz.class);

	@Resource(name="companyNoticeRecordService")
	private CompanyNoticeRecordService companyNoticeRecordService;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 加工用户读取信息
	 * </pre>
	 * @param body
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月22日下午4:40:57
	 */
	@Override
	public void processMessageBody(Object body) {
		if (null != body) {
			String message = body.toString().replaceAll("@type", "null");
			PortalCompanyNoticeRecordVo portalCompanyNoticeRecordVo = JSONObject.parseObject(message, PortalCompanyNoticeRecordVo.class);
			PortalCompanyNoticeRecord portalCompanyNoticeRecord = ObjectConvertUtil.convertObject(portalCompanyNoticeRecordVo, PortalCompanyNoticeRecord.class);
			portalCompanyNoticeRecord.setViewUser(portalCompanyNoticeRecordVo.getEmpCode());
			portalCompanyNoticeRecord.setViewTime(portalCompanyNoticeRecordVo.getCreateTime());
			int viewNum = companyNoticeRecordService.findNoticeRecord(portalCompanyNoticeRecord);
			
			if (viewNum == 0) {
				portalCompanyNoticeRecord.setEmpName(portalCompanyNoticeRecordVo.getEmpName());
				portalCompanyNoticeRecord.setViewTime(new Date());
				portalCompanyNoticeRecord.setViewFlag(true);
				companyNoticeRecordService.saveNoticeRecord(portalCompanyNoticeRecord);
			}
		}
	}

}
