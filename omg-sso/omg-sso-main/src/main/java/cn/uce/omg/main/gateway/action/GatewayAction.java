/** 
 * @项目名称: FSP
 * @文件名称: GatewayAction 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.gateway.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.uce.omg.main.gateway.service.GatewayService;
import cn.uce.omg.main.gateway.util.RequestUtils;
import cn.uce.omg.main.gateway.util.StringUtil;
import cn.uce.omg.main.gateway.vo.RequestVo;
import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.vo.ResponseVo;

/**
 * http请求统一入口
 * @author huangting
 * @date 2017年6月9日 下午12:03:47
 */
@Controller
@RequestMapping("/gateway")
public class GatewayAction {
	protected final Log LOG = LogFactory.getLog(GatewayAction.class);

	private GatewayService gatewayService;

	/**
	 * http请求入口
	 * @param request
	 * @param response
	 * @author huangting
	 * @date 2017年6月9日 下午12:04:17
	 */
	@RequestMapping(value = "/gateway.action")
	public void gateway(HttpServletRequest request, HttpServletResponse response) {
		RequestVo requestVo = null;
		String responseString = null;
		if ("GET".equals(request.getMethod())) {
			ResponseVo responseVo = new ResponseVo(ResponseVo.FALSE, ErrorCode.ILLEGAL_REQUEST_WAY);
			responseVo.setErrorMessage("服务端错误码：" + ErrorCode.ILLEGAL_REQUEST_WAY);
			responseString = gatewayService.getResponseString(responseVo, request.getContentType());
			// 输出应答信息
			gatewayService.outputResponse(requestVo, responseString, response);
			return;
		}
		try {
			//获取请求参数
			requestVo = RequestUtils.getRequestDto(request);
			// 参数检查
			gatewayService.requestParamCheck(requestVo, request);
			// 解析业务数据，调用业务组件进行业务处理，格式化处理结果
			responseString = gatewayService.serviceInvokeString(requestVo);
			// 返回处理结果
		} catch (GatewayException gateWayException) {
			LOG.error(gateWayException);
			ResponseVo responseVo = new ResponseVo(ResponseVo.FALSE, gateWayException.getErrorCode());
			if (StringUtil.isNotEmpty(gateWayException.getErrorMessage())) {
				responseVo.setErrorMessage(gateWayException.getErrorMessage());
			} else {
				responseVo.setErrorMessage("服务端错误码：" + gateWayException.getErrorCode());
			}
			if (requestVo != null) {
				responseString = gatewayService.getResponseString(responseVo, requestVo.getContentType());
			}
		}

		// 输出应答信息
		gatewayService.outputResponse(requestVo, responseString, response);
		return;
	}

	/**
	 * @param gatewayService the gatewayService to set
	 */
	public void setGatewayService(GatewayService gatewayService) {
		this.gatewayService = gatewayService;
	}

}
