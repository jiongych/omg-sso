/** 
 * @项目名称: FSP
 * @文件名称: PushService 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.service;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.omg.gateway.api.IGwPushApi;
import cn.uce.omg.gateway.api.vo.PushRequestVo;
import cn.uce.omg.gateway.api.vo.PushResponseVo;
import cn.uce.omg.gateway.constant.OmgTrackConstants.PushHelperType;
import cn.uce.omg.gateway.constant.OmgTrackConstants.PushSubscribe;
import cn.uce.omg.sso.util.ObjectConvertUtil;

/**
 * 
 * 推送数据给第三方
 * @author: huangting   
 * @date: 2016年11月25日
 * @version: V1.0
 */
public class PushService {
	private final Log log = LogFactory.getLog(this.getClass());
	
	protected IGwPushApi gwPushApi;
	protected Map<String, PushRequestVo> pushConfVoMap;
	
	public void setGwPushApi(IGwPushApi gwPushApi) {
		this.gwPushApi = gwPushApi;
	}
	
	public void setPushConfVoMap(Map<String, PushRequestVo> pushConfVoMap) {
		this.pushConfVoMap = pushConfVoMap;
	}
	
	public void pushToAllPartner(Object msgBody, String productCode, PushSubscribe pushSubscribe) {
		for (PushHelperType partner : pushSubscribe.partners()) {
			String key = productCode + "-" + partner.code();
			PushRequestVo pushRequestVoTmp = pushConfVoMap.get(key);
			// add by majun 20190523 begin 多线程情况下，map中取出的对象被共享了，内部的Data被修改，导致推送了其他线程修改后的Data，本该推送的数据未推送
			PushRequestVo pushRequestVo = ObjectConvertUtil.convertObject(pushRequestVoTmp, PushRequestVo.class);
			// add by majun 20190523 end
			pushRequestVo.setProductCode(productCode);
			pushRequestVo.setPartnerId(partner.code());
			pushRequestVo.setData(msgBody);
			try {
				PushResponseVo responseVo = gwPushApi.push(pushRequestVo);
				if (log.isInfoEnabled()) {
					if (responseVo != null && responseVo.getSuccess()) {
						log.info("推送成功，产品code:" + pushRequestVo.getProductCode()
								+ ",消息内容:" + pushRequestVo.getData());
					}
				}
			} catch (Exception e) {
				log.error("推送失败，推送者信息: " + key + ", 消息内容：" + pushRequestVo.getData(), e);
			}
		}
	}
}
