package com.flong.kotlin.modules.rabbitmq.consumer

import org.springframework.stereotype.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.core.Message;
import com.rabbitmq.client.Channel;
import com.flong.kotlin.core.rabbitmq.RabbitConstants
import org.springframework.util.SerializationUtils

@Service
open class UserConsumerService {
	
	companion object{
		private val log: Logger = LoggerFactory.getLogger(UserConsumerService::class.java)
	}
	
	
	@RabbitHandler
	@RabbitListener(queues = arrayOf(RabbitConstants.USER_QUEUE))
	fun syncUserQueue(message:Message, channel: Channel ) {
		try {
			var user = SerializationUtils.deserialize(message.getBody());//反序列化
			
			println("rabbitmq获取到的用户信息为：" + user )
	 
		} catch ( e:Exception) {
			log.error("syncUserQueue异常:" + e.message);
		}finally{
			
			if(channel != null){
				channel.close()
			}
		}
	}
	
}