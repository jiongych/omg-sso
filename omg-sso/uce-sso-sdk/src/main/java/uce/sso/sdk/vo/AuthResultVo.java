/** 
 * @项目名称: FSP
 * @文件名称: AuthResultVo implements Serializable 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 登录返回vo
 * @author tanchong
 */
public class AuthResultVo implements Serializable {

	/**  */
	private static final long serialVersionUID = 1L;

	/** 返回的tokenid */
	private String tokenId;

	/** 员工id */
	private Integer empId;

	/** 员工所属机构id */
	private Integer orgId;

	/** 登录成功的userName */
	private String userName;

	/** 过期时间 */
	private Date expireTime;

	/** 用户权限 */
	private List<UserRoleVo> userRoleList;

	/** 备注 */
	private String remark;
	
	/** 工号 */
	private String empCode;
	
	/** 员工姓名 */
	private String empName;
	
	/** 乾坤机构id */
	private Integer otherOrgId;
	
	/** 修改密码标识 */
	private Boolean updPwdFlag;
	
	/** 修改密码间隔天数 */
	private Integer updPwdIntervalDay;
	
	/** 是否为负责人. add By BaoJingyu 20180312*/
	private Boolean principalFlag;

	/** 机构名称. add By BaoJingyu 20180312*/
	private String orgName;

	/** 机构类型. 总部-10,财务中心-20,操作中心-21,网点-30,承包区-40 add By BaoJingyu 20180312*/
	private Integer orgType;

	public Boolean getPrincipalFlag() {
		return principalFlag;
	}

	public void setPrincipalFlag(Boolean principalFlag) {
		this.principalFlag = principalFlag;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public Integer getOtherOrgId() {
		return otherOrgId;
	}

	public void setOtherOrgId(Integer otherOrgId) {
		this.otherOrgId = otherOrgId;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public List<UserRoleVo> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<UserRoleVo> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Boolean getUpdPwdFlag() {
		return updPwdFlag;
	}

	public Integer getUpdPwdIntervalDay() {
		return updPwdIntervalDay;
	}

	public void setUpdPwdFlag(Boolean updPwdFlag) {
		this.updPwdFlag = updPwdFlag;
	}

	public void setUpdPwdIntervalDay(Integer updPwdIntervalDay) {
		this.updPwdIntervalDay = updPwdIntervalDay;
	}

}
