package com.flong.kotlin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.view.InternalResourceViewResolver

/**
 * @Description    启动SpringBoot的主线程类
 * @ClassName    Application.kt
 * @Date        2018年3月11日 下午6:52:08
 * @Author        liangjl
 * @Copyright (c) All Rights Reserved, 2018.
 */
@EnableAsync
@Configuration
@EnableScheduling
@EnableAutoConfiguration  //启用读取配置
@ComponentScan("com.flong.kotlin")  //扫描com.flong文件目录下
@SpringBootApplication(scanBasePackages = ["com.flong.kotlin"] )
//@SpringBootApplication(scanBasePackages = arrayOf("com.flong.kotlin")) 这种写法也OK
open class Application {


	@Bean
	open fun jspViewResolver(): InternalResourceViewResolver {
		var resolver = InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	//静态类
	companion object {

		/**启动SpringBoot的主入口.*/
		@JvmStatic fun main(args: Array<String>) {
			//*args的星号表示引用相同类型的变量
			SpringApplication.run(Application::class.java, *args)

		}
	}


}


