package cn.uce.omg.main.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 
  * <p>Title : IllegalCharacterFilter</p>
  * <p>Description :非法字符过滤器，用来处理request.getParamater中的非法字符。如<script>alert('123');</script> </p>
  * @author : crj
  * @date : 2017年11月7日下午6:56:42
 */
public class IllegalCharacterFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// sonar检查需要方法体有代码或者注释
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		   HttpServletRequest req = (HttpServletRequest)request;
		   req = new MHttpServletRequest(req);
	       chain.doFilter(req, response);
	}

	@Override
	public void destroy() {
		// sonar检查需要方法体有代码或者注释
	}


}
