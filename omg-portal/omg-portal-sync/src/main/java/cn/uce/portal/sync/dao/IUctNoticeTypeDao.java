package cn.uce.portal.sync.dao;

import org.springframework.stereotype.Repository;

import cn.uce.core.sync.dao.IBaseSyncDao;
import cn.uce.portal.sync.entity.UctNoticeType;

/**
 * 接收优速通公告类型实体类 DAO
 *<pre>
 * IUctNoticeTypeDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午3:41:58
 */
@Repository("uctNoticeTypeDao")
public interface IUctNoticeTypeDao extends IBaseSyncDao<UctNoticeType, Long> {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID查询
	 * </pre>
	 * @param id
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:22:51
	 */
	UctNoticeType findNoticeTypeById(Long id);
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID更新
	 * </pre>
	 * @param uctNoticeType
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:22:09
	 */
	int updateNoticeTypeById(UctNoticeType uctNoticeType);
	
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
	 * @date 2018年7月25日下午4:24:10
	 */
	int deleteNoticeTypeById(Long id);
}
