/** 
 * @项目名称: FSP
 * @文件名称: EmpInfoVo extends BaseVo 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import cn.uce.base.vo.BaseVo;
import cn.uce.omg.vo.EmpVo;
import cn.uce.omg.vo.UserVo;

/**
 * 员工信息
 * @author huangting
 * @date 2017年6月8日 下午2:47:36
 */
public class EmpInfoVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 员工信息 */
	private EmpVo empVo;
	/** 员工密码信息 */
	private UserVo userVo;


	/**
	 * @return the empVo
	 */
	public EmpVo getEmpVo() {
		return empVo;
	}

	/**
	 * @param empVo the empVo to set
	 */
	public void setEmpVo(EmpVo empVo) {
		this.empVo = empVo;
	}
	
	/**
	 * @return the userVo
	 */
	public UserVo getUserVo() {
		return userVo;
	}

	/**
	 * @param userVo the userVo to set
	 */
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
	
}
