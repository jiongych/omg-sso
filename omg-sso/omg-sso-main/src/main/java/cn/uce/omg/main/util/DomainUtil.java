/** 
 * @项目名称: FSP
 * @文件名称: DomainUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DomainUtil  
 * @Description: DomainUtil  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class DomainUtil {
		private static Log LOG = LogFactory.getLog(DomainUtil.class);
	    private static Pattern pattern;  
	  
	    // 定义正则表达式，域名的根需要自定义，这里不全  
	    private static final String RE_TOP = "[\\w-]+\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)\\b()*";  
	  
	    // 构造函数  
	    static {  
	        pattern = Pattern.compile(RE_TOP , Pattern.CASE_INSENSITIVE);  
	    }  
	  
	    /**
	     * 获取域名
	     * @param url
	     * @return
	     * @author huangting
	     * @date 2017年6月9日 下午12:17:14
	     */
	    public static String getTopDomain(String url) {  
	        String result = url;  
            Matcher matcher = pattern.matcher(url);  
            boolean findResult = matcher.find();  
            if (findResult) {
            	result = matcher.group();  
            } 
	        return result;  
	    }  
	  
	  
	    public static void main(String[] args) {  
	    	DomainUtil obj = new DomainUtil();  
	  
	        // 示例  
	        String url = "http://localhost:8080/uce-web-main/";  
	        String res1 = obj.getTopDomain(url);  
	        System.out.println(url + " ==> " + res1);  
	        
	        // 示例  
	       /* url = "www.uc56.com";  
	        String res6 = obj.getTopDomain(url);  
	        System.out.println(url + " ==> " + res6);  
	  
	        url = "ac.asd.c.sina.com.cn";  
	        String res2 = obj.getTopDomain(url);  
	        System.out.println(url + " ==> " + res2);  
	  
	        url = "whois.chinaz.com/reverse?ddlSearchMode=1";  
	        String res3 = obj.getTopDomain(url);  
	        System.out.println(url + " ==> " + res3);  
	  
	        url = "http://write.blog.csdn.net/";  
	        String res4 = obj.getTopDomain(url);  
	        System.out.println(url + " ==> " + res4);  
	  
	        url = "http://write.test.org.nz/";  
	        String res5 = obj.getTopDomain(url);  
	        System.out.println(url + " ==> " + res5);  */
	    }   
}
