/** 
 * @项目名称: FSP
 * @文件名称: UpdPwdVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.sso.converter;

import cn.uce.omg.main.converter.ResponseXmlConverter;
import cn.uce.omg.sso.vo.UpdPwdVo;

/**
 * 修改密码vo转换类
 * @author huangting
 * @date 2017年6月8日 下午1:49:01
 */
public class UpdPwdVoConverter extends ResponseXmlConverter {

	public UpdPwdVoConverter() {
		this("xml");
	}
	/**
	 * 
	 * @param contentType
	 */
	public UpdPwdVoConverter(String contentType) {
		super(contentType);
		changeField();
	}

	/**
	 * 数据转换
	 * 
	 * @author huangting
	 * @date 2017年6月9日 下午12:09:59
	 */
	public void changeField() {
		this.aliasClass("updPwd", UpdPwdVo.class);
	}

}
