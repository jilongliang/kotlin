package com.flong.kotlin.core.config

import com.flong.kotlin.core.web.XssFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.Filter


/**
 * XSS 拦截配置
 */
@Configuration
open class XssConfiguration {

    /**
     * xss 过滤拦截器
     */
    @Bean
    open fun xssFilterRegistrationBean(): FilterRegistrationBean<Filter>  {
        var filterRegistrationBean: FilterRegistrationBean<Filter>  = FilterRegistrationBean(XssFilter())
        filterRegistrationBean.order = 1
        filterRegistrationBean.isEnabled = true
        filterRegistrationBean.addUrlPatterns("/*")
        // 过滤
        var initParameters = HashMap<String, String>()
        initParameters["excludes"] = "/*/favicon.ico,static/*,/assets/*,/verifyImage"
        initParameters["isIncludeRichText"] = "true"
        filterRegistrationBean.initParameters = initParameters
        return filterRegistrationBean
    }


}
