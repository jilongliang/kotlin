package com.flong.kotlin.core.rabbitmq

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import org.slf4j.LoggerFactory
import org.slf4j.Logger

@Component
open class RabbitTemplateClient {

	@Autowired lateinit var amqpTemplate: AmqpTemplate

	companion object {
		private val log: Logger = LoggerFactory.getLogger(RabbitTemplateClient::class.java)
	}

	/**
	 * @Description 生产者发送消息
	 * @Author    liangjl
	 * @Date    2018年5月10日 下午5:57:43
	 * @param content 生产者的内容
	 * @param exchangeName 交换机名称
	 * @param routingKey 路由key
	 * @return void 返回类型
	 */
	fun sendMessage(`object`: Any, exchangeName: String, routingKey: String) {
		var properties = MessagePropertiesBuilder.newInstance().setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
		var message = Message(SerializationUtils.serialize(`object`), properties);
		this.amqpTemplate.convertAndSend(exchangeName, routingKey, message);
	}


	/**
	 * @Description 发送消息
	 * @Author     liangjl
	 * @Date      2018年6月4日 下午6:35:36
	 * @param object
	 * @param queueName 参数
	 * @return void 返回类型
	 */
	fun sendMessage(`object`: Object, queueName: String) {
		//序列化对象
		this.amqpTemplate.convertAndSend(queueName, SerializationUtils.serialize(`object`));
	}


	/**
	 * @Description basic
	 * @Author        liangjl
	 * @Date        2018年6月4日 下午8:11:56
	 * @param @param message
	 * @param @param channel
	 * @param @param tipMsg 参数
	 * @return void 返回类型
	 */
	fun basic(message: Message, channel: Channel, tipMsg: String) {
		try {
			// 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (e: IOException) {
			log.error("【" + tipMsg + "】RabbitMQ消费者执行,确认回答出现异常，异常信息为：" + e.message);
			try {
				// ack返回false，并重新回到队列，
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
			} catch (e1: IOException) {
				e1.printStackTrace();
			}
		} finally {
			try {
				//释放资源
				if (channel != null) {
					channel.close();
				}
			} catch (e: Exception) {
				e.printStackTrace();
			}
		}
	}
	
	 

}