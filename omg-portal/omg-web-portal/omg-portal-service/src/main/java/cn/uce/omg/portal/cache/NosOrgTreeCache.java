package cn.uce.omg.portal.cache;

import java.io.IOException;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;

import cn.uce.base.exception.BusinessException;
import cn.uce.core.cache.CacheSupport;
import cn.uce.omg.portal.biz.IOrgBiz;
import cn.uce.omg.portal.vo.OrgZTreeNodeVo;

public class NosOrgTreeCache extends CacheSupport<String>{
	
	@Resource
	private IOrgBiz orgBiz;
	
	@Override
	public String doGet(String orgZTreeNodeVo) throws BusinessException {
		try {
			return orgBiz.findAllSyncNosOrgZTree(JSON.parseObject(orgZTreeNodeVo, OrgZTreeNodeVo.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getCacheId() {
		return getClass().getSimpleName();
	}

}
