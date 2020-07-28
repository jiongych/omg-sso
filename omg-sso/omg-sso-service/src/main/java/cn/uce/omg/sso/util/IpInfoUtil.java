/** 
 * @项目名称: FSP
 * @文件名称: IpInfoUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import cn.uce.omg.sso.cache.HashRedisWithFieldExpireCache;
import cn.uce.omg.sso.cache.IpInfoCache;
import cn.uce.omg.vo.UserVo;

/**
 * IpInfoUtil  
 * @Description: IpInfoUtil  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class IpInfoUtil {

	/** 起始IP段 */
	private IpInfoCache startIpInfoCache;
	/** 截止IP段 */
	private IpInfoCache endIpInfoCache;
	/** 员工密码缓存 */
	private HashRedisWithFieldExpireCache<UserVo> empPasswordCache;
	
}
