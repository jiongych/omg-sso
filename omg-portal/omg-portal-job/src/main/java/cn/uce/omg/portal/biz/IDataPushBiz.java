package cn.uce.omg.portal.biz;

/**
 * MQ数据同步服务接口
 * @author uce
 */
public interface IDataPushBiz {
	
	/**
	 * 数据同步异常重推
	 * @param t
	 * @param constants
	 * @return
	 */
	int resend(String obj, String otype, String mqTemplate);
}
