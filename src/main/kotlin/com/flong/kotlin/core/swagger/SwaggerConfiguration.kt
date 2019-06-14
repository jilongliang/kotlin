package com.flong.kotlin.core.swagger

import org.springframework.context.annotation.Bean
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import io.swagger.annotations.ApiOperation
import org.springframework.context.annotation.Configuration

/*
  * 访问路径: http://localhost:8080/swagger-ui.html#/
 */
@EnableSwagger2 // 启用Swagger
@Configuration
open class SwaggerConfiguration {

	companion object {
		const val SWAGGER_SCAN_BASE_PACKAGE = "com.flong";
		const val VERSION = "1.0.0";
	}

	@Bean
	open fun createRestApi(): Docket {
		/*return Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
			.apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))// api接口包扫描路径
			//.paths(regex("/restApi.*"))//只对restApi路径下面的所有连接请求
			.paths(PathSelectors.any())// 可以根据url路径设置哪些请求加入文档，忽略哪些请求
			.build();*/
		
		 return  Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation::class.java)).build();
	}

	fun apiInfo(): ApiInfo {
		return ApiInfoBuilder().title("Swagger2 接口文档示例")// 设置文档的标题
			.description("更多内容请关注：https://github.com/jilongliang")// 设置文档的描述->1.Overview
			.version(VERSION)// 设置文档的版本信息-> 1.1 Version information
			.contact("liangjl")
			.termsOfServiceUrl("www.apache.org")// 设置文档的License信息->1.3 License information
			.build();
	}
	
}