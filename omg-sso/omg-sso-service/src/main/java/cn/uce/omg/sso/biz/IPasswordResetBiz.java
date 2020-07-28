package cn.uce.omg.sso.biz;

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
public interface IPasswordResetBiz {
    /**
     * 发送验证码
     * @param expCode
     * @return sendResult
     * @throws Exception
     * @author BaoJingyu
     * @date 2018年1月19日 上午14:36:13
     */
    public ReturnResultVo sendVerificationCode(ExpCodeVo expCode) throws Exception;

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
     * 找回密码，提供RMI，HTTP方式进行校验
     * @param rtePwdVo
     * @return
     * @throws Exception
     */
    public ReturnResultVo resetPassword(ResetPwdVo resetPwdVo) throws Exception;
}