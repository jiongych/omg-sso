package cn.uce.omg.portal.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.omg.portal.dao.IPushErrorLogDao;
import cn.uce.omg.portal.vo.PushErrorLogVo;
/**
 * 推送异常数据处理服务类
 * @author uce
 *
 */
@Service("pushErrorLogService")
public class PushErrorLogService {
	@Resource
	private IPushErrorLogDao pushErrorLogDao;
	/**
	 * 查询处理中的推送失败的数据记录
	 * @param searchVo
	 * @return
	 */
	public List<PushErrorLogVo> findProcessingLog(PushErrorLogVo searchVo) {
		return pushErrorLogDao.findProcessingLog(searchVo);
	}
	
	/**
	 * 更新重推数据状态
	 */
	public int updateToProcessEnd(PushErrorLogVo updateLog) {
		return pushErrorLogDao.updateToProcessEnd(updateLog);
	}

	/**
	 * 根据id删除推送错误日志记录
	 * @param id
	 * @return
	 */
	public int deleteLog(Long id) {
		return pushErrorLogDao.deleteLog(id);
	}

	/**
	 * 查询待处理的错误数据
	 */
	public List<PushErrorLogVo> findWaitProcessLog(PushErrorLogVo searchVo) {
		return pushErrorLogDao.findWaitProcessLog(searchVo);
	}

	/**
	 * 推送失败记录错误日志记录
	 */
	public int savePushErrorLog(PushErrorLogVo pushErrorLog) {
		return pushErrorLogDao.savePushErrorLog(pushErrorLog);
	}

	/**
	 * 根据ID更新数据
	 * @param updateLog
	 * @return
	 */
	public int updateById(PushErrorLogVo updateLog) {
		return pushErrorLogDao.updateById(updateLog);
	}

}
