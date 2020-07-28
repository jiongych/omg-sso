package cn.uce.omg.portal.biz;

import java.util.List;
import java.util.Map;

import cn.uce.uct.app.entity.ConfigureResult;
import cn.uce.uct.app.entity.OmgEmpResult;
import cn.uce.uct.app.vo.AdministrationVo;
import cn.uce.uct.app.vo.ConfigureVo;
import cn.uce.uct.app.vo.ResultData;
import cn.uce.uct.app.vo.UserCollectVo;

public interface IFileInfoBiz {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据受众群体查询文件类型
	 * </pre>
	 * @param oneTypeName
	 * @return
	 * @return List<ConfigureResult>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午5:22:01
	 */
	List<ConfigureResult> getFileType(String oneTypeName);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 获取受众群体
	 * </pre>
	 * @param empCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午5:08:34
	 */
	List<String> getUserViewScope(String empCode);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 获取文件列表
	 * </pre>
	 * @param administrationVo
	 * @return
	 * @return Map<String,Object>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午5:08:49
	 */
	Map<String, Object> getNewFileList(AdministrationVo administrationVo);
	
	/**
	 * 查询的我收藏文件
	 * 方法的描述：
	 * <pre>
	 * 
	 * </pre>
	 * @param userCollectVo
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @return List<UserCollectVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午5:26:29
	 */
	Map<String, Object> getCollectFile(UserCollectVo userCollectVo, Integer currentPage, Integer pageSize);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 对文件进行收藏
	 * </pre>
	 * @param userCollectVo
	 * @return
	 * @return ResultData<UserCollectVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午6:46:44
	 */
	ResultData<UserCollectVo> addCollect(UserCollectVo userCollectVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 对文件进行取消收藏
	 * </pre>
	 * @param userCollectVo
	 * @return
	 * @return ResultData<UserCollectVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午6:46:55
	 */
	ResultData<UserCollectVo> cancelCollect(UserCollectVo userCollectVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 更新阅读量
	 * </pre>
	 * @param administrationVo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月17日下午7:41:25
	 */
	int updateBrowse(AdministrationVo administrationVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询当前员工的受众群体（单个）
	 * </pre>
	 * @param configureVo
	 * @return
	 * @return OmgEmpResult
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月18日下午8:46:37
	 */
	OmgEmpResult findEmpIdentity(ConfigureVo configureVo);
}
