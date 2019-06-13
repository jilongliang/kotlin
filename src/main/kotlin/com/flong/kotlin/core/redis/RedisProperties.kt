package com.flong.kotlin.core.redis

import org.springframework.context.annotation.Configuration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@Configuration
@ConfigurationProperties(prefix = "redis")
open  class RedisProperties {
	
	// Redis服务器地址
	var host: String = "192.168.1.133"
	// Redis服务器连接端口
	var port: Int = 6739
	// Redis服务器连接密码（默认为空）
	var password: String = ""
	// 连接超时时间（毫秒）
	var timeout: Int = 5000
	// 连接超时时间（毫秒）
	var database: Int = 1
	// 连接池最大连接数（使用负值表示没有限制）
	var maxTotal: Int = 8
	// 连接池最大阻塞等待时间（使用负值表示没有限制）
	var maxWaitMillis: Int = 1
	// 连接池中的最大空闲连接
	var maxIdle: Int = 8
	// 连接池中的最小空闲连接
	var minIdle: Int = 8
}