package cn.uce.omg.portal.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.omg.portal.biz.IPrsBiz;
import cn.uce.prs.common.base.vo.RequstParamVo;
import cn.uce.prs.prepay.api.IPrsPrepayApi;
import cn.uce.prs.prepay.vo.PrepayAccountVo;
import cn.uce.utils.JsonUtil;

/**
 * PRS接口BIZ
 *<pre>
 * PrsBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年3月20日上午9:48:01
 */
@Service("prsBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class PrsBiz implements IPrsBiz {

	private final Log log = LogFactory.getLog(this.getClass());
	@Resource
	private IPrsPrepayApi prsPrepayApi;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 预付款查询接口
	 * </pre>
	 * @param prepayAccountVos
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月20日上午9:48:09
	 */
	@Override
	public String searchPrepayAccount(List<PrepayAccountVo> prepayAccountVos) {
		RequstParamVo<PrepayAccountVo> requstParamVo = new RequstParamVo<PrepayAccountVo>();
		requstParamVo.setPartnerId("PORTAL");
		requstParamVo.setDataType("JSON");
		requstParamVo.setCharset("UTF-8");
		requstParamVo.setReqList(prepayAccountVos);
		if (log.isInfoEnabled()) {
			log.info("预付款余额查询请求参数：" + JsonUtil.toJson(requstParamVo));
		}
		return prsPrepayApi.searchPrepayAccount(JsonUtil.toJson(requstParamVo));
	}

}
