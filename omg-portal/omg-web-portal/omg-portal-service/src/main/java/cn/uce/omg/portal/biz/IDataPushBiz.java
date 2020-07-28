package cn.uce.omg.portal.biz;

import java.util.List;
import java.util.Map;

import cn.uce.omg.portal.util.LogConstants;
import cn.uce.omg.portal.vo.PortalDataPushVo;

/**
 * MQ数据同步服务接口
 * @author uce
 */
public interface IDataPushBiz {
	
	/**
	 * 数据同步公共调用类
	 * @param t
	 * @param constants
	 * @return
	 */
	int push(Object[] obj, LogConstants constants);
	
	/**
	 * 数据同步异常重推
	 * @param t
	 * @param constants
	 * @return
	 */
	int resend(String obj, String otype, String mqTemplate);
	
	/**
	 * 处理页面重推信息
	 * @param t
	 * @param constants
	 * @return
	 */
	Map<String, Object> processPushData(PortalDataPushVo dataPushVo);

	/**
	 * 根据推送数据类型，查询推送范围数据
	 * @param dataType
	 * @param startTime
	 * @param endTime
	 * @param receiver
	 * @return
	 */
	List<Object> findPushRange(PortalDataPushVo dataPushVo);
}
