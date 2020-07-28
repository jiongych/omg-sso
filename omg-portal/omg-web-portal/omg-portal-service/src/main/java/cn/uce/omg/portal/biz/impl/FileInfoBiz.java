package cn.uce.omg.portal.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.omg.portal.biz.IFileInfoBiz;
import cn.uce.omg.portal.util.UctConstant;
import cn.uce.uct.app.api.IAdministrationBiz;
import cn.uce.uct.app.api.IConfigureBiz;
import cn.uce.uct.app.api.IUserCollectApi;
import cn.uce.uct.app.entity.ConfigureResult;
import cn.uce.uct.app.entity.OmgEmpResult;
import cn.uce.uct.app.vo.AdministrationVo;
import cn.uce.uct.app.vo.ConfigureVo;
import cn.uce.uct.app.vo.ResultData;
import cn.uce.uct.app.vo.UserCollectVo;
import cn.uce.utils.JsonUtil;

@Service("fileInfoBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class FileInfoBiz implements IFileInfoBiz {

	private final Log log = LogFactory.getLog(this.getClass());
	@Resource
	private IAdministrationBiz administrationApi;
	@Resource
	private IConfigureBiz configureApi;
	@Resource
	private IUserCollectApi<UserCollectVo> userCollectApi;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据受众群体查询文件类型
	 * </pre>
	 * @param oneTypeName
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午5:22:34
	 */
	@Override
	public List<ConfigureResult> getFileType(String oneTypeName) {
		ConfigureVo configureVo = new ConfigureVo();
		configureVo.setOneTypeName(oneTypeName);//省区、网点、快递员......
		List<ConfigureResult> configureResults = configureApi.findConfigureList(configureVo);
		if (log.isInfoEnabled()) {
			log.info("根据受众群体查询文件类型请求参数：" + JsonUtil.toJson(configureVo) + "，返回参数：" + JsonUtil.toJson(configureResults));
		}
		return configureResults;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据员工工号查询所属身份
	 * </pre>
	 * @param empCode
	 * @param orgId
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午4:44:27
	 */
	@Override
	public List<String> getUserViewScope(String empCode) {
		ConfigureVo configureVo = new ConfigureVo();
		configureVo.setEmpCode(empCode);
		OmgEmpResult omgEmpResult = findEmpIdentity(configureVo);
		if (log.isInfoEnabled()) {
			log.info("根据员工工号查询所属身份请求参数：" + JsonUtil.toJson(configureVo) + "，返回参数：" + JsonUtil.toJson(omgEmpResult));
		}
		if (null == omgEmpResult) {
			return null;
		}
		List<String> viewScopes = UctConstant.findViewScope(omgEmpResult.getIdentityType());
		return viewScopes;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询当前员工的受众群体（单个）
	 * </pre>
	 * @param configureVo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月18日下午8:47:35
	 */
	@Override
	public OmgEmpResult findEmpIdentity(ConfigureVo configureVo) {
		OmgEmpResult omgEmpResult = administrationApi.findEmpIdentity(configureVo);
		return omgEmpResult;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询公司文件（分页）
	 * </pre>
	 * @param administrationVo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午4:35:27
	 */
	@Override
	public Map<String, Object> getNewFileList(AdministrationVo administrationVo) {
		Map<String, Object> uctFileResult = administrationApi.findByPagination(administrationVo);
		if (log.isInfoEnabled()) {
			log.info("查询优速通文件列表请求参数：" + JsonUtil.toJson(administrationVo) + "，返回参数：" + uctFileResult);
		}
		return uctFileResult;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询我的收藏
	 * </pre>
	 * @param userCollectVo
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午6:44:06
	 */
	@Override
	public Map<String, Object> getCollectFile(UserCollectVo userCollectVo, Integer currentPage, Integer pageSize) {
		Map<String, Object> omgEmpResult = userCollectApi.findListforPortal(userCollectVo, currentPage, pageSize, null);
		if (log.isInfoEnabled()) {
			log.info("查询优速通我的收藏请求参数：" + JsonUtil.toJson(userCollectVo) + "，返回参数：" + JsonUtil.toJson(omgEmpResult));
		}
		return omgEmpResult;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 文件增加收藏
	 * </pre>
	 * @param userCollectVo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午6:48:28
	 */
	@Override
	public ResultData<UserCollectVo> addCollect(UserCollectVo userCollectVo) {
		ResultData<UserCollectVo> omgEmpResult = userCollectApi.addCollect(userCollectVo);
		if (log.isInfoEnabled()) {
			log.info("对文件进行收藏请求参数：" + JsonUtil.toJson(userCollectVo) + "，返回参数：" + JsonUtil.toJson(omgEmpResult));
		}
		return omgEmpResult;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 文件取消收藏
	 * </pre>
	 * @param userCollectVo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午6:48:39
	 */
	@Override
	public ResultData<UserCollectVo> cancelCollect(UserCollectVo userCollectVo) {
		ResultData<UserCollectVo> omgEmpResult = userCollectApi.cancelCollect(userCollectVo);
		if (log.isInfoEnabled()) {
			log.info("对文件进行收藏请求参数：" + JsonUtil.toJson(userCollectVo) + "，返回参数：" + JsonUtil.toJson(omgEmpResult));
		}
		return omgEmpResult;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 更新阅读量
	 * </pre>
	 * @param administrationVo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午7:41:53
	 */
	@Override
	public int updateBrowse(AdministrationVo administrationVo) {
		
		return administrationApi.updateBrowse(administrationVo);
	}


}
