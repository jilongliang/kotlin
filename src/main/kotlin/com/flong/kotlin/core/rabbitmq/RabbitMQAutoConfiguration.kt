package com.flong.kotlin.core.rabbitmq

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

/*SpringBoot精髓所在：
 * 1、在springboot启动加载大量的自动配置
 * 2、我们写好自动配置类交给EnableAutoConfiguration进行处理
 * 3、然后交给容器自动装配类添加到组件时候会从META-INF的spring.factories读取相关属性
 * 4、一般有XXProperties，这个类是处理配置的属性自动的key-value
 */
@EnableConfigurationProperties(RabbitMQProperties::class)
open class RabbitMQAutoConfiguration {
	
	@Autowired lateinit var rabbitMQProperties: RabbitMQProperties;
	
	@Bean
	@ConditionalOnMissingBean(RabbitMQConfiguration::class)
	@ConditionalOnProperty(name = arrayOf("rabbitmq.host"))
	open fun rabbitMQTemplateClient():RabbitMQConfiguration {
		return RabbitMQConfiguration(rabbitMQProperties.host,rabbitMQProperties.port,
			rabbitMQProperties.username,rabbitMQProperties.password,rabbitMQProperties.virtualHost )
	}
	
}