package cn.uce.omg.portal.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalPushErrorLog;
import cn.uce.omg.portal.vo.PushErrorLogVo;
@Repository("pushErrorLogDao")
public interface IPushErrorLogDao extends IBaseDao<PortalPushErrorLog, Long> {

	/**
	 * 查询处理中的推送失败的数据记录
	 * @param searchVo
	 * @return
	 */
	List<PushErrorLogVo> findProcessingLog(PushErrorLogVo searchVo);

	/**
	 * 更新重推数据状态
	 * @param searchVo
	 * @return
	 */
	int updateToProcessEnd(PushErrorLogVo updateLog);

	/**
	 * 根据id删除推送错误日志记录
	 * @param id
	 * @return
	 */
	int deleteLog(Long id);

	/**
	 * 查询待处理的错误数据
	 */
	List<PushErrorLogVo> findWaitProcessLog(PushErrorLogVo searchVo);

	/**
	 * 推送失败记录错误日志记录
	 */
	int savePushErrorLog(PushErrorLogVo pushErrorLog);

	/**
	 * 根据ID更新数据状态
	 * @param updateLog
	 * @return
	 */
	int updateById(PushErrorLogVo updateLog);

}
