package cn.uce.omg.portal.biz;

import cn.uce.omg.portal.vo.OtherEmpRelationVo;

/**
 * @Description: (IOtherEmpRelationBiz) 
 * @author XJ
 * @date 2018年3月28日 下午6:32:47
 */
public interface IOtherEmpRelationBiz {
	
	/**
	 * @Description: (findOtherEmpRelationByEmpCode) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年3月28日 下午6:32:55
	 */
	OtherEmpRelationVo findOtherEmpRelationByEmpCode(String empCode);

}
