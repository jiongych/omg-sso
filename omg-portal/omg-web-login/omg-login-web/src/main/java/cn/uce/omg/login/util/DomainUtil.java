package cn.uce.omg.login.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: (域名工具类) 
 * @author XJ
 * @date 2017年7月29日 下午1:33:06
 */
public class DomainUtil {
	    private static Pattern pattern;  
	  
	    // 定义正则表达式，域名的根需要自定义，这里不全  
	    private static final String RE_TOP = "[\\w-]+\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)\\b()*";  
	  
	    // 构造函数  
	    static {  
	        pattern = Pattern.compile(RE_TOP , Pattern.CASE_INSENSITIVE);  
	    }  
	  
	    public static String getTopDomain(String url) {  
	        String result = url;  
            Matcher matcher = pattern.matcher(url);  
            matcher.find();  
            result = matcher.group();  
	        return result;  
	    }  
	   
}
