/** 
 * @项目名称: FSP
 * @文件名称: OtherEmpRelationService 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.service;

import java.util.List;

import cn.uce.omg.sso.constant.OmgConstants;
import cn.uce.omg.sso.dao.IOtherEmpRelationDao;
import cn.uce.omg.vo.OtherEmpRelationVo;

/**
 * OtherEmpRelationService  
 * @Description: OtherEmpRelationService  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class OtherEmpRelationService {

	private IOtherEmpRelationDao otherEmpRelationDao;
	
	/**
	 * 根据员工id查询乾坤员工绑定关系
	 * @Description: 根据员工id查询乾坤员工绑定关系
	 * @param empId
	 * @return
	 * @author user
	 * @date 2017年8月1日 下午4:02:45
	 */
	public List<OtherEmpRelationVo> findQkEmpRelationByEmpId(Integer empId) {
		OtherEmpRelationVo searchOtherEmpRelationVo = new OtherEmpRelationVo();
		searchOtherEmpRelationVo.setEmpId(empId);
		searchOtherEmpRelationVo.setSystemId(OmgConstants.SYSTEM_ID_QIANKUN);
		return otherEmpRelationDao.findOtherEmpRelationByCondition(searchOtherEmpRelationVo);
	}

	public void setOtherEmpRelationDao(IOtherEmpRelationDao otherEmpRelationDao) {
		this.otherEmpRelationDao = otherEmpRelationDao;
	}

}
