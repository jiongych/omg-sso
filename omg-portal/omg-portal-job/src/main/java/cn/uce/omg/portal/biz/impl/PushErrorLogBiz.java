package cn.uce.omg.portal.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.uce.omg.portal.biz.IPushErrorLogBiz;
import cn.uce.omg.portal.service.PushErrorLogService;
import cn.uce.omg.portal.vo.PushErrorLogVo;
/**
 * 数据同步异常处理具体实现类
 * @author uce
 *
 */
@Component("pushErrorLogBiz")
public class PushErrorLogBiz implements IPushErrorLogBiz {
	@Resource(name="pushErrorLogService")
	private PushErrorLogService pushErrorLogService;
	@Override
	public int updateById(PushErrorLogVo updateLog) {
		return pushErrorLogService.updateById(updateLog);
	}

	@Override
	public List<PushErrorLogVo> findLogByCondition(PushErrorLogVo searchVo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询待处理的错误数据
	 */
	@Override
	public List<PushErrorLogVo> findWaitProcessLog(PushErrorLogVo searchVo) {
		return pushErrorLogService.findWaitProcessLog(searchVo);
	}

	/**
	 * 查询处理中的数据记录
	 */
	@Override
	public List<PushErrorLogVo> findProcessingLog(PushErrorLogVo searchVo) {
		return pushErrorLogService.findProcessingLog(searchVo);
	}

	/**
	 * 推送失败记录错误日志记录
	 */
	@Override
	public int savePushErrorLog(PushErrorLogVo productErrorLog) {
		return pushErrorLogService.savePushErrorLog(productErrorLog);
	}

	/**
	 * 根据ID删除推送异常的错误日志记录
	 */
	@Override
	public int deleteLog(Long id) {
		return pushErrorLogService.deleteLog(id);
	}

	/**
	 * 更新重推数据状态
	 */
	@Override
	public int updateToProcessEnd(PushErrorLogVo updateLog) {
		return pushErrorLogService.updateToProcessEnd(updateLog);
	}

}
