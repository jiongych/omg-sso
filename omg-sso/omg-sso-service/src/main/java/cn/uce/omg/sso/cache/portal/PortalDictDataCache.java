package cn.uce.omg.sso.cache.portal;

import java.util.List;

import cn.uce.core.cache.CacheSupport;
import cn.uce.core.cache.exception.BusinessException;
import cn.uce.omg.sso.biz.IPortalBiz;
import cn.uce.omg.sso.vo.portal.PortalDictDataVo;

public class PortalDictDataCache extends CacheSupport<List<PortalDictDataVo>>{
	
	private IPortalBiz portalBiz;

	@Override
	public List<PortalDictDataVo> doGet(String typeClassCode) throws BusinessException {
		return portalBiz.findDictDataByTypeClassCode(typeClassCode);
	}

	@Override
	public String getCacheId() {
		return getClass().getSimpleName();
	}

	public void setPortalBiz(IPortalBiz portalBiz) {
		this.portalBiz = portalBiz;
	}
}
