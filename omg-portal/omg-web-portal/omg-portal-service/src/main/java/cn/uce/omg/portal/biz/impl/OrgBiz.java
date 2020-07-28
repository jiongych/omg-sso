package cn.uce.omg.portal.biz.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.uce.omg.portal.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.core.cache.CacheManager;
import cn.uce.core.cache.base.ICache;
import cn.uce.omg.portal.biz.IOrgBiz;
import cn.uce.omg.portal.service.OrgService;
import cn.uce.web.common.util.GZIPHelper;

import com.alibaba.fastjson.JSON;

/**
 * @Description: (OrgBiz) 
 * @author XJ
 * @date 2017年8月2日 下午3:43:38
 */
@Service("orgBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class OrgBiz implements IOrgBiz {
	
	@Resource
	private OrgService orgService;
	
	@Override
	public List<CmsOrgVo>  findCmsOrgByCondition(CmsOrgVo cmsOrgVo) {
		return orgService.findCmsOrgByCondition(cmsOrgVo);
	}

	public List<SiteInfoVo>  findSimpleByCondition(CmsOrgVo cmsOrgVo) {
		return orgService.findSimpleByCondition(cmsOrgVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getAllSyncOrgZTree</p> 
	* <p>Description: 获取全量同步机构树 </p> 
	* @param orgZTreeNodeVo
	* @return
	* @throws IOException 
	* @see cn.uce.omg.portal.biz.IOrgBiz#getAllSyncOrgZTree(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public String getAllSyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		ICache<String,String> cache = CacheManager.getInstance().getCache("OrgTreeAllCache");
		return cache.get(JSON.toJSONString(orgZTreeNodeVo));
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getAllSyncCmsOrgZTree</p> 
	* <p>Description: 获取全量同步乾坤机构树</p> 
	* @param orgZTreeNodeVo
	* @return
	* @throws IOException 
	* @see cn.uce.omg.portal.biz.IOrgBiz#getAllSyncCmsOrgZTree(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public String getAllSyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		ICache<String,String> cache = CacheManager.getInstance().getCache("CmsOrgTreeAllCache");
		return cache.get(JSON.toJSONString(orgZTreeNodeVo));
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getAllSyncNosOrgZTree</p> 
	* <p>Description: 获取全量同步营运机构树</p> 
	* @param orgZTreeNodeVo
	* @return
	* @throws IOException 
	* @see cn.uce.omg.portal.biz.IOrgBiz#getAllSyncCmsOrgZTree(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public String getAllSyncNosOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		ICache<String,String> cache = CacheManager.getInstance().getCache("NosOrgTreeAllCache");
		return cache.get(JSON.toJSONString(orgZTreeNodeVo));
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findAllSyncOrgZTree</p> 
	* <p>Description: 查询全量同步机构树</p> 
	* @param orgZTreeNodeVo
	* @return
	* @throws IOException 
	* @see cn.uce.omg.portal.biz.IOrgBiz#findAllSyncOrgZTree(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public String findAllSyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		List<OrgZTreeNodeVo> orgTreeNodeVoList = orgService.findAllSyncOrgZTree(orgZTreeNodeVo);
		Map<String,OrgZTreeNodeVo> idNodeRef = new HashMap<String,OrgZTreeNodeVo>();
		List<OrgZTreeNodeVo> resultTreeNodeList = new ArrayList<OrgZTreeNodeVo>();
		for(OrgZTreeNodeVo orgTreeNodeVo : orgTreeNodeVoList){
			idNodeRef.put(orgTreeNodeVo.getId(), orgTreeNodeVo);
		}
		for(OrgZTreeNodeVo orgTreeNodeVo : orgTreeNodeVoList){
			if(orgTreeNodeVo.getPid() != null && idNodeRef.get(orgTreeNodeVo.getPid()) != null){
				if(idNodeRef.get(orgTreeNodeVo.getPid()).getChildren() == null){
					idNodeRef.get(orgTreeNodeVo.getPid()).setChildren(new ArrayList<OrgZTreeNodeVo>());
				}
				idNodeRef.get(orgTreeNodeVo.getPid()).getChildren().add(orgTreeNodeVo);
			}else{
				resultTreeNodeList.add(orgTreeNodeVo);
			}
		}
		return GZIPHelper.zip(JSON.toJSONString(resultTreeNodeList));
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findAllSyncCmsOrgZTree</p> 
	* <p>Description: 查询全量同步机构树</p> 
	* @param orgZTreeNodeVo
	* @return
	* @throws IOException 
	* @see cn.uce.omg.portal.biz.IOrgBiz#findAllSyncCmsOrgZTree(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public String findAllSyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		List<OrgZTreeNodeVo> orgTreeNodeVoList = orgService.findAllSyncCmsOrgZTree(orgZTreeNodeVo);
		Map<String,OrgZTreeNodeVo> idNodeRef = new HashMap<String,OrgZTreeNodeVo>();
		List<OrgZTreeNodeVo> resultTreeNodeList = new ArrayList<OrgZTreeNodeVo>();
		for(OrgZTreeNodeVo orgTreeNodeVo : orgTreeNodeVoList){
			idNodeRef.put(orgTreeNodeVo.getId(), orgTreeNodeVo);
		}
		for(OrgZTreeNodeVo orgTreeNodeVo : orgTreeNodeVoList){
			if(orgTreeNodeVo.getPid() != null && idNodeRef.get(orgTreeNodeVo.getPid()) != null){
				if(idNodeRef.get(orgTreeNodeVo.getPid()).getChildren() == null){
					idNodeRef.get(orgTreeNodeVo.getPid()).setChildren(new ArrayList<OrgZTreeNodeVo>());
				}
				idNodeRef.get(orgTreeNodeVo.getPid()).getChildren().add(orgTreeNodeVo);
			}else{
				resultTreeNodeList.add(orgTreeNodeVo);
			}
		}
		return GZIPHelper.zip(JSON.toJSONString(resultTreeNodeList));
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findAllSyncNosOrgZTree</p> 
	* <p>Description: 查询全量同步营运机构树</p> 
	* @param orgZTreeNodeVo
	* @return
	* @throws IOException 
	* @see cn.uce.omg.portal.biz.IOrgBiz#findAllSyncCmsOrgZTree(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public String findAllSyncNosOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		List<OrgZTreeNodeVo> cmsOrgTreeNodeVoList = orgService.findAllSyncCmsOrgZTree(orgZTreeNodeVo);
		List<OrgZTreeNodeVo> virtualOrgTreeNodeVoList = orgService.findAllSyncVirtualOrgZTree();
		List<VirtualOrgRefCmsVo> virtualOrgRefCmsVoList = orgService.findVirtualOrgRefCmsRef();
		
		Map<String,String> ref = new HashMap<String,String>();
		for(VirtualOrgRefCmsVo virtualOrgRefCmsVo : virtualOrgRefCmsVoList){
			ref.put(virtualOrgRefCmsVo.getCmsBaseOrgCode(), virtualOrgRefCmsVo.getVirtualBaseOrgCode());
		}
		
		Map<String,OrgZTreeNodeVo> idNodeRef = new HashMap<String,OrgZTreeNodeVo>();
		
		for(OrgZTreeNodeVo treeNode : cmsOrgTreeNodeVoList){
			if(treeNode.getOrgType() == 20){
				continue;
			}
			if(ref.get(treeNode.getId()) != null){
				treeNode.setPid(ref.get(treeNode.getId()));
			}
			idNodeRef.put(treeNode.getId(), treeNode);
		}
		for(OrgZTreeNodeVo treeNode : virtualOrgTreeNodeVoList){
			idNodeRef.put(treeNode.getId(), treeNode);
		}
		
		List<OrgZTreeNodeVo> resultTreeNodeList = new ArrayList<OrgZTreeNodeVo>();
 		
		for(OrgZTreeNodeVo treeNode : cmsOrgTreeNodeVoList){
			if(treeNode.getOrgType() == 20){
				continue;
			}
			if(treeNode.getPid() != null && idNodeRef.get(treeNode.getPid()) != null){
				if(idNodeRef.get(treeNode.getPid()).getChildren() == null){
					idNodeRef.get(treeNode.getPid()).setChildren(new ArrayList<OrgZTreeNodeVo>());
				}
				idNodeRef.get(treeNode.getPid()).getChildren().add(treeNode);
			}else{
				resultTreeNodeList.add(treeNode);
			}
		}
		for(OrgZTreeNodeVo treeNode : virtualOrgTreeNodeVoList){
			if(treeNode.getPid() != null && idNodeRef.get(treeNode.getPid()) != null){
				if(idNodeRef.get(treeNode.getPid()).getChildren() == null){
					idNodeRef.get(treeNode.getPid()).setChildren(new ArrayList<OrgZTreeNodeVo>());
				}
				idNodeRef.get(treeNode.getPid()).getChildren().add(treeNode);
			}else{
				resultTreeNodeList.add(treeNode);
			}
		}
		
		return GZIPHelper.zip(JSON.toJSONString(resultTreeNodeList));
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getChildSyncOrgZTree</p> 
	* <p>Description: 获取子同步机构树</p> 
	* @param orgZTreeNodeVo
	* @return
	* @throws IOException 
	* @see cn.uce.omg.portal.biz.IOrgBiz#getChildSyncOrgZTree(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public String getChildSyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		ICache<String,String> cache = CacheManager.getInstance().getCache("OrgTreeChildCache");
		return cache.get(JSON.toJSONString(orgZTreeNodeVo));
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getChildSyncCmsOrgZTree</p> 
	* <p>Description: 获取子同步乾坤机构树</p> 
	* @param orgZTreeNodeVo
	* @return
	* @throws IOException 
	* @see cn.uce.omg.portal.biz.IOrgBiz#getChildSyncCmsOrgZTree(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public String getChildSyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		ICache<String,String> cache = CacheManager.getInstance().getCache("CmsOrgTreeChildCache");
		return cache.get(JSON.toJSONString(orgZTreeNodeVo));
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findChildSyncOrgZTree</p> 
	* <p>Description: 查询子同步机构树</p> 
	* @param orgZTreeNodeVo
	* @return
	* @throws IOException 
	* @see cn.uce.omg.portal.biz.IOrgBiz#findChildSyncOrgZTree(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public String findChildSyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		List<OrgZTreeNodeVo> orgZTreeNodeList = orgService.findChildSyncOrgZTree(orgZTreeNodeVo);
		List<OrgZTreeNodeVo> zTree = new ArrayList<OrgZTreeNodeVo>();
		Map<String,OrgZTreeNodeVo> idNodeRef = new HashMap<String,OrgZTreeNodeVo>();
		if (orgZTreeNodeList != null && !orgZTreeNodeList.isEmpty()) {
			for (OrgZTreeNodeVo zTreeNode : orgZTreeNodeList) {
				idNodeRef.put(zTreeNode.getId(), zTreeNode);
			}
			for(OrgZTreeNodeVo zTreeNode : orgZTreeNodeList){
				if(zTreeNode.getPid() != null && idNodeRef.get(zTreeNode.getPid()) != null){
					if(idNodeRef.get(zTreeNode.getPid()).getChildren() ==null){
						idNodeRef.get(zTreeNode.getPid()).setChildren(new ArrayList<OrgZTreeNodeVo>());
					}
					idNodeRef.get(zTreeNode.getPid()).getChildren().add(idNodeRef.get(zTreeNode.getId()));
				}else{
					zTree.add(idNodeRef.get(zTreeNode.getId()));
				}
			}
		}
		return GZIPHelper.zip(JSON.toJSONString(zTree));
	}
	
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findChildSyncOrgZTreeNew</p> 
	* <p>Description: 查询子同步机构树</p> 
	* @param orgZTreeNodeVo
	* @return
	* @throws IOException 
	* @see cn.uce.omg.portal.biz.IOrgBiz#findChildSyncOrgZTreeNew(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public String findChildSyncOrgZTreeNew(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		List<OrgZTreeNodeVo> orgZTreeNodeList = orgService.findChildSyncOrgZTreeNew(orgZTreeNodeVo);
		List<OrgZTreeNodeVo> zTree = new ArrayList<OrgZTreeNodeVo>();
		Map<String,OrgZTreeNodeVo> idNodeRef = new HashMap<String,OrgZTreeNodeVo>();
		if (orgZTreeNodeList != null && !orgZTreeNodeList.isEmpty()) {
			for (OrgZTreeNodeVo zTreeNode : orgZTreeNodeList) {
				idNodeRef.put(zTreeNode.getId(), zTreeNode);
			}
			for(OrgZTreeNodeVo zTreeNode : orgZTreeNodeList){
				if(zTreeNode.getPid() != null && idNodeRef.get(zTreeNode.getPid()) != null){
					if(idNodeRef.get(zTreeNode.getPid()).getChildren() ==null){
						idNodeRef.get(zTreeNode.getPid()).setChildren(new ArrayList<OrgZTreeNodeVo>());
					}
					idNodeRef.get(zTreeNode.getPid()).getChildren().add(idNodeRef.get(zTreeNode.getId()));
				}else{
					zTree.add(idNodeRef.get(zTreeNode.getId()));
				}
			}
		}
		return GZIPHelper.zip(JSON.toJSONString(zTree));
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findChildSyncCmsOrgZTree</p> 
	* <p>Description: 查询子同步乾坤机构树</p> 
	* @param orgZTreeNodeVo
	* @return
	* @throws IOException 
	* @see cn.uce.omg.portal.biz.IOrgBiz#findChildSyncCmsOrgZTree(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public String findChildSyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) throws IOException {
		List<OrgZTreeNodeVo> orgZTreeNodeList = orgService.findChildSyncCmsOrgZTree(orgZTreeNodeVo);
		List<OrgZTreeNodeVo> zTree = new ArrayList<OrgZTreeNodeVo>();
		Map<String,OrgZTreeNodeVo> idNodeRef = new HashMap<String,OrgZTreeNodeVo>();
		if (orgZTreeNodeList != null && !orgZTreeNodeList.isEmpty()) {
			for (OrgZTreeNodeVo zTreeNode : orgZTreeNodeList) {
				idNodeRef.put(zTreeNode.getId(), zTreeNode);
			}
			for(OrgZTreeNodeVo zTreeNode : orgZTreeNodeList){
				if(zTreeNode.getPid() != null && idNodeRef.get(zTreeNode.getPid()) != null){
					if(idNodeRef.get(zTreeNode.getPid()).getChildren() ==null){
						idNodeRef.get(zTreeNode.getPid()).setChildren(new ArrayList<OrgZTreeNodeVo>());
					}
					idNodeRef.get(zTreeNode.getPid()).getChildren().add(idNodeRef.get(zTreeNode.getId()));
				}else{
					zTree.add(idNodeRef.get(zTreeNode.getId()));
				}
			}
		}
		return GZIPHelper.zip(JSON.toJSONString(zTree));
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findChildAsyncOrgZTree</p> 
	* <p>Description: 查询子异步机构树</p> 
	* @param orgZTreeNodeVo
	* @return 
	* @see cn.uce.omg.portal.biz.IOrgBiz#findChildAsyncOrgZTree(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public List<OrgZTreeNodeVo> findChildAsyncOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) {
		return orgService.findChildAsyncOrgZTree(orgZTreeNodeVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findChildAsyncCmsOrgZTree</p> 
	* <p>Description: 查询子异步乾坤机构树</p> 
	* @param orgZTreeNodeVo
	* @return 
	* @see cn.uce.omg.portal.biz.IOrgBiz#findChildAsyncCmsOrgZTree(cn.uce.omg.portal.vo.OrgZTreeNodeVo)
	 */
	@Override
	public List<OrgZTreeNodeVo> findChildAsyncCmsOrgZTree(OrgZTreeNodeVo orgZTreeNodeVo) {
		return orgService.findChildAsyncCmsOrgZTree(orgZTreeNodeVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findCmsOrgByBaseOrgCode</p> 
	* <p>Description: 根据基准编码查询乾坤机构</p> 
	* @param baseOrgCode
	* @return 
	* @see cn.uce.omg.portal.biz.IOrgBiz#findCmsOrgByBaseOrgCode(java.lang.String)
	 */
	@Override
	public CmsOrgVo findCmsOrgByBaseOrgCode(String baseOrgCode, Integer cmsOrgId) {
		return orgService.findCmsOrgByBaseOrgCode(baseOrgCode, cmsOrgId);
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
	@Override
	public OrgVo findByOrgId(Integer orgId) {
		return orgService.findByOrgId(orgId);
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
	@Override
	public OrgVo findByOrgCode(String orgCode) {
		return orgService.findByOrgCode(orgCode);
	}
	
}
