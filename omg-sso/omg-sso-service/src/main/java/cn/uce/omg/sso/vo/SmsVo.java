/** 
 * @项目名称: FSP
 * @文件名称: SmsVo extends BaseVo
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import cn.uce.base.vo.BaseVo;

/**
 * 短信交互vo
 * @author huangting
 * @date 2017年6月8日 下午2:46:40
 */
public class SmsVo extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 手机号码 */
	private String mobile;
	/** 验证码 */
	private String code;
	/** 短信模板id*/
	private String tempId;
	/** 扣费网点Code */
	private String smspCostOrgCode;
	/** 短信内容 */
	private String content;

	private String empCode;

	private Integer empOrg;

	public Integer getEmpOrg() {
		return empOrg;
	}

	public void setEmpOrg(Integer empOrg) {
		this.empOrg = empOrg;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the tempId
	 */
	public String getTempId() {
		return tempId;
	}

	/**
	 * @param tempId the tempId to set
	 */
	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	/**
	 * @return the smspCostOrgCode
	 */
	public String getSmspCostOrgCode() {
		return smspCostOrgCode;
	}

	/**
	 * @param smspCostOrgCode the smspCostOrgCode to set
	 */
	public void setSmspCostOrgCode(String smspCostOrgCode) {
		this.smspCostOrgCode = smspCostOrgCode;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}
