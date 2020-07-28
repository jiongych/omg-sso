package cn.uce.portal.sync.dao;

import org.springframework.stereotype.Repository;

import cn.uce.core.sync.dao.IBaseSyncDao;
import cn.uce.portal.sync.entity.UctNoticeData;

/**
 * 优速通公告主数据Dao
 *<pre>
 * IUctNoticeDataDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午3:41:30
 */
@Repository("uctNoticeDataDao")
public interface IUctNoticeDataDao extends IBaseSyncDao<UctNoticeData, Long> {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID查询
	 * </pre>
	 * @param id
	 * @return
	 * @return UctNoticeData
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:24:47
	 */
	UctNoticeData findNoticeDataById(Long id);
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据noticeId更新
	 * </pre>
	 * @param uctNoticeData
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:01:26
	 */
	int updateNoticeDataById(UctNoticeData uctNoticeData);
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID删除
	 * </pre>
	 * @param id
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:21:23
	 */
	int deleteNoticeDataById(Long id);
}
