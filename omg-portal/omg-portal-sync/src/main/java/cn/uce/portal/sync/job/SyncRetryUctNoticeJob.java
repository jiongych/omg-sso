package cn.uce.portal.sync.job;

import org.springframework.context.ApplicationContext;

import cn.uce.mq.rocket.exp.job.MqErrorMsgElasticJob;
import cn.uce.mq.rocket.exp.job.MqErrorRetryJobConfig;

public class SyncRetryUctNoticeJob extends MqErrorMsgElasticJob {

	@Override
	public void setMqErrorRetryJobConfig(ApplicationContext applicationContext) {
		// 从spring中获取配置信息
		MqErrorRetryJobConfig mqErrorRetryJobConfig = (MqErrorRetryJobConfig) applicationContext.getBean("syncRetryUctNoticeJob");
		// 设置值
		setMqErrorRetryJobConfig(mqErrorRetryJobConfig);
	}

}
