package cn.uce.portal.common.filter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import cn.uce.portal.common.util.IOUtils;


/**
 * 工具：资源搬迁至war
 * */
public final class ModuleManager {

	private static final Log LOGGER = LogFactory.getLog(ModuleManager.class);
	
	/**
	 * spring的类实例  本类用其进行资源文件的匹配
	 * */
	private static PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

	/**
	 * 以下是  各资源文件的匹配规则以及war中新建规则
	 * */
	private static final String PACKAGE_PREFIX = "cn/uce/";

	private static final String PAGES_FROM = "/server/META-INF/pages/";
	
	private static final String PAGES_TO = "/WEB-INF/pages/";

	private static final String PAGES_SUFFIX = "";
	
	private static final String SCRIPTS_FROM = "/server/META-INF/scripts/";
	
	private static final String SCRIPTS_TO = "/scripts/";

	private static final String SCRIPTS_SUFFIX = "";
	
	private static final String STYLES_FROM = "/server/META-INF/styles/";
	
	private static final String STYLES_TO = "/styles/";

	private static final String STYLES_SUFFIX = "";
	
	private static final String IMAGES_FROM = "/server/META-INF/images/";
	
	public static final String IMAGES_TO = "/images/";

	private static final String IMAGES_SUFFIX = "";
	
	private ModuleManager() {
	}

	public static void export(ServletContext servletContext) {
		//导出页面
		export(servletContext, PAGES_FROM, PAGES_TO, PAGES_SUFFIX);
		//导出js脚本
		export(servletContext, SCRIPTS_FROM, SCRIPTS_TO, SCRIPTS_SUFFIX);
		//导出css
		export(servletContext, STYLES_FROM, STYLES_TO, STYLES_SUFFIX);
		//导出图片
		export(servletContext, IMAGES_FROM, IMAGES_TO, IMAGES_SUFFIX);
	}
	
	private static void export(ServletContext servletContext, String from, String to, String suffix) {
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("[Framework] servlet root dir: " + servletContext.getRealPath("/"));
			}
			
			//按照参数，匹配当前项目下 所有包
			Resource[] resources = resolver.getResources("classpath*:" + PACKAGE_PREFIX + "**" + from + "**" + suffix);
			//当匹配到的时候
			if (resources != null && resources.length > 0) {
				for (Resource resource : resources) {
					try {
						String path = resource.getURL().getPath();
						
						int j = path.lastIndexOf(from);
					
						String pathHeader = path.substring(0, j);
						
						int i = pathHeader.lastIndexOf('/');
						String module = path.substring(i + 1, j);
						String page = path.substring(j + from.length());
						String dist = to + module + "/" + page;
						
						//上面的都是在拼文件的导出地址，下面要真正的建文件
						File file = new File(servletContext.getRealPath(dist));
						//查验是否已经建立了文件
						if (!file.getName().matches("^.*[.].*$")) {
							File dir = new File(file.getPath() + "\\" + File.separator);
							//先创建目录
							if (!dir.exists()) {
								boolean b = file.mkdirs();
								if(!b) {
									LOGGER.error("create dir failure!");
								}
								if (LOGGER.isInfoEnabled()) {
									LOGGER.info("[Framework] create dir: " + dir);
								}
							}
						}
						File dir = file.getParentFile();
						if (! dir.exists()) {
							boolean b = dir.mkdirs();
							if(!b) {
								LOGGER.error("create dir failure!");
							}
							if (LOGGER.isInfoEnabled()) {
								LOGGER.info("[Framework] create dir: " + dir);
							}
						}
						if (LOGGER.isInfoEnabled()) {
							LOGGER.info("[Framework] release resource: " + dist);
						}
						
						//建好了目录，准备复制文件到war
						OutputStream out = new FileOutputStream(file);
						//从resource中，把资源文件写往war
						InputStream in = resource.getInputStream();
						IOUtils.copy(in, out, true);	
						
						//设置释放文件的最后修改时间
						file.setLastModified(resource.lastModified());
						
					} catch (Exception e) {
						LOGGER.error(e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
