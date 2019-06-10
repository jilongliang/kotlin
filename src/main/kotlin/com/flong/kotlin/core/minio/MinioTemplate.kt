package com.flong.kotlin.core.minio

import io.minio.MinioClient
import io.minio.ObjectStat
import io.minio.messages.Bucket
import io.minio.messages.Item
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import java.io.InputStream
import java.util.ArrayList
import java.util.Optional

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.beans.factory.annotation.Autowired
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
	 *
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
	 *
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
	 *
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
	 *
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