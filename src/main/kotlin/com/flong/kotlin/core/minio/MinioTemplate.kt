package com.flong.kotlin.core.minio

import com.flong.kotlin.core.minio.vo.MinioItem
import io.minio.MinioClient
import io.minio.Result
import io.minio.messages.Bucket
import io.minio.messages.Item
import java.io.Serializable
import java.util.Optional
import java.io.InputStream;
import java.util.ArrayList;


data class MinioTemplate constructor(
	var endpoint: String? = "",
	var accessKey: String? = "",
	var secretKey: String? = ""
) : Serializable {


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
	fun  getObjectURL(bucketName:String , objectName:String , expires:Int ) :String {
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
	fun  getObject(bucketName:String , objectName:String ) :InputStream{
		return getMinioClient().getObject(bucketName, objectName);
	}
	
	
		/**
	 * 上传文件
	 *
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @param stream     文件流
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
	 */
	fun   putObject(bucketName:String , objectName:String ,  stream:InputStream)   {
		//假设不存在 bucket名称，程序创建 bucket名称,不需要手动创建
		createBucket(bucketName);
		getMinioClient().putObject(bucketName, objectName, stream, stream.available(), "application/octet-stream");
	}


	//获取MinioClient
	fun getMinioClient(): MinioClient {
		return MinioClient(endpoint, accessKey, secretKey)
	}


}