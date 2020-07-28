package cn.uce.omg.main.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.constant.GatewayConstants;
import cn.uce.omg.sso.util.SignUtils;
import cn.uce.omg.sso.vo.LoginResultVo;
/**
 * 
  * <p>Title : ImageCodeUtils</p>
  * <p>Description : 图片验证吗</p>
  * @author : crj
  * @date : 2017年10月27日上午10:05:30
 */
public class ImageCodeUtils {
	/** 打印日志 */
	protected Log LOG = LogFactory.getLog(ImageCodeUtils.class);

	/** 验证码缓存 */
	//private HashRedisWithFieldExpireCache<ImageCodeVo> imageCodeCache;
	/** 验证码超时时间：毫秒，默认10分钟 */
	private Integer imageCodeTimeout = 60000;
	/** 锁管理对象 */
	//private LockManager lockManager;
	/**
	 * 进行加密的key
	 */
	private String md5key;
	/**
	 * 
	  * <p>Description : 获取验证码并保存到redis中</p>
	  * @author : crj
	 * @throws IOException 
	  * @date : 2017年10月27日上午11:06:12
	 */
/*	public void getImageCodeAndSaveRedis(HttpServletResponse response,
			String flag,String userName,String ipAddr) throws IOException{
		
		//判断如果用户为defaultValue则设置为空字符串
		userName = "defaultValue".equals(userName) ? "" : userName;
		*//**
		 * jgp格式验证码
		 * 宽，高，位数。
		 *//*
		CaptchaUtil captchaUtil = new SpecCaptchaUtil(95, 32, 4);
		//将图片验证码输出
		captchaUtil.out(response.getOutputStream());
		//保存到redis中的key
		String key = "";
		//保存到redis中的value
		ImageCodeVo imageCodeVo = new ImageCodeVo();
		//设置值
		imageCodeVo.setCode(captchaUtil.text().toLowerCase());
		long nowDate = System.currentTimeMillis();
		//设置值
		imageCodeVo.setExpireTime(nowDate+(imageCodeTimeout*1000l));
		//设置值
		imageCodeVo.setUserName(userName);
		//设置值
		imageCodeVo.setSendTime(new Date());
		//判断是从什么页面过来的
		if(StringUtils.equals(flag,"true")){
			//获取KEY
			key = userName+ipAddr+SsoMainConstant.IMAGE_CODE_TYPE_PHONE;
			//设置值
			imageCodeVo.setCodeType(SsoMainConstant.IMAGE_CODE_TYPE_PHONE);
		}else{
			//设置值
			key = userName+ipAddr+SsoMainConstant.IMAGE_CODE_TYPE_CODE;
			//设置值
			imageCodeVo.setCodeType(SsoMainConstant.IMAGE_CODE_TYPE_CODE);
		}
		increment(key, imageCodeVo);
	}*/
	/**
	 * 
	  * <p>Description :获取验证码 </p>
	  * @author : crj
	  * @date : 2017年10月31日上午10:02:00
	 */
	public Map<String, Object> getImageCode(String ipAdd) throws IOException{

		Map<String, Object> map = new HashMap<String,Object>();
		/**
		 * jgp格式验证码
		 * 宽，高，位数。
		 */
		CaptchaUtil captchaUtil = new SpecCaptchaUtil(95, 32, 4);
		//将图片验证码输出
		String outToString = captchaUtil.outToString();
        if(outToString != null && !"".equals(outToString)){
        	long currentTimeMillis = System.currentTimeMillis();
        	map.put("resultCode", "success");
        	map.put("image", outToString);
        	map.put("t", currentTimeMillis);
        	String code = ipAdd+captchaUtil.text().toLowerCase()+md5key+currentTimeMillis;
        	String md5 = SignUtils.md5(code, GatewayConstants.DEFAULT_CHARSET);
        	map.put("sig", md5);
        }else{
        	map.put("resultCode", "error");
        	map.put("resultMessage", "获取验证码失败");
        }
        return map;
	}
	
	/**
	 * 
	  * <p>Description : 验证图片验证码是否正确</p>
	  * @author : crj
	 * @throws UnsupportedEncodingException 
	  * @date : 2017年10月27日下午12:38:57
	 */
	public LoginResultVo checkImageCode(String imageCode,Integer errorCount,
			String sig,String ipAdd,long t){
		//返回验证的信息
		LoginResultVo loginResultVo = new LoginResultVo();
		//判断验证码是为空
		if(StringUtils.isBlank(imageCode)){
			//如果验证码为空返回消息
			loginResultVo.setCheckCode(ErrorCode.CHECK_CODE_ERROR_CODE);
			//设置输入密码失败的次数
			loginResultVo.setErrorCount(errorCount);
			//错误消息
			loginResultVo.setErrorMsg("请输入图片验证码 ...");
		}else{
			//获取当前时间
			long currentTimeMillis = System.currentTimeMillis();
			//判断图片验证码是否过期
			if(currentTimeMillis - t > imageCodeTimeout){
				loginResultVo.setCheckCode(ErrorCode.CHECK_CODE_ERROR_CODE);
				loginResultVo.setErrorCount(errorCount);
				loginResultVo.setErrorMsg("图片验证码失效，请重新输入 ...");
			}else{
				//获取redi中缓存的图片验证码的key
				String code = ipAdd+imageCode.trim().toLowerCase()+md5key+t;
				String md5 = "";
				try {
					md5 = SignUtils.md5(code, GatewayConstants.DEFAULT_CHARSET);
				} catch (UnsupportedEncodingException e) {
					LOG.error("比较验证码异常",e);
				}
				//验证码为空，返回消息
				if(sig == null){
					loginResultVo.setCheckCode(ErrorCode.CHECK_CODE_ERROR_CODE);
					loginResultVo.setErrorCount(errorCount);
					loginResultVo.setErrorMsg("图片验证码失效，请重新输入 ...");
				}else{
					//判断验证码是否正确
					if(md5.equals(sig)){
						loginResultVo.setCheckCode(ErrorCode.CHECK_CODE_SUCCESS_CODE);
					}else{
						loginResultVo.setCheckCode(ErrorCode.CHECK_CODE_ERROR_CODE);
						loginResultVo.setErrorCount(errorCount);
						loginResultVo.setErrorMsg("图片验证码错误,请重新输入 ...");
					}
				}
			}
		}
		return loginResultVo;
	}
	
	/**
	 *  
		 * <pre>
		 *		描述:通过管理锁的方式控制并发操作
		 * </pre>
		 * @param key 
		 * @param codeVo
		 * @author yangjun
	 * @throws GatewayException 
		 * @date 2017-09-14
	 */
/*	public void increment(String key, ImageCodeVo codeVo){
	    try {
	      lockManager.getLock(SsoMainConstant.IMAGE_CODE_TYPE, key, 5000);
	    } catch (TimeoutException e) {
	    	LOG.error("LockManager: "+e.getMessage(),e);      
	    }
	    try {
	    	imageCodeCache.put(key, codeVo);
	    } finally {
	      lockManager.releaseLock(SsoMainConstant.IMAGE_CODE_TYPE, key);
	    }
	  }*/

	
	/**
	 * 
	  * <p>Description : 验证图片验证码是否正确</p>
	  * @author : crj
	  * @date : 2017年10月27日下午12:38:57
	 */
/*	public LoginResultVo checkImageCode(String imageCode,Integer errorCount,
			String imageCodeType,String userName,String ipAddr){
		//返回验证的信息
		LoginResultVo loginResultVo = new LoginResultVo();
		//判断验证码是为空
		if(StringUtils.isBlank(imageCode)){
			//如果验证码为空返回消息
			loginResultVo.setCheckCode(ErrorCode.CHECK_CODE_ERROR_CODE);
			//设置输入密码失败的次数
			loginResultVo.setErrorCount(errorCount);
			//错误消息
			loginResultVo.setErrorMsg("请输入图片验证码 ...");
		}else{
			//获取redi中缓存的图片验证码的key
			String key = "";
			if(SsoMainConstant.IMAGE_CODE_TYPE_CODE.equals(imageCodeType)){
				 key = userName+ipAddr+SsoMainConstant.IMAGE_CODE_TYPE_CODE;
			}else if(SsoMainConstant.IMAGE_CODE_TYPE_PHONE.equals(imageCodeType)){
				 key = userName+ipAddr+SsoMainConstant.IMAGE_CODE_TYPE_PHONE;
			}
			//从redis中获取图片验证码
			ImageCodeVo imageCodeVo = imageCodeCache.get(key);
			//验证码为空，返回消息
			if(imageCodeVo == null){
				loginResultVo.setCheckCode(ErrorCode.CHECK_CODE_ERROR_CODE);
				loginResultVo.setErrorCount(errorCount);
				loginResultVo.setErrorMsg("图片验证码失效，请重新输入 ...");
			}else{
				//判断验证码是否正确
				if(imageCode.equals(imageCodeVo.getCode())){
					loginResultVo.setCheckCode(ErrorCode.CHECK_CODE_SUCCESS_CODE);
					imageCodeCache.delete(key);
					if(SsoMainConstant.IMAGE_CODE_TYPE_CODE.equals(imageCodeType)){
						 key = ipAddr+SsoMainConstant.IMAGE_CODE_TYPE_CODE;
					}else if(SsoMainConstant.IMAGE_CODE_TYPE_PHONE.equals(imageCodeType)){
						 key = ipAddr+SsoMainConstant.IMAGE_CODE_TYPE_PHONE;
					}
					imageCodeCache.delete(key);
				}else{
					loginResultVo.setCheckCode(ErrorCode.CHECK_CODE_ERROR_CODE);
					loginResultVo.setErrorCount(errorCount);
					loginResultVo.setErrorMsg("图片验证码错误,请重新输入 ...");
				}
			}
		}
		return loginResultVo;
	}*/

	public void setMd5key(String md5key) {
		this.md5key = md5key;
	}
	
	
	/**
	 * 
	  * <p>Description : 设置缓存</p>
	  * @author : crj
	  * @date : 2017年10月27日上午10:07:44
	 */
/*	public void setImageCodeCache(
			HashRedisWithFieldExpireCache<ImageCodeVo> imageCodeCache) {
		this.imageCodeCache = imageCodeCache;
	}*/
	/**
	 * 
	  * <p>Description : 设置超时时间</p>
	  * @author : crj
	  * @date : 2017年10月27日上午10:08:02
	 */
	public void setImageCodeTimeout(Integer imageCodeTimeout) {
		this.imageCodeTimeout = imageCodeTimeout;
	}
	/**
	 * 
	  * <p>Description : 设置锁对象</p>
	  * @author : crj
	  * @date : 2017年10月27日下午2:24:56
	 */
/*	public void setLockManager(LockManager lockManager) {
		this.lockManager = lockManager;
	}*/
	
}
