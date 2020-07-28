package cn.uce.omg.portal.util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkTimeoutException;
import org.apache.http.ParseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import cn.uce.base.exception.BusinessException;
import cn.uce.core.mq.api.exception.MqException;
import cn.uce.omg.portal.exception.ErrorCode;
import cn.uce.omg.portal.vo.InvokeExceptionInfoVo;
import cn.uce.utils.StringUtil;

import com.alibaba.dubbo.rpc.RpcException;

public class ExceptionUtil {

	public static InvokeExceptionInfoVo getException(Exception e) {
		InvokeExceptionInfoVo invokeExceptionInfoVo = new InvokeExceptionInfoVo();
		invokeExceptionInfoVo.setExceptionMsg(e.getMessage());
		//http连接异常
		if (e instanceof ConnectTimeoutException ||
				e instanceof ConnectionPoolTimeoutException ||
				e instanceof IOException 
				|| e instanceof HttpHostConnectException
				|| e instanceof KeyManagementException
				|| e instanceof NoSuchAlgorithmException
				|| e instanceof ParseException 
				|| e instanceof MqException
				|| e instanceof SocketTimeoutException) {
			invokeExceptionInfoVo.setExceptionCode(ErrorCode.CONNECTION_ERROR.code());
			invokeExceptionInfoVo.setRetry(true);
		//调用dubbo服务异常
		} else if (e instanceof RpcException || e instanceof IllegalStateException || e instanceof ZkTimeoutException || e instanceof ZkException) {
			invokeExceptionInfoVo.setExceptionCode(ErrorCode.DUBBO_SERVICE_ERROR.code());
			invokeExceptionInfoVo.setRetry(true);
		} else if (e instanceof BusinessException) {
			String code = ((BusinessException) e).getCode();
			if (StringUtil.isBlank(code)) {
				code = ErrorCode.SYSTEM_ERROR.code();
			}
			invokeExceptionInfoVo.setExceptionCode(code);
			invokeExceptionInfoVo.setRetry(false);
		} else if (
				e instanceof NumberFormatException ||
				e instanceof NullPointerException ||
				e instanceof ClassNotFoundException) {
			invokeExceptionInfoVo.setExceptionCode(ErrorCode.SYSTEM_ERROR.code());
			invokeExceptionInfoVo.setRetry(false);
		} else {
			invokeExceptionInfoVo.setExceptionCode(ErrorCode.SYSTEM_ERROR.code());
			invokeExceptionInfoVo.setRetry(false);
		}
		return invokeExceptionInfoVo;
	}
}
