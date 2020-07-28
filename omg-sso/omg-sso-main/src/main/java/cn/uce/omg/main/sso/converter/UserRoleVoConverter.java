/** 
 * @项目名称: FSP
 * @文件名称: UserRoleVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.sso.converter;

import cn.uce.omg.main.converter.ResponseXmlConverter;
import cn.uce.omg.sso.vo.EmpRoleRelVo;
import cn.uce.omg.sso.vo.ResultRoleVo;
import cn.uce.omg.sso.vo.UserRoleVo;

/**
 * 认证结果
 * @author tanchong
 *
 */
public class UserRoleVoConverter extends ResponseXmlConverter {

	public UserRoleVoConverter() {
		this("xml");
	}

	/**
	 * 
	 * @param contentType
	 */
	public UserRoleVoConverter(String contentType) {
		super(contentType);
		this.xstream.ignoreUnknownElements();
		this.xstream.aliasSystemAttribute(null, "class");
		changeField();
	}

	/**
	 * 数据转换
	 * 
	 * @author huangting
	 * @date 2017年6月9日 下午12:09:59
	 */
	public void changeField() {
		this.aliasClass("result", ResultRoleVo.class);
		this.aliasClass("role", UserRoleVo.class);
		this.aliasClass("roleRel", EmpRoleRelVo.class);
	}

}
