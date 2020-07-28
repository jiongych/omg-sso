package cn.uce.omg.sso.biz.impl;

import cn.uce.omg.sso.api.IPasswordResetApi;
import cn.uce.omg.sso.biz.IAuthBiz;
import cn.uce.omg.sso.biz.IPasswordResetBiz;
import cn.uce.omg.sso.constant.AuthConstants;
import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.util.MobileUtil;
import cn.uce.omg.sso.vo.*;
import cn.uce.utils.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 描述:重置密码接口
 *
 * @outhor BaoJingyu
 * @create 2018-01-20 19:30
 * @empNo 015966
 * @email baojingyu@uce.cn
 */
public class PasswordResetBiz implements IPasswordResetBiz, IPasswordResetApi {

    private Log log = LogFactory.getLog(this.getClass());

    private IAuthBiz authBiz;

    /**
     * 发送手机短信验证
     */
    private MobileUtil mobileUtil;

    public void setAuthBiz(IAuthBiz authBiz) {
        this.authBiz = authBiz;
    }

    public void setMobileUtil(MobileUtil mobileUtil) {
        this.mobileUtil = mobileUtil;
    }

    /**
     * 发送验证码 --->检查用户,存在则发送验证码,否则返回错误信息
     *
     * @param expCode
     * @param CodeType 重置密码=RESETPWD,修改密码=UPDPWD
     * @return sendResult
     * @throws Exception
     * @author BaoJingyu
     * @date 2018年1月20日 上午10:56:13
     */
    @Override
    public ReturnResultVo sendVerificationCode(ExpCodeVo expCode) throws Exception {
        ReturnResultVo returnResult = new ReturnResultVo();
        String typeStr = "";
        // 必要参数判断
        if (expCode == null || StringUtil.isBlank(expCode.getSystemCode())
                || (StringUtil.isBlank(expCode.getEmpCode())
                && StringUtil.isBlank(expCode.getMobile())
                && StringUtil.isBlank(expCode.getEmail()))) {
            throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
        }
        try {
            Map<String, Object> map = getKeyType(typeStr, expCode);
            typeStr = (String) map.get("typeStr");
            // 校验用户是否存在
            EmpInfoVo empinfo = authBiz.authCheckUser((String) map.get("key"), (Integer) map.get("type"));
            // 设置手机号码和发送时间
            expCode.setMobile(empinfo.getEmpVo().getMobile());
            if (expCode.getSendTime() == null) {
                expCode.setSendTime(new Date());
            }
            if (StringUtil.isBlank(expCode.getCodeType())) {
                // 验证码类型：重置密码
                expCode.setCodeType(AuthConstants.CODE_TYPE_RTEPWD);
            }
            // addby yangjun 2017-09-13 增加手机频繁重复发送短信验证 begin
            boolean result = mobileUtil.checkMobile(expCode.getMobile());
            if (!result) {
                throw new GatewayException(ErrorCode.MOBILE_CHECKED_ERROR, ErrorCode.MOBILE_CHECKED_ERROR_MESSGE);
            }
            // 发送验证码
            authBiz.getCode(expCode);
            returnResult.setSuccessMsg("验证码已发送");
            returnResult.setMobile(empinfo.getEmpVo().getMobile());
            return returnResult;
        } catch (GatewayException ge) {
            log.error("校验" + typeStr + "异常", ge);
            throw new GatewayException(ge.getErrorCode(), ge.getErrorMessage());
        } catch (Exception e) {
            log.error("sendVerificationCode error", e);        
            throw new Exception(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 校验验证码
     *
     * @param expCode
     * @return
     * @throws Exception
     * @author BaoJingyu
     * @date 2018年1月20日 上午10:56:13
     */
    @Override
    public ReturnResultVo checkVerificationCode(ExpCodeVo expCode) throws Exception {
        ReturnResultVo returnResult = new ReturnResultVo();
        //校验请求参数
        if (expCode == null || StringUtil.isBlank(expCode.getSystemCode())
                || StringUtil.isBlank(expCode.getMobile())
                || StringUtil.isBlank(expCode.getCode())) {
            throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
        }
        try {
            ExpCodeVo expCodeVo = new ExpCodeVo();
            if (StringUtil.isBlank(expCode.getCodeType())) {
                // 验证码类型：重置密码
                expCodeVo.setCodeType(AuthConstants.CODE_TYPE_RTEPWD);
            } else {
                expCodeVo.setCodeType(expCode.getCodeType());
            }
            expCodeVo.setSystemCode(expCode.getSystemCode());
            //设置校验方式为手机号码
            expCodeVo.setEmpCode(expCode.getMobile());
            expCodeVo.setCode(expCode.getCode());
            if (StringUtil.isNotBlank(expCode.getMachineCode())) {
                expCodeVo.setMachineCode(expCode.getMachineCode());
            }
            if (StringUtil.isNotBlank(expCode.getIpAddr())) {
                expCodeVo.setIpAddr(expCode.getIpAddr());
            }
            if (expCode.getSendTime() != null) {
                expCodeVo.setSendTime(expCode.getSendTime());
            } else {
                expCodeVo.setSendTime(new Date());
            }
            // 校验验证码
            String result = authBiz.checkCode(expCodeVo);
            if (result != null) {
                returnResult.setResetPwdKey(result);
                returnResult.setMobile(expCode.getMobile());
            }
            return returnResult;
        } catch (GatewayException ge) {
            log.error("校验验证码异常", ge);
            throw new GatewayException(ge.getErrorCode(), ge.getErrorMessage());
        } catch (Exception e) {
            log.error("checkCode error", e);
            throw new Exception(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 重置密码
     *
     * @param resetPwd
     * @return
     * @throws Exception
     * @author BaoJingyu
     * @date 2018年1月20日 上午10:56:13
     */
    @Override
    public ReturnResultVo resetPassword(ResetPwdVo resetPwd) throws Exception {
        ReturnResultVo returnResult = new ReturnResultVo();
        //校验请求参数
        if (resetPwd == null || StringUtil.isBlank(resetPwd.getSystemCode())
                || StringUtil.isBlank(resetPwd.getMobile())
                || StringUtil.isBlank(resetPwd.getResetPwdKey())
                || StringUtil.isBlank(resetPwd.getNewPassword())
                || StringUtil.isBlank(resetPwd.getPasswordStrength())) {
            throw new GatewayException(ErrorCode.SYSTEM_PARAM_ERROR, ErrorCode.SYSTEM_PARAM_ERROR_MESSGE);
        }
        try {
            ResetPwdVo resetPwdVo = new ResetPwdVo();
            //设置校验方式为手机号码
            resetPwdVo.setEmpCode(resetPwd.getMobile());
            resetPwdVo.setSystemCode(resetPwd.getSystemCode());
            resetPwdVo.setNewPassword(resetPwd.getNewPassword());
            resetPwdVo.setResetPwdKey(resetPwd.getResetPwdKey());
            resetPwdVo.setPasswordStrength(resetPwd.getPasswordStrength());
            if (resetPwd.getRestTime() != null) {
                resetPwdVo.setRestTime(resetPwd.getRestTime());
            } else {
                resetPwdVo.setRestTime(new Date());
            }
            // 检查系统是否需要机器码信息,需要的相关参数
            if (StringUtil.isNotBlank(resetPwd.getMachineCode())) {
                resetPwdVo.setMachineCode(resetPwd.getMachineCode());
            }
            if (StringUtil.isNotBlank(resetPwd.getIpAddr())) {
                resetPwdVo.setIpAddr(resetPwd.getIpAddr());
            }
            if (StringUtil.isNotBlank(resetPwd.getMacAddr())) {
                resetPwdVo.setMacAddr(resetPwd.getMacAddr());
            }
            AuthResultVo resultVo = authBiz.resetPwd(resetPwdVo);
            if (resultVo != null) {
                returnResult.setSuccessMsg("密码重置成功");
            }
            return returnResult;
        } catch (GatewayException ge) {
            log.error("重置密码失败", ge);
            throw new GatewayException(ge.getErrorCode(), ge.getErrorMessage());
        } catch (Exception e) {
            log.error("resetPassword error", e);
            throw new Exception(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 获取Key和Type
     *
     * @param typeStr
     * @param expCode
     * @return
     * @author BaoJingyu
     * @date 2018年1月20日 上午10:56:13
     */
    public Map<String, Object> getKeyType(String typeStr, ExpCodeVo expCode) {
        String key;
        Integer type;
        if (StringUtil.isNotBlank(expCode.getEmpCode())) {
            key = expCode.getEmpCode();
            type = AuthConstants.LOGIN_TYPE_EMP_CODE;
            typeStr = "员工编号";
        } else if (StringUtil.isNotBlank(expCode.getEmail())) {
            key = expCode.getEmail();
            type = AuthConstants.LOGIN_TYPE_EMAIL;
            typeStr = "邮箱";
        } else {
            key = expCode.getMobile();
            type = AuthConstants.LOGIN_TYPE_MOBILE;
            typeStr = "手机号";
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("type", type);
        map.put("typeStr", typeStr);
        return map;
    }
}