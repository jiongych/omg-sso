package uce.sso.sdk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * web连接工具类
 * @author tanchong
 *
 */
public abstract class WebUtils {

	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String METHOD_POST = "POST";
	private static final String METHOD_GET = "GET";

	/**
	 * 发送post请求
	 * @param url
	 * @param params 参数
	 * @param connectTimeout 连接超时时间（毫秒）
	 * @param readTimeout 读取超时时间（毫秒）
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params, int connectTimeout, int readTimeout) throws IOException {
		return doPost(url, params, "UTF-8", connectTimeout, readTimeout);
	}

	/**
	 * 发送post请求
	 * @param url
	 * @param params 参数
	 * @param charset 编码格式
	 * @param connectTimeout 连接超时时间（毫秒）
	 * @param readTimeout 读取超时时间（毫秒）
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout) throws IOException {
		String ctype = "application/x-www-form-urlencoded;charset=" + charset;
		String query = buildQuery(params, charset);
		byte[] content = new byte[0];
		if (query != null) {
			content = query.getBytes(charset);
		}
		return doPost(url, ctype, content, connectTimeout, readTimeout);
	}

	/**
	 * 发送post请求
	 * @param url
	 * @param ctype c类型
	 * @param content 内容
	 * @param connectTimeout 连接超时时间（毫秒）
	 * @param readTimeout 读取超时时间（毫秒）
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout) throws IOException {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			try {
				conn = getConnection(new URL(url), "POST", ctype);
				conn.setConnectTimeout(connectTimeout);
				conn.setReadTimeout(readTimeout);
			} catch (IOException e) {
				Map<String, String> map = getParamsFromUrl(url);
//				AlipayLogger.logCommError(e, url, (String) map.get("app_key"), (String) map.get("method"), content);
				throw e;
			}
			try {
				out = conn.getOutputStream();
				out.write(content);
				rsp = getResponseAsString(conn);
			} catch (IOException e) {
				Map<String, String> map = getParamsFromUrl(url);
//				AlipayLogger.logCommError(e, conn, (String) map.get("app_key"), (String) map.get("method"), content);
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

//	public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, int connectTimeout, int readTimeout) throws IOException {
//		if ((fileParams == null) || (fileParams.isEmpty())) {
//			return doPost(url, params, "UTF-8", connectTimeout, readTimeout);
//		}
//		return doPost(url, params, fileParams, "UTF-8", connectTimeout, readTimeout);
//	}
//
//	public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset, int connectTimeout, int readTimeout) throws IOException {
//		if ((fileParams == null) || (fileParams.isEmpty())) {
//			return doPost(url, params, charset, connectTimeout, readTimeout);
//		}
//
//		String boundary = System.currentTimeMillis() + "";
//		HttpURLConnection conn = null;
//		OutputStream out = null;
//		String rsp = null;
//		try {
//			try {
//				String ctype = "multipart/form-data;boundary=" + boundary + ";charset=" + charset;
//				conn = getConnection(new URL(url), "POST", ctype);
//				conn.setConnectTimeout(connectTimeout);
//				conn.setReadTimeout(readTimeout);
//			} catch (IOException e) {
//				Map<String, String> map = getParamsFromUrl(url);
////				AlipayLogger.logCommError(e, url, (String) map.get("app_key"), (String) map.get("method"), params);
//				throw e;
//			}
//			try {
//				out = conn.getOutputStream();
//
//				byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(charset);
//
//				Set<Entry<String, String>> textEntrySet = params.entrySet();
//				for (Map.Entry<String, String> textEntry : textEntrySet) {
//					byte[] textBytes = getTextEntry((String) textEntry.getKey(), (String) textEntry.getValue(), charset);
//
//					out.write(entryBoundaryBytes);
//					out.write(textBytes);
//				}
//
//				Set<Entry<String, FileItem>> fileEntrySet = fileParams.entrySet();
//				for (Entry<String, FileItem> fileEntry : fileEntrySet) {
//					FileItem fileItem = (FileItem) fileEntry.getValue();
//					byte[] fileBytes = getFileEntry((String) fileEntry.getKey(), fileItem.getFileName(), fileItem.getMimeType(), charset);
//
//					out.write(entryBoundaryBytes);
//					out.write(fileBytes);
//					out.write(fileItem.getContent());
//				}
//
//				byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
//				out.write(endBoundaryBytes);
//				rsp = getResponseAsString(conn);
//			} catch (IOException e) {
//				Map<String, String> map = getParamsFromUrl(url);
////				AlipayLogger.logCommError(e, conn, (String) map.get("app_key"), (String) map.get("method"), params);
//				throw e;
//			}
//		} finally {
//			if (out != null) {
//				out.close();
//			}
//			if (conn != null) {
//				conn.disconnect();
//			}
//		}
//
//		return rsp;
//	}

	private static byte[] getTextEntry(String fieldName, String fieldValue, String charset) throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
		entry.append(fieldValue);
		return entry.toString().getBytes(charset);
	}

	private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset) throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\";filename=\"");
		entry.append(fileName);
		entry.append("\"\r\nContent-Type:");
		entry.append(mimeType);
		entry.append("\r\n\r\n");
		return entry.toString().getBytes(charset);
	}

	/**
	 * 发送GET请求
	 * @param url
	 * @param params 参数
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params) throws IOException {
		return doGet(url, params, "UTF-8");
	}

	/**
	 * 发送GET请求
	 * @param url
	 * @param params 参数
	 * @param charset 编码格式
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params, String charset) throws IOException {
		HttpURLConnection conn = null;
		String rsp = null;
		try {
			String ctype = "application/x-www-form-urlencoded;charset=" + charset;
			String query = buildQuery(params, charset);
			try {
				conn = getConnection(buildGetUrl(url, query), "GET", ctype);
			} catch (IOException e) {
				Map<String, String> map = getParamsFromUrl(url);
//				AlipayLogger.logCommError(e, url, (String) map.get("app_key"), (String) map.get("method"), params);
				throw e;
			}
			try {
				rsp = getResponseAsString(conn);
			} catch (IOException e) {
				Map<String, String> map = getParamsFromUrl(url);
//				AlipayLogger.logCommError(e, conn, (String) map.get("app_key"), (String) map.get("method"), params);
				throw e;
			}
		} finally {
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
	 * 根据参数，拼接GET方法
	 * @param strUrl
	 * @param query
	 * @return
	 * @throws IOException
	 */
	private static URL buildGetUrl(String strUrl, String query) throws IOException {
		URL url = new URL(strUrl);
		if (StringUtils.isEmpty(query)) {
			return url;
		}

		if (StringUtils.isEmpty(url.getQuery())) {
			if (strUrl.endsWith("?"))
				strUrl = strUrl + query;
			else {
				strUrl = strUrl + "?" + query;
			}
		} else if (strUrl.endsWith("&")) {
			strUrl = strUrl + query;
		} else {
			strUrl = strUrl + "&" + query;
		}

		return new URL(strUrl);
	}

	/**
	 * 根据参数，拼接条件
	 * @param params
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String buildQuery(Map<String, String> params, String charset) throws IOException {
		if ((params == null) || (params.isEmpty())) {
			return null;
		}

		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Map.Entry<String, String> entry : entries) {
			String name = (String) entry.getKey();
			String value = (String) entry.getValue();

			if (StringUtils.areNotEmpty(new String[] { name, value })) {
				if (hasParam)
					query.append("&");
				else {
					hasParam = true;
				}

				query.append(name).append("=").append(URLEncoder.encode(value, charset));
			}
		}

		return query.toString();
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
			if (stream != null)
				stream.close();
		}
	}

	/**
	 * 获取编码
	 * @param ctype
	 * @return
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

	public static String decode(String value) {
		return decode(value, "UTF-8");
	}

	public static String encode(String value) {
		return encode(value, "UTF-8");
	}

	public static String decode(String value, String charset) {
		String result = null;
		if (!StringUtils.isEmpty(value)) {
			try {
				result = URLDecoder.decode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	public static String encode(String value, String charset) {
		String result = null;
		if (!StringUtils.isEmpty(value)) {
			try {
				result = URLEncoder.encode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	private static Map<String, String> getParamsFromUrl(String url) {
		Map<String, String> map = null;
		if ((url != null) && (url.indexOf('?') != -1)) {
			map = splitUrlQuery(url.substring(url.indexOf('?') + 1));
		}
		if (map == null) {
			map = new HashMap<String, String>();
		}
		return map;
	}

	public static Map<String, String> splitUrlQuery(String query) {
		Map<String, String> result = new HashMap<String, String>();

		String[] pairs = query.split("&");
		if ((pairs != null) && (pairs.length > 0)) {
			for (String pair : pairs) {
				String[] param = pair.split("=", 2);
				if ((param != null) && (param.length == 2)) {
					result.put(param[0], param[1]);
				}
			}
		}

		return result;
	}

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
