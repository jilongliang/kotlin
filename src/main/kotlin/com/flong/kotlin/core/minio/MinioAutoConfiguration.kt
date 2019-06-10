package com.flong.kotlin.core.minio

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

@EnableConfigurationProperties(MinioProperties::class)
open class MinioAutoConfiguration {
	@Autowired lateinit var minioProperties: MinioProperties;
	
	@Bean
	@ConditionalOnMissingBean(MinioTemplate::class)
	@ConditionalOnProperty(name = arrayOf("minio.url"))
	open fun minioTemplate():MinioTemplate {
		return MinioTemplate(minioProperties.url,minioProperties.accessKey,minioProperties.secretKey)
	}
}