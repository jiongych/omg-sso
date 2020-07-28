/** 
 * @项目名称: FSP
 * @文件名称: ISysTypeBiz 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz;

import java.util.List;

import cn.uce.base.exception.BusinessException;
import cn.uce.omg.vo.SysTypeVo;


/**
 * ISysTypeBiz  
 * @Description: ISysTypeBiz  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface ISysTypeBiz {
	
	/**
	 * 根据条件查询数据
	 * @param typeClassCode
	 * @return
	 * @author zhangRb
	 * @date 2018年9月5日
	 */
	public List<SysTypeVo> findListByTypeClassCode(String typeClassCode) throws BusinessException;
}
