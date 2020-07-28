package cn.uce.omg.sso.cache;

import java.util.List;

import cn.uce.core.cache.CacheSupport;
import cn.uce.core.cache.exception.BusinessException;
import cn.uce.omg.sso.biz.ISysTypeBiz;
import cn.uce.omg.vo.SysTypeVo;

public class FspDictDataCache extends CacheSupport<List<SysTypeVo>>{
	
	private ISysTypeBiz sysTypeBiz;

	@Override
	public List<SysTypeVo> doGet(String typeClassCode) throws BusinessException {
		return sysTypeBiz.findListByTypeClassCode(typeClassCode);
	}

	@Override
	public String getCacheId() {
		return getClass().getSimpleName();
	}

	public void setSysTypeBiz(ISysTypeBiz sysTypeBiz) {
		this.sysTypeBiz = sysTypeBiz;
	}
}
