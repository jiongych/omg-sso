/** 
 * @项目名称: FSP
 * @文件名称: HttpUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * http请求工具类
 * @author huangting 2017-03-30
 *
 */
public class HttpUtil {
	private static final int CONNECT_TIMEOUT=1*1000*60;
	private static final int READ_TIMEOUT=1*1000*60;
	
	/**
	 * 
	 * @param url
	 * @param content
	 * @return
	 * @throws IOException
	 * @author huangting
	 * @date 2017年6月9日 下午6:20:55
	 */
	public static String doPostRequest(String url, byte[] content) throws IOException {
		String ctype = " application/json;charset=utf-8";
		return doPostRequest(url, content, ctype);
	}
	
	/**
	 * post请求
	 * @param url
	 * @param content
	 * @param ctype
	 * @return
	 * @throws IOException
	 * @author huangting
	 * @date 2017年6月9日 下午6:20:58
	 */
	public static String doPostRequest(String url, byte[] content, String ctype) throws IOException {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			try {
				conn = getConnection(new URL(url), "POST", ctype);
				conn.setConnectTimeout(CONNECT_TIMEOUT);
				conn.setReadTimeout(READ_TIMEOUT);
			} catch (IOException e) {
				throw e;
			}
			try {
				out = conn.getOutputStream();
				out.write(content);
				rsp = getResponseAsString(conn);
			} catch (IOException e) {
				throw e;
			}
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}
	
	/**
	 * 获取一个HTTP连接
	 * @param url
	 * @param method 方法
	 * @param ctype type
	 * @return
	 * @throws IOException
	 */
	private static HttpURLConnection getConnection(URL url, String method, String ctype) throws IOException {
		HttpURLConnection conn = null;
		if ("https".equals(url.getProtocol())) {
			SSLContext ctx = null;
			try {
				ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
			} catch (Exception e) {
				throw new IOException(e);
			}
			HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
			connHttps.setSSLSocketFactory(ctx.getSocketFactory());
			connHttps.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return false;
				}
			});
			conn = connHttps;
		} else {
			conn = (HttpURLConnection) url.openConnection();
		}

		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", "aop-sdk-java");
		conn.setRequestProperty("Content-Type", ctype);
		return conn;
	}
	
	/**
	 * 获取返回的response转换成string
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
		String charset = getResponseCharset(conn.getContentType());
		InputStream es = conn.getErrorStream();
		if (es == null) {
			return getStreamAsString(conn.getInputStream(), charset);
		}
		String msg = getStreamAsString(es, charset);
		if (StringUtils.isEmpty(msg)) {
			throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
		}
		throw new IOException(msg);
	}

	/**
	 * 根据输入流转换成string
	 * @param stream
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	private static String getStreamAsString(InputStream stream, String charset) throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
			StringWriter writer = new StringWriter();

			char[] chars = new char[256];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}

			String str = writer.toString();
			return str;
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * 获取编码
	 * @param ctype
	 * @return
	 * @author huangting
	 * @date 2017年6月9日 下午6:21:22
	 */
	private static String getResponseCharset(String ctype) {
		String charset = "UTF-8";

		if (!StringUtils.isEmpty(ctype)) {
			String[] params = ctype.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if ((pair.length != 2) || (StringUtils.isEmpty(pair[1])))
						break;
					charset = pair[1].trim();
					break;
				}

			}
		}
		return charset;
	}
	/**
	 * 获取一个HTTP连接
	 * @param 
	 * @param  
	 * @param 
	 * @return
	 * @throws 
	 */
	private static class DefaultTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			// sonar检查需要方法体有代码或者注释
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			// sonar检查需要方法体有代码或者注释
		}
	}
}
