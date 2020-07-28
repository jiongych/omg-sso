package cn.uce.portal.common.constants;

/**
 * 
 * <p>Description:框架常用常量定义 </p>
 * @author mshi
 * @date 2017年3月8日
 */
public interface Constants {

	/**
	 * 异常信息统一头信息<br>
	 * 非常遗憾的通知您,程序发生了异常
	 */
	public static final String Exception_Head = "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :";
	/** 操作名称 */
	public static final String OPERATION_NAME = "OPERATION_NAME";
	/** 客户端语言 */
	public static final String USERLANGUAGE = "userLanguage";
	/** 客户端主题 */
	public static final String WEBTHEME = "webTheme";
	/** 当前用户 */
	public static final String CURRENT_USER = "CURRENT_USER";
	/** 在线用户数量 */
	public static final String ALLUSER_NUMBER = "ALLUSER_NUMBER";
	/** 登录用户数量 */
	public static final String USER_NUMBER = "USER_NUMBER";
	/** 上次请求地址 */
	public static final String PREREQUEST = "PREREQUEST";
	/** 上次请求时间 */
	public static final String PREREQUEST_TIME = "PREREQUEST_TIME";
	/** 登录地址 */
	public static final String LOGIN_URL = "/login.html";
	/** 非法请求次数 */
	public static final String MALICIOUS_REQUEST_TIMES = "MALICIOUS_REQUEST_TIMES";
	/** 系统名称 */
	public static final String SYSTEM_NAME = "SYSTEM_NAME";
	/** 资源文件静态地址 */
	public static final String STATIC_SERVERADDRESS = "STATIC_SERVERADDRESS";
	/** 日志表状态 */
	public interface JOBSTATE {
		/**
		 * 日志表状态，初始状态，插入
		 */
		public static final String INIT_STATS = "I";
		/**
		 * 日志表状态，成功
		 */
		public static final String SUCCESS_STATS = "S";
		/**
		 * 日志表状态，失败
		 */
		public static final String ERROR_STATS = "E";
		/**
		 * 日志表状态，未执行
		 */
		public static final String UN_STATS = "N";
	}
	
	public static final String ENCODING="UTF-8";
}
