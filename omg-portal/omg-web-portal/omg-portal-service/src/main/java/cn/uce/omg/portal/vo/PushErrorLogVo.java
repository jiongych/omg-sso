package cn.uce.omg.portal.vo;

import java.util.Date;

import cn.uce.base.page.Page;
import cn.uce.base.vo.BaseVo;

public class PushErrorLogVo extends BaseVo {
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** id. */
    private Long id;

    /** 请求唯一标识. */
    private String reqId;

    /** 操作类型. */
    private String operatorType;

    /** MQ模板. */
    private String mqTemplete;

    /** 请求参数. */
    private String syncContent;

    /** 下次处理时间. */
    private Date nextProcessTime;

    /** 总处理次数. */
    private Integer totalProcessNum;

    /** 处理状态. */
    private String processStatus;

    /** 处理次数. */
    private Integer processNum;
    
    /** 分片序列号. */
    private Integer shardingItem;
    
    /** 分片总数. */
    private Integer shardingTotal;

    /** 备注. */
    private String remark;

    /** 版本. */
    private String version;

    /** 创建人. */
    private String createEmp;

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;
    
    /** 分页. */
    private Page page;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getMqTemplete() {
		return mqTemplete;
	}

	public void setMqTemplete(String mqTemplete) {
		this.mqTemplete = mqTemplete;
	}

	public String getSyncContent() {
		return syncContent;
	}

	public void setSyncContent(String syncContent) {
		this.syncContent = syncContent;
	}

	public Date getNextProcessTime() {
		return nextProcessTime;
	}

	public void setNextProcessTime(Date nextProcessTime) {
		this.nextProcessTime = nextProcessTime;
	}

	public Integer getTotalProcessNum() {
		return totalProcessNum;
	}

	public void setTotalProcessNum(Integer totalProcessNum) {
		this.totalProcessNum = totalProcessNum;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public Integer getProcessNum() {
		return processNum;
	}

	public void setProcessNum(Integer processNum) {
		this.processNum = processNum;
	}

	public Integer getShardingItem() {
		return shardingItem;
	}

	public void setShardingItem(Integer shardingItem) {
		this.shardingItem = shardingItem;
	}

	public Integer getShardingTotal() {
		return shardingTotal;
	}

	public void setShardingTotal(Integer shardingTotal) {
		this.shardingTotal = shardingTotal;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
