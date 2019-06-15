package com.flong.kotlin.core.rabbitmq


/**
 *常量
 */
open class RabbitConstants {

	//半生静态对象，不const val不可以修改的常量
	companion object {

		//交换器名称
		const val TOPIC_EXCHANGE 		= "topic_exchange";

		//queue名称
		const val USER_QUEUE 			= "user_queue";

		//route_key名称
		const val USER_QUEUE_ROUTE_KEY 	= "user_queue_route_key";

	}
	
}