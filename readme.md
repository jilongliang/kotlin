# 1、前言
* RabbitMQ是一个开源的AMQP实现，服务器端用Erlang语言编写，支持多种客户端，如：Python、Ruby、.NET、Java、JMS、C、PHP、ActionScript、XMPP、STOMP等，支持AJAX。用于在分布式系统中存储转发消息，在易用性、扩展性、高可用性等方面表现不俗。

# 2、RabbitMQ安装
* RabbitMQ安装过程请参考[CentOS-7下安装rabbitMQ-3.7.3]()

# 3、RabbitMQ协议
* RabbitMQ是基于AMQP协议，即Advanced Message Queuing Protocol，高级消息队列协议，是应用层协议的一个开放标准，为面向消息的中间件设计。消息中间件主要用于组件之间的解耦，消息的发送者无需知道消息使用者的存在，反之亦然。 AMQP的主要特征是面向消息、队列、路由（包括点对点和发布/订阅）、可靠性、安全。

# 4、为什么要用RabbitMQ？
* 1、当处理一些第三方的接口的时候可以选择它，在处理订单的时候较为常见。就用亲身经历参与的开发过的一个物流撮合平台来说，当初就大量使用MQ处理不同业务的场景。

* 2、订单系统和库存系统高耦合

* 3、流量削峰，比如：秒杀活动，流量过大时，容易导致应用挂掉,为了解决这个问题，一般在应用加入消息队列来缓解短时间的负载

* 4、拥有异步处理机制.

* 5、MQ拥有良好的队列算法，有先进先出的特性，在处理第三方下单和处理延迟取消订单的等更好不过了，可以减少传统做法扫描全表取消订单。

> **生活场景**： 消息队列（Message、Queue）就好比广州的brt的公交车、每天的车（来来来往往）都会在一条道路上面行走偶遇、而这个道路类似MQ的Channel（io管道）当红绿灯的红灯亮的时候、道路就开始拥挤阻塞、当绿灯亮起的时、排在最前车辆会优先行驶走出这条道路、这就是MQ的先进先出的特性原则、除非开车的人不按常规行驶、这时运气不好的话可能会进入try、catch处理。

* ![生活场景](https://upload-images.jianshu.io/upload_images/14586304-14aa277e2c9f8a49.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 5、与MQ技术有哪些
![MQ.png](https://upload-images.jianshu.io/upload_images/14586304-3566d13c907f1846.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 6、Kotlin代码实现

* #### RabbitM依赖jar
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```
* 关键代码类
![配置类](https://upload-images.jianshu.io/upload_images/14586304-cd5f916969b0cc0c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* #### RabbitMQProperties.kt 代码
>EnableConfigurationProperties支持下滑线读取配置的属性,Value注解不支持
```
@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
open class RabbitMQProperties {
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
}
```
* #### RabbitConstants.kt 代码
```
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
```
* #### RabbitTemplateClient 代码
```
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
```
* #### RabbitMQConfiguration代码
```
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
	 * 在创建了多个ConnectionFactory时，必须定义RabbitAdmin，
否则无法自动创建exchange,queue
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

	
	
	
	///////////////////////////////////////////////////////
	//////////////////////// Exchange ////////////
	/////////////////////////////////////////////////////
	/**
	 * @Description Topic交换器
	 * @Author liangjl
	 * @Date 2018年6月4日 下午5:49:01
	 * @return TopicExchange 返回类型
	 */
	@Bean(name = arrayOf("topicExchange"))
	open fun topicExchange(@Qualifier("rabbitAdmin") rabbitAdmin: RabbitAdmin): T
opicExchange {
		var exchange = TopicExchange(RabbitConstants.TOPIC_EXCHANGE);
		exchange.setShouldDeclare(true);
		exchange.setAdminsThatShouldDeclare(rabbitAdmin);
		return exchange;
	}

	///////////////////////////////////////////////////////
	//////////////////////// Queue ////////////////
	/////////////////////////////////////////////////////
	
	@Bean(name = arrayOf("userQueue"))
	open fun userQueue(@Qualifier("rabbitAdmin")  rabbitAdmin:RabbitAdmin):
Queue {
		// 队列持久
		var queue = Queue(RabbitConstants.USER_QUEUE, true);
		queue.setAdminsThatShouldDeclare(rabbitAdmin);
		queue.setShouldDeclare(true);
		return queue;
	}

	///////////////////////////////////////////////////////
	//////////////////////// Binding////////////////
	/////////////////////////////////////////////////////
	
	@Bean
	open fun bindingUserQueue(@Qualifier("userQueue")queue:Queue, 
@Qualifier("topicExchange")topicExchange:TopicExchange):Binding {
		// 路由Key
		var routeKey = RabbitConstants.USER_QUEUE_ROUTE_KEY;
		return BindingBuilder.bind(queue).to(topicExchange).with(routeKey);
	}
	
}
```
* #### RabbitMQAutoConfiguration代码
> 【SpringBoot精髓所在】：
 1、在springboot启动加载大量的自动配置
  2、我们写好自动配置类交给EnableAutoConfiguration进行处理
 3、然后交给容器自动装配类添加到组件时候会从META-INF的spring.factories读取相关属性
  4、一般有XXProperties，这个类是处理配置的属性自动的key-value
```
@EnableConfigurationProperties(RabbitMQProperties::class)
open class RabbitMQAutoConfiguration {
	
	@Autowired lateinit var rabbitMQProperties: RabbitMQProperties;
	
	@Bean
	@ConditionalOnMissingBean(RabbitMQConfiguration::class)
	@ConditionalOnProperty(name = arrayOf("rabbitmq.host"))
	open fun rabbitMQTemplateClient():RabbitMQConfiguration {
		return RabbitMQConfiguration(rabbitMQProperties.host,
rabbitMQProperties.port,
			rabbitMQProperties.username,rabbitMQProperties.password
,rabbitMQProperties.virtualHost )
	}
}
```
* #### RabbitMq连接配置
```
rabbitmq.host=127.0.0.1
rabbitmq.port=5672
rabbitmq.username=admin
rabbitmq.password=admin
rabbitmq.virtual-host=/rabbitmq
```

* #### Provider提供者或生产者
```
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
```
* #### Consumer消费者
```
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
```
* ####  Controller代码
```
@Autowired private lateinit var userProviderService :UserProviderService
//rabbitMq简单测试
	@RequestMapping("/rabbitMq/{userId}")
    fun rabbitMq(@PathVariable("userId") userId:Long ){
		var user = userService.getUserId(userId);
		userProviderService.syncUserQueue(user.toString())
    }
```
* ####  运行结果
* ![运行结果.png](https://upload-images.jianshu.io/upload_images/14586304-5cbadcb0cd3a1a96.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 7 、工程架构与源代码
> 工程基础架构 [Kotlin +SpringBoot + MyBatis完美搭建最简洁最酷的前后端分离框架](https://www.jianshu.com/p/0acd593fd11e)

> [Kotlin+SpringBoot与RabbitMQ整合工程源代码](https://github.com/jilongliang/kotlin/tree/dev-rabbitmq)


# 8 、总结与建议
1 、以上问题根据搭建 kotlin与RabbitMQ实际情况进行总结整理，除了技术问题查很多网上资料，通过自身进行学习之后梳理与分享。

2、 在学习过程中也遇到很多困难和疑点，如有问题或误点，望各位老司机多多指出或者提出建议。本人会采纳各种好建议和正确方式不断完善现况，人在成长过程中的需要优质的养料。

3、 希望此文章能帮助各位老铁们更好去了解如何在 kotlin上搭建RabbitMQ，也希望您看了此文档或者通过找资料进行手动安装效果会更好。

> 备注：此文章属于本人原创,欢迎转载和收藏.

