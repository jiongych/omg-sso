package cn.uce.omg.portal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalOrg;
import cn.uce.omg.portal.vo.OrgVo;
import cn.uce.omg.portal.vo.OrgZTreeNodeVo;
 
/**
 * @Description: (这里用一句话描述这个类的作用) 
 * @author XJ
 * @date 2018年3月29日 下午5:56:50
 */
@Repository("orgDao")
public interface IOrgDao extends IBaseDao<PortalOrg, Long> {
	
	/**
	 * @Description: (findAllSyncOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 下午9:22:47
	 */
	List<OrgZTreeNodeVo> findAllSyncOrgZTree(@Param("orgZTreeNodeVo")OrgZTreeNodeVo orgZTreeNodeVo);
	
	/**
	 * @Description: (findChildOrgZTree) 
	 * @param orgId
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 下午5:56:55
	 */
	List<OrgZTreeNodeVo> findChildSyncOrgZTree(@Param("orgZTreeNodeVo")OrgZTreeNodeVo orgZTreeNodeVo);
	
	/**
	 * @Description: (findChildAsyncOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 下午9:23:00
	 */
	List<OrgZTreeNodeVo> findChildAsyncOrgZTree(@Param("orgZTreeNodeVo")OrgZTreeNodeVo orgZTreeNodeVo);

	List<OrgZTreeNodeVo> findChildSyncOrgZTreeNew(Map map);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据orgId查询机构
	 * </pre>
	 * @param orgId
	 * @return
	 * @return OrgVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月29日上午9:21:06
	 */
	OrgVo findByOrgId(Integer orgId);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据orgCode查询机构
	 * </pre>
	 * @param orgCode
	 * @return
	 * @return OrgVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月29日上午9:21:06
	 */
	OrgVo findByOrgCode(String orgCode);
}
