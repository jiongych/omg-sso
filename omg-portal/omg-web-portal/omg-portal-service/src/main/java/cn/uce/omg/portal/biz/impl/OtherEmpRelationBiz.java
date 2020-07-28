package cn.uce.omg.portal.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.omg.portal.biz.IOtherEmpRelationBiz;
import cn.uce.omg.portal.service.OtherEmpRelationService;
import cn.uce.omg.portal.vo.OtherEmpRelationVo;

/**
 * 
 * @Description: (OtherEmpRelationBiz) 
 * @author XJ
 * @date 2018年3月28日 下午6:34:42
 */
@Service("otherEmpRelationBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class OtherEmpRelationBiz implements IOtherEmpRelationBiz{
	
	@Autowired
	private OtherEmpRelationService otherEmpRelationService;
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findOtherEmpRelationByEmpCode</p> 
	* <p>Description: 根据empCode查询员工绑定关系</p> 
	* @param empCode
	* @return 
	* @see cn.uce.omg.portal.biz.IOtherEmpRelationBiz#findOtherEmpRelationByEmpCode(java.lang.String)
	 */
	@Override
	public OtherEmpRelationVo findOtherEmpRelationByEmpCode(String empCode) {
		return otherEmpRelationService.findOtherEmpRelationByEmpCode(empCode);
	}

}
