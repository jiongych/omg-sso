/** 
 * @项目名称: FSP
 * @文件名称: SmsUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.uce.mcs.base.constant.ChannelType;
import cn.uce.mcs.caller.api.vo.MessageRespVo;
import cn.uce.mcs.caller.sdk.CallerHelper;
import cn.uce.mcs.caller.sdk.vo.SendMessageVo;
import cn.uce.omg.sso.constant.OmgConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;

import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.constant.SmsConstants;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.vo.SmsVo;
import cn.uce.smsp.base.HttpRequestParamVo;
import cn.uce.smsp.base.HttpResponseVo;
import cn.uce.smsp.smsc.vo.TempParamVo;
import cn.uce.utils.FastJsonUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * 发送短信util
 * @author huangting
 * @date 2017年6月9日 下午2:46:12
 */
public class SmsUtil {
	private Log log = LogFactory.getLog(this.getClass());
	
	/** 发送短信url */
	private String smsUrl;
	/** 请求参数 */
	private HttpRequestParamVo httpRequestParam;

	private CallerHelper callerHelper;

	/** 总部基准编码 */
	private String baseOrgCodeHq;

	public void setBaseOrgCodeHq(String baseOrgCodeHq) {
		this.baseOrgCodeHq = baseOrgCodeHq;
	}

	public void setHttpRequestParam(HttpRequestParamVo httpRequestParam) {
		this.httpRequestParam = httpRequestParam;
	}
	
	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	public void setCallerHelper(CallerHelper callerHelper) {
		this.callerHelper = callerHelper;
	}

	/**
	 * 发送短信
	 * @param smsVo
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2017年6月9日 下午2:46:40
	 */
	public MessageRespVo sendMessage(SmsVo smsVo) throws Exception{
		if (smsVo == null || smsVo.getMobile() == null) {
			log.info("调用短信平台发送短信,重要参数为空");
			throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
		}
		SendMessageVo message = new SendMessageVo();
		// 模板编码
		message.setTemplateCode("common_sms");
		// 通道类型
		message.setChannelTypes(ChannelType.SMS.code());
		// 请求人
		message.setReqEmp(smsVo.getEmpCode());
		// 请求网点omg org
		message.setReqOrg(smsVo.getEmpOrg());
		// 扣款网点
		message.setPayOrgCode(baseOrgCodeHq);
		// 业务id
		message.setBizId(OmgConstants.SYSTEM_CODE_SSO + System.currentTimeMillis());
		// 请求参数，与模板里一致。
		message.addMsgParam("content",smsVo.getContent());
		// 通过员工工号的方式添加接收范围方式
		message.addReceiverMobile(smsVo.getMobile());
		MessageRespVo sendMessage = callerHelper.sendMessage(message);
		/*//设置默认数据
		setDefaultData(smsVo);
		//获取发送短信数据
		HttpRequestParamVo httpRequestParamVo = getSendData(smsVo);
		String jsonStr = FastJsonUtil.toJsonString(httpRequestParamVo, false);
		log.info("调用短信平台发送短信,请求参数:" + jsonStr);
		byte[] content = new byte[0];
		if (jsonStr != null) {
			content = jsonStr.getBytes(httpRequestParam.getCharset());
		}
		String result = HttpUtil.doPostRequest(smsUrl, content);
		log.info("调用短信平台发送短信,返回结果:" + result);
		HttpResponseVo httpResponseVo  = JSON.parseObject(result, HttpResponseVo.class);*/
		return sendMessage;
	}
	
	/**
	 * 设置默认值
	 * @param smsVo
	 * @author huangting
	 * @date 2017年6月9日 下午2:47:22
	 */
	private void setDefaultData(SmsVo smsVo) {
		//设置默认短信模板
		if (smsVo.getTempId() == null ) {
			smsVo.setTempId(SmsConstants.DEFAULT_TEMP_ID);
		}
		//设置默认扣费网点Code
		if (smsVo.getSmspCostOrgCode() == null ) {
			smsVo.setSmspCostOrgCode(SmsConstants.DEFAULT_COST_ORG_CODE);
		}
	}
	
	/**
	 * 组装发送短信数据信息
	 * @param smsVo
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2017年6月9日 下午2:47:31
	 */
	private HttpRequestParamVo getSendData(SmsVo smsVo) throws Exception{
		//待签名字符串
		httpRequestParam.setUnsignedString(httpRequestParam.getPartnerId() + httpRequestParam.getServiceName());
		//签名
		httpRequestParam.setSign(SignUtils.sign(httpRequestParam.getUnsignedString(), httpRequestParam.getSecrtKey() ,httpRequestParam.getCharset()));
		Map<String, String> reqMap = new HashMap<String, String>();
		//短信模板
		reqMap.put(SmsConstants.SMS_TEMP_ID, smsVo.getTempId());
		//扣费网点CODE
		reqMap.put(SmsConstants.SMS_COST_ORG_CODE, smsVo.getSmspCostOrgCode());
		httpRequestParam.setParamsMap(reqMap);
		
		List<TempParamVo> messageList = new ArrayList<TempParamVo>();
		TempParamVo tempParamVo = new TempParamVo();
		//手机号
		tempParamVo.setMobile(smsVo.getMobile());
		Map<String, String> paramsMap = new HashMap<String, String>();
		//短信内容
		paramsMap.put(SmsConstants.SMS_CONTENT, smsVo.getContent());
		tempParamVo.setParamsMap(paramsMap);
		messageList.add(tempParamVo);
		httpRequestParam.setContent(messageList);
		return httpRequestParam;
	}
	
}
