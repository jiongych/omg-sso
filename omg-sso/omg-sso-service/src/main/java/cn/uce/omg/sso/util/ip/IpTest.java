/** 
 * @项目名称: FSP
 * @文件名称: IpTest 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util.ip;

import java.io.BufferedWriter;
import java.util.List;

import cn.uce.omg.sso.util.FileUtil;
import cn.uce.omg.sso.vo.IPEntry;

public class IpTest {

	public static void main(String[] args) {
		IPSeeker ipSeeker = IPSeeker.getInstance("C:\\qqwry.dat");
//		ipSeeker.setIP_FILE("C:\\qqwry.dat");
		// 获取所有ip信息
		/*List<IPEntry> ipEntryList = ipSeeker.getIPEntriesDebug(null);
		StringBuffer sbStr = new StringBuffer();
		for(IPEntry ipEntry:ipEntryList){
			System.out.println(ipEntry.getArea());
			sbStr.append(ipEntry.getArea()+"\r\n");
		}
		FileUtil.saveFile("C:\\a.txt", sbStr.toString());*/
	}
}
