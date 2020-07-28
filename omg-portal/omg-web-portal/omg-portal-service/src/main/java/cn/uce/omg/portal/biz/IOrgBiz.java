package cn.uce.omg.portal.biz;

import java.io.IOException;
import java.util.List;

import cn.uce.omg.portal.vo.CmsOrgVo;
import cn.uce.omg.portal.vo.OrgVo;
import cn.uce.omg.portal.vo.OrgZTreeNodeVo;
import cn.uce.omg.portal.vo.SiteInfoVo;

/**
 * 
 * @Description: (IOrgBiz) 
 * @author XJ
 * @date 2017年4月22日 下午12:47:24
 */;
public interface IOrgBiz {
	List<CmsOrgVo>  findCmsOrgByCondition(CmsOrgVo cmsOrgVo);

	List<SiteInfoVo>  findSimpleByCondition(CmsOrgVo cmsOrgVo);
	
	/**
	 * @Description: (getAllSyncOrgTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年3月29日 下午9:44:20
	 */
	String getAllSyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException;
	
	/**
	 * @Description: (getAllSyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年4月2日 下午9:08:02
	 */
	String getAllSyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException;
	
	/**
	 * @Description: (getAllSyncNosOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年4月2日 下午9:08:02
	 */
	String getAllSyncNosOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException;
	
	/**
	 * @Description: (findAllSyncOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @throws IOException 
	 * @date 2018年3月29日 下午9:26:49
	 */
	String findAllSyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException;
	
	/**
	 * @Description: (findAllSyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年4月2日 下午9:08:32
	 */
	String findAllSyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException;
	
	/**
	 * @Description: (findAllSyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年4月2日 下午9:08:32
	 */
	String findAllSyncNosOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException;
	
	/**
	 * @Description: (getChildSyncOrgTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年3月29日 下午9:42:55
	 */
	String getChildSyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException;
	
	/**
	 * @Description: (getChildSyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年4月2日 下午9:09:38
	 */
	String getChildSyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException;
	/**
	 * @Description: (findChildOrgZTree) 
	 * @param orgId
	 * @return
	 * @author XJ
	 * @throws IOException 
	 * @date 2018年3月29日 下午6:11:11
	 */
	String findChildSyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException;
	
	/**
	 * @Description: (findChildSyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年4月2日 下午9:10:01
	 */
	String findChildSyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException;
	/**
	 * @Description: (findChildAsyncOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 下午9:27:20
	 */
	List<OrgZTreeNodeVo> findChildAsyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo);
	
	/**
	 * @Description: (findChildAsyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @date 2018年4月2日 下午9:10:34
	 */
	List<OrgZTreeNodeVo> findChildAsyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo);
	/**
	 * @Description: (findCmsOrgByBaseOrgCode) 
	 * @param baseOrgCode
	 * @return
	 * @author XJ
	 * @date 2018年3月30日 下午3:35:38
	 */
	CmsOrgVo findCmsOrgByBaseOrgCode(String baseOrgCode, Integer cmsOrgId);

	String findChildSyncOrgZTreeNew(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException;
	
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
