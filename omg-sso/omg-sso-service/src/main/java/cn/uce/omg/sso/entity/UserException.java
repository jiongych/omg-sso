/** 
 * @项目名称: FSP
 * @文件名称: UserException extends BaseEntity 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import cn.uce.base.entity.BaseEntity;

/**
 * 用户异常类
 * @author huangting
 * @date 2017年6月8日 下午2:28:23
 */
public class UserException extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 员工ID. */
    private Integer empId;

    /** 异常类型.用户登录异常，其他异常 */
    private String exceptionType;

    /** 异常内容. */
    private String content;

    /** 提示次数.
     * 默认值为3次，
     * 当为0时，登录不在进行提示 */
    private Integer promptNum;

    /** 通知方式.
     * 2登录提示，
     * 4邮箱，
     * 8短信通知用户 */
    private Integer noticeMode;

    /** 备注. */
    private String remark;

    /** 取得 提示次数.
     * 默认值为3次，当为0时，登录不在进行提示 */
    public Integer getPromptNum() {
        return this.promptNum;
    }

    /** 设置 提示次数.
     * 默认值为3次，当为0时，登录不在进行提示 */
    public void setPromptNum(Integer promptNum) {
        this.promptNum = promptNum;
    }
    
    /** 取得 通知方式.
     * 2登录提示，
     * 4邮箱，
     * 8短信通知用户 */
    public Integer getNoticeMode() {
        return this.noticeMode;
    }

    /** 
     * 设置 通知方式.
     * 2登录提示，
     * 4邮箱，
     * 8短信通知用户 */
    public void setNoticeMode(Integer noticeMode) {
        this.noticeMode = noticeMode;
    }

	/**
	 * @return the empId
	 */
	public Integer getEmpId() {
		return empId;
	}

	/**
	 * @return the exceptionType
	 */
	public String getExceptionType() {
		return exceptionType;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	/**
	 * @param exceptionType the exceptionType to set
	 */
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
