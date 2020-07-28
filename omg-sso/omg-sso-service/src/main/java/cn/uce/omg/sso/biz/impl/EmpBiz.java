/**
 * @项目名称: FSP
 * @文件名称: EmpBiz implements IEmpBiz, IEmpApi
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
 * 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package cn.uce.omg.sso.biz.impl;

import java.util.ArrayList;
import java.util.List;

import cn.uce.omg.sso.biz.IEmpBiz;
import cn.uce.omg.sso.service.EmpService;
import cn.uce.omg.sso.service.OtherEmpRelationService;
import cn.uce.omg.sso.vo.EmpOrgInfoVo;
import cn.uce.omg.vo.EmpVo;
import cn.uce.omg.vo.OtherEmpRelationVo;
import cn.uce.utils.StringUtil;

/**
 * 员工BIZ实现类
 * @author huangting
 * @date 2017年6月9日 下午4:17:50
 */
public class EmpBiz implements IEmpBiz {

    private EmpService empService;
    private OtherEmpRelationService otherEmpRelationService;

    /**
     * 根据员工id查找员工信息
     * @param empId
     * @return
     */
    @Override
    public EmpVo findEmpByEmpId(Integer empId) {
        if (empId == null) {
            return null;
        }
        return this.empService.findEmpByEmpId(empId);
    }

    /**
     * 根据员工编号查询员工信息
     * @param empCode
     * @return
     */
    @Override
    public EmpVo findEmpByEmpCode(String empCode) {
        if (StringUtil.isBlank(empCode)) {
            return null;
        }
        return this.empService.findEmpByEmpCode(empCode);
    }
    
    /**
	 * Description: 根据员工手机号查询员工信息
	 * @param mobile
	 * @return
	 * @author zhangRenbing
	 * @date 2018年9月5日
	 */
	@Override
	public EmpVo findEmpByMobile(String mobile) {
		return this.empService.findEmpByMobile(mobile);
	}

    @Override
    public List<OtherEmpRelationVo> findQkEmpRelationByEmpId(Integer empId) {
        if (empId == null) {
            return new ArrayList<OtherEmpRelationVo>();
        }
        return otherEmpRelationService.findQkEmpRelationByEmpId(empId);
    }

    public void setEmpService(EmpService empService) {
        this.empService = empService;
    }


    public void setOtherEmpRelationService(
            OtherEmpRelationService otherEmpRelationService) {
        this.otherEmpRelationService = otherEmpRelationService;
    }

    /**
     * 
     * 方法的描述：
     * <pre>
     * 根据员工编号查询员工姓名、所属机构、所属机构名称
     * </pre>
     * @param empCode
     * @return
     * @author jiyongchao
     * @email jiyongchao@uce.cn
     * @date 2019年7月18日下午6:12:24
     */
	@Override
	public EmpOrgInfoVo findEmpAndOrgByEmpCode(String empCode) {
		
		return empService.findEmpAndOrgByEmpCode(empCode);
	}
}
