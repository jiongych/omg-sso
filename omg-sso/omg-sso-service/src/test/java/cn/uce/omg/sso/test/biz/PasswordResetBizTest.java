package cn.uce.omg.sso.test.biz;

import cn.uce.omg.sso.api.IPasswordResetApi;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.test.BaseJunitTest;
import cn.uce.omg.sso.vo.ExpCodeVo;
import cn.uce.omg.sso.vo.ResetPwdVo;
import cn.uce.omg.sso.vo.ReturnResultVo;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 描述:
 * 重置密码接口测试类
 *
 * @outhor BaoJingyu
 * @create 2018-01-22 11:07
 * @empNo 015966
 * @email baojingyu@uce.cn
 */
public class PasswordResetBizTest extends BaseJunitTest {

    @Autowired
    private IPasswordResetApi passwordResetApi;

    @Test
    public void testSendCode() {
        ExpCodeVo expCodeVo = new ExpCodeVo();
        expCodeVo.setSystemCode("OMG");
        expCodeVo.setMobile("15221260245");
        expCodeVo.setSendTime(new Date());
        try {
            ReturnResultVo returnResultVo = passwordResetApi.sendVerificationCode(expCodeVo);
            System.out.println(JSON.toJSON(returnResultVo));
        } catch (GatewayException ge) {
            ge.getErrorCode();
            ge.getErrorMessage();
            System.out.println(JSON.toJSON(ge));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckCode() {
        ExpCodeVo expCodeVo = new ExpCodeVo();
        expCodeVo.setSystemCode("OMG");
        expCodeVo.setMobile("15221260245");
        expCodeVo.setCode("810392");
        expCodeVo.setSendTime(new Date());
        try {
            ReturnResultVo returnResultVo = passwordResetApi.checkVerificationCode(expCodeVo);
            System.out.println(JSON.toJSON(returnResultVo));
        } catch (GatewayException ge) {
            ge.getErrorCode();
            ge.getErrorMessage();
            System.out.println(JSON.toJSON(ge));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testResetPassword() {
        ResetPwdVo resetPwdVo = new ResetPwdVo();
        resetPwdVo.setSystemCode("OMG");
        resetPwdVo.setMobile("15221260245");
        resetPwdVo.setResetPwdKey("3993586e6763d96ae9db9e9c673a5a78");
        resetPwdVo.setNewPassword("81ecddf30a8c57d549663f51c5cdc3d4");
        resetPwdVo.setPasswordStrength("STRONG");
        try {
            ReturnResultVo returnResultVo = passwordResetApi.resetPassword(resetPwdVo);
            System.out.println(JSON.toJSON(returnResultVo));
        } catch (GatewayException ge) {
            ge.getErrorCode();
            ge.getErrorMessage();
            System.out.println(JSON.toJSON(ge));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}