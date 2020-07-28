/** 
 * @项目名称: FSP
 * @文件名称: IpConstant 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.constant;

/**
 * ip地址常量类
 * @author tanchong
 *
 */
public interface IpConstant {

	/** 直辖市 */
	String[] DIRECT_CONTROLLED_MUNICIPALITY = new String[] { "北京市", "天津市", "上海市", "重庆市" };

	/** 普通省份 */
	String[] COMMON_PROVINCE = new String[] { "河北", "山西", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建", "江西", "山东", "河南", "湖北", "湖南", "广东", "海南", "四川", "贵州", "云南", "陕西", "甘肃", "青海",
			"台湾" };

	/** 自治区 */
	String[] MUNICIPALITY = new String[] { "广西", "内蒙古", "西藏", "宁夏", "新疆" };

	/** 特殊行政区 */
	String[] SPECIAL_MUNICIPALITY = new String[] { "香港", "澳门" };

	/** 所有省份 */
	String[] PROVINCE = new String[] { "北京市", "天津市", "上海市", "重庆市", "河北", "山西", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建", "江西", "山东", "河南", "湖北", "湖南", "广东", "海南", "四川", "贵州",
			"云南", "陕西", "甘肃", "青海", "台湾", "广西", "内蒙古", "西藏", "宁夏", "新疆", "香港", "澳门" };

	/** 运营商 */
	String[] OPERATOR = new String[] { "电信", "联通", "移动", "铁通", "教育网", "鹏博士长城宽带", "华数宽带", "阿里巴巴", "阿里巴巴网络有限公司" };
}
