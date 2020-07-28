/** 
 * @项目名称: FSP
 * @文件名称: IpinfoBiz implements IIpInfoBiz 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz.impl;

import java.util.Date;
import java.util.List;

import cn.uce.omg.sso.biz.IIpInfoBiz;
import cn.uce.omg.sso.constant.IpConstant;
import cn.uce.omg.sso.service.IpInfoService;
import cn.uce.omg.sso.util.StringUtil;
import cn.uce.omg.sso.util.ip.IpAddrUtil;
import cn.uce.omg.sso.vo.IPEntry;
import cn.uce.omg.sso.vo.IpInfoVo;

import com.uc56.bfs.domain.ResponseResult;
import com.uc56.bfs.domain.ZoneParam;
import com.uc56.bfs.service.ICheckOuterZoneService;

/**
 * ip信息处理biz
 * @author tanchong
 *
 */
public class IpinfoBiz implements IIpInfoBiz {

	/** 
	 * ip信息service 
	 */
	private IpInfoService ipInfoService;
	/** 
	 * G7服务调用 
	 */
	private ICheckOuterZoneService checkOuterZoneService;

	/**
	 * 根据查询条件查询数据
	 * @param ipInfoVo
	 * @return
	 * @throws Exception
	 */
	public List<IpInfoVo> findIpInfoByCondition(IpInfoVo ipInfoVo) throws Exception {
		return ipInfoService.findIpInfoByCondition(ipInfoVo);
	}

	/**
	 * 导入IP信息到数据中，并刷新缓存信息
	 * @param filePath
	 * @throws Exception
	 */
	public Integer insertIpinfo(IPEntry ipEntry) throws Exception {
		if (ipEntry == null) {
			return null;
		}
		// 获取ip的long值
		if (ipEntry.getBeginIp() == null || ipEntry.getEndIp() == null) {
			return null;
		}
		IpInfoVo infoVo = new IpInfoVo();
		//设置创建时间
		infoVo.setCreateTime(new Date());
		//设置起始段
		infoVo.setStartSection(ipEntry.getBeginIp());
		//设置截止段
		infoVo.setEndSection(ipEntry.getEndIp());
		// 设置ip地址转换后的long数据
		infoVo.setStartNum(IpAddrUtil.ipToLong(ipEntry.getBeginIp()));
		// 设置ip地址转换后的long数据
		infoVo.setEndNum(IpAddrUtil.ipToLong(ipEntry.getEndIp()));
		// 设置数据
		setAddrInfo(infoVo, ipEntry);
		// 插入到数据库中
		return ipInfoService.insertIpInfo(infoVo);
	}

	/**
	 * 设置地址等信息
	 * @param infoVo
	 */
	private void setAddrInfo(IpInfoVo infoVo, IPEntry ipEntry) {
		// 判断国家是否属于中国
		if (StringUtil.isEmpty(ipEntry.getCountry())) {
			return;
		}
		boolean isChinaFlag = isChinaProvince(ipEntry.getCountry());
		if (!isChinaFlag) {
			// 不属于中国，那么只需要解析国家即可
			infoVo.setCountry(ipEntry.getCountry());
			//设置国家
			infoVo.setAddr(ipEntry.getCountry());
			//运营商
			infoVo.setOperator(ipEntry.getArea());
			return;
		}
		// 属于中国，进行解析省市区，获取G7的ip地址
		ResponseResult result = findOrgInfoByG7(ipEntry);
		infoVo.setCountry("中国");
		//设置省份
		infoVo.setProvince(result.getProvince());
		//设置城市
		infoVo.setCity(result.getCity());
		//设置区/县. 
		infoVo.setCounty(result.getCounty());
		// 解析完地址后，进行解析运营商
		String operatorStr = findOperator(ipEntry.getArea());
		//设置运营商
		if (StringUtil.isNotEmpty(operatorStr)) {
			infoVo.setOperator(operatorStr);
		}
		//设置 地址
		infoVo.setAddr(ipEntry.getCountry() + ipEntry.getArea());
	}

	/**
	 * 根据地址，判断是否是中国的省份
	 * @param addrStr
	 * @return
	 */
	private boolean isChinaProvince(String addrStr) {
		//匹配省份
		for (String province : IpConstant.PROVINCE) {
			int index = addrStr.indexOf(province);
			if (index != 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否存在运营商
	 * @param str
	 * @return
	 */
	private String findOperator(String str) {
		//参数为空返回空
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		//匹配运营商
		for (String operator : IpConstant.OPERATOR) {
			if (operator.equals(str)) {
				return str;
			}
		}
		return null;
	}

	/**
	 * 根据G7进行查找当前地址的省市区信息
	 * @param ipEntry ip对象
	 * @return
	 */
	private ResponseResult findOrgInfoByG7(IPEntry ipEntry) {
		ZoneParam senderAddressParams = new ZoneParam();
		// 直接设置详细地址
		senderAddressParams.setAddress(ipEntry.getCountry() + ipEntry.getArea());
		//校验地址
		ResponseResult result = checkOuterZoneService.checkAddress(senderAddressParams);
		if (result != null) {
			return result;
		}
		return null;
	}
	/**
	 * @param ipInfoService the ipInfoService to set
	 */
	public void setIpInfoService(IpInfoService ipInfoService) {
		this.ipInfoService = ipInfoService;
	}
}
