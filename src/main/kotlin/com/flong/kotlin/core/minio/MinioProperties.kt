package com.flong.kotlin.core.minio

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value

/*
 * Value注解的使用
 * https://segmentfault.com/a/1190000010978025
 * https://blog.jetbrains.com/idea/2018/10/spring-kotlin-references-in-value-annotation/
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
open class MinioProperties {
	/**
	 * minio 服务地址 http://ip:port
	 */
	var url:String=""

	/**
	 * 用户名
	 */
	var accessKey:String=""

	/**
	 * 密码
	 */
	var secretKey:String =""
	
	
}