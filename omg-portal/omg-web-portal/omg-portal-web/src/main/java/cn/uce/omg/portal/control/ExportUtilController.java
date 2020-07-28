package cn.uce.omg.portal.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.omg.portal.biz.IExportSetBiz;
import cn.uce.omg.portal.vo.PortalExportSetVo;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.i18n.Resources;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 导出文件工具类
 *<pre>
 * ExportUtilController
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月19日下午3:41:45
 */
@Controller
@RequestMapping("/export")
public class ExportUtilController extends BaseController {

	@Resource
	private IExportSetBiz fspExportSetBiz;
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 表格导出设置
	 * </pre>
	 * @param request
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午3:42:23
	 */
	@RequestMapping(value = "/tableSet" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> tableSet(String key,String value) {
		//获取前台传入数据，新增或更新数据
		int result = fspExportSetBiz.addOrUpdateExportSet(key,value);
		if (result > 0) {
			return returnSuccess(Resources.getMessage("common.save.success"));
	    } else {
	    	return returnError(Resources.getMessage("common.save.fail"));
	    }
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据key查询数据
	 * </pre>
	 * @param key
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午5:28:03
	 */
	@RequestMapping(value = "/findExportSetByKey" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findExportSetByKey(String key) {
		PortalExportSetVo exportSetVo = fspExportSetBiz.findExportSetByKey(key);
		exportSetVo.setKey(exportSetVo.getSetKey());
		exportSetVo.setSetValue(handleValue(exportSetVo.getValue()));
		return returnSuccess(exportSetVo);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 将json转为map
	 * </pre>
	 * @param value
	 * @return
	 * @return Map<String,String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午5:42:24
	 */
	public static Map<String, String> handleValue(String value) {
		Map<String, String> setValue = new HashMap<>();
		if (null != value && value.length() > 0) {
			JSONArray json = (JSONArray) JSON.parse(value);
			if (null != json && json.size() > 0) {
				for (Object object : json) {
					JSONObject jsonO = (JSONObject) object;
					setValue.put(jsonO.getString("title"), jsonO.getString("field"));
				}
			}
		}
		return setValue;
	}
	
}
