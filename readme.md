
## 一、为什么学习（初衷）：
* 1、经过调研很多培训机构大量投入课程的产出,说明在新一代的编程领域有一定的地位，它前程应用一定会有更好的广泛的使用。
 * 2、阿里p3c扫描代码ReView插件底层大量使用了Kotlin进行实际开发
 * 3、经过调研SpringBoot2.以上的全家桶很多组件底层框架和Spring5.x版本用到kotlin支持开发(拥抱Kotlin)。
 * 4、在2017年在朋友圈偶遇它的影子。
*  5、2018年准备学习kotlin相关的热身工作，在学习过程中，把牺牲个人周末休息时间，尝试把公司的Spring Cloud工程改写成Kotlin框架。
*  6、当然了现在学习和了解它，不一定现在就要用它，主要目的是为以后做准备。
*  7、作为技术人员，希望为kotlin的生态作出一份渺小的贡献。
![Spring5.x暗示什么呢？](https://upload-images.jianshu.io/upload_images/14586304-301e181a168f86c0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![Kotlin&Spring5.x](https://upload-images.jianshu.io/upload_images/14586304-b66d0a7a8e028de6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 二、技术选型
 ![Kotlin框架.png](https://upload-images.jianshu.io/upload_images/14586304-3b5c6736f45dc432.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* 解析器：JSOUP、FastJSON
* 开发工具：JDK1.8 、Maven 、Eclipse
* 技术框架：SpringBoot
* ORM技术：MyBatisPlus
* 数据库：MySQL
* Apache 工具：HttpClient、Lang3
* Git代码版本控制



## 三、kotlin背景简要描述
* Kotlin 是一种在 Java 虚拟机上运行的静态类型编程语言，被称之为 Android 世界的Swift，由 JetBrains 设计开发并开源。Kotlin 可以编译成Java字节码，也可以编译成 JavaScript，方便在没有 JVM 的设备上运行。
*  **立太子**：在Google I/O 2017中，Google 宣布 Kotlin 成为 Android 官方开发语言
* Kotlin支持表达式语法编程非常友好,以简洁代码等众多方面强大功能，吸引很多开发者的青睐。
> [官方网站](https://kotlinlang.org/)
[kotlin手册](https://kotlinlang.org/docs/tutorials/) 

## 四、Spring Boot 发展路线简要描述
* 随着动态语言的流行 (Ruby、Groovy、Scala、Node.js)，Java 的开发显得格外的笨重：繁多的配置、低下的开发效率、复杂的部署流程以及第三方技术集成难度大。
* 在上述环境下，Spring Boot 应运而生。它使用“习惯优于配置”（项目中存在大量的配置，此外还内置了一个习惯性的配置，让你无需手动进行配置）的理念让你的项目快速的运行起来。使用 Spring Boot 很容易创建一个独立运行（运行 Jar，内嵌 Servlet 容器）准生产级别的基于 Spring框架的项目，使用 Spring Boot 你可以不用或者只需很少的 Spring 配置。

### 4.1 SpringBoot插件使用 
* spring-boot-starter-actuator    actuator是监控系统健康情况的工具
* spring-boot-devtools			实现热部署,实际开发过程中，修改应用的业务逻辑时常常需要重启应用，这显得非常繁琐，降低了开发效率，所以热部署对于开发来说显得十分必要了
* spring-boot-starter-aop	此插件没什么好说的了,aop是spring的两大功能模块之一，功能非常强大，为解耦提供了非常优秀的解决方案。如：面向方面编程
* spring-boot-starter-tomcat  	spring boot 内置Tomcat插件
* spring-boot-starter-test  		测试工具
* mybatis-spring-boot-starter 	spring boot整合MyBatis的jar
* spring-boot-maven-plugin    	Spring Boot Maven plugin能够将Spring Boot应用打包为可执行的jar或war文件，然后以通常的方式运行Spring Boot应用。 

## 五、Kotlin插件

* kotlin-stdlib-jdk8	这个是kotlin的标准jar，也就是最基础的一个工程，底层封装了大量kotlin的语法和实现调用逻辑，也是最复杂的一个工程‘引入工程会出现这三个jar工程
* kotlin-stdlib-jdk8-1.2.20.jar
* kotlin-stdlib-1.2.20.jar
* kotlin-stdlib-jdk7-1.2.20.jar

* kotlin-reflect     reflect 顾名思义就是反射工程了

## 六、jsoup简要
* jsoup 是一款Java 的HTML解析器，可直接解析某个URL地址、HTML文本内容。它提供了一套非常省力的API，可通过DOM，CSS以及类似于jQuery的操作方法来取出和操作数据。
*  个人认为jsoup是`最好的解析器`，在很多场景都能见到他的影子,不单只可以解析HTML所有结构，还可以解析XML，在做爬虫器最为广泛。
> [官方网站](https://jsoup.org/)  https://jsoup.org/
>[深入理解Jsoup解析器API与实际运用](https://www.jianshu.com/p/e036ba0b3acc)


## 七、fastJson
* 阿里JSON解析器
>详细文档请看官方 https://github.com/alibaba/fastjson

## 八、HttpClient
* HttpClient 是Apache Jakarta Common 下的子项目，可以用来提供高效的、最新的、功能丰富的支持 HTTP 协议的客户端编程工具包，并且它支持 HTTP 协议最新的版本。
>[官方网站](http://hc.apache.org/httpcomponents-client-ga/)



## 九、Maven
   * Apache Maven是一个软件项目管理和理解工具。 基于项目对象模型（POM）的概念，Maven可以从中央信息管理项目的构建，报告和文档。

* 官方网：http://maven.apache.org/
* 学习博客： https://yq.aliyun.com/articles/28591  

## 十、MyBatis-Plus 
* 为简化开发而生，官方文档 [https://mp.baomidou.com/](https://mp.baomidou.com/)


## 十一、工程准备

* 方法一、打开Eclipse Marketplace-->>输入kotlin在线安装即可
![Kotlin在线安装](https://upload-images.jianshu.io/upload_images/14586304-b981546772d7b10a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* 方法二、下载离线安装包（不提供教程，说明有这种方式）
![工程选择插件安装](https://upload-images.jianshu.io/upload_images/14586304-9b16ebeb924e7f2f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* 方法三、可以使用优秀的kotlin编辑器eg: **[IntelliJ IDEA]([https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/)
)**

## 十二、工程结构

![工程结构1](https://upload-images.jianshu.io/upload_images/14586304-2d5ab080ce3ae9bc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![工程结构2](https://upload-images.jianshu.io/upload_images/14586304-bbab634b5aaec5f2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![工程结构3](https://upload-images.jianshu.io/upload_images/14586304-af4887719ace2510.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 十三、工程代码结构
* SpringBoot入口类
 ```
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

 ```
* #### Maven 的pom依赖的jar工程配置
 > [Maven 的pom依赖的jar工程配置](https://github.com/jilongliang/kotlin/blob/master/pom.xml)

* #### WebCofig工具类统一处理配置
* **消息转换器,中文乱码，Long的精度长度问题，时间格式等问题**
*  cors 跨域支持 可以用@CrossOrigin在controller上单独设置
*  统一处理请求URL拦截器
```
@Configuration
@ConditionalOnClass(WebMvcConfigurer::class)
@Order(Ordered.HIGHEST_PRECEDENCE)
open class WebConfig : WebMvcConfigurer{

	constructor() : super()

	@Bean
	open fun customConverters(): HttpMessageConverters {
		//创建fastJson消息转换器
		var fastJsonConverter = FastJsonHttpMessageConverter()
		//创建配置类
		var fastJsonConfig = FastJsonConfig()
		//修改配置返回内容的过滤
		fastJsonConfig.setSerializerFeatures(
				// 格式化
				SerializerFeature.PrettyFormat,
				// 可解决long精度丢失 但会有带来相应的中文问题
				//SerializerFeature.BrowserCompatible,
				// 消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
				SerializerFeature.DisableCircularReferenceDetect,
				// 是否输出值为null的字段,默认为false
				SerializerFeature.WriteMapNullValue,
				// 字符类型字段如果为null,输出为"",而非null
				SerializerFeature.WriteNullStringAsEmpty,
				// List字段如果为null,输出为[],而非null
				SerializerFeature.WriteNullListAsEmpty
		)
		// 日期格式
		fastJsonConfig.dateFormat = "yyyy-MM-dd HH:mm:ss"
		
		// long精度问题
		var serializeConfig = SerializeConfig.globalInstance
		serializeConfig.put(Integer::class.java, ToStringSerializer.instance)
		serializeConfig.put(BigInteger::class.java, ToStringSerializer.instance)
		serializeConfig.put(Long::class.java, ToStringSerializer.instance)
		serializeConfig.put(Long::class.javaObjectType, ToStringSerializer.instance)
		fastJsonConfig.setSerializeConfig(serializeConfig)
		
		//处理中文乱码问题
		var fastMediaTypes = ArrayList<MediaType>()
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8)
		fastMediaTypes.add(MediaType(MediaType.TEXT_HTML, Charset.forName("UTF-8")))
		fastMediaTypes.add(MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8")))
		fastMediaTypes.add(MediaType(MediaType.APPLICATION_FORM_URLENCODED, Charset.forName("UTF-8")))
		fastMediaTypes.add(MediaType.MULTIPART_FORM_DATA)
		
		fastJsonConverter.setSupportedMediaTypes(fastMediaTypes)
		fastJsonConverter.setFastJsonConfig(fastJsonConfig)
		//将fastjson添加到视图消息转换器列表内
		return HttpMessageConverters(fastJsonConverter)
	}

	/**
	 * 拦截器
	 */
	open override fun addInterceptors(registry: InterceptorRegistry) {
		//registry.addInterceptor(logInterceptor).addPathPatterns("/**")
		//registry.addInterceptor(apiInterceptor).addPathPatterns("/**")
	}

	/**
	 * cors 跨域支持 可以用@CrossOrigin在controller上单独设置
	 */
	open override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**")
				//设置允许跨域请求的域名
				.allowedOrigins("*")
				//设置允许的方法
				.allowedMethods("*")
				//设置允许的头信息
				.allowedHeaders("*")
				//是否允许证书 不再默认开启
				.allowCredentials(java.lang.Boolean.TRUE)
	}
}
```
* BaseServcie的**业务逻辑基类**的封装
* 【注意】：BaseServcie里面必须要加上构造方法 **constructor() : super()**实现ServiceImpl底层的代码，但有些不需要你实现，来解决内部报 **(This type has a constructor, and thus must be initialized here)** 错误
* 截图 ![ServiceImpl底层的代码，](https://upload-images.jianshu.io/upload_images/14586304-4a98c5f9bf84251a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* 单冒号的作用：在Kotlin编程里面单个冒号**代表实现接口或继承父类接口的作用**。如下代码：
```
//BaseService实现ServiceImpl
BaseService<M : BaseMapper<T>, T, Q : Query> : ServiceImpl<M, T> {
```
```
open class BaseService<M : BaseMapper<T>, T, Q : Query> : ServiceImpl<M, T> {
	//构造方法
	constructor() : super()
	var IN_SIZE: Int = 1000;
	//新增
	fun add(obj: T): Boolean {
		var affCnt = baseMapper.insert(obj);
		return null != affCnt && affCnt > 0;
	}
       //更加id去更新数据
	override fun updateById(obj: T): Boolean {
		var affCnt = baseMapper.updateById(obj);
		return null != affCnt && affCnt > 0;
	}
	/**
	 * 删除
	 */
	override fun deleteById(id: Serializable): Boolean {
		var affCnt = baseMapper.deleteById(id);
		return null != affCnt && affCnt > 0;
	}
	/**
	 * ID 取对象,取不到为空
	 */
	fun get(id: Serializable): T {
		return baseMapper.selectById(id);
	}
	fun getMapper(): M {
		return this.baseMapper;
	}
	fun buildQuery(query: Q): EntityWrapper<T> {
		return EntityWrapper<T>();
	}
}
```
* #### SpringBoot与**Druid**配置数据库连接池数据源
* > [Druid 的GitHub](https://github.com/alibaba/druid)
* > **[Druid常见问与解答](https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98
)**

```
@Configuration
@MapperScan(basePackages = arrayOf(DataSourceConfig.PACKAGE), sqlSessionFactoryRef = "sessionFactory")
open class DataSourceConfig  {
	//静态常量
	companion object {
		//const 关键字用来修饰常量，且只能修饰  val，不能修饰var,  companion object 的名字可以省略，可以使用 Companion来指代
         const val  PACKAGE = "com.flong.kotlin.*.mapper";
		 const val TYPEALIASESPACKAGE = "com.flong.kotlin.modules.entity";
    }

	final var MAPPERLOCATIONS = "classpath*:mapper/*.xml";
	@Primary
	@Bean("dataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	open fun dataSource(): DataSource {
		return DruidDataSource();
	}


	@Bean
	open fun transactionManager(@Qualifier("dataSource") dataSource: DataSource): DataSourceTransactionManager {
		return DataSourceTransactionManager(dataSource);
	}

	@Bean
	open fun sessionFactory(dataSource: DataSource):SqlSessionFactory {
		//===1.mybatis-plus globalConfig配置
	    var cfg =  GlobalConfiguration();
		
	    // 字段的驼峰下划线转换
	    cfg.setDbColumnUnderline(true);
	    // 全局主键策略
	    cfg.setIdType(IdType.AUTO.key);
	    // 全局逻辑删除
		cfg.sqlInjector 		= LogicSqlInjector()
		cfg.logicDeleteValue 	= "1"
		cfg.logicNotDeleteValue = "0"
	
	    //===2.构造sessionFactory(mybatis-plus)
	    var sf = MybatisSqlSessionFactoryBean();
	    sf.setDataSource(dataSource);
	    sf.setMapperLocations(PathMatchingResourcePatternResolver().getResources(MAPPERLOCATIONS));
	    sf.setGlobalConfig(cfg);
		sf.setTypeAliasesPackage(TYPEALIASESPACKAGE) 
	    // 分页插件
		sf.setPlugins(arrayOf(PaginationInterceptor()))
	    //请注意：这种return sf.getObject();与return sf.`object`写法完全一样,但是object是kotiln的关键字，所以要用 【单引号】引起来
		return sf.`object`
	}
	
	/**
	 * @Description 初始化druid
	 * @Author		liangjl
	 * @Date		2018年1月17日 下午4:52:05
	 * @return 参数
	 * @return ServletRegistrationBean 返回类型 
	 */
	@Bean
	open fun druidServlet() : ServletRegistrationBean<Servlet>{
		var bean:ServletRegistrationBean<Servlet> = ServletRegistrationBean(StatViewServlet(), "/druid/*") ;
		/** 初始化参数配置，initParams**/
	    //登录查看信息的账号密码.
	    bean.addInitParameter("loginUsername", "root");
	    bean.addInitParameter("loginPassword", "root");
	    //IP白名单 (没有配置或者为空，则允许所有访问)
	    bean.addInitParameter("allow", "");
	    //IP黑名单 (共存时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
	    bean.addInitParameter("deny", "192.88.88.88");
	    //禁用HTML页面上的“Reset All”功能
	    bean.addInitParameter("resetEnable", "false");
		return bean;
	}
	@Bean
	open fun filterRegistrationBean() : FilterRegistrationBean<Filter>{
		var filterRegistrationBean =   FilterRegistrationBean<Filter>()
		filterRegistrationBean.setFilter(WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}

}
```
* #### 分页工具类PageVO
```
open class PageVO<E>  {
	/**
	 * 总条数
	 */
	var total: Long? = null
	/**
	 * 查询结果
	 */
	var records: Collection<E>? = null
	/**
	 * 当前页
	 */
	var page: Int? = null
	/**
	 * 总页数
	 */
	var totalPage: Long? = null
	/**
	 * 每页显示条数
	 */
	var pageSize: Int? = null
	//init { }

	//多重构造方法
	constructor(records: Collection<E>?, pageVO: PageVO<E>){
		this.records = records;
		this.total = pageVO.total;
		this.totalPage = pageVO.totalPage;
		this.page = pageVO.page;
		this.pageSize = pageVO.pageSize;
		
	}
	//多重构造方法
	constructor(records: Collection<E>?, page: Page<Any>){
		this.records = records;
		this.total = page.getTotal();
		this.totalPage = page.getPages();
		this.page = page.getCurrent();
		this.pageSize = page.getSize();
		
	}
}
```
* #### 分页工具类PageUtil
```
open class PageUtil {
  /**
   * 取mybatis-plus分页对象
   */
  open fun getPage(query : Query):Page<Any>? {
      return Page(query.page, query.pageSize);
  }
}
```
* #### **表名与字段的映射**的JavaBean实体类
```
/**
 *data保持数据data class就是一个类中只包含一些数据字段，类似于vo,pojo,java bean。一般而言，
 *我们在Java中定义了这个数据类之后要重写一下toString，equals等方法。要生成get,set方法
 *https://www.cnblogs.com/liuliqianxiao/p/7152773.html
 *注意mybatis查询数据，然后封装实体的时候，构造方法这里有点儿坑，查询的字段必须与构造方法一直。
 */
@TableName("t_user")
data  class User constructor(
	@TableId(value = "user_id", type = IdType.ID_WORKER)
	var userId: Long?= null,//用户Id主键,IdWork生成
	var userName: String? = null,//用户名
	var passWord: String? = null,//密码
	@TableLogic
	@JSONField(name = "isDeleted")
	var isDeleted: Int? = null,//删除
	var createTime: Date? = null //创建时间,允许为空,让数据库自动生成即可
  ) :Serializable{
 
	//手动重写toString方法
	override fun toString(): String {
		return "[User(userId = $userId,userName = $userName, passWord=$passWord,isDeleted=$isDeleted,createTime=$createTime),]"
	}

	//equals
	override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as User
        if (userId != other.userId) return false
        if (userName != other.userName) return false
		if (passWord != other.passWord) return false
		if (isDeleted != other.isDeleted) return false
		if (createTime != other.createTime) return false
        return true
    }
	override fun hashCode(): Int {
        var result = userId?.hashCode() ?: 0
        result = 31 * result + (userName?.hashCode() ?: 0)
        result = 31 * result + (passWord?.hashCode() ?: 0)
        result = 31 * result + (isDeleted?.hashCode() ?: 0)
        result = 31 * result + (createTime?.hashCode() ?: 0)
        
        return result
    }

}
```
* #### MyBatis的Mapper接口
```
interface IUserMapper : BaseMapper<User>{

	//这里的?表示当前是否对象可以为空 
    //@see http://blog.csdn.net/android_gjw/article/details/78436707
	fun getUserList(query : UserQuery , page : Page<Any>): List<UserRespVo>?
 
}
```
* #### **IUserMapper对应的MyBatis的XMl**
```
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.flong.kotlin.modules.mapper.IUserMapper">
	<!-- 抽取公共字段 -->
	<sql id="find_AS_R">
	    <![CDATA[
	    	SELECT A.user_Id as userId, A.USER_NAME as userName, 
	    	A.PASS_WORD as passWord,is_deleted as isDeleted,
	    	A.create_time as createTime
	    ]]>
	</sql>

	<!-- 用户查询 -->
	<select id="getUserList" resultType="com.flong.kotlin.modules.vo.resp.UserRespVo" >
		<include refid="find_AS_R" /> FROM T_USER A 
		<where>
			<if test="userId != null">	and A.user_Id = #{userId}</if>
			<if test="userName != null and  userName !='' ">and A.USER_NAME = #{userName}</if>
		</where>
	</select>
</mapper>

```
* #### 业务Service代码**ORM**
* 对象关系映射（英语：`Object Relational Mapping，简称ORM，或O/RM，或O/R mapping`），是一种程序技术，用于实现面向对象编程语言里不同类型系统的数据之间的转换。
* 此类是用于与MyBatis的Mapper进行映射进行操作ORM操作，在这里不过多的详解ORM，在代码中看到操作几乎都是MyBatisPlus自身封装的CRUD的操作，除了分页是自定义MyBatis的XML进行处理。这里提供这样的支持，主要是为了处理对业务复杂拓展查询场景。
> [初窥理解Kotlin的ORM框架Ktorm](https://www.jianshu.com/p/927cf50782b5)

```
@Service
open class UserService : BaseService<IUserMapper,User,UserQuery>() {
	//Kotlin lateinit 和 by lazy 的区别
	//http://blog.csdn.net/Sherlbon/article/details/72769843
	@Autowired lateinit var userMapper: IUserMapper;
	//根据
	open fun queryAllUser(): List<User>? {
		var wrapper = createWrapper();
		return this.selectList(wrapper);
	}
	open fun listPage(query : UserQuery) : PageVO<UserRespVo> ? {
		var page = PageUtil().getPage(query)  ;// 设置分页
		var dataList = userMapper.getUserList(query, page!!);//page!!强制告诉编辑器不可能为空
	    
		var json = JSON.toJSONString(dataList);
		println(json)
		return  PageVO(dataList, page);// 获取分页数和总条数
	}
		
       //通过主键id进行查询
	open fun getUserId(userId: Long): User? {
		return get(userId);
	}

	//插入用户
	open fun addUser() {
		var userId = IdWorker.getId();
		var u = User(userId, "liangjl", "123456",null, Date());
		var json = JSON.toJSONString(u);
		println(json)
		add(u);
	}
	fun createWrapper(): Wrapper<User> {
		var wrapper = EntityWrapper<User>();
		wrapper.setEntity(User());//设置实体
		return wrapper;
	}

}
```
* #### Controller代码的**CRUD**的代码实现
* C - Create 对数据添加或增加操作
* R - Retrieve  对数据读取查询操作
* U - Update 对数据更新或修改操作
* D - Delete 对数据删除操作
```
@RestController
@RequestMapping("/rest")
open class UserController : BaseController(){
	
    @Autowired private lateinit var userService: UserService
 
  @RequestMapping("/list1")
    fun list1():  String{
		return "NewFile" //跳转页面
    }
	
    //添加
   @RequestMapping("/add")
    fun add():  Unit{
		userService.addUser()
    }
   //根据用户Id进行删除用户信息
   @RequestMapping("/deletedById")
    fun deletedById(userId : Long):  Unit{
		userService.deleteById(userId);
    }

   //更新用户信息，通过Id唯一主键进行操作。
   @RequestMapping("/update")
    fun update(user : User):  Unit{
		userService.updateById(user)
    }
	
	//根据Id查询用户
	@RequestMapping("/getUserId")
    fun getUserId(userId :Long):Any?{
		var user = userService.getUserId(userId);
		if(user ==null){
			var msgCode = UserMsgCode.FIND_NOT_USER;
			throw BaseException(msgCode.code!! ,msgCode.message!!)
		}
		return userService.getUserId(userId)
    }
	
   //查询所有用户信息
    @RequestMapping("/queryAllUser")
    fun queryAllUser():List<User>?{
	   return userService.queryAllUser()
    }
	
    //分页查询
    @RequestMapping("listPage")
    fun listPage(query :UserQuery) :PageVO<UserRespVo> ? {
    	   var listPage = userService.listPage(query);
           return listPage;
    }
	
    @RequestMapping("/getBody")
    fun getBody(@RequestBody jsonText:UserRespVo){
	   println(jsonText.component2())
	   println(jsonText.userName)
    }
}
```

* #### properties数据库配置
```
logging.evel.root=info
logging.evel.com.flong.kotlin=debug

# =======mybatis_config=====
 ## 指定是开发文件
spring.profiles.active=dev
#=======datasource========
#spring.datasource.name=na
spring.datasource.url=jdbc:mysql://localhost:3306/kotlin?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.initialSize=5
spring.datasource.minIdle=5
spring.datasource.maxActive=20
spring.datasource.maxWait=60000
spring.datasource.timeBetweenEvictionRunsMillis=60000
spring.datasource.minEvictableIdleTimeMillis=300000
spring.datasource.validationQuery=SELECT 1 FROM DUAL
spring.datasource.testWhileIdle=true
spring.datasource.testOnBorrow=false
spring.datasource.testOnReturn=false
spring.datasource.poolPreparedStatements=true
spring.datasource.maxPoolPreparedStatementPerConnectionSize=20
#spring.datasource.filters=stat,wall,log4j,slf4j
##配置支持【emoji表情】与【不合法输入参数】
##作用：用于处理请求与响应一些不可信的字符串
spring.datasource.connectionInitSqls=set names utf8mb4
spring.datasource.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000

spring.devtools.restart.enabled=false


spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp
 

```
* #### Apache **HttpClient的网络Http通信**工具类封装
* HttpClient的网络Http通信工具类封装**[请看下一篇文章](https://www.jianshu.com/p/5f36cd3d26c4)**
```
package com.flong.kotlin.utils
/**
 * object表示这个类是单例模式
 * @Description HttpClientUtil帮助类
 * @Author      liangjilong
 * @Date        2018年12月17日
 */
 object HttpClientUtil {
	@JvmStatic //main测试
	fun main(args: Array<String>) {
		//var b = mapOf("age" to 23, "userName" to "ljl")
		var map3 = HashMap<String,String>()
		map3.put("age","23")
		map3.put("userName","寅务")
		//var p = getConcatParams(map3,null)
		//var p = paramsToQueryString(map3)
		var reqUrl = "http://localhost:8080/rest/getBody"
		var method = "POST"
		var json = JSONObject()
		json.put("passWord","123456")
		json.put("userName","寅务")
		var header =   HashMap<String, String>()
		header.put("Content-Type", JSON_APPLICATION)
		var retMsg = 
 createHttp(reqUrl,method,json.toJSONString(),header,"utf-8")
		println("retMsg=" +retMsg)

	}
}
```
* #### LiveRequestBodyAdvice全局处理**请求Body参数**工具类
* 【请注意】：InputMessage输出流信息，这里的类和重写方法要加上$\color{red}{open}$或$\color{red}{public}$关键字修饰，因为kotlin默认是$\color{red}{private}$，不加上会报**HttpHeaders defined in ......**和**InputStream defined in ......**
```

/**
 * 针对@RequestBody请求的处理
 */
@ControllerAdvice
class LiveRequestBodyAdvice : RequestBodyAdvice {

	companion object {
		var DEFAULT_CHARSET = "UTF-8";
		private val logger: Logger = LoggerFactory.getLogger(LiveRequestBodyAdvice::class.java)
	}

	override fun handleEmptyBody(body: Any?, inputMessage: HttpInputMessage?, parameter: MethodParameter?, targetType: Type?, converterType: Class<out HttpMessageConverter<*>>?): Any? {
		return body;
	}

	override fun afterBodyRead(body: Any?, inputMessage: HttpInputMessage?, parameter: MethodParameter?, targetType: Type?, converterType: Class<out HttpMessageConverter<*>>?): Any? {
		return body;
	}

	override fun beforeBodyRead(inputMessage: HttpInputMessage?, parameter: MethodParameter?, targetType: Type?, converterType: Class<out HttpMessageConverter<*>>?): HttpInputMessage? {
		var body 	= IOUtils.toString(inputMessage?.getBody(), DEFAULT_CHARSET) as java.lang.String;
		var headers = inputMessage!!.getHeaders();
		var bis 	= ByteArrayInputStream(body.getBytes(DEFAULT_CHARSET));
		logger.info("request body params : {}", body);

		return InputMessage(headers, bis);
	}

	override fun supports(methodParameter: MethodParameter?, targetType: Type?, converterType: Class<out HttpMessageConverter<*>>?): Boolean {
		return true;
	}


	//【请注意】：InputMessage输出流信息，这里的重写方法必须要加上open，因为kotlin默认是private，不加上会报HttpHeaders defined in 
	open class InputMessage : HttpInputMessage {
		private var headers: HttpHeaders
		private var body: InputStream
		
		//构造方法
		constructor(headers: HttpHeaders, body: InputStream) {
			this.headers = headers;
			this.body = body;
		}
		open override fun getBody(): InputStream 	= this.body
		open override fun getHeaders(): HttpHeaders = this.headers
	}

}
```
* #### LiveRespBodyAdvice处理统一**响应Body的JSON体**工具类
```
@RestControllerAdvice
@Slf4j
@Order(Integer.MIN_VALUE)
class LiveRespBodyAdvice : ResponseBodyAdvice<Any> {
	
	companion object {
		//const 关键字用来修饰常量，且只能修饰  val，不能修饰var,  companion object 的名字可以省略，可以使用 Companion来指代
		 const val LIFE_PACKAGE = "com.flong.kotlin";
    }
	
	//Class<out HttpMessageConverter<*>>?) 相当于Java的  Class<? extends HttpMessageConverter<?>>
	//list :ArrayList<in Fruit>，相当于Java的 ArrayList< ? super Fruit> 
	//*，相当于Java的?
	override fun supports(methodParameter: MethodParameter?, converterType: Class<out HttpMessageConverter<*>>?): Boolean {
		//处理类型 
		var className 	= methodParameter?.getContainingClass()?.name;
		var sw 			= className?.startsWith(LIFE_PACKAGE);
		var eaf 		= ErrorResp::class.java.isAssignableFrom(methodParameter?.getParameterType());
		var laf 		= LiveResp::class.java.isAssignableFrom(methodParameter?.getParameterType());
		var saf 		= String::class.java.isAssignableFrom(methodParameter?.getParameterType());
		
		return (sw==true && !eaf && !laf && !saf);
	}
	
	override fun beforeBodyWrite(body: Any?, returnType: MethodParameter?, selectedContentType: MediaType?, selectedConverterType: Class<out HttpMessageConverter<*>>?, request: ServerHttpRequest?, response: ServerHttpResponse?): Any? {
		//?： elvis操作符(猫王）,age?.toInt()表示if(age!=null) age.toInt else 返回 默认就给10
		//var path = request?.getURI()?.getPath();
		if(body != null){
			return LiveResp(body);
		}else{
			return LiveResp("");
		}
		 
	}
}

```

* #### 统一处理**异常工具类**
```
@Slf4j
@RestControllerAdvice
class ExceptionAdvice{
	companion object {
		private val log: Logger = LoggerFactory.getLogger(ExceptionAdvice::class.java)
	}
	@ExceptionHandler(HttpRequestMethodNotSupportedException::class)
	fun onException(e : HttpRequestMethodNotSupportedException, request: HttpServletRequest): ErrorResp? {
		var uri = request.getRequestURI();
		createLog(e, uri, "找不到请求的方法");
		return ErrorResp(CommMsgCode.NOT_SUPPORTED.code!!, CommMsgCode.DB_ERROR.message!!)
	}

	//is 相当于Java的 instanceof ，as就是强制转换(对象)
	@ExceptionHandler(Exception::class)
	fun onException(e: Exception, request: HttpServletRequest): ErrorResp? {
		
		var uri 	= request.getRequestURI();
		var params 	= JSONObject.toJSONString(request.getParameterMap());
		if (e is SQLException || e is DataAccessException) {
			createLog(e, uri, params);
			return ErrorResp(CommMsgCode.DB_ERROR.code!!, CommMsgCode.DB_ERROR.message!!)

		} else if (e is BaseException) {
			var be = e as BaseException
			log.error("uri:{},params:{},code:{},message:{}", uri, params)
			return ErrorResp(be.code,be.msg)
			
		} else if (e is MissingServletRequestParameterException
				|| e is BindException
				|| e is ConstraintViolationException
				|| e is TypeMismatchException
				|| e is ServletRequestBindingException) {

			createLog(e, uri, params);
			
			return ErrorResp(CommMsgCode.PARAM_ERROR.code!!, CommMsgCode.PARAM_ERROR.message!!)
		} else {
			return ErrorResp(CommMsgCode.SERVER_ERROR.code!!, CommMsgCode.SERVER_ERROR.message!!)
		}
	}
	
	
	//错误信息
	fun createErrorResp(msgCode : MsgCode,  message : String?) :ErrorResp {
		var msg = "";
		if(ObjectUtil().isNotEmpty(message)){
			msg = message +"";
		}else{
			msg = msgCode.getMessage();
		}
		return ErrorResp(msgCode.getCode(), msg);
	}

	//打印log
	fun createLog(e: Exception, uri: String, params: String) {
		log.error("uri:" + uri + ",params:" + params, e);
	}
	
}
```
* #### Jsoup **防止 XSS 攻击**工具类
```

/**
 * Jsoup 防止 XSS 攻击 工具类
 */
object Jsoup2XssUtils {
    /**
     * 使用自带的 basicWithImages 白名单
     * 允许的便签有 a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
     * strike,strong,sub,sup,u,ul,img
     * 以及 a 标签的 href,img 标签的 src,align,alt,height,width,title 属性
     */
    private val whitelist: Whitelist = Whitelist.basicWithImages()

    /**
     * 配置过滤化参数 不对代码进行格式化
     */
    private val outputSettings: Document.OutputSettings = Document.OutputSettings().prettyPrint(false)
    init {
        // 富文本编辑时一些样式是使用style来进行实现的
        // 比如红色字体 style="color:red;"
        // 所以需要给所有标签添加 style 属性
        whitelist.addAttributes(":all", "style")
    }
    /**
     * 清除
     * @param content 内容
     */
    fun clean(content: String): String {
        var contentStr: String = content
        if (contentStr.isNotBlank()) {
            contentStr = content.trim { it <= ' ' }
        }
        return Jsoup.clean(contentStr, "", whitelist, outputSettings)
    }
}
```
* ####  数据库脚本(t_user)以**用户表为代表**
```
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户Id主键,IdWork生成',
  `user_name` varchar(255) DEFAULT '' COMMENT '用户名',
  `pass_word` varchar(255) DEFAULT '' COMMENT '密码',
  `is_deleted` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除,0-不删除,1-删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `id` (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

--  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',是mysql5.6以上的版本

```

* #### 工程**启动类Appalachian.kt与运行结果**
```
// 20190319105420
// 访问路径 ：http://localhost:8080/rest/listPage
//访问结果
{
  "code": "200",
  "data": {
    "page": "1",
    "pageSize": "20",
    "records": [
      {
        "createTime": "2019-01-04 16:01:34",
        "passWord": "123456",
        "userId": "1081098298906394625",
        "userName": "liangjl"
      },
      {
        "createTime": "2019-01-04 16:14:50",
        "passWord": "123456",
        "userId": "1081101635991232513",
        "userName": "liangjl"
      }
    ],
    "total": "2",
    "totalPage": "1"
  },
  "msg": "",
  "timestamp": "1552964058194"
}
```
![项目工程](https://upload-images.jianshu.io/upload_images/14586304-36ca8a09ee4dd84f.gif?imageMogr2/auto-orient/strip)

 >  [***CSDN源代码下载需要积分***](https://download.csdn.net/download/l_ji_l/11036742)
 > **[Github下载不需要积分](https://github.com/jilongliang/kotlin)**

 
##  十四、为什么记录笔记
*  1、在平时工作下来，一天认真工作下来，脑子肯定非常很疲倦，故容易导致脑子健忘。也许跟年龄的岁数增大有关系。人精力与体力等各方面都有限的，毕竟人不是机器。
*  2、常言道：***好记性不如烂笔头***。切记千万别以自己有点小聪明对自己懒惰。
* 3、如果经常健忘的时候，不防借助一些软件工具或者云服务进行帮助存储生活中的痕迹。比如：思维导图（`XMind, MindManager,FreeMind`）云道笔记等等方式。
 

## 十五、其他说明

* 1 、以上问题都是根据自己学习实际情况进行总结整理，除了技术问题查很多网上资料通过进行学习之后梳理。

* 2、 在学习过程中也遇到很多困难和疑点，如有问题或误点，望各位老司机多多指出或者提出建议。本人会采纳各种好建议和正确方式不断完善现况，**人在成长过程中的需要优质的养料**。

* 3、 导入代码的时候遇到最多的问题，我想应该是Maven较多，此时不懂maven的童鞋们可以通过自身情况，进行网上查资料学习。如通过网上找资料长时间解决不了，或者框架有不明白可以通过博客留言,在能力范围内会尽力帮助大家解决问题所在，希望在过程中一起进步，一起成长。

* 4 、请牢记一句话：内事问度娘, 外事问谷歌
>**此文章属于本人原创可以转载或收藏**
 今后我可能不在[   Iteye   ](https://jilongliang.iteye.com)  或  [    CSDN    ](https://me.csdn.net/jilongliang)的更新博客文章,会选型简书.后面有时间会针对这个框架进行细化写简书进行讲解。