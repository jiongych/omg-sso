package cn.uce.omg.main.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
  * <p>Title : XssShieldUtil</p>
  * <p>Description : 处理非法字符</p>
  * @author : crj
  * @date : 2017年11月7日下午7:01:51
 */
public class XssShieldUtil {
	//正则表达式
	private static List<Pattern> patterns = null;
	/**
	 * 
	  * <p>Description :获取过滤url中的特殊字符正则列表 </p>
	  * @author : crj
	  * @date : 2017年11月7日下午7:07:03
	 */
	private static List<Object[]> getXssPatternList() {
		List<Object[]> ret = new ArrayList<Object[]>();
		//添加正则
		ret.add(new Object[] { "<(no)?script[^>]*>.*?</(no)?script>",
				Pattern.CASE_INSENSITIVE });
		ret.add(new Object[] { "eval\\((.*?)\\)",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
		ret.add(new Object[] { "expression\\((.*?)\\)",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
		ret.add(new Object[] { "(javascript:|vbscript:|view-source:)*",
				Pattern.CASE_INSENSITIVE });
		/*ret.add(new Object[] { "<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });*/
		ret.add(new Object[] {
				"(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
		ret.add(new Object[] {
				"<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
		/*ret.add(new Object[] {
				"[`~!@#$^*()+|{}''\\[\\]<>~！#￥……*（）——+|{}【】‘；：”“’。，、？]",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });*/
		ret.add(new Object[] { "javascript|function|alert|location.href|onfocus|prompt|onload|onread|confirm",
				Pattern.CASE_INSENSITIVE });	
		ret.add(new Object[] { "</script>|<script>|&lt;/script&gt;|&lt;script&gt;",
				Pattern.CASE_INSENSITIVE });	
		return ret;
	}
	/**
	 * 
	  * <p>Description : 获取正则</p>
	  * @author : crj
	  * @date : 2017年11月7日下午7:07:15
	 */
	private static List<Pattern> getPatterns() {

		if (patterns == null) {

			List<Pattern> list = new ArrayList<Pattern>();

			String regex = null;
			Integer flag = null;
			int arrLength = 0;

			for (Object[] arr : getXssPatternList()) {
				arrLength = arr.length;
				for (int i = 0; i < arrLength; i++) {
					regex = (String) arr[0];
					flag = (Integer) arr[1];
					list.add(Pattern.compile(regex, flag));
				}
			}

			patterns = list;
		}
		return patterns;
	}
	/**
	 * 
	  * <p>Description : 过滤地址中的特殊字符</p>
	  * @author : crj
	  * @date : 2017年11月7日下午7:09:10
	 */
	public static String stripXss(String value) {
		if (StringUtils.isNotBlank(value)) {

			Matcher matcher = null;

			for (Pattern pattern : getPatterns()) {
				matcher = pattern.matcher(value);
				// 匹配
				if (matcher.find()) {
					// 删除相关字符串
					value = matcher.replaceAll("");
				}
			}
			//value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		}
		
		
		   // 预防SQL盲注
        /*String[] pattern = {"select", "insert", "delete", "from",
                "count\\(", "drop table", "update", "truncate", "asc\\(",
                "mid\\(", "char\\(", "xp_cmdshell", "exec", "master",
                "netlocalgroup administrators", "net user", "or", "and" };
        for (int i = 0; i < pattern.length; i++) {
            value = value.replace(pattern[i].toString(), "");
        }*/
		
		return value;
	}

	public static void main(String[] args) {

		String value = null;
		value = XssShieldUtil
				.stripXss("<script language=text/javascript>alert(document.cookie);</script>");
		System.out.println("type-1: '" + value + "'");

		value = XssShieldUtil
				.stripXss("<script src='' onerror='alert(document.cookie)'></script>");
		System.out.println("type-2: '" + value + "'");

		value = XssShieldUtil.stripXss("</script>");
		System.out.println("type-3: '" + value + "'");

		value = XssShieldUtil.stripXss(" eval(abc);");
		System.out.println("type-4: '" + value + "'");

		value = XssShieldUtil.stripXss(" expression(abc);");
		System.out.println("type-5: '" + value + "'");

		value = XssShieldUtil
				.stripXss("<img src='' onerror='alert(document.cookie);'></img>");
		System.out.println("type-6: '" + value + "'");

		value = XssShieldUtil
				.stripXss("<img src='' onerror='alert(document.cookie);'/>");
		System.out.println("type-7: '" + value + "'");

		value = XssShieldUtil
				.stripXss("<img src='' onerror='alert(document.cookie);'>");
		System.out.println("type-8: '" + value + "'");

		value = XssShieldUtil
				.stripXss("<script language=text/javascript>alert(document.cookie);");
		System.out.println("type-9: '" + value + "'");

		value = XssShieldUtil.stripXss("<script>window.location='url'");
		System.out.println("type-10: '" + value + "'");

		value = XssShieldUtil.stripXss(" onload='alert(\"abc\");");
		System.out.println("type-11: '" + value + "'");

		value = XssShieldUtil.stripXss("<img src=x<!--'<\"-->>");
		System.out.println("type-12: '" + value + "'");

		value = XssShieldUtil.stripXss("<=img onstop=");
		System.out.println("type-13: '" + value + "'");
		
		value = XssShieldUtil
	                .stripXss("<br>select  ***//||&;/*-+ <>$###@%$#@$%^#$^%$&^(&*)*\\''count or %% ..... ,,,, ");
	        System.out.println("type-1: '" + value + "'");

	}
}
