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
        redisScript.setScriptSource(ResourceScriptSource(ClassPathResource("limitrate.lua")))
        redisScript.setResultType(Number::class.java)
        return redisScript
    }
}