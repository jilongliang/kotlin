package com.flong.kotlin.core.advice

import com.flong.kotlin.core.vo.LiveResp
import com.flong.kotlin.core.vo.ErrorResp
import lombok.extern.slf4j.Slf4j
import org.springframework.core.MethodParameter
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

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


 