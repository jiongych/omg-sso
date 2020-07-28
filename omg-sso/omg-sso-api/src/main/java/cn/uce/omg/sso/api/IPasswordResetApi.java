package cn.uce.omg.sso.api;

import cn.uce.omg.sso.vo.ExpCodeVo;
import cn.uce.omg.sso.vo.ResetPwdVo;
import cn.uce.omg.sso.vo.ReturnResultVo;

/**
 * @author BaoJingyu
 * @Description 重置密码接口
 * @create 2018/1/20
 * @empNo 015966
 * @email baojingyu@uce.cn
 */
public interface IPasswordResetApi {
    /**
     * 检查验证码
     * @param expCode
     * @return sendResult
     * @throws Exception
     * @author BaoJingyu
     * @date 2018年1月20日 上午17:01:13
     */
    public ReturnResultVo checkVerificationCode(ExpCodeVo expCode) throws Exception;

    /**
     * 发送验证码
     * @param expCode
     * @return
     * @throws Exception
     */
    public ReturnResultVo sendVerificationCode(ExpCodeVo expCode) throws Exception;

    /**
     * 找回密码，提供RMI，HTTP方式进行校验
     * @param resetPwd
     * @return
     * @throws Exception
     */
    public ReturnResultVo resetPassword(ResetPwdVo resetPwd) throws Exception;

}
