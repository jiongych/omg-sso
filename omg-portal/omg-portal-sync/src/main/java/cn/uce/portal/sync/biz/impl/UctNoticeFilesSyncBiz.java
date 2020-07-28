package cn.uce.portal.sync.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.core.sync.biz.BaseReceivePushMsgBiz;
import cn.uce.core.sync.service.AbstractReceivePushMsgListService;
import cn.uce.portal.sync.entity.UctNoticeFiles;
import cn.uce.portal.sync.service.UctNoticeFilesService;
import cn.uce.portal.sync.vo.UctNoticeFilesVo;

/**
 * 接收优速通文件路径及文件相关的数据BIZ
 *<pre>
 * UctNoticeFilesSyncBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午4:00:38
 */
@Service("uctNoticeFilesSyncBiz")
public class UctNoticeFilesSyncBiz extends BaseReceivePushMsgBiz<UctNoticeFilesVo, Integer, UctNoticeFiles, Long> {

	@Resource(name="uctNoticeFilesService")
	private UctNoticeFilesService noticeFilesService;
	
	@Override
	public AbstractReceivePushMsgListService<UctNoticeFilesVo, Integer, UctNoticeFiles, Long> getReceivePushMsgListService() {
		return noticeFilesService;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 处理优速通的数据
	 * </pre>
	 * @param uctNoticeFiles
	 * @param dealType
	 * @return void
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月24日下午5:46:26
	 */
	public void processPushNoticeFilesMsg(UctNoticeFiles uctNoticeFiles, Integer dealType) {
		if (dealType == 1) {
			//增加
			noticeFilesService.saveNoticeFiles(uctNoticeFiles);
		} else if (dealType == 2) {
			//删除
			noticeFilesService.deleteNoticeFiles(uctNoticeFiles.getId());
		} else if (dealType == 3) {
			//修改
			UctNoticeFiles uctNoticeFilesSql = noticeFilesService.findNoticeFilesById(uctNoticeFiles.getId());
			if (null == uctNoticeFilesSql) {
				//增加
				noticeFilesService.saveNoticeFiles(uctNoticeFiles);
			} else {
				noticeFilesService.updateNoticeFiles(uctNoticeFiles);
			}
		}
	}
}
