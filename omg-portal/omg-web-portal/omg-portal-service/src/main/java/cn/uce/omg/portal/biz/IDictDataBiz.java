package cn.uce.omg.portal.biz;

import java.util.List;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.vo.PortalDictDataTreeVo;
import cn.uce.omg.portal.vo.PortalDictDataVo;
import cn.uce.omg.portal.vo.PortalDictListVo;
import cn.uce.omg.portal.vo.PortalDictTypeVo;
import cn.uce.omg.portal.vo.PermissionVo;

/**
 * @Description: (IDictDataBiz) 
 * @author XJ
 * @date 2017年8月2日 上午11:06:48
 */
public interface IDictDataBiz {
	
	/**
	 * @Description: (分页查询DictData) 
	 * @param dictDataVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 上午11:07:04
	 */
	Pagination<PortalDictDataVo> findDictDataByPage(PortalDictDataVo dictDataVo, Page page);
	
	/**
	 * @Description: (增加DictData) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 上午11:07:38
	 */
	int addDictData(PortalDictDataVo dictDataVo);
	
	/**
	 * @Description: (修改DictData) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 上午11:08:01
	 */
	int updateDictData(PortalDictDataVo dictDataVo);
	
	/**
	 * @Description: (删除DictData) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 上午11:08:22
	 */
	int deleteDictData(PortalDictDataVo dictDataVo);
	
	/**
	 * @Description: (条件查询DictData) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 上午11:08:45
	 */
	PortalDictDataVo findOneByCondition(PortalDictDataVo dictDataVo);
	
	/**
	 * @Description: (查询DictData) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 上午11:28:05
	 */
	List<PortalDictDataVo> findDictData(String typeClassCode);

	/**
	 * @Description: (根据多个数据字典类型查询数据字典list) 
	 * @param typeClassCodes
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 上午11:28:45
	 */
	List<PortalDictListVo> findByTypesCodes(String typeClassCodes);
	
	/**
	 * @Description: (查询数据字典树) 
	 * @param parentNodeId
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 上午11:28:57
	 */
	List<PortalDictDataTreeVo> findDictDataTree();

	/**
	 * @Description: (查询typeCode是否存在)
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年7月11日 下午4:38:53
	 */
	PortalDictDataVo findExitTypeCode(PortalDictDataVo dictDataVo);
	
	/**
	 * @Description: (条件查询DictType) 
	 * @param dictTypeVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:21:03
	 */
	List<PortalDictTypeVo> findDictTypeList(PortalDictTypeVo dictTypeVo);
	
	/**
	 * @Description: (增加DictType) 
	 * @param dictTypeVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:21:25
	 */
	int addDictType(PortalDictTypeVo dictTypeVo);
	
	/**
	 * @Description: (修改DictType) 
	 * @param dictTypeVo
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:21:46
	 */
	int updateDictType(PortalDictTypeVo dictTypeVo);
	
	/**
	 * @Description: (删除DictType) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:22:00
	 */
	int deleteDictType(String typeClassCode);
	
	/**
	 * @Description: (根据TypeClassCode查询DictType) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午2:22:21
	 */
	PortalDictTypeVo findByTypeClassCode(String typeClassCode);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过empcode查询所属权限对应的系统编码
	 * </pre>
	 * @param empCode
	 * @return
	 * @return List<PortalPermissionVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月28日上午9:25:41
	 */
	List<PermissionVo> findPermissionByEmpCode(String empCode);
	
}
