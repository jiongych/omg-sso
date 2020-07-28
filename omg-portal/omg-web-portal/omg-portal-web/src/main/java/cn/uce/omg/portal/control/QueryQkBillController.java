/*package cn.uce.omg.portal.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.uce.core.cache.CacheManager;
import cn.uce.core.cache.base.ICache;
import cn.uce.omg.portal.biz.IBillQueryBiz;
import cn.uce.omg.portal.vo.PortalDictDataVo;
import cn.uce.web.common.base.BaseController;

*//**
 * portal首页运单查询，调用乾坤dubbo接口
 *<pre>
 * QueryQkBillController
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年9月6日下午12:26:27
 *//*
@Controller
@RequestMapping("/bill")
public class QueryQkBillController extends BaseController {

	@Resource
	private IBillQueryBiz billQueryBiz;
	
	*//**
	 * 
	 * 方法的描述：
	 * <pre>
	 * portal首页运单查询，调用乾坤dubbo接口，查入详情页面
	 * </pre>
	 * @param request
	 * @param response
	 * @param billCodes
	 * @return
	 * @throws IOException
	 * @return String
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年9月6日下午12:27:09
	 *//*
	@RequestMapping(value = "/queryQkBill")
	public String queryQkBill(HttpServletRequest request, HttpServletResponse response, String billCodes) throws IOException {
		
		String content = "";
		List<String> billCodeList = new ArrayList<>();
		if (null != billCodes && billCodes.length() > 0) {
			String[] strArr = billCodes.split(",");
			for (String billCode : strArr) {
				if (!billCodeList.contains(billCode)) {
					billCodeList.add(billCode);
				}
			}
			content = billErrorRemind(billCodeList);
		}
		request.setAttribute("content", content);
		return "portal/queryQkBill";
	}
	
	*//**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 判断接口是否返回为空或错误
	 * </pre>
	 * @param content
	 * @return
	 * @return String
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年9月7日上午10:41:01
	 *//*
	public String billErrorRemind(List<String> billCodeList) {
		String content = billQueryBiz.queryQkBill(billCodeList);
		if (content == null || content.length() == 0) {
			content = "<div style ='position: absolute;top: 50%;left: 50%;height: 30%;width: 50%;margin: -15% 0 0 -25%;'>网络异常，请稍后重试或点击<a href=''>刷新</a>。</div>";
			try{
				ICache<String,List<PortalDictDataVo>> cache = CacheManager.getInstance().getCache("DictDataCache");
				List<PortalDictDataVo> dictTypeVoList = cache.get("PORTAL_INFO");
				
				if (null != dictTypeVoList && dictTypeVoList.size() > 0) {
					for (PortalDictDataVo portalDictDataVo : dictTypeVoList) {
						if (null != portalDictDataVo && 
								null != portalDictDataVo.getTypeCode() &&
								null != portalDictDataVo.getTypeName() &&
										portalDictDataVo.getTypeName().equals("SEARCH_BILL_ERROR_REMIND")) {
							content = "<div style ='position: absolute;top: 50%;left: 50%;height: 30%;width: 50%;margin: -15% 0 0 -25%;'>" + portalDictDataVo.getTypeCode() + "<a href=''>刷新</a>。</div>";
						}
					}
				}
			} catch (Exception e) {
				content = "<div style ='position: absolute;top: 50%;left: 50%;height: 30%;width: 50%;margin: -15% 0 0 -25%;'>网络异常，请稍后重试或点击<a href=''>刷新</a>。</div>";
			}
		} else {
			content = content.replaceAll("【<a", "<a style='display:none;' ").replaceAll("</a>】", "</a>");
		}
		return content;
	}
}
*/