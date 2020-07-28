package cn.uce.omg.main.sso.mq.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.core.mq.listener.MqMessageListener;
import cn.uce.core.mq.listener.Subscription;
import cn.uce.core.mq.rocket.RocketConsumer;
import cn.uce.core.mq.rocket.RocketMessage;
import cn.uce.core.mq.rocket.listener.RocketConsumerContext;
import cn.uce.core.mq.rocket.util.RocketExceptionTranslator;
import cn.uce.utils.StringUtil;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * 由于框架1.0.0版本监听容器无法注册两个不同地址的mqFactory故自定义监听容器
 * @author huangting
 * @date 2019年10月13日 下午12:07:22
 */
public class RocketMqMsgListenerContainer {
	private static final Log LOG = LogFactory.getLog(RocketConsumer.class);
	
	protected Map<Subscription, MqMessageListener> subscriptionMap;
	
	protected Map<String, MqMessageListener> topicSubscribeMap = new HashMap<String, MqMessageListener>();
	
	private DefaultMQPushConsumer mqConsumer;
	
	private MqMessageListener listener;
	
	protected String topicName;
	
    protected String tag;
	
	private Boolean orderly = false;
	
	private String ymMqClient;
	
	public void start(){
		if (this.mqConsumer == null) {
            throw new IllegalArgumentException("Property 'consumer' is required");
        }
		if (subscriptionMap == null && (topicName == null || this.listener == null)) {
    		if (topicName == null) {
    			throw new IllegalArgumentException("Property 'topicName' is required");
    		} else if (this.listener == null) {
    			throw new IllegalArgumentException("Property 'listener' is required");
    		}
    	}
		mqConsumer.setNamesrvAddr(ymMqClient);
		subscribe();
		if (orderly) {
			mqConsumer.registerMessageListener(new RocketOrderlyMessageListener());
		} else {
			mqConsumer.registerMessageListener(new RocketConcurrentlyMessageListener());
		}
		startConsumer();
	}
	
	class RocketOrderlyMessageListener implements MessageListenerOrderly {

		@SuppressWarnings("unchecked")
		@Override
		public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
			try {
				RocketConsumerContext consumerContext = new RocketConsumerContext();
	        	consumerContext.setConsumerGroup(RocketMqMsgListenerContainer.this.mqConsumer.getConsumerGroup());
	        	RocketMessage rocketMessage = new RocketMessage();
	        	MessageExt messageExt = msgs.get(0);
	            rocketMessage = rocketMessage.convertFromRocketMQMessage(messageExt);
	        	MqMessageListener listener = RocketMqMsgListenerContainer.this.topicSubscribeMap.get(rocketMessage.getTopic());
	        	if (listener == null) {
	        		LOG.error("Consume message error, messageListiner is null, message=" + msgs.get(0));
	            	return ConsumeOrderlyStatus.SUCCESS;
	        	}
	        	
	        	listener.onReceiveMessage(rocketMessage, consumerContext);
				return ConsumeOrderlyStatus.SUCCESS;
			} catch (Exception e) {
				LOG.error("Consume message error, mesasge = " + msgs.get(0) , e);
                return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
			}
        }
    }
    
    /**
     * 骞跺彂娑堣垂娑堟伅鐩戝惉鍣�     * @author zhangfh
     */
    class RocketConcurrentlyMessageListener implements MessageListenerConcurrently {
			
		@SuppressWarnings("unchecked")
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context) {
			try {
				RocketConsumerContext consumerContext = new RocketConsumerContext();
	        	consumerContext.setConsumerGroup(RocketMqMsgListenerContainer.this.mqConsumer.getConsumerGroup());
	        	RocketMessage rocketMessage = new RocketMessage();
	        	MessageExt messageExt = msgs.get(0);
	            rocketMessage = rocketMessage.convertFromRocketMQMessage(messageExt);
	        	MqMessageListener listener = RocketMqMsgListenerContainer.this.topicSubscribeMap.get(rocketMessage.getTopic());
	        	if (listener == null) {
	        		LOG.error("Consume message error, messageListiner is null, message=" + msgs.get(0));
	        		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	        	}
	        	listener.onReceiveMessage(rocketMessage, consumerContext);
	        	return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			} catch (Exception e) {
				LOG.error("Consume message error, mesasge = " + msgs.get(0) , e);
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}
        }
    }
    
    public void setYmMqClient(String ymMqClient) {
		this.ymMqClient = ymMqClient;
	}
	
	private void startConsumer() {
		try {
			mqConsumer.start();
		} catch (MQClientException e) {
			throw RocketExceptionTranslator.tranlate(e);
		}
	}
	
	private void subscribe() {
		try {
			if(StringUtil.isNotBlank(this.topicName) && this.listener != null) {
				subscriptionMap = new HashMap<Subscription, MqMessageListener>();
				Subscription subscription = new Subscription();
				subscription.setTag(this.tag);
				subscription.setTopicName(topicName);
				subscriptionMap.put(subscription, listener);
			}
			if (subscriptionMap != null && subscriptionMap.size() > 0) {
				for(Subscription sub :subscriptionMap.keySet()) {
					String topic = sub.getTopicName();
					if (topicSubscribeMap.containsKey(topic) && topicSubscribeMap.get(topic) != listener) {
						throw new IllegalArgumentException("In the same message listener container, a topic can only specify a message listener");
					} else {
						topicSubscribeMap.put(topicName, subscriptionMap.get(sub));
						this.mqConsumer.subscribe(topic, sub.getTag());
					}
				}
			}
		} catch (Exception e) {
		}
	}
	
	
    public void shutdown() {
        if (mqConsumer != null) {
        	mqConsumer.shutdown();
        }
    }
    
    public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
    
    public void setTag(String tag) {
		this.tag = tag;
	}
    
    public void setSubscriptionMap(Map<Subscription, MqMessageListener> subscriptionMap) {
		this.subscriptionMap = subscriptionMap;
	}
    
    public void setTopicSubscribeMap(Map<String, MqMessageListener> topicSubscribeMap) {
		this.topicSubscribeMap = topicSubscribeMap;
	}
    
    public void setOrderly(Boolean orderly) {
		this.orderly = orderly;
	}
	
    public void setListener(MqMessageListener listener) {
		this.listener = listener;
	}
	
	public void setMqConsumer(DefaultMQPushConsumer mqConsumer) {
		this.mqConsumer = mqConsumer;
	}
}
