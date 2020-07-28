package cn.uce.omg.portal.cache;

import java.util.List;

import javax.annotation.Resource;

import cn.uce.base.exception.BusinessException;
import cn.uce.core.cache.CacheSupport;
import cn.uce.omg.portal.biz.IMenuBiz;
import cn.uce.omg.portal.vo.PortalMenuTreeVo;
import cn.uce.web.common.base.CurrentUser;

import com.alibaba.fastjson.JSON;

public class MenuTreeCache extends CacheSupport<List<PortalMenuTreeVo>>{
	
	@Resource
	private IMenuBiz portalMenuBiz;
	
	@Override
	public List<PortalMenuTreeVo> doGet(String arg0) throws BusinessException {
		
		return portalMenuBiz.findMenuTree( JSON.parseObject(arg0, CurrentUser.class));
	}

	@Override
	public String getCacheId() {
		return getClass().getSimpleName();
	}

}
