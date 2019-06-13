package com.flong.kotlin.core.redis

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean

@EnableConfigurationProperties(RedisProperties::class)
open class RedisAutoConfiguration {
	
	@Autowired lateinit var redisProperties: RedisProperties;
	
	@Bean
	@ConditionalOnMissingBean(RedisTemplateDataSource::class)
	@ConditionalOnProperty(name = arrayOf("redis.host"))
	open fun redisTemplateDataSource():RedisTemplateDataSource {
		return RedisTemplateDataSource(redisProperties.host,redisProperties.port,redisProperties.password,
			redisProperties.timeout,redisProperties.database,redisProperties.maxTotal,
			redisProperties.maxWaitMillis,redisProperties.maxIdle,redisProperties.minIdle)
	}
	
}