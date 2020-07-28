package cn.uce.omg.sso.cache;

import cn.uce.core.cache.CacheSupport;
import cn.uce.core.cache.exception.BusinessException;
import cn.uce.omg.sso.biz.IEmpBiz;
import cn.uce.omg.vo.EmpVo;

public class EmpMobileCache extends CacheSupport<String>{
	
	private IEmpBiz empBiz;

	@Override
	public String doGet(String key) throws BusinessException {
		EmpVo empVo = empBiz.findEmpByMobile(key);
		if (empVo != null) {
			return empVo.getEmpCode();
		}else {
			return key;
		}
	}

	@Override
	public String getCacheId() {
		return getClass().getSimpleName();
	}

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}
}
