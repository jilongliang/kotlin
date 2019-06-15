# 1、Minio安装
 >请看我之前撰写的 [Docker安装Minio存储服务器详解](https://www.jianshu.com/p/52dbc679094a)

# 2、工程架构模式
> 请看我之前撰写的 [Kotlin +SpringBoot + MyBatis完美搭建最简洁最酷的前后端分离框架](https://www.jianshu.com/p/0acd593fd11e)

# 3 、依赖jar
```
<dependency>
	<groupId>io.minio</groupId>
	<artifactId>minio</artifactId>
	<version>3.0.7</version>
</dependency>
```
# 4、自动装配
* 在resource在源文件里面添加一个`spring.factories`配置文件,配置代码如下：
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.flong.kotlin.core.minio.MinioAutoConfiguration
```
# 5、properties配置minio信息
```
# minio服务器url
minio.url=http://192.168.1.133:9000
# minio安装指定的访问key
minio.accessKey=admin
# minio安装指定的访问秘钥
minio.secretKey=admin123456
# minio的启用配置
minio.endpoint.enable=true
```
# 6、工程核心代码如下
* ##### MinioAutoConfiguration 自动装配类
```kotlin

@EnableConfigurationProperties(MinioProperties::class)
open class MinioAutoConfiguration {
	@Autowired lateinit var minioProperties: MinioProperties;
	
	@Bean
	@ConditionalOnMissingBean(MinioTemplate::class)
	@ConditionalOnProperty(name = arrayOf("minio.url"))
	open fun minioTemplate():MinioTemplate {
		return MinioTemplate(minioProperties.url,
                minioProperties.accessKey,
                minioProperties.secretKey)
	}
}
```

*  ##### MinioProperties 配置属性类
```
/*
 * Value注解的使用
 * https://segmentfault.com/a/1190000010978025
 * https://blog.jetbrains.com/idea/2018/10/spring-kotlin-references-in-value-annotation/
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
open class MinioProperties {
	/**
	 * minio 服务地址 http://ip:port
	 */
	var url:String=""
	/**
	 * 用户名
	 */
	var accessKey:String=""
	/**
	 * 密码
	 */
	var secretKey:String =""
}
```
*  ##### MinioTemplate 连接minio服务器模板工具类
```
/**
 * MinioTemplate連接工具
 * Linux的时区与minio容器的时区不一致导致的异常
 * ErrorResponse(code=RequestTimeTooSkewed, message=The difference between the request time and the server's time is too large.,
 * bucketName=null, objectName=null, resource=/kotlin, requestId=null, hostId=null)
 */
@Component
@Configuration
@EnableConfigurationProperties(MinioProperties::class)
open class MinioTemplate  {
	var url:String 		 = ""
	var accessKey:String = ""
	var secretKey:String = ""
	//constructor无参数构造方法是为了解决以下错误:
	//Unsatisfied dependency expressed through constructor parameter 0; nested exception is
	//org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'java.lang.String'
	//available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}
	constructor()
	
	//有参数的构造方法
	constructor(url:String , accessKey:String, secretKey:String){
		this.url = url
		this.accessKey = accessKey
		this.secretKey = secretKey
	}
	
	@Autowired lateinit var minioProperties: MinioProperties
	
	open fun getMinioClient(): MinioClient {
		return MinioClient(minioProperties.url,minioProperties.accessKey,minioProperties.secretKey)
	}
	
	/**
	 * 创建bucket
	 * @param bucketName bucket名称
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#makeBucket
	 */
	fun createBucket(bucketName: String) {
		var client = getMinioClient()
		if (!client.bucketExists(bucketName)) {
			client.makeBucket(bucketName)
		}
	}

	/**
	 * 获取全部bucket
	 * <p>
	 * https://docs.minio.io/cn/java-client-api-reference.html#listBuckets
	 */
	fun getAllBuckets(): List<Bucket> {
		return getMinioClient().listBuckets()
	}

	/**
	 * @param bucketName bucket名称
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#listBuckets
	 */
	fun getBucket(bucketName: String): Optional<Bucket> {
		return getMinioClient().listBuckets().stream().filter({ b -> b.name().equals(bucketName) }).findFirst()
	}
	/**
	 * @param bucketName bucket名称
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#removeBucket
	 */
	fun removeBucket(bucketName: String) {
		getMinioClient().removeBucket(bucketName)
	}
	/**
	 * 根据文件前置查询文件
	 * @param bucketName bucket名称
	 * @param prefix     前缀
	 * @param recursive  是否递归查询
	 * @return
	 * @throws Exception
	 */
	fun getAllObjectsByPrefix(bucketName: String, prefix: String, recursive: Boolean): List<Item> {
		var objectList = ArrayList<Item>()
		var objectsIterator = getMinioClient().listObjects(bucketName, prefix, recursive)
		while (objectsIterator.iterator().hasNext()) {
			objectList.add(objectsIterator.iterator().next().get())
		}
		return objectList
	}
	
	/**
	 * 获取文件外链
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @param expires    过期时间 <=7
	 * @return url
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#getObject
	 */
	fun getObjectURL(bucketName:String , objectName:String , expires:Int ) :String {
		return getMinioClient().presignedGetObject(bucketName, objectName, expires)
	}
	
	/**
	 * 获取文件
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @return 二进制流
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#getObject
	 */
	fun getObject(bucketName:String , objectName:String ) :InputStream{
		return getMinioClient().getObject(bucketName, objectName)
	}
	
	/**
	 * 上传文件
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @param stream     文件流
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
	 */
	fun putObject(bucketName:String , objectName:String , stream:InputStream)  {
		//假设不存在 bucket名称，程序创建 bucket名称,不需要手动创建
		createBucket(bucketName)
		getMinioClient().putObject(bucketName, objectName, stream, stream.available().toLong(), "application/octet-stream");
	}
	/**
	 * 上传文件
	 *
	 * @param bucketName  bucket名称
	 * @param objectName  文件名称
	 * @param stream      文件流
	 * @param size        大小
	 * @param contextType 类型
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
	 */
	fun putObject(bucketName:String, objectName:String, stream:InputStream, size:Long,contextType:String ):Unit {
		//假设不存在 bucket名称，程序创建 bucket名称,不需要手动创建
		createBucket(bucketName)
		getMinioClient().putObject(bucketName, objectName, stream, size, contextType)
	}
	
	/**
	 * 获取文件信息
	 *
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
	 */
	fun getObjectInfo(bucketName:String , objectName:String ) : ObjectStat {
		return getMinioClient().statObject(bucketName, objectName)
	}
	
	/**
	 * 删除文件
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#removeObject
	 */
	fun removeObject(bucketName:String ,objectName : String ) :Unit {
		getMinioClient().removeObject(bucketName, objectName)
	}

}
```
> **注意点1**: Linux的时区与minio容器的时区不一致导致的异常 
ErrorResponse(code=RequestTimeTooSkewed, message=The difference between the `request time and the server's time is too large`., bucketName=null, objectName=null, resource=/kotlin, requestId=null, hostId=null)

> **注意点2** `constructor`无参数构造方法是为了解决以下错误:
Unsatisfied dependency expressed through constructor parameter 0; nested exception is
org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'java.lang.String' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}	
```
constructor()
```
* ##### Spring控制层测试类
```
package com.flong.kotlin.modules.controller
@ConditionalOnProperty(name = arrayOf("minio.endpoint.enable"), havingValue = "true")
@RestController
@RequestMapping("/rest")
class MinioController : BaseController() {
	@Autowired private lateinit var template: MinioTemplate

	//常量
	companion object MinioConstant {
		const val fileName = "kotlin.xls"
		const val bucketName = "kotlin"
	}

	/**
	 * Bucket Endpoints
	 */
	@PostMapping("/bucket/{bucketName}")
	fun createBucker(@PathVariable bucketName: String): Bucket {
		print("bucketName=" + bucketName)
		template.createBucket(bucketName)
		return template.getBucket(bucketName).get()
	}

	@GetMapping("/bucket")
	fun getBuckets(): List<Bucket> {
		return template.getAllBuckets()
	}
	@GetMapping("/bucket/{bucketName}")
	fun getBucket(@PathVariable bucketName: String): Bucket {
		return template.getBucket(bucketName).orElseThrow({ IllegalStateException("Bucket Name not found!") })
	}

	@DeleteMapping("/bucket/{bucketName}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	fun deleteBucket(@PathVariable bucketName: String) {
		template.removeBucket(bucketName)
	}

	/**
	 * Object Endpoints
	 */

	@PostMapping("/object/{bucketName}")
	fun createObject(@RequestBody file: MultipartFile, @PathVariable bucketName: String): MinioObject {
		var name = file.getOriginalFilename()
		template.putObject(bucketName, name, file.getInputStream(), file.getSize(), file.getContentType())
		return MinioObject(template.getObjectInfo(bucketName, name))

	}

	@PostMapping("/object/{bucketName}/{objectName}")
	fun createObject(@RequestBody file: MultipartFile, @PathVariable bucketName: String, @PathVariable objectName: String): MinioObject {
		template.putObject(bucketName, objectName, file.getInputStream(), file.getSize(), file.getContentType())
		return MinioObject(template.getObjectInfo(bucketName, objectName))
	}

	@GetMapping("/object/{bucketName}/{objectName}")
	fun filterObject(@PathVariable bucketName: String, @PathVariable objectName: String): List<Item> {
		return template.getAllObjectsByPrefix(bucketName, objectName, true)
	}

	@GetMapping("/object/{bucketName}/{objectName}/{expires}")
	fun getObject(@PathVariable bucketName: String, @PathVariable objectName: String, @PathVariable expires: Int): Map<String, Any> {
		var responseBody = HashMap<String, Any>()
		// Put Object info
		responseBody.put("bucket", bucketName)
		responseBody.put("object", objectName)
		responseBody.put("url", template.getObjectURL(bucketName, objectName, expires))
		responseBody.put("expires", expires)
		return responseBody
	}

	@DeleteMapping("/object/{bucketName}/{objectName}/")
	@ResponseStatus(HttpStatus.ACCEPTED)
	fun deleteObject(@PathVariable bucketName: String, @PathVariable objectName: String) {
		template.removeObject(bucketName, objectName)
	}

	/*
	 *下载文件
	 */
	@GetMapping("/downloadFile")
	fun downloadFile(response: HttpServletResponse) {
		var fileName = MinioConstant.fileName
		var bucketName = MinioConstant.bucketName
		var inputStream = template.getObject(bucketName, fileName)
		IoUtils.downloadFile(response, fileName, "UTF-8", inputStream) //从minio下载
	}

	/*
	 *上传文件
	 */
	@PostMapping("/upload")
	fun upload(@RequestParam("file") file: MultipartFile) : LiveResp<Any>{
		var originalFilename = file.getOriginalFilename();
		//随机数和源文件名称
		var newFilename = UUID.randomUUID().toString().replace("-", "") + originalFilename

		var resultMap = HashMap<String, String>(4);
		resultMap.put("bucketName", MinioConstant.bucketName);
		resultMap.put("fileName", newFilename);
		template.putObject(MinioConstant.bucketName, newFilename, file.getInputStream())
		
		return LiveResp(resultMap);
		
	}

}
```
> 常量值可以根据自己实际情况进行修改测试，这里以一个图片进行测试。
```
companion object MinioConstant {
	const val fileName = "kotlin.xls"
	const val bucketName = "kotlin"
}
```
* 运行Application.kt并访问测试
```
访问路径：http://localhost:8080/rest/downloadFile
```
* ![运行结果](https://upload-images.jianshu.io/upload_images/14586304-c528e8432de92a01.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#7、工程源代码
> [工程源代码在dev-minio分支上](https://github.com/jilongliang/kotlin/blob/dev-minio)

# 8 、总结与建议
1 、以上问题根据搭建 kotlin与Minio 实际情况进行总结整理，除了技术问题查很多网上资料,通过自身进行学习之后梳理与分享。

2、 在学习过程中也遇到很多困难和疑点，如有问题或误点，望各位老司机多多指出或者提出建议。本人会采纳各种好建议和正确方式不断完善现况，人在成长过程中的需要优质的养料。

3、 希望此文章能帮助各位老铁们更好去了解如何在 kotlin上搭建Minio，也希望您看了此文档或者通过找资料进行手动安装效果会更好。

> 备注：此文章属于本人原创,欢迎转载和收藏.

