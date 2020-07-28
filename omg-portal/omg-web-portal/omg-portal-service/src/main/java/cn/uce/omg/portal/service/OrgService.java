package cn.uce.omg.portal.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.uce.omg.portal.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.omg.portal.dao.ICmsOrgDao;
import cn.uce.omg.portal.dao.IOrgDao;
import cn.uce.omg.portal.dao.IUserOrgManagerDao;
import cn.uce.omg.portal.dao.IVirtualOrgDao;
import cn.uce.omg.portal.dao.IVirtualOrgRefCmsDao;
import cn.uce.utils.StringUtil;

import com.alibaba.dubbo.common.utils.CollectionUtils;

/**
 * 
 * @Description: (orgService) 
 * @author XJ
 * @date 2017年4月22日 上午8:29:18
 */
@Service("orgService")
public class OrgService {
	Logger logger = LoggerFactory.getLogger(OrgService.class);
	
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private ICmsOrgDao cmsOrgDao;
	@Autowired
	private IVirtualOrgDao virtualOrgDao;
	@Autowired
	private IVirtualOrgRefCmsDao virtualOrgRefCmsDao;
	@Autowired
	private IUserOrgManagerDao userOrgManagerDao;
	
	public List<CmsOrgVo>  findCmsOrgByCondition(CmsOrgVo cmsOrgVo) {
		return cmsOrgDao.findByCondition(cmsOrgVo);
	}

	public List<SiteInfoVo>  findSimpleByCondition(CmsOrgVo cmsOrgVo) {
		return cmsOrgDao.findSimpleByCondition(cmsOrgVo);
	}
	/**
	 * @Description: (findAllSyncOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 下午9:25:27
	 */
	public List<OrgZTreeNodeVo> findAllSyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo){
		return orgDao.findAllSyncOrgZTree(orgZTreeNodeVo);
	}
	
	/**
	 * @Description: (findAllSyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @date 2018年4月2日 下午9:02:36
	 */
	public List<OrgZTreeNodeVo> findAllSyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo){
		return cmsOrgDao.findAllSyncCmsOrgZTree(orgZTreeNodeVo);
	}
	
	/**
	 * @Description: (findChildSyncOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 下午9:25:36
	 */
	public List<OrgZTreeNodeVo> findChildSyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo){
		return orgDao.findChildSyncOrgZTree(orgZTreeNodeVo);
	}
	
	/**
	 * @Description: (findChildSyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @date 2018年4月2日 下午9:03:07
	 */
	public List<OrgZTreeNodeVo> findChildSyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo){
		return cmsOrgDao.findChildSyncCmsOrgZTree(orgZTreeNodeVo);
	}
	/**
	 * @Description: (findChildAsyncOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @date 2018年3月29日 下午9:25:45
	 */
	public List<OrgZTreeNodeVo> findChildAsyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo){
		return orgDao.findChildAsyncOrgZTree(orgZTreeNodeVo);
	}
	
	/**
	 * @Description: (findChildAsyncCmsOrgZTree) 
	 * @param orgZTreeNodeVo
	 * @return
	 * @author XJ
	 * @date 2018年4月2日 下午9:03:22
	 */
	public List<OrgZTreeNodeVo> findChildAsyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo){
		return cmsOrgDao.findChildAsyncCmsOrgZTree(orgZTreeNodeVo);
	}
	
	/**
	 * @Description: (findCmsOrgByBaseOrgCode) 
	 * @param baseOrgCode
	 * @return
	 * @author XJ
	 * @date 2018年3月30日 下午3:40:03
	 */
	public CmsOrgVo findCmsOrgByBaseOrgCode(String baseOrgCode, Integer cmsOrgId){
		return cmsOrgDao.findCmsOrgByBaseOrgCode(baseOrgCode, cmsOrgId);
	}
	
	/**
	 * @Description: (findAllSyncVirtualOrgZTree) 
	 * @return
	 * @author XJ
	 * @date 2018年4月11日 上午11:05:26
	 */
	public List<OrgZTreeNodeVo> findAllSyncVirtualOrgZTree(){
		return virtualOrgDao.findAllSyncVirtualOrgZTree();
	}
	
	/**
	 * @Description: (findVirtualOrgRefCmsRef) 
	 * @return
	 * @author XJ
	 * @date 2018年4月11日 上午11:06:33
	 */
	public List<VirtualOrgRefCmsVo> findVirtualOrgRefCmsRef(){
		return virtualOrgRefCmsDao.findVirtualOrgRefCmsRef();
	}

	public List<OrgZTreeNodeVo> findChildSyncOrgZTreeNew(
			OrgZTreeNodeVo orgZTreeNodeVo) {
		Map map  = new HashMap();
		if(!StringUtil.isEmpty(orgZTreeNodeVo.getEmpCode())){
			String orgCode = userOrgManagerDao.findDataManagerByEmpCode(orgZTreeNodeVo.getEmpCode());
			if(!StringUtil.isEmpty(orgCode)){
				String[] list = orgCode.split(",");
				List<String> idList = Arrays.asList(list);
				if(CollectionUtils.isNotEmpty(idList)){
					map.put("idList", idList);
				}
			}else if(!StringUtil.isEmpty(orgZTreeNodeVo.getId())){
				map.put("id", orgZTreeNodeVo.getId());
			}
		}
		return orgDao.findChildSyncOrgZTreeNew(map);
	}
	
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
	public OrgVo findByOrgId(Integer orgId) {
		return orgDao.findByOrgId(orgId);
	}
	
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
	public OrgVo findByOrgCode(String orgCode) {
		return orgDao.findByOrgCode(orgCode);
	}
}
