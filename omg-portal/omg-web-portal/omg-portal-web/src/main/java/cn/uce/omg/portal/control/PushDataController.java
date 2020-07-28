package cn.uce.omg.portal.control;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.omg.portal.biz.IDataPushBiz;
import cn.uce.omg.portal.vo.PortalDataPushVo;
import cn.uce.utils.JsonUtil;
import cn.uce.web.common.base.BaseController;
/**
 * 重推数据处理控制器
 * @author uce
 */
@Controller
@RequestMapping("/push")
public class PushDataController extends BaseController {
	private Logger log = LoggerFactory.getLogger(PushDataController.class);
	@Autowired
	private IDataPushBiz dataPushBiz;
	@RequestMapping(value = "/forward")
	public String get()
			throws IOException {
		return "portal/pushData";
	}
	
	/**
	 * 根据推送数据类型，开始时间，结束时间，接收方推送数据
	 * @param dataType
	 * @param startTime
	 * @param endTime
	 * @param receiver
	 * @return
	 */
	@RequestMapping(value = "/startPush")
	@ResponseBody
	public Map<String,Object> pushData(PortalDataPushVo dataPushVo) {
		if(log.isInfoEnabled()) {
			log.info("推送条件为：" + JsonUtil.toJson(dataPushVo));
		}
		return dataPushBiz.processPushData(dataPushVo);
//		return returnSuccess();
	}
	
	/**
	 * 根据推送数据类型，查询推送范围数据
	 * @param dataType
	 * @param startTime
	 * @param endTime
	 * @param receiver
	 * @return
	 */
	@RequestMapping(value = "/loadDataRange")
	@ResponseBody
	public List<Object> findPushRange(HttpServletRequest request) {
		PortalDataPushVo dataPushVo = new PortalDataPushVo();
		dataPushVo.setDataType(request.getParameter("dataType"));
		dataPushVo.setPushRange(request.getParameter("q"));
		if(log.isInfoEnabled()) {
			log.info("推送类型为：" + JsonUtil.toJson(dataPushVo));
		}
		return dataPushBiz.findPushRange(dataPushVo);
	}
}
