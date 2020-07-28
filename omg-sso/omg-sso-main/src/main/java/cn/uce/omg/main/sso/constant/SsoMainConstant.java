package cn.uce.omg.main.sso.constant;

public interface SsoMainConstant {
	/**图片验证码类型：找回密码，或设置密码**/
	String IMAGE_CODE_TYPE_PHONE = "phone_code";
	/**图片验证码类型：登录**/
	String IMAGE_CODE_TYPE_CODE = "code";
	/**
	 * 安全锁类型lockType：图片验证码
	 */
	String IMAGE_CODE_TYPE = "image_code";
	
	//add by xj
	public interface PORTAL_ACCESS_CONTROL{
		//允许访问工号
		public static final String ALLOW_EMPCODE = "ALLOW_EMPCODE";
		//是否全网放开新portal
		public static final String ALL_OPEN_PORTAL = "ALL_OPEN_PORTAL";
		//PORTAL
		public static final String PORTAL_ACCESS_SWITCH = "PORTAL_ACCESS_SWITCH";
		
		public interface PORTAL_ACCESS_SWITCHS{
			//修改密码推送
			public static final String UPDATE_PASSWORD_PUSH = "UPDATE_PASSWORD_PUSH";
			//收集浏览器信息
			public static final String GATHER_BROWSER_INFO = "GATHER_BROWSER_INFO";
		}
		
	}
	//add by xj
}
