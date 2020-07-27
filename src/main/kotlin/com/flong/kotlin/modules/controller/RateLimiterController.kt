package com.flong.kotlin.modules.controller

import com.flong.kotlin.core.annotation.RateLimiter
import org.apache.commons.lang3.time.DateFormatUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.support.atomic.RedisAtomicInteger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("rest")
class RateLimiterController {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(RateLimiterController::class.java)
    }

    @Autowired
    private val redisTemplate: RedisTemplate<*, *>? = null

    @GetMapping(value = "/limit")
    @RateLimiter(key = "limit", time = 10, count = 1)
    fun test(): ResponseEntity<Any> {

        val date = DateFormatUtils.format(Date(), "yyyy-MM-dd HH:mm:ss.SSS")
        val limitCounter = RedisAtomicInteger("limitCounter", redisTemplate!!.connectionFactory!!)
        val str = date + " 累计访问次数：" + limitCounter.andIncrement
        log.info(str)

        return ResponseEntity.ok<Any>(str)
    }
}