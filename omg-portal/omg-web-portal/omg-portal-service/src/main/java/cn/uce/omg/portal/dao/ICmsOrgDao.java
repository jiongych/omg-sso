package cn.uce.omg.portal.dao;

import java.util.List;

import cn.uce.omg.portal.vo.SiteInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.uce.omg.portal.vo.CmsOrgVo;
import cn.uce.omg.portal.vo.OrgZTreeNodeVo;


/**
 * @Description: (ICmsOrgDao) 
 * @author XJ
 * @date 2017年8月2日 下午4:27:29
 */
@Repository("cmsOrgDao")
public interface ICmsOrgDao {
	
	List<CmsOrgVo>  findByCondition(CmsOrgVo cmsOrgVo);

	List<SiteInfoVo>  findSimpleByCondition(CmsOrgVo cmsOrgVo);
	
	/**
	 * @Description: (findCmsOrgByBaseOrgCode) 
	 * @param baseOrgCode
	 * @return
	 * @author XJ
	 * @date 2018年3月30日 下午3:37:40
	 */
	CmsOrgVo findCmsOrgByBaseOrgCode(@Param("baseOrgCode") String baseOrgCode, @Param("cmsOrgId") Integer cmsOrgId);
	
	/**
	 * 
	 * @Description: (findAllSyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @date 2018年4月2日 下午8:56:41
	 */
	List<OrgZTreeNodeVo> findAllSyncCmsOrgZTree(@Param("orgZTreeNodeVo")OrgZTreeNodeVo orgZTreeNodeVo);
	
	/**
	 * @Description: (findChildSyncCmsOrgZTree) 
	 * @param orgId
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 下午5:56:55
	 */
	List<OrgZTreeNodeVo> findChildSyncCmsOrgZTree(@Param("orgZTreeNodeVo")OrgZTreeNodeVo orgZTreeNodeVo);
	
	/**
	 * @Description: (findChildAsyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 下午9:23:00
	 */
	List<OrgZTreeNodeVo> findChildAsyncCmsOrgZTree(@Param("orgZTreeNodeVo")OrgZTreeNodeVo orgZTreeNodeVo);
	
}
