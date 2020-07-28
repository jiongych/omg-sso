package cn.uce.omg.portal.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.uce.omg.portal.vo.OtherEmpRelationVo;


/**
 * @Description: (IOtherEmpRelationDao) 
 * @author XJ
 * @date 2017年8月2日 下午4:27:29
 */
@Repository("otherEmpRelationDao")
public interface IOtherEmpRelationDao {
	
	/**
	 * @Description: (findOtherEmpRelationByEmpCode) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年3月28日 下午6:48:00
	 */
	OtherEmpRelationVo findOtherEmpRelationByEmpCode(@Param("empCode")String empCode);
}
