package com.flong.kotlin.core.annotation


//@Target(ElementType.TYPE, ElementType.METHOD)
//@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RateLimiter(
        /**
         * 限流唯一标识
         * @return
         */
        val key: String = "",
        /**
         * 限流时间
         * @return
         */
        val time: Int,
        /**
         * 限流次数
         * @return
         */
        val count: Int)