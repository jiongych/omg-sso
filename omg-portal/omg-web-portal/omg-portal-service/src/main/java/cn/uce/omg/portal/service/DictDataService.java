package cn.uce.omg.portal.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.base.page.Sort;
import cn.uce.core.cache.CacheManager;
import cn.uce.core.cache.base.ICache;
import cn.uce.omg.portal.dao.IDictDataDao;
import cn.uce.omg.portal.dao.IDictTypeDao;
import cn.uce.omg.portal.entity.PortalDictData;
import cn.uce.omg.portal.entity.PortalDictType;
import cn.uce.omg.portal.exception.DictDataException;
import cn.uce.omg.portal.vo.PortalDictDataTreeVo;
import cn.uce.omg.portal.vo.PortalDictDataVo;
import cn.uce.omg.portal.vo.PortalDictTypeVo;
import cn.uce.web.common.util.ObjectConvertUtil;

/**
 * @Description: (DictDataService) 
 * @author XJ
 * @date 2017年8月2日 下午2:28:57
 */
@Service("fspDictDataService")
public class DictDataService {
	
	@Resource
	private IDictTypeDao fspDictTypeDao;
	@Resource
	private IDictDataDao fspDictDataDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @Description: (分页查询DictData) 
	 * @param dictDataVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:29:11
	 */
	public Pagination<PortalDictDataVo> findDictDataByPage(PortalDictDataVo dictDataVo, Page page) {
		return fspDictDataDao.findDictDataByPage(dictDataVo, page);
	}
	
	/**
	 * @Description: (增加DictData) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:29:34
	 */
	public int addDictData(PortalDictDataVo dictDataVo) {
		Integer maxTypeId = fspDictDataDao.findMaxTypeIdByTypeClassCode(dictDataVo.getTypeClassCode());
		if(maxTypeId == null){
			dictDataVo.setTypeId(1);
		}else{
			dictDataVo.setTypeId(maxTypeId+1);
		}
		PortalDictData dictData = ObjectConvertUtil.convertObject(dictDataVo, PortalDictData.class);
		int insert = fspDictDataDao.insert(dictData);
		if(insert>0){
			CacheManager.getInstance().getCache("DictDataCache").refresh(dictDataVo.getTypeClassCode());
		}else{
			logger.error("@_@数据字典增加失败");
			throw new DictDataException("add dictData fail","数据字典增加失败");
		}
		return insert;
	}
	
	/**
	 * @Description: (更新DictData) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:29:50
	 */
	public int updateDictData(PortalDictDataVo dictDataVo) {
		PortalDictData dictData = ObjectConvertUtil.convertObject(dictDataVo, PortalDictData.class);
		int update = fspDictDataDao.updateById(dictData);
		if(update>0){
			CacheManager.getInstance().getCache("DictDataCache").refresh(dictDataVo.getTypeClassCode());
		}else{
			logger.error("@_@数据字典更新失败");
			throw new DictDataException("update dictData fail","数据字典更新失败");
		}
		return update;
	}
	
	/**
	 * @Description: (删除DictData) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:30:10
	 */
	public int deleteDictData(PortalDictDataVo dictDataVo) {
		int delete = fspDictDataDao.deleteById(dictDataVo.getId());
		if(delete>0){
			CacheManager.getInstance().getCache("DictDataCache").refresh(dictDataVo.getTypeClassCode());
		}else{
			logger.error("@_@数据字典删除失败");
			throw new DictDataException("update dictData fail","数据字典删除失败");
		}
		return delete;
	}
	
	/**
	 * @Description: (条件查询DictData) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:30:39
	 */
	public PortalDictDataVo findOneByCondition(PortalDictDataVo dictDataVo) {
		return fspDictDataDao.findOneByCondition(dictDataVo);
	}
	
	/**
	 * @Description: (输入typeClassCode，走缓存中取) 
	 * @return
	 * @author XJ
	 * @date 2017年4月13日 下午5:35:40
	 */
	public List<PortalDictDataVo> findDictData(String typeClassCode){
		ICache<String, List<PortalDictDataVo>> dictDataCache = CacheManager.getInstance().getCache("DictDataCache");
		return dictDataCache.get(typeClassCode);
	}
	
	/**
	 * @Description: (根据typeClassCode初始化缓存) 
	 * @return
	 * @author XJ
	 * @date 2017年4月13日 下午5:36:30
	 */
	public List<PortalDictDataVo> findAll() {
		List<PortalDictData> dictDataList = fspDictDataDao.findAll(Sort.parse("type_class_code:asc"));
		List<PortalDictDataVo> dictDataVoList = ObjectConvertUtil.convertList(dictDataList, PortalDictDataVo.class);
		return dictDataVoList;
	}
	/**
	 * 
	 * @Description: (根据TypeClassCode查询DictData) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2017年4月17日 下午2:16:21
	 */
	public List<PortalDictDataVo> findByTypeClassCode(String typeClassCode){
		return fspDictDataDao.findListByTypeClassCode(typeClassCode);
	}
	
	/**
	 * 
	 * @Description: (查找DictDataTree) 
	 * @param parentNodeId
	 * @return
	 * @author XJ
	 * @date 2017年4月22日 上午8:23:17
	 */
	public List<PortalDictDataTreeVo> findDictDataTree(){
		//数据字典树的集合
		List<PortalDictDataTreeVo> treeNodeVoList = new ArrayList<PortalDictDataTreeVo>();
		PortalDictTypeVo dictTypeVoParam = new PortalDictTypeVo();
		dictTypeVoParam.setVisible(true);
		List<PortalDictTypeVo> dictTypeVoList = fspDictTypeDao.findDictTypeList(dictTypeVoParam);
		for(PortalDictTypeVo dictTypeVo : dictTypeVoList){
			PortalDictDataTreeVo dictDataTreeNode =  convertToTreeVo(dictTypeVo);
			treeNodeVoList.add(dictDataTreeNode);
		}
		return treeNodeVoList;
	}
	
	/**
	 * 
	 * @Description: (把DictTypeVo转换为DictDataTreeVo节点) 
	 * @param dictTypeVo
	 * @return
	 * @author XJ
	 * @date 2017年4月22日 上午8:22:23
	 */
	private PortalDictDataTreeVo convertToTreeVo(PortalDictTypeVo dictTypeVo){
		PortalDictDataTreeVo treeNodeVo = new PortalDictDataTreeVo();
		treeNodeVo.setId(dictTypeVo.getTypeClassCode());
		treeNodeVo.setText(dictTypeVo.getTypeClassName()+"("+dictTypeVo.getTypeClassCode()+")");
		treeNodeVo.setTypeClassName(dictTypeVo.getTypeClassName());
		treeNodeVo.setSourceType(dictTypeVo.getSourceType());
		treeNodeVo.setRemark(dictTypeVo.getRemark());
		treeNodeVo.setVisible(dictTypeVo.getVisible());
		treeNodeVo.setSystemCode(dictTypeVo.getSystemCode());
		return treeNodeVo;
	}
	
	/**
	 * @Description: (查询typeCode是否存在) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年7月11日 下午4:37:38
	 */
	public PortalDictDataVo findExitTypeCode(PortalDictDataVo dictDataVo){
		return fspDictDataDao.findExitTypeCode(dictDataVo);
	}
	
	/**
	 * @Description: (条件查询DictType) 
	 * @param dictTypeVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:32:06
	 */
	public List<PortalDictTypeVo> findDictTypeList(PortalDictTypeVo dictTypeVo) {
		return fspDictTypeDao.findDictTypeList(dictTypeVo);
	}
	
	/**
	 * @Description: (增加DictType) 
	 * @param dictTypeVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:32:29
	 */
	public int addDictType(PortalDictTypeVo dictTypeVo) {
		PortalDictType dictType = ObjectConvertUtil.convertObject(dictTypeVo, PortalDictType.class);
		return fspDictTypeDao.insert(dictType);
	}
	
	/**
	 * @Description: (修改DictType) 
	 * @param dictTypeVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:32:49
	 */
	public int updateDictType(PortalDictTypeVo dictTypeVo) {
		//DictType dictType = ObjectConvertUtil.convertObject(dictTypeVo, DictType.class);
		return fspDictTypeDao.updateByCode(dictTypeVo);
	}
	
	/**
	 * @Description: (根据typeClassCode查询DictType) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:33:20
	 */
	public PortalDictTypeVo findSingleByTypeClassCode(String typeClassCode) {
		return fspDictTypeDao.findByTypeClassCode(typeClassCode);
	}
	
	/**
	 * @Description: (删除DictType) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:33:59
	 */
	public int deleteDictType(String typeClassCode) {
		return fspDictTypeDao.deleteByTypeClassCode(typeClassCode);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过empcode查询所属权限对应的系统编码
	 * </pre>
	 * @param empCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月28日上午9:26:01
	 */
	public List<String> findPermissionByEmpCode(String empCode) {
		return fspDictTypeDao.findPermissionByEmpCode(empCode);
	}
	
}
