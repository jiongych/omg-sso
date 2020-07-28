package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;

public class SiteInfoVo extends BaseVo {

    /** 机构编号.机构编号唯一 */
    private String orgCode;

    /** 机构名称（简体）.机构名称唯一 */
    private String orgName;


    /** 基准编码. */
    private String baseOrgCode;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getBaseOrgCode() {
        return baseOrgCode;
    }

    public void setBaseOrgCode(String baseOrgCode) {
        this.baseOrgCode = baseOrgCode;
    }
}
