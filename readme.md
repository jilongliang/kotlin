# 1、前言与目的

> 工程架构，请看我之前撰写的 [Kotlin +SpringBoot + MyBatis完美搭建最简洁最酷的前后端分离框架](https://www.jianshu.com/p/0acd593fd11e)

* 使用Swagger开放源码和专业工具集，为用户、团队和企业简化API开发。

* 简单的来说，Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务的接口文档。

*  减少开发人员编写API文档，可以通过代码生成器生成API文档。

# 2、API工具有哪些呢？
* 这里列举一下常见的工具清单，以及常见比较好用的推荐给大家伙使用

|名称| 官方|	说明
|:--|:--|:--|
| Swagger| [https://swagger.io/](https://swagger.io/)| 推荐
| Apidocjs| [http://apidocjs.com/](http://apidocjs.com/) |推荐
|confluence| [https://www.atlassian.com/software/confluence](https://www.atlassian.com/software/confluence) |推荐
|showdoc| [https://www.showdoc.cc/](https://www.showdoc.cc/) |无
|eolinker| [https://www.eolinker.com/#/](https://www.eolinker.com/#/) |无
|MinDoc| [https://www.iminho.me/](https://www.iminho.me/)  |无
|apizza| [https://apizza.net/pro/#/](https://apizza.net/pro/#/) |无
|RAP| [http://rapapi.org/org/index.do](http://rapapi.org/org/index.do)  |无
|raml| [https://raml.org/](https://raml.org/) |无
|APIJSON| [https://jsonapi.org/](https://jsonapi.org/) |无

* 为了手机的布局问题，这里提供一些图片
![API工具.png](https://upload-images.jianshu.io/upload_images/14586304-26804e82069694ba.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


* 由于前三者我都用过，经过开发过程中个人觉得最快生成的文档的是Swagger和Apidocjs，而且confluence需要自己写文档，从时间上的成本会高点，如果说到规范文档这方面肯定它比前2者会更优

* apidocjs ,这个生成方式需要安装NodeJS，定好模板，然后使用Nodejs命令进行生成相应的接口API文档，只需要懂点简单的语法和规则即可，不需要像Swagger那么麻烦要整和Spring或SpringBoot等方式，然后再打包把工程进行发布前端才可以看到相应的API，然而apidocjs 生成静态的Html即可。

* 其他无使用过，不发表其他建议。希望通过以上的列表，根据自己的情况进行选择与学习

# 3、springfox大致原理:
* springfox的大致原理就是，在项目启动的过种中，spring上下文在初始化的过程， 
框架自动跟据配置加载一些swagger相关的bean到当前的上下文中，并自动扫描系统中可能需要生成api文档那些类， 并生成相应的信息缓存起来。如果项目MVC控制层用的是springMvc那么会自动扫描所有Controller类，并生成对应的文档描述数据.

* 该数据是json格式，通过路径：项目地址/ v2/api-docs可以访问到该数据，然后swaggerUI根据这份数据生成相应的文档描述界面。 
因为我们能拿到这份数据，所以我们也可以生成自己的页面.

# 4、Swagger注解详解

#### ApiModel 属性

|属性名称|数据类型|默认值|说明
|:--|:--|:--|:--|
|value			|String				| 类名		| 为模型提供备用名称
|description	|String				| ""		| 提供详细的类描述
|parent			|Class<?> parent	| Void.class| 为模型提供父类以允许描述继承关系
|discriminatory	|String				| ""		| 支持模型继承和多态，使用鉴别器的字段的名称，可以断言需要使用哪个子类型
|subTypes		|Class<?>[]			| {}		| 从此模型继承的子类型数组
|reference		|String				| ""		| 指定对应类型定义的引用，覆盖指定的任何其他元数据

* 为了手机的布局问题，这里提供一些图片
![ApiModel .png](https://upload-images.jianshu.io/upload_images/14586304-1d87587c1e443f5f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### ApiModelProperty 属性
|属性名称|数据类型|默认值|说明
|:--|:--|:--|:--|
|value			| String	|""		| 属性简要说明
|name			| String	|""		| 运行覆盖属性的名称。重写属性名称
|allowableValues| String	|""		| 限制参数可接收的值，三种方法，固定取值，固定范围
|access			| String	|""		| 过滤属性，参阅:io.swagger.core.filter.SwaggerSpecFilter
|notes			| String	|""		| 目前尚未使用
|dataType		| String	|""		| 参数的数据类型，可以是类名或原始数据类型，此值将覆盖从类属性读取的数据类型
|required		| boolean	|false	| 是否为必传参数,false:非必传参数; true:必传参数
|position		| int		|0		| 允许在模型中显示排序属性
|hidden			| boolean	|false	| 隐藏模型属性，false:不隐藏; true:隐藏
|example		| String	|""		| 属性的示例值
|readOnly		| boolean	|false	| 指定模型属性为只读，false:非只读; true:只读
|reference		| String	|""		| 指定对应类型定义的引用，覆盖指定的任何其他元数据
|allowEmptyValue| boolean	|false	| 允许传空值，false:不允许传空值; true:允许传空值

* 为了手机的布局问题，这里提供一些图片
![ApiModelProperty .png](https://upload-images.jianshu.io/upload_images/14586304-10c592a19bcfb7d0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 5、代码实现

#### swagger 依赖的jar
```
<!--swagger 依赖-->
  <dependency>
       <groupId>io.springfox</groupId>
       <artifactId>springfox-swagger2</artifactId>
        <version>2.8.0</version>
  </dependency>
 <dependency>
    <groupId>io.springfox</groupId>
   <artifactId>springfox-swagger-ui</artifactId>
   <version>2.8.0</version>
</dependency>
```

* #### 第一种方式 SwaggerConfiguration 代码实现
```
/*
  * 访问路径: http://localhost:8080/swagger-ui.html#/
 */
@EnableSwagger2 // 启用Swagger
@Configuration
open class SwaggerConfiguration {
	@Bean
	open fun createRestApi(): Docket {	 
		 return  Docket(DocumentationType.SWAGGER_2).select()
          .apis(RequestHandlerSelectors
          .withMethodAnnotation(ApiOperation::class.java)).build();
	}
	
}
```
 * #### 第二种方式 SwaggerConfiguration 代码实现
```

/*
  * 访问路径: http://localhost:8080/swagger-ui.html#/
 */
@EnableSwagger2 // 启用Swagger
open class SwaggerConfiguration {

	companion object {
		const val SWAGGER_SCAN_BASE_PACKAGE = "com.flong";
		const val VERSION = "1.0.0";
	}

	@Bean
	open fun createRestApi(): Docket {
		return Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
	    .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
          //  api接口包扫描路径
         // 只对restApi路径下面的所有连接请求
        //.paths(regex("/restApi.*"))
        // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
        .paths(PathSelectors.any()) .build();
		
	}

	fun apiInfo(): ApiInfo {
            // 设置文档的标题
		return ApiInfoBuilder().title("Swagger2 接口文档示例")
            // 设置文档的描述->1.Overview
			.description("更多内容请关注：https://github.com/jilongliang")
            // 设置文档的版本信息-> 1.1 Version information
			.version(VERSION)
			.contact("liangjl")
             // 设置文档的License信息->1.3 License information
			.termsOfServiceUrl("www.apache.org")
			.build();
	}
}
```
* #### Model代码配置
```

/**
 *data保持数据data class就是一个类中只包含一些数据字段，类似于vo,pojo,java bean。一般而言，
 *我们在Java中定义了这个数据类之后要重写一下toString，equals等方法。要生成get,set方法
 *https://www.cnblogs.com/liuliqianxiao/p/7152773.html
 *注意mybatis查询数据，然后封装实体的时候，构造方法这里有点儿坑，查询的字段必须与构造方法一直。
 * 
 * @ApiModelProperty()用于方法，字段； 表示对model属性的说明或者数据操作更改 
 * value–字段说明 
 * name–重写属性名字 
 * dataType–重写属性类型 
 * required–是否必填 
 * example–举例说明
 * hidden–隐藏
 * notes用于提示内容 
 */
@TableName("t_user")
@ApiModel(value = "用户对象", description = "用户对象")
data class User constructor(
	
	@TableId(value = "user_id", type = IdType.ID_WORKER)
	@ApiModelProperty(value = "用户Id",notes = "用户Id")
	var userId: Long?= null,//用户Id主键,IdWork生成
	
	@ApiModelProperty(value = "用户名称",notes ="用户名称")
	var userName: String? = null,//用户名
	
	
	@ApiModelProperty(value = "用户密码",notes ="用户密码")
	var passWord: String? = null,//密码
	
	@TableLogic
	@JSONField(name = "isDeleted")
	@ApiModelProperty(value = "删除状态,0-未删除,1-已删除",notes ="删除状态,0-未删除,1-已删除")
	var isDeleted: Int? = null,//删除
	
	@ApiModelProperty(value = "创建时间",notes ="创建时间")
	var createTime: Date? = null //创建时间,允许为空,让数据库自动生成即可

  ) :Serializable{
 
	//手动重写toString方法
	override fun toString(): String {
		return "[User(userId = $userId,userName = $userName, passWord=$passWord,isDeleted=$isDeleted,createTime=$createTime)]"
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

* #### Controller代码
```

@RestController
@RequestMapping("/rest")
@Api("用户模块")
open class UserController : BaseController(){
	
    @Autowired private lateinit var userService: UserService
 
	
	@ApiOperation(value="调整页面")
	@GetMapping("/list1")
    fun list1():  String{
		return "NewFile" //跳转页面
    }
	
    //添加
	@ApiOperation(value="添加用户")
	@PostMapping("/add")
    fun add():  Unit{
		userService.addUser()
    }
	
	//删除
	@ApiOperation(value="删除用户")
	@DeleteMapping("/deletedById")
    fun deletedById(userId : Long):  Unit{
		userService.deleteById(userId);
    }
	//更新
	@ApiOperation(value="更新用户")
	@PostMapping("/update")
    fun update(user : User):  Unit{
		userService.updateById(user)
    }
	//根据Id查询用户
	@ApiOperation(value="根据Id查询用户")
	@GetMapping("/getUserId")
    fun getUserId(userId :Long):Any?{
		var user = userService.getUserId(userId);
		if(user ==null){
			var msgCode = UserMsgCode.FIND_NOT_USER;
			throw BaseException(msgCode.code!! ,msgCode.message!!)
		}
		return userService.getUserId(userId)
    }
	
	//查询所有
	@ApiOperation(value="查询所有用户信息")
	@GetMapping("/queryAllUser")
    fun queryAllUser():List<User>?{
		return userService.queryAllUser()
    }
	//分页查询
	@ApiOperation(value="用户列表分页",notes="用户列表分页")
	@GetMapping("listPage")
    fun listPage(query :UserQuery) :PageVO<UserRespVo> ? {
    	var listPage = userService.listPage(query);
        return listPage;
    }
	
}
```
 * #### Application 入口启动代码
```
@EnableAsync
@EnableSwagger2
@Configuration
@EnableScheduling
@EnableAutoConfiguration  //启用读取配置
@ComponentScan("com.flong.kotlin")  //扫描com.flong文件目录下
@SpringBootApplication(scanBasePackages = ["com.flong.kotlin"] )
//@SpringBootApplication(scanBasePackages = arrayOf("com.flong.kotlin")) 这种写法也OK
open class Application :WebMvcConfigurerAdapter{
    //无参数构造方法，为了解决WebMvcConfigurerAdapter类的内部问题，
	constructor()
	
  /**添加swagger-ui的资源文件拦截器的支持addResourceHandlers是
  WebMvcConfigurerAdapter里面的 */
	override fun addResourceHandlers( registry:ResourceHandlerRegistry) {
		// super.addResourceHandlers(registry);
		registry.addResourceHandler("/WEB-INF/views/**").addResourceLocations("/WEB-INF/views/");
		/** 添加swagger-ui的资源文件拦截器的支持 */
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
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
>注意点： 
`constructor()`无参数构造方法，为了解决WebMvcConfigurerAdapter类的内部问题，实现 WebMvcConfigurerAdapter此类主要是为addResourceHandlers了添加加swagger-ui的`资源文件拦截器的支持 `

# 6、运行接口
* ![Swagger-UI.png](https://upload-images.jianshu.io/upload_images/14586304-d68ae5be3fa620da.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* ![Model实体类](https://upload-images.jianshu.io/upload_images/14586304-dd0eb75b21b47d25.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 7、工程代码
> [工程代码在本人的GitHub的dev-swagger分支上](https://github.com/jilongliang/kotlin/tree/dev-swagger)


# 8 、总结与建议
1 、以上问题根据搭建 kotlin与Swagger 实际情况进行总结整理，除了技术问题查很多网上资料，通过自身进行学习之后梳理与分享。

2、 在学习过程中也遇到很多困难和疑点，如有问题或误点，望各位老司机多多指出或者提出建议。本人会采纳各种好建议和正确方式不断完善现况，人在成长过程中的需要优质的养料。

3、 希望此文章能帮助各位老铁们更好去了解如何在 kotlin上搭建Swagger，也希望您看了此文档或者通过找资料进行手动安装效果会更好。

> 备注：此文章属于本人原创,欢迎转载和收藏.




