package com.flong.kotlin.core.rabbitmq

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder


/**
 * @Description 启动RabbitMQ的配置处理
 * @ClassName RabbitMQTemplateClient
 * @Date 2018年8月10日 下午5:22:54
 * @Author liangjl
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Component
@Configuration
@EnableConfigurationProperties(RabbitMQProperties::class)
open class RabbitMQConfiguration {

	@Autowired lateinit var rabbitMQProperties:RabbitMQProperties
	
	// MQ的主机
	var host: String = ""
	// 主机端口号
	var port: Int = 5672
	// 用户名
	var username: String = ""
	// 密码
	var password: String = ""
	// 虚拟主机路径
	/**EnableConfigurationProperties支持下滑线读取配置的属性,Value注解不支持 */
	var virtualHost: String = ""
	
	//无参数构造方法
	constructor()
	
	//有参数的构造方法
	constructor(host: String,port: Int,username: String,password: String,virtualHost: String){
		this.host = host
		this.port = port
		this.username = username
		this.password =password
		this.virtualHost= virtualHost
	}
	
	
	
	/**
	 * 在创建了多个ConnectionFactory时，必须定义RabbitAdmin，否则无法自动创建exchange,queue
	 * @param connectionFactory
	 */
	@Bean(name = arrayOf("rabbitAdmin"))
	@Qualifier("rabbitAdmin")
	open fun rabbitAdmin(@Qualifier("connectionFactory") connectionFactory: ConnectionFactory): RabbitAdmin {
		var rabbitAdmin = RabbitAdmin(connectionFactory);
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
	}
	
	
	/**
	 * 在创建了多个ConnectionFactory 
	 * @param connectionFactory
	 */
	@Bean(name = arrayOf("connectionFactory"))
	open fun connectionFactory(): ConnectionFactory {
		var connectionFactory =  CachingConnectionFactory();
		connectionFactory.setHost(rabbitMQProperties.host);
		connectionFactory.setPort(rabbitMQProperties.port);
		connectionFactory.setUsername(rabbitMQProperties.username);
		connectionFactory.setPassword(rabbitMQProperties.password);
		connectionFactory.setVirtualHost(rabbitMQProperties.virtualHost);
		connectionFactory.setConnectionTimeout(1000 * 20);
		return connectionFactory;
	}

	
	
	
	//////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////// Exchange////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	/**
	 * @Description Topic交换器
	 * @Author liangjl
	 * @Date 2018年6月4日 下午5:49:01
	 * @return TopicExchange 返回类型
	 */
	@Bean(name = arrayOf("topicExchange"))
	open fun topicExchange(@Qualifier("rabbitAdmin") rabbitAdmin: RabbitAdmin): TopicExchange {
		var exchange = TopicExchange(RabbitConstants.TOPIC_EXCHANGE);
		exchange.setShouldDeclare(true);
		exchange.setAdminsThatShouldDeclare(rabbitAdmin);
		return exchange;
	}

	
	
	//////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////// Queue////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	
	@Bean(name = arrayOf("userQueue"))
	open fun userQueue(@Qualifier("rabbitAdmin")  rabbitAdmin:RabbitAdmin):Queue {
		// 队列持久
		var queue = Queue(RabbitConstants.USER_QUEUE, true);
		queue.setAdminsThatShouldDeclare(rabbitAdmin);
		queue.setShouldDeclare(true);
		return queue;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////// Binding////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	
	
	
	@Bean
	open fun bindingUserQueue(@Qualifier("userQueue")queue:Queue, @Qualifier("topicExchange")topicExchange:TopicExchange):Binding {
		// 路由Key
		var routeKey = RabbitConstants.USER_QUEUE_ROUTE_KEY;
		return BindingBuilder.bind(queue).to(topicExchange).with(routeKey);
	}
	
	
	
	
	
}