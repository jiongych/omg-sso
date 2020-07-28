package cn.uce.omg.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.omg.portal.dao.IOtherEmpRelationDao;
import cn.uce.omg.portal.vo.OtherEmpRelationVo;

/**
 * 
 * @Description: (OtherEmpRelationService) 
 * @author XJ
 * @date 2018年3月28日 下午6:31:12
 */
@Service("otherEmpRelationService")
public class OtherEmpRelationService {
	
	@Autowired
	private IOtherEmpRelationDao otherEmpRelationDao;
	
	/**
	 * @Description: (findOtherEmpRelationByEmpCode) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年3月28日 下午6:31:22
	 */
	public OtherEmpRelationVo findOtherEmpRelationByEmpCode(String empCode){
		return otherEmpRelationDao.findOtherEmpRelationByEmpCode(empCode);
	}

}
