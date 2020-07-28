/** 
 * @项目名称: FSP
 * @文件名称: AuthResultVo implements Serializable 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 登录返回vo
 * @author tanchong
 */
public class AuthResultVo implements Serializable {

	/**
	 * serialVersionUID
	 **/
	private static final long serialVersionUID = 1L;
	/**
	 * 返回的tokenid
	 **/
	private String tokenId;
	/**
	 * 员工id
	 **/
	private Integer empId;
	/**
	 * 员工所属机构id
	 **/
	private Integer orgId;
	/**
	 *  登录成功的userName
	 **/
	private String userName;
	/**
	 * 过期时间
	 **/
	private Date expireTime;
	/**
	 * 用户权限
	 **/
	private List<UserRoleVo> userRoleList;
	/**
	 * 备注
	 **/
	private String remark;
	/**
	 * 工号 
	 **/
	private String empCode;
	/**
	 * 员工姓名
	 **/
	private String empName;
	/**
	 * 乾坤机构id
	 **/
	private Integer otherOrgId;
	/**
	 * 修改密码标识
	 **/
	private Boolean updPwdFlag;
	/**
	 * 修改密码间隔天数
	 **/
	private Integer updPwdIntervalDay;

	/**
	 * 乾坤机构id集合
	 **/
	String otherOrgIdStr;
	
	/** 最后登录时间. */
	private Date lastLoginTime;

	/** 是否为负责人. add By BaoJingyu 20180312*/
	private Boolean principalFlag;

	/** 机构名称. add By BaoJingyu 20180312*/
	private String orgName;

	/** 机构类型. 总部-10,财务中心-20,操作中心-21,网点-30,承包区-40 add By BaoJingyu 20180312*/
	private Integer orgType;
	/**
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	
	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the userName
	 */
	public Integer getOtherOrgId() {
		return otherOrgId;
	}

	/**
	 * @param otherOrgId the otherOrgId to set
	 */
	public void setOtherOrgId(Integer otherOrgId) {
		this.otherOrgId = otherOrgId;
	}

	/**
	 * @return the userName
	 */
	public String getTokenId() {
		return tokenId;
	}

	/**
	 * @param tokenId the tokenId to set
	 */
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the expireTime
	 */
	public Date getExpireTime() {
		return expireTime;
	}

	/**
	 * @param expireTime the expireTime to set
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the empId
	 */
	public Integer getEmpId() {
		return empId;
	}

	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	/**
	 * @return the orgId
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the userRoleList
	 */
	public List<UserRoleVo> getUserRoleList() {
		return userRoleList;
	}

	/**
	 * @param userRoleList the userRoleList to set
	 */
	public void setUserRoleList(List<UserRoleVo> userRoleList) {
		this.userRoleList = userRoleList;
	}

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
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * @return the updPwdFlag
	 */
	public Boolean getUpdPwdFlag() {
		return updPwdFlag;
	}

	/**
	 * @return the updPwdIntervalDay
	 */
	public Integer getUpdPwdIntervalDay() {
		return updPwdIntervalDay;
	}

	/**
	 * @param updPwdFlag the updPwdFlag to set
	 */
	public void setUpdPwdFlag(Boolean updPwdFlag) {
		this.updPwdFlag = updPwdFlag;
	}

	/**
	 * @param updPwdIntervalDay the updPwdIntervalDay to set
	 */
	public void setUpdPwdIntervalDay(Integer updPwdIntervalDay) {
		this.updPwdIntervalDay = updPwdIntervalDay;
	}

	/**
	 * @return the otherOrgIdStr
	 */
	public String getOtherOrgIdStr() {
		return otherOrgIdStr;
	}

	/**
	 * @param otherOrgIdStr the otherOrgIdStr to set
	 */
	public void setOtherOrgIdStr(String otherOrgIdStr) {
		this.otherOrgIdStr = otherOrgIdStr;
	}

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
}
