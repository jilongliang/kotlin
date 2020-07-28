package com.flong.kotlin.core.redis

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.stereotype.Component
import redis.clients.jedis.JedisPoolConfig
import java.time.Duration

/**
 *参考博客： https://blog.csdn.net/New_CJ/article/details/84892543
 */
@Component
@EnableCaching // 启动缓存
@Configuration
@EnableConfigurationProperties(RedisProperties::class)
open class RedisTemplateDataSource : CachingConfigurerSupport {


    // Redis服务器地址
    var host: String = "192.168.1.133"
    // Redis服务器连接端口
    var port: Int = 6739
    // Redis服务器连接密码（默认为空）
    var password: String = ""
    // 连接超时时间（毫秒）
    var timeout: Int = 5000
    // 连接超时时间（毫秒）
    var database: Int = 1
    // 连接池最大连接数（使用负值表示没有限制）
    var maxTotal: Int = 8
    // 连接池最大阻塞等待时间（使用负值表示没有限制）
    var maxWaitMillis: Int = 1
    // 连接池中的最大空闲连接
    var maxIdle: Int = 8
    // 连接池中的最小空闲连接
    var minIdle: Int = 8


    //解決This type has a constructor, and thus must be initialized here異常信息
    constructor()

    //获取配置信息构造方法
    constructor(host: String, port: Int, password: String, timeout: Int, database: Int, maxTotal: Int, maxWaitMillis: Int, maxIdle: Int, minIdle: Int) {
        this.host = host
        this.port = port
        this.password = password
        this.timeout = timeout
        this.database = database
        this.maxTotal = maxTotal
        this.maxWaitMillis = maxWaitMillis
        this.maxIdle = maxIdle
        this.minIdle = minIdle
    }


    companion object {
        private val log: Logger = LoggerFactory.getLogger(RedisTemplateDataSource::class.java)
    }


    @Autowired lateinit var redisProperties: RedisProperties

    /**
     * 配置JedisPoolConfig
     * @return JedisPoolConfig实体
     */
    @Bean(name = arrayOf("jedisPoolConfig"))
    open fun jedisPoolConfig(): JedisPoolConfig {
        log.info("初始化JedisPoolConfig")
        var jedisPoolConfig = JedisPoolConfig()
        // 连接池最大连接数（使用负值表示没有限制）
        jedisPoolConfig.maxTotal = redisProperties.maxTotal
        // 连接池最大阻塞等待时间（使用负值表示没有限制）
        jedisPoolConfig.maxWaitMillis = redisProperties.maxWaitMillis.toLong()
        // 连接池中的最大空闲连接
        jedisPoolConfig.maxIdle = redisProperties.maxIdle
        // 连接池中的最小空闲连接
        jedisPoolConfig.minIdle = redisProperties.minIdle
        // jedisPoolConfig.setTestOnBorrow(true)
        // jedisPoolConfig.setTestOnCreate(true)
        // jedisPoolConfig.setTestWhileIdle(true)
        return jedisPoolConfig
    }


    /**
     * 实例化 RedisConnectionFactory 对象
     * @param poolConfig
     * @return
     */
    @Bean(name = arrayOf("jedisConnectionFactory"))
    open fun jedisConnectionFactory(@Qualifier(value = "jedisPoolConfig") poolConfig: JedisPoolConfig): RedisConnectionFactory {
        log.info("初始化RedisConnectionFactory")
        var jedisConnectionFactory = JedisConnectionFactory(poolConfig)
        jedisConnectionFactory.hostName = redisProperties.host
        jedisConnectionFactory.port = redisProperties.port
        jedisConnectionFactory.database = redisProperties.database
        return jedisConnectionFactory
    }

    /**
     *  实例化 RedisTemplate 对象
     * @return
     */
    @Bean(name = arrayOf("redisTemplateStr"))
    open fun redisTemplateStr(@Qualifier(value = "jedisConnectionFactory") factory: RedisConnectionFactory): RedisTemplate<String, String> {
        log.info("初始化RedisTemplate")
        var redisTemplate = RedisTemplate<String, String>()
        /*   redisTemplate.setConnectionFactory(factory)
        redisTemplate.setKeySerializer(StringRedisSerializer())
        redisTemplate.setHashKeySerializer(StringRedisSerializer())
        redisTemplate.setHashValueSerializer(JdkSerializationRedisSerializer())
        redisTemplate.setValueSerializer(JdkSerializationRedisSerializer())*/

        redisTemplate.connectionFactory = factory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = JdkSerializationRedisSerializer()
        redisTemplate.valueSerializer = JdkSerializationRedisSerializer()
        redisTemplate.afterPropertiesSet()
        redisTemplate.setEnableTransactionSupport(true)
        return redisTemplate
    }


    @Bean
    override
    fun cacheManager(): CacheManager {
        var redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5))
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(jedisConnectionFactory(jedisPoolConfig())))
                .cacheDefaults(redisCacheConfiguration).build()
    }


    @Bean(name = arrayOf("redisTemplate"))
    open fun redisTemplate(jedisConnectionFactory: JedisConnectionFactory): RedisTemplate<String, Any> {
        //设置序列化
        var jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(Object::class.java)
        var om = ObjectMapper()
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        jackson2JsonRedisSerializer.setObjectMapper(om)
        // 配置redisTemplate
        var redisTemplate = RedisTemplate<String, Any>()
        var stringSerializer = StringRedisSerializer()
        redisTemplate.connectionFactory = jedisConnectionFactory
        redisTemplate.keySerializer = stringSerializer // key序列化
        redisTemplate.valueSerializer = jackson2JsonRedisSerializer // value序列化
        redisTemplate.hashKeySerializer = stringSerializer // Hash key序列化
        redisTemplate.hashValueSerializer = jackson2JsonRedisSerializer // Hash value序列化
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }


}