package cn.uce.omg.portal.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

/**
 * 调用jira接口工具类
 *<pre>
 * HttpClientUtilforJira
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年3月13日下午5:32:25
 */
@SuppressWarnings("deprecation")
public class HttpClientUtilforJira {
	
	private static Logger log = Logger.getLogger(HttpClientUtilforJira.class);
	// utf-8字符编码
    public static final String CHARSET_UTF_8 = "utf-8";

    // HTTP内容类型。
    public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";

    // HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";

    // HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";
    
    public static final String JIRA_AUTH = "dWNfZGluZ3RhbGs6NVY5MmFpcW9tQUVGTThLWGJXUEg=";

    // 连接管理器
    private static PoolingHttpClientConnectionManager pool;

    // 请求配置
    private static RequestConfig requestConfig;
    
    static {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register(
                    "http", PlainConnectionSocketFactory.getSocketFactory()).register(
                    "https", sslsf).build();
            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            // 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
            pool.setMaxTotal(200);
            // 设置最大路由
            pool.setDefaultMaxPerRoute(2);
            // 根据默认超时限制初始化requestConfig
            int socketTimeout = 10000;
            int connectTimeout = 10000;
            int connectionRequestTimeout = 10000;
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(
                    connectionRequestTimeout).setSocketTimeout(socketTimeout).setConnectTimeout(
                    connectTimeout).build();
        } catch (Exception e) {
        	log.warn("请求失败", e);
        } 
        

        // 设置请求超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000)
                .setConnectionRequestTimeout(50000).build();
    }
    
    public static CloseableHttpClient getHttpClient() {
        
        CloseableHttpClient httpClient = HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(pool)
                // 设置请求配置
                .setDefaultRequestConfig(requestConfig)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();
        
        return httpClient;
    }

    /**
     * 
     * 方法的描述：
     * <pre>
     * 发送Post请求
     * </pre>
     * @param httpPost
     * @return
     * @return String
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:33:32
     */
    private static String sendHttpPost(HttpPost httpPost){

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 判断响应状态
            if (response.getStatusLine().getStatusCode() >= 300) {
            	responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                /*throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode() + responseContent);*/
                log.warn("HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode() + responseContent);
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode() || HttpStatus.SC_CREATED == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
        	log.warn("请求异常", e);
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
            	log.warn("释放资源失败", e);
            }
        }
        return responseContent;
    }
    
    /**
     * 
     * 方法的描述：
     * <pre>
     * PUT请求
     * </pre>
     * @param httpPut
     * @return
     * @return String
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:33:46
     */
    private static String sendHttpPut(HttpPut httpPut){

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpPut.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPut);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 判断响应状态
            if (response.getStatusLine().getStatusCode() >= 300) {
            	responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                /*throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode() + responseContent);*/
                log.warn("HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode() + responseContent);
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode() || HttpStatus.SC_CREATED == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
        	log.warn("请求异常", e);
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
            	log.warn("释放资源失败", e);
            }
        }
        return responseContent;
    }

    /**
     * 
     * 方法的描述：
     * <pre>
     * 发送Get请求
     * </pre>
     * @param httpGet
     * @return
     * @return String
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:33:57
     */
    private static String sendHttpGet(HttpGet httpGet) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            // 得到响应实例
            HttpEntity entity = response.getEntity();
            // 判断响应状态
            if (response.getStatusLine().getStatusCode() >= 300) {
                /*throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());*/
                log.warn("HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
        	log.warn("请求异常", e);
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
            	log.warn("释放资源失败", e);
            }
        }
        return responseContent;
    }
   
    /**
     * 
     * 方法的描述：
     * <pre>
     * 发送Get请求
     * </pre>
     * @param httpGet
     * @return
     * @return InputStream
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:34:37
     */
    private static InputStream sendHttpForPicGet(HttpGet httpGet) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        InputStream inputStream = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            // 得到响应实例
            HttpEntity entity = response.getEntity();
            
            // 判断响应状态
            if (response.getStatusLine().getStatusCode() >= 300) {
                /*throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());*/
                log.warn("HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            	inputStream = entity.getContent();
            	entity.getContentEncoding();
            	entity.getContentLength();
            	entity.getContentType();
            }

        } catch (Exception e) {
        	log.warn("请求异常", e);
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
            	log.warn("释放资源失败", e);
            }
        }
		return inputStream;
    }
    
    /**
     * 
     * 方法的描述：
     * <pre>
     * 发送 post请求
     * </pre>
     * @param httpUrl
     * @return
     * @return String
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:34:46
     */
    public static String sendHttpPost(String httpUrl){
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        return sendHttpPost(httpPost);
    }

    /**
     * 
     * 方法的描述：
     * <pre>
     * 发送 get请求
     * </pre>
     * @param httpUrl
     * @return
     * @return String
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:34:55
     */
    public String sendHttpGet(String httpUrl) {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        httpGet.setHeader("Authorization", "Basic " + JIRA_AUTH);
        return sendHttpGet(httpGet);
    }
  
    /**
     * 
     * 方法的描述：
     * <pre>
     * 发送 get请求
     * </pre>
     * @param httpUrl
     * @return
     * @return InputStream
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:35:07
     */
    public InputStream sendHttpForPicGet(String httpUrl) {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        httpGet.setHeader("Authorization", "Basic " + JIRA_AUTH);
        return sendHttpForPicGet(httpGet);
    }

    /**
     * 发送 post请求（带文件）
     * 
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     * @param fileLists
     *            附件
     * @throws Exception 
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        if (maps != null) {
            for (String key : maps.keySet()) {
                meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
            }
        }
        if (fileLists != null) {
            for (File file : fileLists) {
                FileBody fileBody = new FileBody(file);
                meBuilder.addPart("files", fileBody);
            }
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendHttpPost(httpPost);
    }

    /**
     * 
     * 方法的描述：
     * <pre>
     * 发送 post请求
     * </pre>
     * @param httpUrl
     * @param params
     * @return
     * @return String
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:35:18
     */
    public String sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        httpPost.setHeader("Authorization", "Basic " + JIRA_AUTH);
        httpPost.setHeader("Content-type", "application/json");
        try {
            // 设置参数
            if (params != null && params.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(params, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_FORM_URL);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
        	log.warn("请求异常", e);
        }
        return sendHttpPost(httpPost);
    }
    
    /**
     * 
     * 方法的描述：
     * <pre>
     * PUT请求
     * </pre>
     * @param httpUrl
     * @param params
     * @return
     * @return String
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:35:31
     */
    public String sendHttpPut(String httpUrl, String params) {
        HttpPut httpPut = new HttpPut(httpUrl);// 创建httpPost
        httpPut.setHeader("Authorization", "Basic " + JIRA_AUTH);
        httpPut.setHeader("Content-type", "application/json");
        try {
            // 设置参数
            if (params != null && params.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(params, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_FORM_URL);
                httpPut.setEntity(stringEntity);
            }
        } catch (Exception e) {
        	log.warn("请求异常", e);
        }
        return sendHttpPut(httpPut);
    }
    
    /**
     * 
     * 方法的描述：
     * <pre>
     * POST请求
     * </pre>
     * @param httpUrl
     * @param maps
     * @return
     * @return String
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:35:43
     */
    public String sendHttpPost(String httpUrl, Map<String, Object> maps){
        String parem = convertJSONParamter(maps);
        return sendHttpPost(httpUrl, parem);
    }
    
    public String sendHttpPost(String httpUrl, List<Object> list){
        String parem = convertJSONParamter(list);
        return sendHttpPost(httpUrl, parem);
    }
    
    public String sendHttpPost(String httpUrl, Object object){
        String parem = convertJSONParamter(object);
        return sendHttpPost(httpUrl, parem);
    }
    
    public String sendHttpPut(String httpUrl, Object object){
        String parem = convertJSONParamter(object);
        return sendHttpPut(httpUrl, parem);
    }
    /**
     * 发送 post请求 发送json数据
     * 
     * @param httpUrl
     *            地址
     * @param paramsJson
     *            参数(格式 json)
     * @throws Exception 
     * 
     */
    public static String sendHttpPostJson(String httpUrl, String paramsJson) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            if (paramsJson != null && paramsJson.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_JSON_URL);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
        	log.warn("请求异常", e);
        }
        return sendHttpPost(httpPost);
    }
    
    /**
     * 发送 post请求 发送xml数据
     * 
     * @param httpUrl   地址
     * @param paramsXml  参数(格式 Xml)
     * @throws Exception 
     * 
     */
    public static String sendHttpPostXml(String httpUrl, String paramsXml, String jiraSessionId) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            if (paramsXml != null && paramsXml.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsXml, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_TEXT_HTML);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
        	log.warn("请求异常", e);
        }
        return sendHttpPost(httpPost);
    }
    
    public static String convertJSONParamter(Map<String, Object> parameterMap) {
    	return JSON.toJSONString(parameterMap);
    }
    
    public static String convertJSONParamter(Object object) {
    	return JSON.toJSONString(object);
   }
    
    public static String convertJSONParamter(List<Object> parameterList) {
    	return JSON.toJSONString(parameterList);
    }

    
    
    /**
     * 将map集合的键值对转化成：key1=value1&key2=value2 的形式
     * @param parameterMap
     *            需要转化的键值对集合
     * @return 字符串
     * @描述:
     * @方法名: convertStringParamter
     * @param parameterMap
     * @return
     * @返回类型 String
     * @创建人 Ji Yongchao
     * @修改备注
     * @since
     * @throws
     */
    public static String convertStringParamter(Map<String, Object> parameterMap) {
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator<String> iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        return parameterBuffer.toString();
    }

    /**
     * 发送 post请求（带文件）
     * 方法的描述：
     * <pre>
     * 
     * </pre>
     * @param httpUrl
     * @param maps
     * @param imageBase:base64文件
     * @param imageBaseName:文件名
     * @return void
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年4月17日上午10:32:45
     */
    public void sendJiraHttpPost(String httpUrl, Map<String, String> maps, String[] imageBase, String[] imageBaseName) {
          final HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
          MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
          meBuilder.setCharset(Charset.forName("utf-8"));
          meBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
          final List<File> fileList = Collections.synchronizedList(new ArrayList<File>());
          for (int i = 0; i < imageBase.length; i++) {
        	  if (null != imageBase[i]) {
        		  String imageBaseO = imageBase[i].substring(imageBase[i].indexOf(",") + 1);
        		  File file = BaseFile(imageBaseO, imageBaseName[i]);
            	  fileList.add(file);
                  FileBody fileBody = new FileBody(file);
                  meBuilder.addPart ("file", fileBody);
        	  }
          }
          
          HttpEntity reqEntity = meBuilder.build();
          httpPost.addHeader("Authorization", "Basic " + JIRA_AUTH);
          httpPost.addHeader("X-Atlassian-Token","nocheck");
          httpPost.setEntity(reqEntity);
          
          ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
          cachedThreadPool.execute(new Runnable() {
        	Lock lock = new ReentrantLock();
			@Override
			public void run() {
				
				boolean lockFlag = false;
				try {
					
					sendHttpPost(httpPost);
					for(File file : fileList) {
			        	  boolean delFlag = file.delete();
			        	  if(delFlag) {
			        		  lockFlag = lock.tryLock();
			        	  }
			        }
				} catch (Exception e) {
					log.warn("文件上传失败", e);
				} finally {
					if(lockFlag) {
						lock.unlock();
					}
				}
			}
          });
      }
    
    public static String uploadFileItsmHttpPost(String httpUrl,String id,String userNo,String imageBase, String imageBaseName) {
        final HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        meBuilder.setCharset(Charset.forName("utf-8"));
        meBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (null != imageBase) {
    		  String imageBaseO = imageBase.substring(imageBase.indexOf(",") + 1);
    		  File file = BaseFile(imageBaseO, imageBaseName);
              FileBody fileBody = new FileBody(file);
              meBuilder.addPart ("file", fileBody);
              meBuilder.addPart("fileName", new StringBody((String) imageBaseName, ContentType.create("text/plain",Consts.UTF_8)));
    	  }
        meBuilder.addPart("id",new StringBody((String) id, ContentType.create("text/plain",Consts.UTF_8)));
        meBuilder.addPart("userNo", new StringBody((String) userNo, ContentType.create("text/plain",Consts.UTF_8)));
        
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        httpPost.setHeader("channel", "xqk");
        meBuilder.setCharset(Charset.forName("utf-8"));
        return sendHttpPost(httpPost);
    }
    
    /**
     * 
     * 方法的描述：
     * <pre>
     * 发送 post请求（带文件）
     * </pre>
     * @param httpUrl
     * @param maps
     * @param files
     * @return void
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:36:56
     */
    public void sendJiraHttpPost(String httpUrl, Map<String, String> maps,MultipartFile[] files) {
          final HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
          MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
          meBuilder.setCharset(Charset.forName("utf-8"));
          meBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
          final List<File> fileList = Collections.synchronizedList(new ArrayList<File>());
          for (MultipartFile multipartFile : files) {
        	  if (null != multipartFile) {
        		  File file = BaseFile(multipartFile);
            	  fileList.add(file);
                  FileBody fileBody = new FileBody(file);
                  meBuilder.addPart ("file", fileBody);
        	  }
          }
          
          HttpEntity reqEntity = meBuilder.build();
          httpPost.addHeader("Authorization", "Basic " + JIRA_AUTH);
          httpPost.addHeader("X-Atlassian-Token","nocheck");
          httpPost.setEntity(reqEntity);
          
          ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
          cachedThreadPool.execute(new Runnable() {
        	Lock lock = new ReentrantLock();
			@Override
			public void run() {
				
				boolean lockFlag = false;
				try {
					
					sendHttpPost(httpPost);
					for(File file : fileList) {
			        	  boolean delFlag = file.delete();
			        	  if(delFlag) {
			        		  lockFlag = lock.tryLock();
			        	  }
			        }
				} catch (Exception e) {
					log.warn("文件上传失败", e);
				} finally {
					if(lockFlag) {
						lock.unlock();
					}
				}
			}
          });
      }
    
    
    /**
     * 
     * 方法的描述：
     * <pre>
     * MultipartFile转file
     * </pre>
     * @param file
     * @return
     * @return File
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:37:06
     */
    public static File BaseFile(String imageBase, String imageBaseName) {
    	File newfile = new File(imageBaseName.substring(0, imageBaseName.indexOf("."))+".jpg");
    	InputStream inputStream = null;
    	try {
    		byte[] bytes = Base64.decodeBase64(imageBase);
    		inputStream = new ByteArrayInputStream(bytes); 
    		FileUtils.copyInputStreamToFile(inputStream, newfile);
    	} catch (IOException e) {
    		log.warn("文件转换失败", e);
    	} finally {
    		try {
    			if(inputStream != null) {
    				inputStream.close();
    			}
			} catch (IOException e) {
				log.warn("文件流关闭异常", e);
			}
		}
    	return newfile;
    }

    /**
     * 
     * 方法的描述：
     * <pre>
     * MultipartFile转file
     * </pre>
     * @param file
     * @return
     * @return File
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年3月13日下午5:37:06
     */
    public static File BaseFile(MultipartFile file) {
    	String saveFileName = file.getOriginalFilename();
    	
    	File newfile = new File(saveFileName.substring(0, saveFileName.indexOf("."))+".jpg");
    	InputStream inputStream = null;
    	try {
    		inputStream = file.getInputStream();
    		FileUtils.copyInputStreamToFile(inputStream, newfile);
    	} catch (IOException e) {
    		log.warn("文件转换失败", e);
    	} finally {
    		try {
    			if(inputStream != null) {
    				inputStream.close();
    			}
			} catch (IOException e) {
				log.warn("文件流关闭异常", e);
			}
		}
    	return newfile;
    }
    
    
    
    public static byte[] conver2HexToByte(String hex2Str)  
    {  
        String [] temp = hex2Str.split(",");  
        byte [] b = new byte[temp.length];  
        for(int i = 0;i<b.length;i++)  
        {  
            b[i] = Long.valueOf(temp[i], 2).byteValue();  
        }  
        return b;  
    }
    
    /**
     * 获得服务器端数据，以InputStream形式返回
     * 
     * @return
     * @throws IOException
     */
    public InputStream getInputStream(String url) throws IOException {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        
        try {
            URL url1 = new URL(url);
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            // 设置连接网络的超时时间
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
           // httpURLConnection.setRequestProperty("Authorization", "Basic " + JIRA_AUTH);
            // 设置本次http请求使用get方式请求
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                // 从服务器获得一个输入流
                inputStream = httpURLConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
        	log.warn("请求异常", e);
        }

        return inputStream;
    }
    
    public InputStream getItsmInputStream(String url) throws IOException {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        
        try {
            URL url1 = new URL(url);
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            // 设置连接网络的超时时间
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Authorization", "Basic " + JIRA_AUTH);
            // 设置本次http请求使用get方式请求
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                // 从服务器获得一个输入流
                inputStream = httpURLConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
        	log.warn("请求异常", e);
        }

        return inputStream;
    }
    
    public static String sendItsmHttpPostJson(String httpUrl, String paramsJson) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        httpPost.setHeader("channel", "xqk");
        httpPost.setHeader("content-type", "application/json");
        try {
            // 设置参数
            if (paramsJson != null && paramsJson.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
        	log.warn("请求异常", e);
        }
        return sendHttpPost(httpPost);
    }
}
