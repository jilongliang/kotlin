package com.flong.kotlin.core.advice

import com.flong.kotlin.core.annotation.RateLimiter
import com.flong.kotlin.core.exception.CommMsgCode
import com.flong.kotlin.core.vo.ErrorResp
import com.flong.kotlin.utils.ObjectUtil
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.io.Serializable
import java.util.*
import javax.servlet.http.HttpServletRequest


@Suppress("SpringKotlinAutowiring")
@Aspect
@Configuration
class RateLimiterAspect {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(RateLimiterAspect::class.java)
        fun getIpAddr(request: HttpServletRequest): String? {
            var ipAddress: String? = null
            try {
                ipAddress = request.getHeader("x-forwarded-for")
                if (ipAddress == null || ipAddress!!.length == 0 || "unknown".equals(ipAddress!!, ignoreCase = true)) {
                    ipAddress = request.getHeader("Proxy-Client-IP")
                }
                if (ipAddress == null || ipAddress!!.length == 0 || "unknown".equals(ipAddress!!, ignoreCase = true)) {
                    ipAddress = request.getHeader("WL-Proxy-Client-IP")
                }
                if (ipAddress == null || ipAddress!!.length == 0 || "unknown".equals(ipAddress!!, ignoreCase = true)) {
                    ipAddress = request.getRemoteAddr()
                }
                // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
                if (ipAddress != null && ipAddress!!.length > 15) {
                    // "***.***.***.***".length()= 15
                    if (ipAddress!!.indexOf(",") > 0) {
                        ipAddress = ipAddress!!.substring(0, ipAddress.indexOf(","))
                    }
                }
            } catch (e: Exception) {
                ipAddress = ""
            }

            return ipAddress
        }

    }

    @Autowired
    private val redisTemplate: RedisTemplate<String, Serializable>? = null

    @Autowired
    private val redisScript: DefaultRedisScript<Number>? = null

    @Around("execution(* com.flong.kotlin.modules.controller ..*(..) )")
    @Throws(Throwable::class)
    fun interceptor(joinPoint: ProceedingJoinPoint): Any {

        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val targetClass = method.declaringClass
        val rateLimit = method.getAnnotation(RateLimiter::class.java)

        if (rateLimit != null) {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
            val ipAddress = getIpAddr(request)

            val stringBuffer = StringBuffer()
            stringBuffer.append(ipAddress).append("-")
                    .append(targetClass.name).append("- ")
                    .append(method.name).append("-")
                    .append(rateLimit!!.key)

            val keys = Collections.singletonList(stringBuffer.toString())

            val number = redisTemplate!!.execute<Number>(redisScript, keys, rateLimit!!.count, rateLimit!!.time)

            if (number != null && number!!.toInt() != 0 && number!!.toInt() <= rateLimit!!.count) {
                log.info("限流时间段内访问第：{} 次", number!!.toString())
                return joinPoint.proceed()
            }

        } else {
            var proceed = joinPoint.proceed()
            if (proceed == null) {
                return ErrorResp(CommMsgCode.SUCCESS.code!!, CommMsgCode.SUCCESS.message!!)
            }
            return joinPoint.proceed()
        }

        throw RuntimeException("已经到设置限流次数")
    }


}