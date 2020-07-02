package com.flong.kotlin.core.advice

import org.apache.commons.io.IOUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpInputMessage
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.lang.reflect.Type

/**
 * 针对@RequestBody请求的处理
 */
@ControllerAdvice
class LiveRequestBodyAdvice : RequestBodyAdvice {

	companion object {
		var DEFAULT_CHARSET = "UTF-8"
		private val logger: Logger = LoggerFactory.getLogger(LiveRequestBodyAdvice::class.java)
	}

	override fun handleEmptyBody(body: Any?, inputMessage: HttpInputMessage?, parameter: MethodParameter?, targetType: Type?, converterType: Class<out HttpMessageConverter<*>>?): Any? {
		return body
	}

	override fun afterBodyRead(body: Any?, inputMessage: HttpInputMessage?, parameter: MethodParameter?, targetType: Type?, converterType: Class<out HttpMessageConverter<*>>?): Any? {
		return body
	}

	override fun beforeBodyRead(inputMessage: HttpInputMessage?, parameter: MethodParameter?, targetType: Type?, converterType: Class<out HttpMessageConverter<*>>?): HttpInputMessage? {
		var body 	= IOUtils.toString(inputMessage?.getBody(), DEFAULT_CHARSET) as java.lang.String
		var headers = inputMessage!!.getHeaders()
		var bis 	= ByteArrayInputStream(body.getBytes(DEFAULT_CHARSET))
		logger.info("request body params : {}", body)

		return InputMessage(headers, bis)
	}

	override fun supports(methodParameter: MethodParameter?, targetType: Type?, converterType: Class<out HttpMessageConverter<*>>?): Boolean {
		return true
	}


	//httpInput输出流信息，这里的重写方法必须要加上open，因为kotlin默认是private，不加上会报HttpHeaders defined in 
	open class InputMessage : HttpInputMessage {
		private var headers: HttpHeaders
		private var body: InputStream
		
		//构造方法
		constructor(headers: HttpHeaders, body: InputStream) {
			this.headers = headers
			this.body = body
		}
		override fun getBody(): InputStream = this.body
		override fun getHeaders(): HttpHeaders = this.headers
	}

}


  
	