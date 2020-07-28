package cn.uce.omg.sso.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.omg.sso.vo.SecretFreeLoginVo;
import cn.uce.utils.StringUtil;

import com.alibaba.fastjson.JSONObject;

public class YmHttpClientUtil {
	private static Log LOG = LogFactory.getLog(YmHttpClientUtil.class);
	
	private String yimididaSsoUrl;
	
	private String yimididaYhUrl;
	
	private String oaUrl;

	private String pmsUrl;
	
	public void setYimididaSsoUrl(String yimididaSsoUrl) {
		this.yimididaSsoUrl = yimididaSsoUrl;
	}
	
	public void setYimididaYhUrl(String yimididaYhUrl) {
		this.yimididaYhUrl = yimididaYhUrl;
	}

	public void setOaUrl(String oaUrl) {
		this.oaUrl = oaUrl;
	}

	public void setPmsUrl(String pmsUrl) {
		this.pmsUrl = pmsUrl;
	}

	/**
	 * @Description: 校验快运SSO token是否有效
	 * @param token
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 上午11:26:27
	 */
	public JSONObject checkToken(SecretFreeLoginVo secretFreelogin){
		JSONObject result = null;
		try {
			String resultStr = HttpClientUtilforJira.sendHttpGet(yimididaSsoUrl + "api/profile?access_token=" + secretFreelogin.getTokenId());
			if (StringUtil.isBlank(resultStr)) {
				LOG.error(secretFreelogin.getTokenId() + ",请求快运SSO token校验接口结果:" + resultStr);
				return null;
			}
			result = JSONObject.parseObject(resultStr);
			if(StringUtil.isNotBlank(result.getString("error")) 
					|| !StringUtil.equals(result.getString("success"),"success") 
					|| result.getJSONObject("data") == null
					|| !checkUser(secretFreelogin, result.getJSONObject("data"))) {
				return null;
			}
		} catch (Exception e) {
			LOG.error(secretFreelogin.getTokenId() + ",请求快运SSO token校验接口异常，结果:" + result);
			return null;
		}
		return result;
	}

	public JSONObject checkPmsToken(SecretFreeLoginVo secretFreelogin){
		JSONObject result = null;
		try {
			String resultStr = HttpClientUtilforJira.sendHttpGet(pmsUrl + "Api/SsoCheck/ValidateYimididaCodeForToken?Sso=" +  secretFreelogin.getTokenId());
			if (StringUtil.isBlank(resultStr)) {
				LOG.error(secretFreelogin.getTokenId()+",请求PMS token校验接口结果:" + resultStr);
				return null;
			}
			result = JSONObject.parseObject(resultStr);
			if(result == null || result.getBoolean("IsSuccess") == null || result.getBoolean("IsSuccess") == false){
				return null;
			}
		} catch (Exception e) {
			LOG.error(secretFreelogin.getTokenId()+",请求PMS token校验接口异常，结果:" + result);
			return null;
		}
		return result;
	}
	
	/**
	 * @Description: 校验快运银河 token是否有效
	 * @param token
	 * @return
	 * @author huangting
	 * @date 2019年9月18日 上午11:26:27
	 */
	public JSONObject checkYhToken(SecretFreeLoginVo secretFreelogin){
		JSONObject result = null;
		try {
			String resultStr = HttpClientUtilforJira.sendHttpGet(yimididaYhUrl + "galaxy-user-business/user/yhUser/checkToken?token=" +  secretFreelogin.getTokenId());
			if (StringUtil.isBlank(resultStr)) {
				LOG.error(secretFreelogin.getTokenId()+",请求银河 token校验接口结果:" + resultStr);
				return null;
			}
			result = JSONObject.parseObject(resultStr);
			if(result == null || result.getBoolean("success") == null || result.getBoolean("success") == false
					|| !checkUser(secretFreelogin, result.getJSONObject("data"))){
				return null;
			}
		} catch (Exception e) {
			LOG.error(secretFreelogin.getTokenId()+",请求快运SSO token校验接口异常，结果:" + result);
			return null;
		}
		return result;
	}
	
	private boolean checkUser(SecretFreeLoginVo secretFreelogin,JSONObject obj) {
		if (StringUtil.isNotBlank(obj.getString("code")) && StringUtil.isNotBlank(obj.getString("userAccountType"))
				&& obj.getString("code").equals(secretFreelogin.getUserName())
				&& obj.getString("userAccountType").equals(secretFreelogin.getCompCode())){
			return true;
		}
		return false;
	}
	
	/**
	 * 校验OA token有效性
	 * @param empCode
	 * @param token
	 * @return
	 * @author huangting
	 * @date 2019年10月22日 下午1:58:22
	 */
	public JSONObject checkOAToken(String empCode,String token) {
		String result = null;
		Map<String, Object> map = new HashMap<>();
		map.put("workcode", empCode);
		map.put("token", token);
		try {
			result = HttpClientUtilforJira.sendHttpPostJson(oaUrl + "ymdd/checkToken",HttpClientUtilforJira.convertJSONParamter(map));
			LOG.error("用户:" + empCode +",token:" + token+",请求OA token校验接口结果:" + result);
			if (StringUtil.isBlank(result)) {
				return null;
			}
			JSONObject obj = JSONObject.parseObject(result);
			if (StringUtil.isBlank(obj.getString("code")) || StringUtil.equals(obj.getString("code"),"1")){
				LOG.error("用户:" + empCode +",token:" + token+",请求OA token校验接口结果:" + result);
				return null;
			}
			return obj;
		} catch (Exception e) {
			LOG.error(token+",请求OA  token校验接口异常，结果:" + result);
			return null;
		}
	}
	
}
