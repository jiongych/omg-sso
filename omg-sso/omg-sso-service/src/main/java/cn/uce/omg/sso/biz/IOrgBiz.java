/** 
 * @项目名称: FSP
 * @文件名称: IOrgBiz 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz;

import java.util.List;

import cn.uce.omg.vo.OrgVo;

/**
 * IOrgBiz  
 * @Description: IOrgBiz  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface IOrgBiz {
	
	/**
	 * 根据orgId查询机构
	 * @param OrgId
	 * @return
	 */
	public OrgVo findOrgById(Integer orgId);
	
	/**
	 * 根据机构id集合查询机构
	 * @param OrgId
	 * @return
	 */
	public List<OrgVo> findOrgByIds(List<Integer> orgIdList);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据orgCode查询
	 * </pre>
	 * @param orgCode
	 * @return
	 * @return OrgVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月19日上午11:14:22
	 */
	public OrgVo findOrgByOrgCode(String orgCode);
}
