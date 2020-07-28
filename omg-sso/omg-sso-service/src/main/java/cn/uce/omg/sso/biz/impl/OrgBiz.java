/** 
 * @项目名称: FSP
 * @文件名称: OrgBiz implements IOrgBiz, IOrgApi 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.omg.sso.biz.IOrgBiz;
import cn.uce.omg.sso.service.OrgService;
import cn.uce.omg.vo.OrgVo;

/**
 * @author Administrator
 *
 */
public class OrgBiz implements IOrgBiz {
	private Log LOG = LogFactory.getLog(this.getClass());
	private OrgService orgService;

	/**
	 * 根据orgId查询机构
	 * @param OrgId
	 * @return
	 */
	@Override
	public OrgVo findOrgById(Integer orgId) {
		if (orgId == null) {
			LOG.warn("根据机构ID查询机构信息失败，机构ID为空。");
			return null;
		}
		return this.orgService.findOrgById(orgId);
	}
	
    /**
	 * 根据机构id集合查询机构
	 * @param OrgId
	 * @return
	 */
	@Override
	public List<OrgVo> findOrgByIds(List<Integer> orgIdList) {
		return orgService.findOrgByIds(orgIdList);
	}
	
	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ORGCODE查询
	 * </pre>
	 * @param orgCode
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月19日上午11:14:48
	 */
	@Override
	public OrgVo findOrgByOrgCode(String orgCode) {
		
		return orgService.findOrgByCode(orgCode);
	}

}
