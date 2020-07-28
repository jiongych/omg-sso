package cn.uce.omg.portal.control;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.cache.CacheManager;
import cn.uce.core.cache.base.ICache;
import cn.uce.omg.portal.biz.IDictDataBiz;
import cn.uce.omg.portal.exception.DictDataException;
import cn.uce.omg.portal.util.Constants;
import cn.uce.omg.portal.vo.PermissionVo;
import cn.uce.omg.portal.vo.PortalDictDataTreeVo;
import cn.uce.omg.portal.vo.PortalDictDataVo;
import cn.uce.omg.portal.vo.PortalDictListVo;
import cn.uce.omg.portal.vo.PortalDictTypeVo;
import cn.uce.web.common.base.BaseController;
import cn.uce.web.common.i18n.Resources;
import cn.uce.web.common.util.WebUtil;

/**
 * @Description: (DictDataController) 
 * @author XJ
 * @date 2017年8月2日 下午2:51:33
 */
@Controller
@RequestMapping("/dictData")
public class DictDataController extends BaseController{
	@Resource
	private IDictDataBiz fspDictDataBiz;
	
	/**
	 * @Description: (数据字典页面跳转) 
	 * @return
	 * @throws Exception
	 * @author XJ
	 * @date 2017年8月2日 下午2:52:11
	 */
	@RequestMapping(value ="/forward")
	public String forward() throws Exception {
		return "portal/dictData";
	}
	
	/**
	 * @Description: (分页查询DictData) 
	 * @param dictDataVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:53:04
	 */
	@RequestMapping(value ="/findDictDataByPage")
	@ResponseBody
	public Map<String, Object> findDictDataByPage(PortalDictDataVo dictDataVo, Page page) {
		Pagination<PortalDictDataVo> Paginations = fspDictDataBiz.findDictDataByPage(dictDataVo, page);
		return returnSuccess(Paginations);
	}
	
	/**
	 * @Description: (查询自定义DictType) 
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:55:26
	 */
	@RequestMapping(value ="/findTypeClassCode", method = RequestMethod.POST)
	@ResponseBody
	public List<PortalDictTypeVo> findTypeClassCode() {
		PortalDictTypeVo dictTypeVo = new PortalDictTypeVo();
		dictTypeVo.setVisible(true);
		dictTypeVo.setDeleteFlag(false);
		dictTypeVo.setSourceType(Constants.DATADICTIONARY_SOURCT_TYPE_USERDEFINED);
		return fspDictDataBiz.findDictTypeList(dictTypeVo);
	}
	
	/**
	 * @Description: (增加DictData) 
	 * @param dictDataVo
	 * @param request
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:55:52
	 */
	@RequestMapping(value ="/addDictData")
	@ResponseBody
	public Map<String, Object> addDictData(PortalDictDataVo dictDataVo) {
		if(Constants.SUPER_ADMIN.equals(dictDataVo.getTypeClassCode()) || Constants.WHITE_LIST_SWITCH.equals(dictDataVo.getTypeClassCode()) || Constants.WHITE_LIST.equals(dictDataVo.getTypeClassCode())){
			ICache<String, List<PortalDictDataVo>> dictDataCache = CacheManager.getInstance().getCache("DictDataCache");
			List<PortalDictDataVo> dictDataVoList = dictDataCache.get(Constants.SUPER_ADMIN);
			boolean flag = false;
			for(PortalDictDataVo dictData : dictDataVoList){
				if(WebUtil.getCurrentUser().getEmpCode().equals(dictData.getTypeCode())){
					flag =true;
					break;
				}
			}
			if(!flag){
				return returnError(Resources.getMessage("error.biz.user.add.admindict.isnotallow"));
			}
		}
		PortalDictDataVo isExistDictData = new PortalDictDataVo();
		isExistDictData.setTypeId(dictDataVo.getTypeId());
		isExistDictData.setTypeClassCode(dictDataVo.getTypeClassCode());
		isExistDictData = fspDictDataBiz.findOneByCondition(isExistDictData);
		if (isExistDictData != null) {
			throw new DictDataException("error.biz.dictdata.typeidisexist");
		}
		dictDataVo.setSourceType(Constants.DATADICTIONARY_SOURCT_TYPE_USERDEFINED);
		dictDataVo.setDeleteFlag(false);
		dictDataVo.setCreateEmp(WebUtil.getCurrentUser().getEmpCode());
		dictDataVo.setCreateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		dictDataVo.setCreateTime(new Date());
		dictDataVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		dictDataVo.setUpdateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		dictDataVo.setUpdateTime(new Date());
		//dictDataVo.setVersion(1);
		//数据同步数据，获取系统Code
		PortalDictTypeVo dictTypeVo = fspDictDataBiz.findByTypeClassCode(dictDataVo.getTypeClassCode());
		dictDataVo.setSystemCode(dictTypeVo.getSystemCode());
		int res = fspDictDataBiz.addDictData(dictDataVo);
		if (res > 0) {
			return returnSuccess(Resources.getMessage("common.save.success"));
	    } else {
	        return returnError(Resources.getMessage("common.save.fail"));
	    }
	}
	
	/**
	 * @Description: (修改DictData) 
	 * @param dictDataVo
	 * @param request
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:56:34
	 */
	@RequestMapping(value ="/updateDictData")
	@ResponseBody
	public Map<String, Object> updateDictData(PortalDictDataVo dictDataVo) {
		if(Constants.SUPER_ADMIN.equals(dictDataVo.getTypeClassCode()) || Constants.WHITE_LIST_SWITCH.equals(dictDataVo.getTypeClassCode()) || Constants.WHITE_LIST.equals(dictDataVo.getTypeClassCode())){
			ICache<String, List<PortalDictDataVo>> dictDataCache = CacheManager.getInstance().getCache("DictDataCache");
			List<PortalDictDataVo> dictDataVoList = dictDataCache.get(Constants.SUPER_ADMIN);
			boolean flag = false;
			for(PortalDictDataVo dictData : dictDataVoList){
				if(WebUtil.getCurrentUser().getEmpCode().equals(dictData.getTypeCode())){
					flag =true;
					break;
				}
			}
			if(!flag){
				return returnError(Resources.getMessage("error.biz.user.edit.admindict.isnotallow"));
			}
		}
		dictDataVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		dictDataVo.setUpdateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		dictDataVo.setUpdateTime(new Date());
		PortalDictTypeVo dictTypeVo = fspDictDataBiz.findByTypeClassCode(dictDataVo.getTypeClassCode());
		dictDataVo.setSystemCode(dictTypeVo.getSystemCode());
		int res = fspDictDataBiz.updateDictData(dictDataVo);
		if (res > 0) {
			return returnSuccess(Resources.getMessage("common.update.success"));
	    } else {
	        return returnError(Resources.getMessage("common.update.fail"));
	    }
	}
	
	/**
	 * @Description: (删除DictData) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:56:49
	 */
	@RequestMapping(value ="/deleteDictData")
	@ResponseBody
	public Map<String, Object> deleteDictData(PortalDictDataVo dictDataVo) {
		if(Constants.SUPER_ADMIN.equals(dictDataVo.getTypeClassCode()) || Constants.WHITE_LIST_SWITCH.equals(dictDataVo.getTypeClassCode()) || Constants.WHITE_LIST.equals(dictDataVo.getTypeClassCode())){
			ICache<String, List<PortalDictDataVo>> dictDataCache = CacheManager.getInstance().getCache("DictDataCache");
			List<PortalDictDataVo> dictDataVoList = dictDataCache.get(Constants.SUPER_ADMIN);
			boolean flag = false;
			for(PortalDictDataVo dictData : dictDataVoList){
				if(WebUtil.getCurrentUser().getEmpCode().equals(dictData.getTypeCode())){
					flag =true;
					break;
				}
			}
			if(!flag){
				return returnError(Resources.getMessage("error.biz.user.delete.admindict.isnotallow"));
			}
		}
		PortalDictTypeVo dictTypeVo = fspDictDataBiz.findByTypeClassCode(dictDataVo.getTypeClassCode());
		dictDataVo.setSystemCode(dictTypeVo.getSystemCode());
		int res = fspDictDataBiz.deleteDictData(dictDataVo);
		if (res > 0) {
			return returnSuccess(Resources.getMessage("common.delete.success"));
	    } else {
	        return returnError(Resources.getMessage("common.delete.fail"));
	    }
	}
	
	/**
	 * @Description:(数据字典控件) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年4月18日 上午9:10:12
	 */
	@RequestMapping(value ="/findDictData")
	@ResponseBody
	public List<PortalDictDataVo> findDictData(String typeClassCode){
		return fspDictDataBiz.findDictData(typeClassCode);
	}
	
	/**
	 * 
	 * @Description:(数据字典控件) 
	 * @param dictDataVo
	 * @return
	 * @author zd
	 * @date 2017年6月21日 上午9:10:12
	 */
	@RequestMapping(value ="/findByTypesCodes")
	@ResponseBody
	public List<PortalDictListVo> findByTypesCodes(String typeClassCodes){
		return fspDictDataBiz.findByTypesCodes(typeClassCodes);
	}
	
	/**
	 * 
	 * @Description:(数据字典树) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年4月18日 上午9:10:12
	 */
	@RequestMapping(value ="/findDictDataTree")
	@ResponseBody
	public List<PortalDictDataTreeVo> findDictDataTree(){
		return fspDictDataBiz.findDictDataTree();
	}
	
	/**
	 * 
	 * @Description: (根据typeCode查询数据字典) 
	 * @param typeCode
	 * @return
	 * @author XJ
	 * @date 2017年7月11日 下午4:10:40
	 */
	@RequestMapping(value ="/findExitTypeCode")
	@ResponseBody
	public Boolean findExitTypeCode(PortalDictDataVo dictDataVo){
		PortalDictDataVo dictDataVoRes = fspDictDataBiz.findExitTypeCode(dictDataVo);
		return dictDataVoRes == null ? true : false;
	}
	
	/**
	 * @Description: (查询所有DictType) 
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午3:00:56
	 */
	@RequestMapping(value ="/findAllDictType", method = RequestMethod.POST)
	@ResponseBody
	public List<PortalDictTypeVo> findAllDictType() {
		PortalDictTypeVo dictTypeVo = new PortalDictTypeVo();
		dictTypeVo.setDeleteFlag(false);
		dictTypeVo.setVisible(true);
		return fspDictDataBiz.findDictTypeList(dictTypeVo);
	}
	
	/**
	 * @Description: (根据typeClassCode查询数据字典是否存在) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午3:01:20
	 */
	@RequestMapping(value ="findByTypeClassCode")
	@ResponseBody
	private Boolean findByTypeClassCode(String typeClassCode) {
		PortalDictTypeVo dictTypeVo = fspDictDataBiz.findByTypeClassCode(typeClassCode);
		return dictTypeVo == null ? true : false;
	}
	
	/**
	 * @Description: (增加DictType) 
	 * @param dictTypeVo
	 * @param request
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午3:02:07
	 */
	@RequestMapping(value ="/addDictType")
	@ResponseBody
	public Map<String, Object> addDictType(PortalDictTypeVo dictTypeVo) {
		dictTypeVo.setSourceType(Constants.DATADICTIONARY_SOURCT_TYPE_USERDEFINED);
		dictTypeVo.setDeleteFlag(false);
		dictTypeVo.setCreateEmp(WebUtil.getCurrentUser().getEmpCode());
		dictTypeVo.setCreateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		dictTypeVo.setCreateTime(new Date());
		dictTypeVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		dictTypeVo.setUpdateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		dictTypeVo.setUpdateTime(new Date());
		int res = fspDictDataBiz.addDictType(dictTypeVo);
		if (res > 0) {
			return returnSuccess(Resources.getMessage("common.save.success"));
	    } else {
	        return returnError(Resources.getMessage("common.save.fail"));
	    }
	}
	
	/**
	 * @Description: (修改DictType) 
	 * @param dictTypeVo
	 * @param request
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午3:02:24
	 */
	@RequestMapping(value ="/updateDictType")
	@ResponseBody
	public Map<String, Object> updateDictType(PortalDictTypeVo dictTypeVo) {
		dictTypeVo.setUpdateEmp(WebUtil.getCurrentUser().getEmpCode());
		dictTypeVo.setUpdateOrg(WebUtil.getCurrentUser().getCmsOrgId());
		dictTypeVo.setUpdateTime(new Date());
		int res = fspDictDataBiz.updateDictType(dictTypeVo);
		if (res > 0) {
			return returnSuccess(Resources.getMessage("common.update.success"));
	    } else {
	        return returnError(Resources.getMessage("common.update.fail"));
	    }
	}
	
	/**
	 * @Description: (删除DictType) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午3:02:38
	 */
	@RequestMapping(value ="/deleteDictType")
	@ResponseBody
	public Map<String, Object> deleteDictType(String typeClassCode) {
		List<PortalDictDataVo> dictDataVoList= fspDictDataBiz.findDictData(typeClassCode);
		if(dictDataVoList !=null && !dictDataVoList.isEmpty()){
			return returnError("该系统类型已经使用！");
		}
		int res = fspDictDataBiz.deleteDictType(typeClassCode);
		if (res > 0) {
			return returnSuccess(Resources.getMessage("common.delete.success"));
	    } else {
	        return returnError(Resources.getMessage("common.delete.fail"));
	    }
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过empcode查询所属权限对应的系统编码
	 * </pre>
	 * @return
	 * @return List<PortalPermissionVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月28日上午9:24:41
	 */
	@RequestMapping(value ="/findPermissionByEmpCode")
	@ResponseBody
	public List<PermissionVo> findPermissionByEmpCode() {
		
		return fspDictDataBiz.findPermissionByEmpCode(WebUtil.getCurrentUser().getEmpCode());
	}
	
}
