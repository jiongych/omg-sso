package cn.uce.omg.sso.vo;

import java.io.Serializable;

/**
 * 描述:
 * 返回结果Vo
 *
 * @outhor BaoJingyu
 * @create 2018-01-19 9:26
 * @empNo 015966
 * @email baojingyu@uce.cn
 */
public class ReturnResultVo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private Boolean success;

    private String successMsg;

    private String errorMsg;

    private String errorCode;

    /**
     * 重置密码唯一key
     */
    private String resetPwdKey;

    private String empCode;

    private String mobile;

    private String email;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getResetPwdKey() {
        return resetPwdKey;
    }

    public void setResetPwdKey(String resetPwdKey) {
        this.resetPwdKey = resetPwdKey;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}