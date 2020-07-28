package com.flong.kotlin.utils

import javax.servlet.http.HttpServletRequest

/**
 * User: liangjl
 * Date: 2020/7/28
 * Time: 10:01
 */
object WebUtils {


    fun getIpAddr(request: HttpServletRequest): String? {
        var ipAddress: String? = null
        try {
            ipAddress = request.getHeader("x-forwarded-for")
            if (ipAddress == null || ipAddress!!.length == 0 || "unknown".equals(ipAddress!!, ignoreCase = true)) {
                ipAddress = request.getHeader("Proxy-Client-IP")
            }
            if (ipAddress == null || ipAddress!!.length == 0 || "unknown".equals(ipAddress!!, ignoreCase = true)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP")
            }
            if (ipAddress == null || ipAddress!!.length == 0 || "unknown".equals(ipAddress!!, ignoreCase = true)) {
                ipAddress = request.getRemoteAddr()
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress!!.length > 15) {
                // "***.***.***.***".length()= 15
                if (ipAddress!!.indexOf(",") > 0) {
                    ipAddress = ipAddress!!.substring(0, ipAddress.indexOf(","))
                }
            }
        } catch (e: Exception) {
            ipAddress = ""
        }

        return ipAddress
    }
}