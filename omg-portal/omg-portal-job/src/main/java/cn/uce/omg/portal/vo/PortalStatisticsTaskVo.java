package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;

public class PortalStatisticsTaskVo  extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 636851939733017939L;

	/**
     * 作业名称.
     */
    private String jobName;
    
    /**
     * 作业任务ID.
     */
    private String taskId;
    
    /**
     * 分片总数.
     */
    private int shardingTotalCount;
    
    /**
     * 作业自定义参数.
     * 可以配置多个相同的作业, 但是用不同的参数作为不同的调度实例.
     */
    private String jobParameter;
    
    /**
     * 分配于本作业实例的分片项.
     */
    private int shardingItem;
    
    /**
     * 分配于本作业实例的分片参数.
     */
    private String shardingParameter;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getShardingTotalCount() {
		return shardingTotalCount;
	}

	public void setShardingTotalCount(int shardingTotalCount) {
		this.shardingTotalCount = shardingTotalCount;
	}

	public String getJobParameter() {
		return jobParameter;
	}

	public void setJobParameter(String jobParameter) {
		this.jobParameter = jobParameter;
	}

	public int getShardingItem() {
		return shardingItem;
	}

	public void setShardingItem(int shardingItem) {
		this.shardingItem = shardingItem;
	}

	public String getShardingParameter() {
		return shardingParameter;
	}

	public void setShardingParameter(String shardingParameter) {
		this.shardingParameter = shardingParameter;
	}
}
