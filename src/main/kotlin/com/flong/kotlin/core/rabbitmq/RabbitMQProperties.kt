package com.flong.kotlin.core.rabbitmq

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

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