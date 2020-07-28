package cn.uce.portal.sync.dao;

import org.springframework.stereotype.Repository;

import cn.uce.core.sync.dao.IBaseSyncDao;
import cn.uce.portal.sync.entity.UctNoticeFiles;

/**
 * 接收优速通文件路径及文件相关的数据表实体类 DAO
 *<pre>
 * IUctNoticeFilesDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午3:41:41
 */
@Repository("uctNoticeFilesDao")
public interface IUctNoticeFilesDao extends IBaseSyncDao<UctNoticeFiles, Long> {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID查询
	 * </pre>
	 * @param id
	 * @return
	 * @return UctNoticeFiles
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:23:41
	 */
	UctNoticeFiles findNoticeFilesById(Long id);
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID更新
	 * </pre>
	 * @param uctNoticeFiles
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:20:23
	 */
	int updateNoticeFilesById(UctNoticeFiles uctNoticeFiles);
	
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
	 * @date 2018年7月25日下午4:20:54
	 */
	int deleteNoticeFilesById(Long id);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据公告ID删除数据
	 * </pre>
	 * @param noticeId
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月26日下午2:17:57
	 */
	int deleteNoticeFilesByNoticeId(Long noticeId);
}
