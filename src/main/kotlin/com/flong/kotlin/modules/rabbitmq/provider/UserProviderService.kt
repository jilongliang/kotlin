package com.flong.kotlin.modules.rabbitmq.provider

import com.flong.kotlin.core.rabbitmq.RabbitConstants
import com.flong.kotlin.core.rabbitmq.RabbitTemplateClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat

@Service
open class UserProviderService {

	companion object {
		private val log: Logger = LoggerFactory.getLogger(UserProviderService::class.java)
	}
	
	
	@Autowired lateinit var rabbitTemplateClient :RabbitTemplateClient

	/**
	 * @Description 异步处理MQ生产者
	 * @Author    liangjl
	 * @Date    2018年5月10日 下午8:01:02
	 * @param orderDataList 参数
	 * @return void 返回类型
	 */
	fun syncUserQueue(objectVo: Any) {

		rabbitTemplateClient.sendMessage(objectVo, RabbitConstants.TOPIC_EXCHANGE, RabbitConstants.USER_QUEUE_ROUTE_KEY);

		log.info("[syncUserQueue]RabbitMQ生产者执行完成，完成时间为：" + SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}


}