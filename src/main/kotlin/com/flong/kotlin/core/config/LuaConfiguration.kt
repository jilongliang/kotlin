package com.flong.kotlin.core.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.scripting.support.ResourceScriptSource
import org.springframework.data.redis.core.script.DefaultRedisScript


/**
 * User: liangjl
 * Date: 2020/7/27
 * Time: 10:01
 */
@Configuration
class LuaConfiguration {

    @Bean
    fun redisScript(): DefaultRedisScript<Number> {
        val redisScript = DefaultRedisScript<Number>()
        //设置限流lua脚本
        redisScript.setScriptSource(ResourceScriptSource(ClassPathResource("limitrate.lua")))
        //第1种写法反射转换Number类型
        //redisScript.setResultType(Number::class.java)
        //第2种写法反射转换Number类型
        redisScript.resultType = Number::class.java
        return redisScript
    }


}