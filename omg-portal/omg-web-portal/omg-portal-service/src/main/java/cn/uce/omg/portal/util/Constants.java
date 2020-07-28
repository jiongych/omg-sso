package cn.uce.omg.portal.util;

public interface Constants {
	
	/**权限组件类型:菜单 */
	int PERMISSION_CONTROL_TYPE_MENU = 1;
	
	/**权限组件类型:按钮 */
	int PERMISSION_CONTROL_TYPE_BUTTON = 2;
	
	/** 数据字典数据来源:1.omg同步 */
	int DATADICTIONARY_SOURCT_TYPE_OMGSYNC = 1;
	
	/** 数据字典数据来源:2.自定义 */
	int DATADICTIONARY_SOURCT_TYPE_USERDEFINED = 2;
	
	//营运机构
	public static final String NOS = "NOS";
	//乾坤机构
	public static final String CMS = "CMS";
	//行政机构
	public static final String ADM = "ADM";
	//切换部门
	public static final String CHANGE_ORG="CHANGE_ORG";
		
	//超级管理员
	String SUPER_ADMIN="SUPER_ADMIN";
	//白名单
	String WHITE_LIST="WHITE_LIST";
	//白名单开关
	String WHITE_LIST_SWITCH="WHITE_LIST_SWITCH";
	
	/** 白名单开关状态 */
	public interface WHITELISTSWITCHSTATE {
		//打开
		public static final String SWITCH_OPEN = "OPEN";
		//关闭
		public static final String SWITCH_CLOSE = "CLOSE";
	}
	
	String UNRECOGNIZED_ORG = "不能识别的部门";
	
	String OPERATE_FLAG = "operateFlag";
		
	/** 机构类型：总部 */
	int ORG_TYPE_HQ = 10;
	/** 机构类型：财务中心 */
	int ORG_TYPE_FINANCE_CENTER = 20;
	/** 机构类型：操作中心 */
	int ORG_TYPE_OPERATE_CENTER = 21;
	/** 机构类型：网点 */
	int ORG_TYPE_SITE = 30;
	/** 机构类型：承包区 */
	int ORG_TYPE_CONTRACT = 40;
	/** 机构类型：大区*/
	int ORG_TYPE_SECTION_AREA =3001;
	/**省区*/
	int ORG_TYPE_PROVINCE_AREA=4001;
	
	/** portal菜单 **/
	String SYSTEM_CODE_PORTAL_MENU ="MENU";
}
