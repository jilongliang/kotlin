package com.flong.kotlin.core.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import javax.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletResponse
import java.io.PrintWriter
import com.alibaba.fastjson.JSONObject


/**/
open class BaseController {

	companion object {
		private val logger: Logger = LoggerFactory.getLogger(BaseController::class.java)
	}

	@Autowired protected lateinit var request: HttpServletRequest

	/**
	 * 获取ip地址方法
	 * @return
	 */
	fun getIpAddr(): String? {
		val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
		
		var ip: String? = request.getHeader("x-forwarded-for")
		
		if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
			ip = request.getHeader("Proxy-Client-IP")
		}
		if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
			ip = request.getHeader("WL-Proxy-Client-IP")
		}
		if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
			ip = request.remoteAddr
		}
		return ip
	}

	//打印JSON
	fun writerJson(response: HttpServletResponse, json: JSONObject) {
		var writer: PrintWriter? = null
		response.characterEncoding = "UTF-8"
		response.contentType = "text/html;charset=utf-8"
		try {
			writer = response.writer
			writer!!.print(json)
		} catch (e: Exception) {
			if (logger.isErrorEnabled) {
				logger.error(e.message)
			}
		} finally {
			writer?.close()
		}
	}
}