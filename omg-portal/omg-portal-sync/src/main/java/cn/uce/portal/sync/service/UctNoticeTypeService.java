package cn.uce.portal.sync.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.core.db.IBaseDao;
import cn.uce.core.sync.service.AbstractReceivePushMsgListService;
import cn.uce.portal.sync.dao.IUctNoticeTypeDao;
import cn.uce.portal.sync.entity.UctNoticeType;
import cn.uce.portal.sync.vo.UctNoticeTypeVo;

/**
 * 接收优速通公告类型Service
 *<pre>
 * UctNoticeTypeService
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午3:47:49
 */
@Service("uctNoticeTypeService")
public class UctNoticeTypeService extends AbstractReceivePushMsgListService<UctNoticeTypeVo, Integer, UctNoticeType, Long> {

	@Resource
	private IUctNoticeTypeDao uctNoticeTypeDao;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存数据
	 * </pre>
	 * @param uctNoticeType
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:27:12
	 */
	public int saveNoticeType(UctNoticeType uctNoticeType) {
		return uctNoticeTypeDao.insert(uctNoticeType);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID更新数据
	 * </pre>
	 * @param uctNoticeType
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:27:01
	 */
	public int updateNoticeType(UctNoticeType uctNoticeType) {
		return uctNoticeTypeDao.updateNoticeTypeById(uctNoticeType);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID删除数据
	 * </pre>
	 * @param id
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:27:47
	 */
	public int deleteNoticeType(Long id) {
		return uctNoticeTypeDao.deleteNoticeTypeById(id);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID查询
	 * </pre>
	 * @param id
	 * @return
	 * @return UctNoticeType
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午5:15:15
	 */
	public UctNoticeType findNoticeTypeById(Long id) {
		return uctNoticeTypeDao.findNoticeTypeById(id);
	}
	
	@Override
	public IBaseDao<UctNoticeType, Long> getDao() {
		return uctNoticeTypeDao;
	}

	@Override
	public Class<UctNoticeType> getDbEntityClass() {
		return UctNoticeType.class;
	}

	@Override
	public Class<UctNoticeTypeVo> getMsgEntityClass() {
		return UctNoticeTypeVo.class;
	}

	@Override
	public Long getDbPk(UctNoticeType entity) {
		return entity.getTypeId();
	}

}
