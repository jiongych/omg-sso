package cn.uce.omg.portal.biz;

import java.util.List;

import cn.uce.omg.portal.vo.PushErrorLogVo;

/**
 * 数据同步异常处理类
 * @author uce
 *
 */
public interface IPushErrorLogBiz {
	
    int updateById(PushErrorLogVo updateLog);
	
	List<PushErrorLogVo> findLogByCondition(PushErrorLogVo searchVo);

	List<PushErrorLogVo> findWaitProcessLog(PushErrorLogVo searchVo);

	List<PushErrorLogVo> findProcessingLog(PushErrorLogVo searchVo);

	int savePushErrorLog(PushErrorLogVo productErrorLog);
	
	int deleteLog(Long id);

	int updateToProcessEnd(PushErrorLogVo updateLog);
}
