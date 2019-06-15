# 1、Redis是简介
*  [redis官方网](https://redis.io/)

*  Redis是一个开源的使用ANSI [C语言](http://baike.baidu.com/view/1219.htm)编写、支持网络、可基于内存亦可持久化的日志型、Key-Value[数据库](http://baike.baidu.com/view/1088.htm)，并提供多种语言的API。从2010年3月15日起，Redis的开发工作由VMware主持。从2013年5月开始，Redis的开发由Pivotal赞助


# 2、Redis开发者
*  redis 的作者，叫Salvatore Sanfilippo，来自意大利的西西里岛，现在居住在卡塔尼亚。目前供职于Pivotal公司。他使用的网名是antirez。

# 3、Redis安装
* Redis安装与其他知识点请参考几年前我编写文档 ，这里不做太多的描述，主要讲解在kotlin+SpringBoot然后搭建Redis与遇到的问题
> [Redis详细使用说明书.pdf](https://github.com/jilongliang/kotlin/blob/dev-redis/src/main/resource/Redis%20Detailed%20operating%20instruction.pdf)


![image.png](https://upload-images.jianshu.io/upload_images/14586304-c465eef20f776724.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 4、Redis应该学习那些？
* 列举一些常见的内容
![Redis.png](https://upload-images.jianshu.io/upload_images/14586304-5567b7ff8752061b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 5、Redis有哪些命令
>[Redis官方命令清单](https://redis.io/commands)
* Redis常用命令
![Redis常用命令.png](https://upload-images.jianshu.io/upload_images/14586304-147851dfc3974e44.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 6、 Redis常见应用场景
![应用场景.png](https://upload-images.jianshu.io/upload_images/14586304-4ff8b4d82e79ef2d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 7、 Redis常见的几种特征
* Redis的哨兵机制
* Redis的原子性
* Redis持久化有RDB与AOF方式

# 8、Kotlin与Redis的代码实现
* ##### Redis 依赖的Jar配置
```
<!-- Spring Boot Redis 依赖 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<dependency>
	<groupId>redis.clients</groupId>
	<artifactId>jedis</artifactId>
</dependency>
```
* ##### RedisProperties代码
```
@Component
@Configuration
@ConfigurationProperties(prefix = "redis")
open  class RedisProperties {
	
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
}
```
* ##### RedisTemplateDataSource代码
```
/**
 *参考博客： https://blog.csdn.net/New_CJ/article/details/84892543
 */
@Component
@EnableCaching // 启动缓存
@Configuration
@EnableConfigurationProperties(RedisProperties::class)
open class RedisTemplateDataSource : CachingConfigurerSupport  {

	
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
	constructor(host:String,port:Int,password: String ,timeout: Int,database: Int,maxTotal: Int ,maxWaitMillis: Int,maxIdle: Int,minIdle: Int ){
		this.host= host
		this.port = port
		this.password= password
		this.timeout= timeout
		this.database=database
		this.maxTotal=maxTotal
		this.maxWaitMillis=maxWaitMillis
		this.maxIdle=maxIdle
		this.minIdle=minIdle
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
		log.info("初始化JedisPoolConfig");
		var jedisPoolConfig = JedisPoolConfig();
		// 连接池最大连接数（使用负值表示没有限制）
		jedisPoolConfig.setMaxTotal(redisProperties.maxTotal);
		// 连接池最大阻塞等待时间（使用负值表示没有限制）
		jedisPoolConfig.setMaxWaitMillis(redisProperties.maxWaitMillis.toLong());
		// 连接池中的最大空闲连接
		jedisPoolConfig.setMaxIdle(redisProperties.maxIdle);
		// 连接池中的最小空闲连接
		jedisPoolConfig.setMinIdle(redisProperties.minIdle);
		// jedisPoolConfig.setTestOnBorrow(true);
		// jedisPoolConfig.setTestOnCreate(true);
		// jedisPoolConfig.setTestWhileIdle(true);
		return jedisPoolConfig;
	}


	/**
	 * 实例化 RedisConnectionFactory 对象
	 * @param poolConfig
	 * @return
	 */
	@Bean(name = arrayOf("jedisConnectionFactory"))
	open fun jedisConnectionFactory(@Qualifier(value = "jedisPoolConfig") poolConfig: JedisPoolConfig): RedisConnectionFactory {
		log.info("初始化RedisConnectionFactory");
		var jedisConnectionFactory = JedisConnectionFactory(poolConfig);
		jedisConnectionFactory.hostName=redisProperties.host
		jedisConnectionFactory.port=redisProperties.port
		jedisConnectionFactory.database=redisProperties.database
		return jedisConnectionFactory;
	}

	/**
	 *  实例化 RedisTemplate 对象
	 * @return
	 */
	@Bean(name = arrayOf("redisTemplateStr"))
	open fun redisTemplateStr(@Qualifier(value = "jedisConnectionFactory") factory: RedisConnectionFactory): RedisTemplate<String, String> {
		log.info("初始化RedisTemplate");
		var redisTemplate = RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setKeySerializer(StringRedisSerializer());
		redisTemplate.setHashKeySerializer(StringRedisSerializer());
		redisTemplate.setHashValueSerializer(JdkSerializationRedisSerializer());
		redisTemplate.setValueSerializer(JdkSerializationRedisSerializer());
		redisTemplate.afterPropertiesSet();
		redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate;
	}


    @Bean
	override
	fun cacheManager() : CacheManager{ 
        var redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5));
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(jedisConnectionFactory(jedisPoolConfig())))
               .cacheDefaults(redisCacheConfiguration).build();
    }
 
	
	@Bean(value = arrayOf("redisTemplate"))
	open fun redisTemplate(jedisConnectionFactory: JedisConnectionFactory): RedisTemplate<String, Any> {
		//设置序列化
		var jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(Object::class.java);
		var om = ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		// 配置redisTemplate
		var redisTemplate = RedisTemplate<String, Any>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		var stringSerializer = StringRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer); // key序列化
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer); // value序列化
		redisTemplate.setHashKeySerializer(stringSerializer); // Hash key序列化
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer); // Hash value序列化
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
```
* #### RedisAutoConfiguration代码
```
@EnableConfigurationProperties(RedisProperties::class)
open class RedisAutoConfiguration {
	
	@Autowired lateinit var redisProperties: RedisProperties;
	
	@Bean
	@ConditionalOnMissingBean(RedisTemplateDataSource::class)
	@ConditionalOnProperty(name = arrayOf("redis.host"))
	open fun redisTemplateDataSource():RedisTemplateDataSource {
		return RedisTemplateDataSource(redisProperties.host,redisProperties.port,redisProperties.password,
			redisProperties.timeout,redisProperties.database,redisProperties.maxTotal,
			redisProperties.maxWaitMillis,redisProperties.maxIdle,redisProperties.minIdle)
	}
}
```
* #### META-INF的spring.factories配置
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.flong.kotlin.core.redis.RedisAutoConfiguration
```
* #### Controller代码
```
@Autowired lateinit var stringRedisTemplate: StringRedisTemplate
//简单的缓存测试
	@RequestMapping("/getUserByRedis/{userId}")
	fun getUserByRedis(@PathVariable("userId") userId: Long) {
		var redis_key 	= USER_REDIS_KEY + "_" + userId;
		var user		= stringRedisTemplate.opsForValue().get(redis_key)
		if (user == null) {
			var userObj  = userService.getUserId(userId)
			stringRedisTemplate.opsForValue().set(redis_key, userObj.toString())
			print("从DB获取----" + JSON.toJSONString(userObj));

		} else {
			print("从缓存获取----" + JSON.toJSONString(user));
		}

	}
```
> [参考Springboot2.0 使用redis @cacheable等注解缓存博客](https://blog.csdn.net/New_CJ/article/details/84892543)

# 9 、工程架构与源代码
> 工程基础架构 [Kotlin +SpringBoot + MyBatis完美搭建最简洁最酷的前后端分离框架](https://www.jianshu.com/p/0acd593fd11e)

> [Kotlin+SpringBoot与Redis整合工程源代码](https://github.com/jilongliang/kotlin/tree/dev-redis)


# 10 、总结与建议
* 1 、以上问题根据搭建 kotlin与Redis实际情况进行总结整理，除了技术问题查很多网上资料，通过自身进行学习之后梳理与分享。

* 2、 在学习过程中也遇到很多困难和疑点，如有问题或误点，望各位老司机多多指出或者提出建议。本人会采纳各种好建议和正确方式不断完善现况，人在成长过程中的需要优质的养料。

* 3、 希望此文章能帮助各位老铁们更好去了解如何在 kotlin上搭建RabbitMQ，也希望您看了此文档或者通过找资料进行手动安装效果会更好。

> 备注：此文章属于本人原创,欢迎转载和收藏.

