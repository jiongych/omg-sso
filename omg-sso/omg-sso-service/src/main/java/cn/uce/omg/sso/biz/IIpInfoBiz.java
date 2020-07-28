/** 
 * @项目名称: FSP
 * @文件名称: IIpInfoBiz 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz;

import cn.uce.omg.sso.vo.IPEntry;

/**
 * ip信息接口
 * @author huangting
 * @date 2017年6月9日 下午12:14:47
 */
public interface IIpInfoBiz {

	/**
	 * 导入IP信息到数据中，并刷新缓存信息
	 * @param filePath
	 * @throws Exception
	 */
	public Integer insertIpinfo(IPEntry ipEntry) throws Exception;
}
