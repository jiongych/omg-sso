package cn.uce.omg.portal.control;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.core.cache.CacheManager;
import cn.uce.core.cache.base.ICache;
import cn.uce.omg.portal.biz.IDictDataBiz;
import cn.uce.omg.portal.biz.IOrgBiz;
import cn.uce.omg.portal.biz.IPermissionBiz;
import cn.uce.omg.portal.biz.IPrsBiz;
import cn.uce.omg.portal.vo.CmsOrgVo;
import cn.uce.omg.portal.vo.PortalDictDataVo;
import cn.uce.prs.prepay.vo.PrepayAccountVo;
import cn.uce.utils.JsonUtil;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.util.WebUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * PRS接口调用
 *<pre>
 * PRSController
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年3月20日下午1:35:47
 */
@Controller
@RequestMapping("/prs")
public class PRSController extends BaseController {

	private final Log log = LogFactory.getLog(this.getClass());
	@Resource
	private IPrsBiz prsBiz;
	@Resource
	private IPermissionBiz permissionBiz;
	@Autowired
	private IDictDataBiz dictDataBiz;
	@Resource
	private IOrgBiz orgBiz;
	//允许跨域地址
	@Value("${omg.sso.path}")
	private String accessUrl;
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询PRS的预付款和判断是否有权限
	 * </pre>
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月20日上午10:09:20
	 */
	@RequestMapping(value="findPrepayAccount")
	@ResponseBody
	public Map<String, Object> findPrepayAccount(HttpServletResponse response, String baseOrgCode) {
		String prsRequestParamType = "session";	
		List<PortalDictDataVo> portalDictDataVos = dictDataBiz.findDictData("PORTAL_INFO");
		if (null != portalDictDataVos && portalDictDataVos.size() > 0) {
			for (PortalDictDataVo portalDictDataVo : portalDictDataVos) {
				if (null != portalDictDataVo && "SSO_URL".equals(portalDictDataVo.getTypeName())) {
					accessUrl = portalDictDataVo.getTypeCode();
				}
				if ("PRS_REQ_PARAM_TYPE".equals(portalDictDataVo.getTypeName())) {
					prsRequestParamType = portalDictDataVo.getTypeCode();
				}
			}
		}
		
		response.setHeader("Access-Control-Allow-Origin", accessUrl);
		response.setHeader("Access-Control-Allow-Credentials","true");   
		Map<String, Object> accountResult = new HashMap<>();
		try {
			//判断权限
			if (handleUserPermission(accountResult)) {
				String cmsBaseOrgCode = WebUtil.getCurrentUser().getCmsBaseOrgCode();
				if (null != prsRequestParamType && "cookie".equals(prsRequestParamType) && null != baseOrgCode) {
					cmsBaseOrgCode = baseOrgCode;
				}
				//获取PRS的预付款
				List<PrepayAccountVo> prepayAccountVos = new ArrayList<PrepayAccountVo>();
				PrepayAccountVo prepayAccountVo = new PrepayAccountVo();
				prepayAccountVo.setOrgCode(cmsBaseOrgCode);
				prepayAccountVo.setAccNature("1");
				prepayAccountVos.add(prepayAccountVo);
				String result = prsBiz.searchPrepayAccount(prepayAccountVos);//{'respList':[{'accBalance':3453.05,'accNature':'1','accNo':'PRS-8096654095862758923','alertMoney':4000.0,'lastLockTime':'2019-04-03 10:47:51.0','lockMode':'2','lockMoney':3450.0,'orgCode':'UC014062','orgId':'10020271','prapayFlag':true,'prapayStartTime':'2017-08-14 23:02:45.0','status':'1','temporaryLockMoney':3450.00}]}
				if (log.isInfoEnabled()) {
					log.info("【预付款余额查询返回参数：】" + result + "。【预付款余额查询请求参数：】" + JsonUtil.toJson(prepayAccountVo));
				}
				//账户余额
				DecimalFormat numFormat = new DecimalFormat("#,###.00"); 
				BigDecimal accBalance = handleBigDePrepayAccount(result, "accBalance");
				accountResult.put("prepayAccount", accBalance);
				if (accBalance.compareTo((new BigDecimal("0"))) == 0) {
					accountResult.put("prepayAccountFormat", handleBigDePrepayAccount(result, "accBalance"));
				} else {
					accountResult.put("prepayAccountFormat", numFormat.format(handleBigDePrepayAccount(result, "accBalance")));
				}
				//警戒金额
				accountResult.put("alertMoney", handleBigDePrepayAccount(result, "alertMoney"));
				accountResult.put("alertMoneyFormat", numFormat.format(handleBigDePrepayAccount(result, "alertMoney")));
				//锁机金额
				accountResult.put("lockMoney", handleBigDePrepayAccount(result, "lockMoney"));
				accountResult.put("lockMoneyFormat", numFormat.format(handleBigDePrepayAccount(result, "lockMoney")));
				//临时锁机金额
				accountResult.put("temporaryLockMoney", handleBigDePrepayAccount(result, "temporaryLockMoney"));
				accountResult.put("temporaryLockMoneyFormat", numFormat.format(handleBigDePrepayAccount(result, "temporaryLockMoney")));
				//预付款开通时间
				accountResult.put("prapayStartTime", handlePrepayAccount(result, "prapayStartTime"));
				//上次锁机时间
				accountResult.put("lastLockTime", handlePrepayAccount(result, "lastLockTime"));
				//余额不足锁机. 1:正常，2：锁机
				accountResult.put("lockMode", handlePrepayAccount(result, "lockMode"));
				CmsOrgVo cmsOrgVo = orgBiz.findCmsOrgByBaseOrgCode(cmsBaseOrgCode, null);
				if (null != cmsOrgVo) {
					accountResult.put("cmsStatus", cmsOrgVo.getStatus());
				}
			}
			accountResult.put("success", true);
		} catch(Exception e) {
			accountResult.put("success", false);
		}
		
		return accountResult;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 处理返回的参数
	 * </pre>
	 * @param result
	 * @param key
	 * @return
	 * @return BigDecimal
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月20日下午12:04:12
	 */
	public static String handlePrepayAccount(String result, String key) {
		if (null != result) {
			JSONObject resultJson = JSONObject.parseObject(result);
			JSONArray resultArray = resultJson.getJSONArray("respList");
			if (null != resultArray && resultArray.size() > 0) {
				for (Object object : resultArray) {
					JSONObject resultL = (JSONObject) object;
					if (null != resultL && null != resultL.getString(key)) {
						return resultL.getString(key);
					}
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 处理返回的参数
	 * </pre>
	 * @param result
	 * @param key
	 * @return
	 * @return BigDecimal
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月20日下午12:04:12
	 */
	public static BigDecimal handleBigDePrepayAccount(String result, String key) {
		if (null != result) {
			JSONObject resultJson = JSONObject.parseObject(result);
			JSONArray resultArray = resultJson.getJSONArray("respList");
			if (null != resultArray && resultArray.size() > 0) {
				for (Object object : resultArray) {
					JSONObject resultL = (JSONObject) object;
					if (null != resultL && null != resultL.getString(key)) {
						return resultL.getBigDecimal(key);
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 判断是否有权限
	 * </pre>
	 * @param accountResult
	 * @return
	 * @return boolean
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月20日下午12:04:28
	 */
	public boolean handleUserPermission(Map<String, Object> accountResult) {
		//判断是否有预付款、充值、提现等权限
		ICache<String,List<PortalDictDataVo>> cache = CacheManager.getInstance().getCache("DictDataCache");
		List<PortalDictDataVo> dictTypeVoList = cache.get("PRS_PER_TYPE");
		String empCode = WebUtil.getCurrentUser().getEmpCode();
		String accountBalance = "account_balance";//预付款查询
		String rechargeManagementCode = "recharge_management_code";//预付款充值
		String prepayWithdrawCash = "prepayWithdrawCash";//预付款提现
		String paySystemCode = "PRS";
		if (null != dictTypeVoList && dictTypeVoList.size() > 0) {
			for (PortalDictDataVo portalDictDataVo : dictTypeVoList) {
				if(null != portalDictDataVo.getTypeCode() && null != portalDictDataVo.getTypeName()) {
					if ("预付款查询".equals(portalDictDataVo.getTypeName())) {
						accountBalance = portalDictDataVo.getTypeCode();
					} else if ("预付款充值".equals(portalDictDataVo.getTypeName())) {
						rechargeManagementCode = portalDictDataVo.getTypeCode();
					} else if ("预付款提现".equals(portalDictDataVo.getTypeName())) {
						prepayWithdrawCash = portalDictDataVo.getTypeCode();
					} else if ("所属系统".equals(portalDictDataVo.getTypeName())) {
						paySystemCode = portalDictDataVo.getTypeCode();
					}
				}
			}
		}
		int accountNum = permissionBiz.findCountByUserAndPerCodeAndSysCode(empCode, accountBalance, paySystemCode);
		accountResult.put("accountNum", accountNum);
		accountResult.put("rechargeNum", permissionBiz.findCountByUserAndPerCodeAndSysCode(empCode, rechargeManagementCode, paySystemCode));
		accountResult.put("cashNum", permissionBiz.findCountByUserAndPerCodeAndSysCode(empCode, prepayWithdrawCash, paySystemCode));
		return accountNum == 0 ? false : true;
	}
}
