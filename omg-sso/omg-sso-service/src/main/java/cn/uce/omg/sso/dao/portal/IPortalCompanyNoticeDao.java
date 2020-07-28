package cn.uce.omg.sso.dao.portal;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.portal.PortalCompanyNotice;
import cn.uce.omg.sso.vo.NoticeParentInfoVo;
import cn.uce.omg.sso.vo.portal.PortalCompanyNoticeVo;

/**
 * 公司公告DAO
 *<pre>
 * IPortalCompanyNoticeDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月30日下午2:20:30
 */
public interface IPortalCompanyNoticeDao extends IBaseDao<PortalCompanyNotice, Long> {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询前十条公司公告
	 * </pre>
	 * @return
	 * @return List<PortalCompanyNoticeVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月30日下午2:20:12
	 */
	List<PortalCompanyNoticeVo> findNoticeTopicTen(
			@Param("empCode") String empCode, @Param("typeId") String typeId, @Param("orgId") String orgId);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询前十条公司公告
	 * </pre>
	 * @return
	 * @return List<PortalCompanyNoticeVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月30日下午2:20:12
	 */
	List<PortalCompanyNoticeVo> findNoticeTopicTenNew(
			@Param("empCode") String empCode, @Param("typeId") String typeId, @Param("orgId") String orgId, @Param("orgList") List<String> orgList);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询弹出公告
	 * </pre>
	 * @return
	 * @return List<PortalCompanyNoticeVo>
	 * @author zb
	 * @date 2018年6月08日下午2:20:12
	 */
	List<PortalCompanyNoticeVo> findNoticeAlert(@Param("empCode") String empCode, 
			@Param("limitDate") Date limitDate, @Param("orgList") List<String> orgList);
	
	
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID查询数据
	 * </pre>
	 * @param id
	 * @return
	 * @return PortalCompanyNoticeVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月30日下午3:11:08
	 */
	PortalCompanyNoticeVo findNoticeById(Long id);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param orgId
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年8月31日下午4:15:15
	 */
	String findFullOrgIdList(String orgId);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询公告类型
	 * </pre>
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月19日下午3:14:58
	 */
	List<NoticeParentInfoVo> findNoticeType();
}
