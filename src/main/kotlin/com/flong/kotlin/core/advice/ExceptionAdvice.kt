package com.flong.kotlin.core.advice

 
import com.alibaba.fastjson.JSONObject
import com.flong.kotlin.core.exception.BaseException
import com.flong.kotlin.core.exception.CommMsgCode
import com.flong.kotlin.core.exception.MsgCode
import com.flong.kotlin.core.vo.ErrorResp
import com.flong.kotlin.utils.ObjectUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.TypeMismatchException
import org.springframework.dao.DataAccessException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLException
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ExceptionAdvice{

	companion object {
		private val log: Logger = LoggerFactory.getLogger(ExceptionAdvice::class.java)
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException::class)
	fun onException(e : HttpRequestMethodNotSupportedException, request: HttpServletRequest): ErrorResp? {
		var uri = request.getRequestURI()
		
		createLog(e, uri, "找不到请求的方法")
		
		return ErrorResp(CommMsgCode.NOT_SUPPORTED.code!!, CommMsgCode.DB_ERROR.message!!)
	}

	//is 相当于Java的 instanceof ，as就是强制转换(对象)
	@ExceptionHandler(Exception::class)
	fun onException(e: Exception, request: HttpServletRequest): ErrorResp? {
		
		var uri 	= request.getRequestURI()
		var params 	= JSONObject.toJSONString(request.getParameterMap())
		if (e is SQLException || e is DataAccessException) {
			createLog(e, uri, params)
			return ErrorResp(CommMsgCode.DB_ERROR.code!!, CommMsgCode.DB_ERROR.message!!)

		} else if (e is BaseException ) {
			//var be = e as BaseException
			log.error("uri:{},params:{},code:{},message:{}", uri, params)
			return ErrorResp(e.code,e.msg)
			
		} else if (e is MissingServletRequestParameterException
				|| e is BindException
				|| e is ConstraintViolationException
				|| e is TypeMismatchException
				|| e is ServletRequestBindingException) {

			createLog(e, uri, params)
			
			return ErrorResp(CommMsgCode.PARAM_ERROR.code!!, CommMsgCode.PARAM_ERROR.message!!)
		} else {
			return ErrorResp(CommMsgCode.SERVER_ERROR.code!!, CommMsgCode.SERVER_ERROR.message!!)
		}
	}
	
	
	//错误信息
	fun createErrorResp(msgCode : MsgCode,  message : String?) :ErrorResp {
		var msg = ""
		if(ObjectUtil().isNotEmpty(message)){
			msg = message +""
		}else{
			msg = msgCode.getMessage()
		}
		return ErrorResp(msgCode.getCode(), msg)
	}

	//打印log
	fun createLog(e: Exception, uri: String, params: String) {
		log.error("uri:" + uri + ",params:" + params, e)
	}
	
}
 

