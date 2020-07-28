package cn.uce.omg.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.omg.portal.dao.IEmpDao;
import cn.uce.omg.portal.vo.EmpVo;

@Service("empService")
public class EmpService {

	@Autowired
	private IEmpDao empDao;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据工号查询
	 * </pre>
	 * @param empCode
	 * @return
	 * @return EmpVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月29日上午9:43:29
	 */
	public EmpVo findByEmpCode(String empCode) {
		return empDao.findByEmpCode(empCode);
	}
}
