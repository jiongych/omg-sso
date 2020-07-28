package cn.uce.omg.main.sso.service;

import cn.uce.omg.sso.biz.IPasswordResetBiz;
import cn.uce.omg.sso.vo.ExpCodeVo;
import cn.uce.omg.sso.vo.ResetPwdVo;
import cn.uce.omg.sso.vo.ReturnResultVo;

/**
 * 描述:重置密码Service
 *
 * @outhor BaoJingyu
 * @create 2018-01-20 19:38
 * @empNo 015966
 * @email baojingyu@uce.cn
 */
public class PasswordResetService {

    private IPasswordResetBiz passwordResetBiz;

    public void setPasswordResetBiz(IPasswordResetBiz passwordResetBiz) {
        this.passwordResetBiz = passwordResetBiz;
    }

    /**
     * 发送验证码
     * @param expCode
     * @return
     * @throws Exception
     */
    public ReturnResultVo sendVerificationCode(ExpCodeVo expCode) throws Exception{
        return passwordResetBiz.sendVerificationCode(expCode);
    }

    /**
     * 检查验证码
     * @param expCode
     * @return sendResult
     * @throws Exception
     * @author BaoJingyu
     * @date 2018年1月20日 上午17:01:13
     */
    public ReturnResultVo checkVerificationCode(ExpCodeVo expCode) throws Exception{
        return passwordResetBiz.checkVerificationCode(expCode);
    }

    /**
     * 找回密码
     * @param rtePwd
     * @return
     * @throws Exception
     */
    public ReturnResultVo resetPassword(ResetPwdVo resetPwd) throws Exception{
        return passwordResetBiz.resetPassword(resetPwd);
    }
}