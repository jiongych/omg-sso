package cn.uce.omg.portal.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.IDictDataBiz;
import cn.uce.omg.portal.service.DictDataService;
import cn.uce.omg.portal.vo.PortalDictDataTreeVo;
import cn.uce.omg.portal.vo.PortalDictDataVo;
import cn.uce.omg.portal.vo.PortalDictListVo;
import cn.uce.omg.portal.vo.PortalDictTypeVo;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.utils.StringUtil;

/**
 * @Description: (DictDataBiz) 
 * @author XJ
 * @date 2017年8月2日 下午2:23:06
 */
@Service(value = "fspDictDataBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class DictDataBiz implements IDictDataBiz {
	
	@Resource
	private DictDataService fspDictDataService;
	
	/**
	 * (非 Javadoc) 
	* <p>Title: addDictData</p> 
	* <p>Description:增加DictData </p> 
	* @param dictDataVo
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#addDictData(cn.uce.PortalDictDataVo.authorg.vo.DictDataVo)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int addDictData(PortalDictDataVo dictDataVo) {
		return fspDictDataService.addDictData(dictDataVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: updateDictData</p> 
	* <p>Description: 修改DictData</p> 
	* @param dictDataVo
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#updateDictData(cn.uce.PortalDictDataVo.authorg.vo.DictDataVo)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int updateDictData(PortalDictDataVo dictDataVo) {
		return fspDictDataService.updateDictData(dictDataVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: deleteDictData</p> 
	* <p>Description: 删除DictData</p> 
	* @param dictDataVo
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#deleteDictData(cn.uce.PortalDictDataVo.authorg.vo.DictDataVo)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int deleteDictData(PortalDictDataVo dictDataVo) {
		return fspDictDataService.deleteDictData(dictDataVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findByCondition</p> 
	* <p>Description: 分页查询DictData</p> 
	* @param dictDataVo
	* @param page
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#findByCondition(cn.uce.PortalDictDataVo.authorg.vo.DictDataVo, cn.uce.base.page.Page)
	 */
	@Override
	public Pagination<PortalDictDataVo> findDictDataByPage(PortalDictDataVo dictDataVo, Page page) {
		return fspDictDataService.findDictDataByPage(dictDataVo, page);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findOneByCondition</p> 
	* <p>Description: 条件查询DictData</p> 
	* @param dictDataVo
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#findOneByCondition(cn.uce.PortalDictDataVo.authorg.vo.DictDataVo)
	 */
	@Override
	public PortalDictDataVo findOneByCondition(PortalDictDataVo dictDataVo) {
		return fspDictDataService.findOneByCondition(dictDataVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findDictData</p> 
	* <p>Description: 根据typeClassCode查询DictData</p> 
	* @param typeClassCode
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#findDictData(java.lang.String)
	 */
	@Override
	public List<PortalDictDataVo> findDictData(String typeClassCode) {
		return fspDictDataService.findDictData(typeClassCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findDictDataTree</p> 
	* <p>Description: 查询DictDataTree</p> 
	* @param parentNodeId
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#findDictDataTree(java.lang.String)
	 */
	@Override
	public List<PortalDictDataTreeVo> findDictDataTree() {
		return fspDictDataService.findDictDataTree();
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findByTypesCodes</p> 
	* <p>Description: 根据多个数据字典类型查询数据字典list</p> 
	* @param typeClassCodes
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#findByTypesCodes(java.lang.String)
	 */
	@Override
	public List<PortalDictListVo> findByTypesCodes(String typeClassCodes) {
		if(StringUtil.isEmpty(typeClassCodes)){
			return null;
		};
		//获取code[]
		String[] typeClassCodeArray = typeClassCodes.split(",");
		PortalDictListVo dictListVo=null;
		List<PortalDictListVo> dictDataVoList=null;
		if(typeClassCodeArray.length>0){
			dictDataVoList=new ArrayList<PortalDictListVo>();
			for(String typeClassCode : typeClassCodeArray){
				dictListVo=new PortalDictListVo();
				dictListVo.setDictType(typeClassCode);
				dictListVo.setDictDataList(fspDictDataService.findByTypeClassCode(typeClassCode));
				dictDataVoList.add(dictListVo);
			}
		};
		return dictDataVoList;
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findExitTypeCode</p> 
	* <p>Description: 查询typeCode是否存在</p> 
	* @param dictDataVo
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#findExitTypeCode(cn.uce.PortalDictDataVo.authorg.vo.DictDataVo)
	 */
	@Override
	public PortalDictDataVo findExitTypeCode(PortalDictDataVo dictDataVo) {
		return fspDictDataService.findExitTypeCode(dictDataVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: addDictType</p> 
	* <p>Description: 增加DictType</p> 
	* @param dictTypeVo
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#addDictType(cn.uce.PortalDictTypeVo.authorg.vo.DictTypeVo)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int addDictType(PortalDictTypeVo dictTypeVo) {
		return fspDictDataService.addDictType(dictTypeVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: updateDictType</p> 
	* <p>Description: 修改DictType</p> 
	* @param dictTypeVo
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#updateDictType(cn.uce.PortalDictTypeVo.authorg.vo.DictTypeVo)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int updateDictType(PortalDictTypeVo dictTypeVo) {
		return fspDictDataService.updateDictType(dictTypeVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: deleteDictType</p> 
	* <p>Description: 删除DictType</p> 
	* @param typeClassCode
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#deleteDictType(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int deleteDictType(String typeClassCode) {
		return fspDictDataService.deleteDictType(typeClassCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findByCondition</p> 
	* <p>Description: 条件查询DictType</p> 
	* @param dictTypeVo
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#findByCondition(cn.uce.PortalDictTypeVo.authorg.vo.DictTypeVo)
	 */
	@Override
	public List<PortalDictTypeVo> findDictTypeList(PortalDictTypeVo dictTypeVo) {
		return fspDictDataService.findDictTypeList(dictTypeVo);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findByTypeClassCode</p> 
	* <p>Description: 根据typeClassCode查询DictType</p> 
	* @param typeClassCode
	* @return 
	* @see cn.uce.web.authorg.biz.IDictDataBiz#findByTypeClassCode(java.lang.String)
	 */
	@Override
	public PortalDictTypeVo findByTypeClassCode(String typeClassCode) {
		return fspDictDataService.findSingleByTypeClassCode(typeClassCode);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过empcode查询所属权限对应的系统编码
	 * </pre>
	 * @param empCode
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月28日上午9:25:50
	 */
	@Override
	public List<PermissionVo> findPermissionByEmpCode(String empCode) {
		List<String> permissionList = fspDictDataService.findPermissionByEmpCode(empCode);
		if (null != permissionList) {
			List<PermissionVo> portalPermissionVos = new ArrayList<PermissionVo>();
			for (String string : permissionList) {
				PermissionVo portalPermissionVo = new PermissionVo();
				portalPermissionVo.setSysName(string);
				portalPermissionVos.add(portalPermissionVo);
			}
			return portalPermissionVos;
		}
		
		return null;
	}
}
