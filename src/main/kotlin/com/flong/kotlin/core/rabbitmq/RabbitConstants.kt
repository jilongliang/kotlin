package com.flong.kotlin.core.rabbitmq

import kotlin.jvm.JvmStatic

/**
 *常量
 */
open class RabbitConstants {

	companion object {

		//交换器名称
		const val TOPIC_EXCHANGE 		= "topic_exchange";

		//queue名称
		const val USER_QUEUE 			= "user_queue";

		//route_key名称
		const val USER_QUEUE_ROUTE_KEY 	= "user_queue_route_key";

	}
	
}