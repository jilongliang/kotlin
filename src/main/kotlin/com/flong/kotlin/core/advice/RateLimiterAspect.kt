package com.flong.kotlin.core.advice

import com.flong.kotlin.core.annotation.RateLimiter
import com.flong.kotlin.core.exception.BaseException
import com.flong.kotlin.core.exception.CommMsgCode
import com.flong.kotlin.core.vo.ErrorResp
import com.flong.kotlin.utils.WebUtils
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
import java.util.*


@Suppress("SpringKotlinAutowiring")
@Aspect
@Configuration
class RateLimiterAspect {
    @Autowired lateinit var redisTemplate: RedisTemplate<String, Any>
    @Autowired var redisScript: DefaultRedisScript<Number>? = null

    /**
     * 半生对象
     */
    companion object {
        private val log: Logger = LoggerFactory.getLogger(RateLimiterAspect::class.java)
    }

    @Around("execution(* com.flong.kotlin.modules.controller ..*(..) )")
    @Throws(Throwable::class)
    fun interceptor(joinPoint: ProceedingJoinPoint): Any {

        val signature = joinPoint.signature as MethodSignature
        //获取方法名
        val method = signature.method
        //获取类
        val targetClass = method.declaringClass
        //获取方法里面的注解信息
        val rateLimit = method.getAnnotation(RateLimiter::class.java)

        if (rateLimit != null) {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
            val ipAddress = WebUtils.getIpAddr(request = request)

            val stringBuffer = StringBuffer()
            stringBuffer.append(ipAddress).append("-")
                    .append(targetClass.name).append("- ")
                    .append(method.name).append("-")
                    .append(rateLimit!!.key)

            val keys = Collections.singletonList(stringBuffer.toString())

            print(keys + rateLimit!!.count + rateLimit!!.time)
            val number = redisTemplate!!.execute<Number>(redisScript, keys, rateLimit!!.count, rateLimit!!.time)

            if (number != null && number!!.toInt() != 0 && number!!.toInt() <= rateLimit!!.count) {
                log.info("限流时间段内访问第：{} 次", number!!.toString())
                return joinPoint.proceed()
            }

        } else {
            var proceed: Any? = joinPoint.proceed() ?: return ErrorResp(CommMsgCode.SUCCESS.code!!, CommMsgCode.SUCCESS.message!!)
            return joinPoint.proceed()
        }
        throw BaseException(CommMsgCode.RATE_LIMIT.code!!, CommMsgCode.RATE_LIMIT.message!!)
    }


}