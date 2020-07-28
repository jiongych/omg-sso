/** 
 * @项目名称: FSP
 * @文件名称: TokenVo extends RedisHashFieldExpireVo 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.util.Map;

import cn.uce.omg.vo.SystemInfoVo;

/**
 * 用户token信息vo
 * @author huangting
 * @date 2017年6月8日 下午2:47:00
 */
public class TokenVo extends RedisHashFieldExpireVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/** 员工编码*/
	private String empCode;
	/** 系统信息 */
	private Map<String, SystemInfoVo> systemMap;

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return the systemMap
	 */
	public Map<String, SystemInfoVo> getSystemMap() {
		return systemMap;
	}
	
	/**
	 * @param systemMap the systemMap to set
	 */
	public void setSystemMap(Map<String, SystemInfoVo> systemMap) {
		this.systemMap = systemMap;
	}
	

}
