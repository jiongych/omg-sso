/** 
 * @项目名称: FSP
 * @文件名称: UserRoleResponseVo extends BaseResponseVo 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo.base;

import uce.sso.sdk.vo.ResultRoleVo;

/**
 * UserRoleResponseVo extends BaseResponseVo  
 * @Description: UserRoleResponseVo extends BaseResponseVo  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class UserRoleResponseVo extends BaseResponseVo {
	
	/**  */
	private static final long serialVersionUID = 1L;
	
	public ResultRoleVo data;

	public ResultRoleVo getData() {
		return data;
	}

	public void setData(ResultRoleVo data) {
		this.data = data;
	}

}
