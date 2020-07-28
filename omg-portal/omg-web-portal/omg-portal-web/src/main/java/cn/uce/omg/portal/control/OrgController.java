package cn.uce.omg.portal.control;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.uce.omg.portal.vo.SiteInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.omg.portal.biz.IDictDataBiz;
import cn.uce.omg.portal.biz.IOrgBiz;
import cn.uce.omg.portal.vo.CmsOrgVo;
import cn.uce.omg.portal.vo.OrgZTreeNodeVo;
import cn.uce.omg.portal.vo.PortalDictDataVo;
import cn.uce.web.common.base.BaseController;

/**
 * 
 * @Description: (OrgController) 
 * @author XJ
 * @date 2017年8月2日 下午4:03:50
 */
@Controller
@RequestMapping("/org")
public class OrgController extends BaseController{
	
	@Resource
	private IOrgBiz orgBiz;
	
	@Autowired
	private IDictDataBiz dictDataBiz;
	
	//允许跨域地址
	@Value("${omg.sso.path}")
	private String accessUrl;
	
	/**
     * @param orgType
     * @return 机构信息
     * @Description: 根据机构类型查询机构信息
     * @author huangting
     * @date 2018年03月31日 下午4:26:21
     */
    @RequestMapping(value = "/findBaseOrgByCondition")
    @ResponseBody
    public List<CmsOrgVo> findBaseOrgByCondition(HttpServletResponse response,CmsOrgVo cmsOrgVo) {
    	List<PortalDictDataVo> portalDictDataVos = dictDataBiz.findDictData("PORTAL_INFO");
		if (null != portalDictDataVos && portalDictDataVos.size() > 0) {
			for (PortalDictDataVo portalDictDataVo : portalDictDataVos) {
				if (null != portalDictDataVo && "SSO_URL".equals(portalDictDataVo.getTypeName())) {
					accessUrl = portalDictDataVo.getTypeCode();
					break;
				}
			}
		}
		response.setHeader("Access-Control-Allow-Origin", accessUrl);
		response.setHeader("Access-Control-Allow-Credentials","true");
    	if (cmsOrgVo == null) {
    		return null;
    	}
        return orgBiz.findCmsOrgByCondition(cmsOrgVo);
    }

	/**
	 * @param orgType
	 * @return 机构信息
	 * @Description: 根据机构类型查询机构信息
	 * @author huangting
	 * @date 2018年03月31日 下午4:26:21
	 */
	@RequestMapping(value = "/findSiteInfo")
	@ResponseBody
	public List<SiteInfoVo> findSiteInfo(HttpServletResponse response, CmsOrgVo cmsOrgVo, HttpServletRequest request) {
		String head = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", head);
		response.setHeader("Access-Control-Allow-Credentials","true");
		if (cmsOrgVo == null) {
			return null;
		}
		return orgBiz.findSimpleByCondition(cmsOrgVo);
	}
	
	/**
	 * @Description: (findAllSyncOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年3月29日 下午9:50:30
	 */
	@RequestMapping(value = "findAllSyncOrgZTree")
	@ResponseBody
	public String findAllSyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		return orgBiz.findAllSyncOrgZTree(orgZTreeNodeVo);
	}
	
	/**
	 * @Description: (findAllSyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年4月2日 下午9:41:14
	 */
	@RequestMapping(value = "findAllSyncCmsOrgZTree")
	@ResponseBody
	public String findAllSyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		return orgBiz.findAllSyncCmsOrgZTree(orgZTreeNodeVo);
	}
	
	/**
	 * @Description: (findAllSyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年4月2日 下午9:41:14
	 */
	@RequestMapping(value = "findAllSyncNosOrgZTree")
	@ResponseBody
	public String findAllSyncNosOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		return orgBiz.findAllSyncNosOrgZTree(orgZTreeNodeVo);
	}
	
	/**
	 * @Description: (findChildSyncOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年3月29日 下午9:50:42
	 */
	@RequestMapping("findChildSyncOrgZTree")
	@ResponseBody
	public String findChildSyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException{
		String str = orgBiz.findChildSyncOrgZTree(orgZTreeNodeVo);
		return str;
	}
	
	/**
	 * @Description: (findChildSyncOrgZTreeNew) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年3月29日 下午9:50:42
	 */
	@RequestMapping("findChildSyncOrgZTreeNew")
	@ResponseBody
	public String findChildSyncOrgZTreeNew(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException{
		String str = orgBiz.findChildSyncOrgZTreeNew(orgZTreeNodeVo);
		return str;
	}
	/**
	 * @Description: (findChildSyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年4月2日 下午9:42:01
	 */
	@RequestMapping("findChildSyncCmsOrgZTree")
	@ResponseBody
	public String findChildSyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException{
		String str = orgBiz.findChildSyncCmsOrgZTree(orgZTreeNodeVo);
		return str;
	}
	
	/**
	 * @Description: (findChildAsyncOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年3月29日 下午9:50:53
	 */
	@RequestMapping("findChildAsyncOrgZTree")
	@ResponseBody
	public List<OrgZTreeNodeVo> findChildAsyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException{
		return orgBiz.findChildAsyncOrgZTree(orgZTreeNodeVo);
	}
	
	/**
	 * @Description: (findChildAsyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @throws IOException
	 * @author XJ
	 * @date 2018年4月2日 下午9:43:07
	 */
	@RequestMapping("findChildAsyncCmsOrgZTree")
	@ResponseBody
	public List<OrgZTreeNodeVo> findChildAsyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException{
		return orgBiz.findChildAsyncCmsOrgZTree(orgZTreeNodeVo);
	}
	
	/**
	 * @Description: (findCmsOrgByBaseOrgCode) 
	 * @param baseOrgCode
	 * @return
	 * @author XJ
	 * @date 2018年3月30日 下午3:40:45
	 */
	@RequestMapping("findCmsOrgByBaseOrgCode")
	@ResponseBody
	public CmsOrgVo findCmsOrgByBaseOrgCode(String baseOrgCode){
		return orgBiz.findCmsOrgByBaseOrgCode(baseOrgCode, null);
	}
}
