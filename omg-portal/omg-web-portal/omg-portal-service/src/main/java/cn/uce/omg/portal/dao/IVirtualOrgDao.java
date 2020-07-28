package cn.uce.omg.portal.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.uce.omg.portal.vo.OrgZTreeNodeVo;


@Repository("virtualOrgDao")
public interface IVirtualOrgDao {
	
	/**
	 * @Description: (findAllSyncVirtualOrgZTree) 
	 * @return
	 * @author XJ
	 * @date 2018年4月11日 上午10:31:57
	 */
	List<OrgZTreeNodeVo> findAllSyncVirtualOrgZTree();

}
