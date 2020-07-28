/** 
 * @项目名称: FSP
 * @文件名称: SysTypeBiz implements ISysTypeBiz,ISysTypeApi
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz.impl;

import java.util.List;

import cn.uce.base.exception.BusinessException;
import cn.uce.omg.sso.biz.ISysTypeBiz;
import cn.uce.omg.sso.service.FspDictDataService;
import cn.uce.omg.vo.SysTypeVo;

/**
 * 系统类型BIZ实现类
 * @author huangting
 * @date 2017年6月9日 下午4:40:08
 */
public class SysTypeBiz implements ISysTypeBiz{
	
	/**
	 * 数据字典SERVICE
	 */
	private FspDictDataService fspDictDataService;

	/**
	 * 设置 数据字典SERVICE
	 * @param fspDictDataService 数据字典SERVICE
	 */
	public void setFspDictDataService(FspDictDataService fspDictDataService) {
		this.fspDictDataService = fspDictDataService;
	}
	
	/**
	 * 根据条件查询数据
	 * @param typeClassCode
	 * @return
	 * @author zhangRb
	 * @date 2018年9月5日
	 */
	@Override
	public List<SysTypeVo> findListByTypeClassCode(String typeClassCode) throws BusinessException {
		return fspDictDataService.findListByTypeClassCode(typeClassCode);
	}
}
