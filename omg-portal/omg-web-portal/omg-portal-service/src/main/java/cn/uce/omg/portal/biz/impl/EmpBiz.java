package cn.uce.omg.portal.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.omg.portal.biz.IEmpBiz;
import cn.uce.omg.portal.service.EmpService;
import cn.uce.omg.portal.vo.EmpVo;

@Service(value = "empBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class EmpBiz implements IEmpBiz {

	@Resource
	private EmpService empService;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据工号查询
	 * </pre>
	 * @param empCode
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月29日上午9:44:45
	 */
	@Override
	public EmpVo findByEmpCode(String empCode) {
		
		return empService.findByEmpCode(empCode);
	}

}
