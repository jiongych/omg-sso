package cn.uce.omg.portal.biz.impl;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.uce.core.mq.rocket.RocketTemplate;
import cn.uce.omg.portal.biz.IDataPushBiz;
/**
 * 数据推送具体实现类
 * 
 * @author uce
 */
@Component("dataPushBiz")
public class DataPushBiz implements IDataPushBiz {
	Logger LOG = LoggerFactory.getLogger(DataPushBiz.class);
	@Autowired
	private Map<String, RocketTemplate> mqTemplateMap;

	/**
	 * 推送失败重新推送
	 */
	@Override
	public int resend(String obj, String otype, String mqTemplate) {
		RocketTemplate template = mqTemplateMap.get(mqTemplate);
		template.send(obj, otype);
		return 0;
	}

	public Map<String, RocketTemplate> getMqTemplateMap() {
		return mqTemplateMap;
	}

	public void setMqTemplateMap(Map<String, RocketTemplate> mqTemplateMap) {
		this.mqTemplateMap = mqTemplateMap;
	}
}
