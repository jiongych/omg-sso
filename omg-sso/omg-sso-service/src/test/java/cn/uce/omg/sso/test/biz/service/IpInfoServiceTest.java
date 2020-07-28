/** 
 * @项目名称: FSP
 * @文件名称: IpInfoServiceTest 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.test.biz.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.uce.omg.sso.service.IpInfoService;
import cn.uce.omg.sso.vo.IpInfoVo;

public class IpInfoServiceTest {

	@Autowired
	private IpInfoService infoService;
	
	@Test
	public void insertIpInfoTest() {
		IpInfoVo ipInfoVo = new IpInfoVo();
		try {
			ipInfoVo.setCity("11");
			Integer result = infoService.insertIpInfo(ipInfoVo);
			if(result != null) {
				System.out.println(result);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void findAllIpInfoListTest() {
		try {
			List<IpInfoVo> result = infoService.findAllIpInfoList();
			if(result != null && result.size() > 0) {
				System.out.println(result.size());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	@Test
	public void findIpInfoByConditionTest() {
		IpInfoVo ipInfoVo = new IpInfoVo();
		try {
			ipInfoVo.setCity("11");
			List<IpInfoVo> result = infoService.findIpInfoByCondition(ipInfoVo);
			if(result != null && result.size() > 0) {
				System.out.println(result.size());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
