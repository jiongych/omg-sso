package cn.uce.portal.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 框架过滤器
 * author：zhangd
 * time: 2017-3-13 14:20
 */
public class FrameworkFilter implements Filter{
    
    private static ServletContext servletContext;
    
    public static ServletContext getServletContext() {
        return servletContext;
    }
    
    /**
     * 初始化Filter，导出模块资源
     * init
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
    	getServletContext(config);
        ModuleManager.export(servletContext);
    }
    
    public static void getServletContext(FilterConfig config) {
    	servletContext = config.getServletContext();
    }
    
    /**
     * 设置应用信息
     * doFilter
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
		filterChain.doFilter(request, response);
    }
	
	@Override
	public void destroy() {
		
	}
    
}
