package cn.uce.omg.sso.cache;

import org.springframework.beans.factory.annotation.Autowired;

import cn.uce.base.exception.BusinessException;
import cn.uce.core.cache.CacheSupport;
import cn.uce.omg.sso.biz.IEmpBiz;
import cn.uce.omg.vo.EmpVo;
import cn.uce.utils.StringUtil;

public class EmpCache extends CacheSupport<EmpVo>{
	
	@Autowired
	private IEmpBiz empBiz;
	
	@Override
	public EmpVo doGet(String empId) throws BusinessException {
		if (StringUtil.isBlank(empId)) {
			return null;
		}
		return empBiz.findEmpByEmpId(Integer.parseInt(empId));
	}

	@Override
	public String getCacheId() {
		return "omg-" + getClass().getSimpleName();
	}
	
	public EmpVo get(Integer empId) throws BusinessException {
		if (empId == null) {
			return null;
		}
		return super.get(empId.toString());
	}
	
	public void invalid (Integer empId) {
		if (empId == null) {
			return;
		}
		super.invalid(empId.toString());
	}
//	
//	 /** 
//     * 默认空实现初始化数据回调函数
//     * 当{@link CacheSupport#lazy}=false时需要重写这个方法
//     */
//   public ResultMap<String,List<PermissionTreeVo>> doInitialization(IKeyGenerator<String> generator) throws BusinessException {
//	   	PermissionVo permissionVo = new PermissionVo();
//		List<PermissionTreeVo> permissionTreeVoList=fspPermissionBiz.findPermissionTree(permissionVo);
//		ResultMap<String, List<PermissionTreeVo>> permissionTreeCache= new ResultMap<String, List<PermissionTreeVo>>(generator);
//		permissionTreeCache.put("permissionTree", permissionTreeVoList);
//		return permissionTreeCache;
//    }

	/**
	 * @param empBiz the empBiz to set
	 */
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}
}
