/** 
 * @项目名称: FSP
 * @文件名称: UceSsoClient 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import uce.sso.sdk.constant.ErrorCode;
import uce.sso.sdk.converter.impl.AuthCheckVoConverter;
import uce.sso.sdk.converter.impl.AuthResultVoConverter;
import uce.sso.sdk.converter.impl.ExpCodeVoConverter;
import uce.sso.sdk.converter.impl.LoginVoConverter;
import uce.sso.sdk.converter.impl.LogoutVoConverter;
import uce.sso.sdk.converter.impl.ResetPwdVoConverter;
import uce.sso.sdk.converter.impl.UpdPwdVoConverter;
import uce.sso.sdk.converter.impl.UserInfoVoConverter;
import uce.sso.sdk.converter.impl.UserRoleVoConverter;
import uce.sso.sdk.converter.impl.UserSearchVoConverter;
import uce.sso.sdk.exception.ClientException;
import uce.sso.sdk.util.SecurityUtil;
import uce.sso.sdk.util.WebUtils;
import uce.sso.sdk.vo.AuthCheckVo;
import uce.sso.sdk.vo.ClientVo;
import uce.sso.sdk.vo.ExpCodeVo;
import uce.sso.sdk.vo.LoginVo;
import uce.sso.sdk.vo.LogoutVo;
import uce.sso.sdk.vo.ResetPwdVo;
import uce.sso.sdk.vo.ResultRoleVo;
import uce.sso.sdk.vo.UpdPwdVo;
import uce.sso.sdk.vo.UserInfoVo;
import uce.sso.sdk.vo.UserSearchVo;
import uce.sso.sdk.vo.base.ResponseVo;
import uce.sso.sdk.vo.base.UserInfoResponseVo;
import uce.sso.sdk.vo.base.UserRoleResponseVo;

/**
 * 优速SSO连接客户端
 * @author tanchong
 *
 */
public class UceSsoClient {

	private ClientVo clientVo = null;
	LoginVoConverter loginConverter = new LoginVoConverter();
	LogoutVoConverter logoutConverter = new LogoutVoConverter();
	UpdPwdVoConverter updPwdConverter = new UpdPwdVoConverter();
	ResetPwdVoConverter rtePwdConverter = new ResetPwdVoConverter();
	ExpCodeVoConverter expCodeVoConverter = new ExpCodeVoConverter();
	AuthResultVoConverter resultConverter = new AuthResultVoConverter();
	AuthCheckVoConverter authCheckConverter = new AuthCheckVoConverter();
	UserSearchVoConverter userSearchVoConverter = new UserSearchVoConverter();
	UserInfoVoConverter userInfoVoConverter = new UserInfoVoConverter();
	UserRoleVoConverter userRoleVoConverter = new UserRoleVoConverter();

	public UceSsoClient() {}

	public UceSsoClient(ClientVo clientVo) {
		this.clientVo = clientVo;
	}

	/**
	 * 进行登录操作
	 * @param clientVo
	 * @param loginVo
	 * @throws ClientException
	 * @throws IOException
	 */
	public ResponseVo login(ClientVo clientVo, LoginVo loginVo) throws ClientException, IOException {
		if (clientVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
		this.clientVo = clientVo;
		return login(loginVo);
	}

	/**
	 * 进行登录操作
	 * @param loginVo
	 * @return
	 * @throws ClientException
	 * @throws IOException
	 */
	public ResponseVo login(LoginVo loginVo) throws ClientException, IOException {
		if (loginVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
		//AuthResultVo resultVo = new AuthResultVo();
		String data = loginConverter.marshal(loginVo);
		String result = "";
		try {
			result = sendData(data);
		} catch (ClientException e) {
			throw new ClientException("result:" + result, e);
		} catch (IOException e) {
			throw e;
		}
		if (result == null) {
			return null;
		}
		try {
			ResponseVo responseVo = (ResponseVo) resultConverter.unmarshal(result);
			// System.out.println(result);
			//resultVo = responseVo.getData();
			return responseVo;
		} catch (Exception ex) {
			throw new ClientException("AuthResultVo 转换异常result:" + result, ex);
		}
		
	}
	
	public ResponseVo logout(LogoutVo logoutVo) throws ClientException, IOException {
		if (logoutVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
		String data = logoutConverter.marshal(logoutVo);
		String result = "";
		try {
			result = sendData(data);
		} catch (ClientException e) {
			throw new ClientException("result:" + result, e);
		} catch (IOException e) {
			throw e;
		}
		if (result == null) {
			return null;
		}
		//AuthResultVo resultVo;
		try {
			ResponseVo responseVo = (ResponseVo) resultConverter.unmarshal(result);
			//resultVo = responseVo.getData();
			return responseVo;
		} catch (Exception ex) {
			throw new ClientException("AuthResultVo 转换异常result:" + result, ex);
		}
		
	}

	/**
	 * 修改密码
	 * @param loginVo
	 * @return
	 * @throws ClientException
	 * @throws IOException
	 */
	public ResponseVo updPwd(UpdPwdVo updPwdVo) throws ClientException, IOException {
		if (updPwdVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
		//AuthResultVo resultVo = new AuthResultVo();
		String data = updPwdConverter.marshal(updPwdVo);
		String result = "";
		try {
			result = sendData(data);
		} catch (ClientException e) {
			throw new ClientException("result:" + result, e);
		} catch (IOException e) {
			throw e;
		}
		if (result == null) {
			return null;
		}
		try {
			ResponseVo responseVo = (ResponseVo) resultConverter.unmarshal(result);
			//resultVo = responseVo.getData();
			return responseVo;
		} catch (Exception ex) {
			throw new ClientException("AuthResultVo 转换异常result:" + result, ex);
		}
		
	}

	/**
	 * 重置密码
	 * @param loginVo
	 * @return
	 * @throws ClientException
	 * @throws IOException
	 */
	public ResponseVo resetPwd(ResetPwdVo rtePwdVo) throws ClientException, IOException {
		if (rtePwdVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
		//AuthResultVo resultVo = new AuthResultVo();
		String data = rtePwdConverter.marshal(rtePwdVo);
		String result = "";
		try {
			result = sendData(data);
		} catch (ClientException e) {
			throw new ClientException("result:" + result, e);
		} catch (IOException e) {
			throw e;
		}
		if (result == null) {
			return null;
		}
		try {
			ResponseVo responseVo = (ResponseVo) resultConverter.unmarshal(result);
			//resultVo = responseVo.getData();
			return responseVo;
		} catch (Exception ex) {
			throw new ClientException("AuthResultVo 转换异常result:" + result, ex);
		}
		
	}

	/**
	 * 获取验证码
	 * @param loginVo
	 * @return
	 * @throws ClientException
	 * @throws IOException
	 */
	public String getCode(ExpCodeVo expCodeVo) throws ClientException, IOException {
		if (expCodeVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
//		AuthResultVo resultVo = new AuthResultVo();
		String data = expCodeVoConverter.marshal(expCodeVo);
		String result = "";
		try {
			result = sendData(data);
		} catch (ClientException e) {
			throw new ClientException("result:" + result, e);
		} catch (IOException e) {
			throw e;
		}
		if (result == null) {
			return null;
		}
		String isSuccess;
		try {
			ResponseVo responseVo = (ResponseVo) resultConverter.unmarshal(result);
			isSuccess = responseVo.getIsSuccess();
		} catch (Exception ex) {
			throw new ClientException("AuthResultVo 转换异常result:" + result, ex);
		}
		return isSuccess;
	}

	/**
	 * 校验验证码
	 * @param loginVo
	 * @return
	 * @throws ClientException
	 * @throws IOException
	 */
	public String checkCode(ExpCodeVo expCodeVo) throws ClientException, IOException {
		if (expCodeVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
//		AuthResultVo resultVo = new AuthResultVo();
		String data = expCodeVoConverter.marshal(expCodeVo);
		String result = "";
		try {
			result = sendData(data);
		} catch (ClientException e) {
			throw new ClientException("result:" + result, e);
		} catch (IOException e) {
			throw e;
		}
		if (result == null) {
			return null;
		}
		String isSuccess;
		try {
			ResponseVo responseVo = (ResponseVo) resultConverter.unmarshal(result);
			isSuccess = responseVo.getIsSuccess();
		} catch (Exception ex) {
			throw new ClientException("AuthResultVo 转换异常result:" + result, ex);
		}
		return isSuccess;
	}

	/**
	 * 权限检查（系统切换接口）
	 * @param clientVo
	 * @param loginVo
	 * @throws ClientException
	 * @throws IOException
	 */
	public ResponseVo authCheck(ClientVo clientVo, AuthCheckVo authCheckVo) throws ClientException, IOException {
		if (clientVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
		this.clientVo = clientVo;
		return authCheck(authCheckVo);
	}

	/**
	 * 权限检查（系统切换接口）
	 * @param clientVo
	 * @param loginVo
	 * @throws ClientException
	 * @throws IOException
	 */
	public ResponseVo authCheck(AuthCheckVo authCheckVo) throws ClientException, IOException {
		if (authCheckVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
		//AuthResultVo resultVo = new AuthResultVo();
		String data = authCheckConverter.marshal(authCheckVo);
		String result = "";
		try {
			result = sendData(data);
		} catch (ClientException e) {
			throw new ClientException("result:" + result, e);
		} catch (IOException e) {
			throw e;
		}
		if (result == null) {
			return null;
		}
		try {
			ResponseVo responseVo = (ResponseVo) resultConverter.unmarshal(result);
			//resultVo = responseVo.getData();
			return responseVo;
		} catch (Exception ex) {
			throw new ClientException("AuthResultVo 转换异常result:" + result, ex);
		}
	}

	/**
	 * 根据用户查询用户角色
	 * @param userSearchVo
	 * @return
	 * @throws Exception
	 */
	public ResponseVo findUserRole(UserSearchVo userSearchVo) throws Exception {
		if (userSearchVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
		//AuthResultVo resultVo = new AuthResultVo();
		String data = userSearchVoConverter.marshal(userSearchVo);;
		String result = "";
		try {
			result = sendData(data);
		} catch (ClientException e) {
			throw new ClientException("result:" + result, e);
		} catch (IOException e) {
			throw e;
		}
		if (result == null) {
			return null;
		}
		try {
			ResponseVo responseVo = (ResponseVo) resultConverter.unmarshal(result);
			//resultVo = responseVo.getData();
			return responseVo;
		} catch (Exception ex) {
			throw new ClientException("AuthResultVo 转换异常result:" + result, ex);
		}
		
	}

	/**
	 * 查询用户相关信息
	 * @param userSearchVo
	 * @return
	 * @throws Exception
	 */
	public UserInfoVo findUser(UserSearchVo userSearchVo) throws Exception {
		if (userSearchVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
		UserInfoVo resultVo = new UserInfoVo();
		String data = userSearchVoConverter.marshal(userSearchVo);;
		String result = "";
		try {
			result = sendData(data);
		} catch (ClientException e) {
			throw new ClientException("result:" + result, e);
		} catch (IOException e) {
			throw e;
		}
		if (result == null) {
			return null;
		}
		try {
			UserInfoResponseVo responseVo = (UserInfoResponseVo) userInfoVoConverter.unmarshal(result);
			resultVo = responseVo.getData();
		} catch (Exception ex) {
			throw new ClientException("UserInfoResponseVo 转换异常result:" + result, ex);
		}
		return resultVo;
	}
	
	/**
	 * 根据角色名称或编号查询角色
	 * @param userSearchVo
	 * @return
	 * @throws Exception
	 */
	public ResultRoleVo findRole(UserSearchVo userSearchVo) throws Exception {
		if (userSearchVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
		ResultRoleVo resultRoleVo = new ResultRoleVo();
		String data = userSearchVoConverter.marshal(userSearchVo);;
		String result = "";
		try {
			result = sendData(data);
		} catch (ClientException e) {
			throw new ClientException("result:" + result, e);
		} catch (IOException e) {
			throw e;
		}
		if (result == null) {
			return null;
		}
		try {
			UserRoleResponseVo responseVo = (UserRoleResponseVo) userRoleVoConverter.unmarshal(result);
			resultRoleVo = responseVo.getData();
		} catch (Exception ex) {
			throw new ClientException("UserRoleResponseVo 转换异常result:" + result, ex);
		}
		return resultRoleVo;
	}
	
	/**
	 * 根据员工ID或角色编号查询角色关系
	 * @param userSearchVo
	 * @return
	 * @throws Exception
	 */
	public ResultRoleVo findRoleRel(UserSearchVo userSearchVo) throws Exception {
		if (userSearchVo == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
		ResultRoleVo resultRoleVo = new ResultRoleVo();
		String data = userSearchVoConverter.marshal(userSearchVo);;
		String result = "";
		try {
			result = sendData(data);
		} catch (ClientException e) {
			throw new ClientException("result:" + result, e);
		} catch (IOException e) {
			throw e;
		}
		if (result == null) {
			return null;
		}
		try {
			UserRoleResponseVo responseVo = (UserRoleResponseVo) userRoleVoConverter.unmarshal(result);
			resultRoleVo = responseVo.getData();
		} catch (Exception ex) {
			throw new ClientException("UserRoleResponseVo 转换异常result:" + result, ex);
		}
		return resultRoleVo;
	}

	/**
	 * 获取即将发送的参数
	 * @param data
	 * @return
	 * @throws ClientException
	 */
	public Map<String, String> gendParam(String data) throws ClientException {
		try {
			if (clientVo == null || clientVo.getSystemCode() == null || clientVo.getCharset() == null || clientVo.getDataType() == null || clientVo.getServiceName() == null
					|| clientVo.getSignType() == null || clientVo.getSecurityKey() == null) {
				throw new ClientException(ErrorCode.ILLEGAL_PARAM);
			}
			Map<String, String> param = new HashMap<String, String>();
			param.put("systemCode", clientVo.getSystemCode());
			param.put("charset", clientVo.getCharset());
			param.put("dataType", clientVo.getDataType());
			param.put("serviceName", clientVo.getServiceName());
			param.put("data", data);
			param.put("dataSign", SecurityUtil.sign(param, clientVo.getSignType(), clientVo.getSecurityKey(), clientVo.getCharset()));
			return param;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClientException("生成发送的数据失败");
		}
	}

	/**
	 * 进行发送数据
	 * @param data
	 * @return
	 * @throws ClientException
	 * @throws IOException
	 */
	public String sendData(String data) throws ClientException, IOException {
		if (clientVo == null || clientVo.getSendUrl() == null || data == null) {
			throw new ClientException(ErrorCode.ILLEGAL_PARAM);
		}
		System.out.println(data);
		String resultMsg = WebUtils.doPost(clientVo.getSendUrl(), gendParam(data), clientVo.getConnectTimeout(), clientVo.getReadTimeout());
		System.out.println(resultMsg);
		return resultMsg;
	}

}
