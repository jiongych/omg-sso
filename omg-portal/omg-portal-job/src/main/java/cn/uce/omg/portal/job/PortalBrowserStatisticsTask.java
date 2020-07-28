package cn.uce.omg.portal.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;

import cn.uce.elastic.job.base.UceSimpleJob;
import cn.uce.omg.portal.biz.IBrowerStatisticsBiz;
import cn.uce.omg.portal.vo.PortalStatisticsTaskVo;
/**
 * 该JOB用于定时计算每日登陆人次数据
 * @author uce
 *
 */
public class PortalBrowserStatisticsTask extends UceSimpleJob {
	private Logger log = LoggerFactory.getLogger(PortalBrowserStatisticsTask.class);
	@Autowired
	private IBrowerStatisticsBiz browerStatisticsBiz;
	/**
	 * 具体执行统计方法
	 */
	@Override
	public void doExecute(ShardingContext context) {
		PortalStatisticsTaskVo bsiv = new PortalStatisticsTaskVo();
		bsiv.setJobName(context.getJobName());
		bsiv.setJobParameter(context.getJobParameter());
		bsiv.setShardingItem(context.getShardingItem());
		bsiv.setShardingParameter(context.getShardingParameter());
		bsiv.setShardingTotalCount(context.getShardingTotalCount());
		browerStatisticsBiz.browserStatistics(bsiv);
		String name  = context.getJobName() + context.getJobParameter() + context.getShardingItem() + context.getShardingParameter() + context.getShardingTotalCount();
	    if(log.isInfoEnabled()) {
	    	log.info(name);
	    }
	}

}
