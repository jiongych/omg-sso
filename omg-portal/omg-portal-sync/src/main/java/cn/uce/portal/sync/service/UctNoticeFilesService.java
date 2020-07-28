package cn.uce.portal.sync.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.core.db.IBaseDao;
import cn.uce.core.sync.service.AbstractReceivePushMsgListService;
import cn.uce.portal.sync.dao.IUctNoticeFilesDao;
import cn.uce.portal.sync.entity.UctNoticeFiles;
import cn.uce.portal.sync.vo.UctNoticeFilesVo;

/**
 * 接收优速通文件路径及文件相关的数据表Service
 *<pre>
 * UctNoticeFilesService
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午3:45:13
 */
@Service("uctNoticeFilesService")
public class UctNoticeFilesService extends AbstractReceivePushMsgListService<UctNoticeFilesVo, Integer, UctNoticeFiles, Long> {

	@Resource
	private IUctNoticeFilesDao uctNoticeFilesDao;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存数据
	 * </pre>
	 * @param uctNoticeFiles
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:25:27
	 */
	public int saveNoticeFiles(UctNoticeFiles uctNoticeFiles) {
		return uctNoticeFilesDao.insert(uctNoticeFiles);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID更新数据
	 * </pre>
	 * @param uctNoticeFiles
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:25:41
	 */
	public int updateNoticeFiles(UctNoticeFiles uctNoticeFiles) {
		return uctNoticeFilesDao.updateNoticeFilesById(uctNoticeFiles);
	}
	
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
	 * @date 2018年7月25日下午4:26:25
	 */
	public int deleteNoticeFiles(Long id) {
		return uctNoticeFilesDao.deleteNoticeFilesById(id);
	}
	
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
	 * @date 2018年7月26日下午2:18:38
	 */
	public int deleteNoticeFilesByNoticeId(Long noticeId) {
		return uctNoticeFilesDao.deleteNoticeFilesByNoticeId(noticeId);
	}
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
	 * @date 2018年7月25日下午5:14:19
	 */
	public UctNoticeFiles findNoticeFilesById(Long id) {
		return uctNoticeFilesDao.findNoticeFilesById(id);
	}
	
	@Override
	public IBaseDao<UctNoticeFiles, Long> getDao() {
		return uctNoticeFilesDao;
	}

	@Override
	public Class<UctNoticeFiles> getDbEntityClass() {
		return UctNoticeFiles.class;
	}

	@Override
	public Class<UctNoticeFilesVo> getMsgEntityClass() {
		return UctNoticeFilesVo.class;
	}

	@Override
	public Long getDbPk(UctNoticeFiles entity) {
		return entity.getId();
	}

}
