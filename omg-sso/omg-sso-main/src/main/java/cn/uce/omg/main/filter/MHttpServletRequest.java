package cn.uce.omg.main.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 
  * <p>Title : MHttpServletRequest</p>
  * <p>Description :参数特殊字符过滤 </p>
  * @author : crj
  * @date : 2017年11月7日下午6:59:22
 */
public class MHttpServletRequest extends HttpServletRequestWrapper {

    public MHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        // 返回值之前 先进行过滤
        return XssShieldUtil.stripXss(super.getParameter(XssShieldUtil.stripXss(name)));
    }

    @Override
    public String[] getParameterValues(String name) {
        // 返回值之前 先进行过滤
        String[] values = super.getParameterValues(XssShieldUtil.stripXss(name));
        if(values != null){
            for (int i = 0; i < values.length; i++) {
                values[i] = XssShieldUtil.stripXss(values[i]);
            }
        }
        return values;
    }

}