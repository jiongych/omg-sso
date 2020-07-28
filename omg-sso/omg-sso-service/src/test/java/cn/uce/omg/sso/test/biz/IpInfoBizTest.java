/** 
 * @项目名称: FSP
 * @文件名称: IpInfoBizTest extends BaseJunitTest 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.test.biz;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.uce.omg.sso.biz.IIpInfoBiz;
import cn.uce.omg.sso.test.BaseJunitTest;
import cn.uce.omg.sso.vo.IPEntry;

public class IpInfoBizTest extends BaseJunitTest {

	@Autowired
	private IIpInfoBiz ipInfoBiz;
	
	@Test
	public void insertIpinfoTest() {
		IPEntry ipEntry = new IPEntry();
		try {
			ipEntry.setArea("00fdsa");
			ipEntry.setBeginIp("11");
			ipEntry.setCountry("123");
			ipEntry.setEndIp("22");
			Integer result = ipInfoBiz.insertIpinfo(ipEntry);
			if(result != null) {
				System.out.println(result);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
