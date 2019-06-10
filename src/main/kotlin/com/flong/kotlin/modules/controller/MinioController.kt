package com.flong.kotlin.modules.controller

import io.minio.messages.*;
import com.flong.kotlin.core.PageVO
import com.flong.kotlin.core.exception.BaseException
import com.flong.kotlin.core.web.BaseController
import com.flong.kotlin.modules.entity.User
import com.flong.kotlin.modules.enums.UserMsgCode
import com.flong.kotlin.modules.query.UserQuery
import com.flong.kotlin.modules.service.UserService
import com.flong.kotlin.modules.vo.resp.UserRespVo
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile
import org.springframework.beans.factory.annotation.Autowired


import com.flong.kotlin.core.minio.MinioTemplate
import com.flong.kotlin.core.minio.vo.*

import java.lang.*
import javax.servlet.http.HttpServletResponse
import com.flong.kotlin.utils.IoUtils
import java.util.UUID
import com.flong.kotlin.core.vo.LiveResp


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