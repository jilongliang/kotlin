

package com.flong.kotlin.core.web

import org.apache.commons.lang3.BooleanUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.flong.kotlin.core.web.XssHttpServletRequestWrapper
import java.io.IOException
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

/**
 * 拦截防止 xss 注入
 * 通过 jsoup 过滤请求参数内的特定字符
 */
class XssFilter : Filter {


    companion object {

        private val logger: Logger = LoggerFactory.getLogger(XssFilter::class.java)

        /**
         * 是否过滤富文本内容
         */
        private var IS_INCLUDE_RICH_TEXT = false

        /**
         * .标识
         */
        private const val DOT_IDENTIFY = "."

    }

    private val excludes = ArrayList<String>()


    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse,
                          filterChain: FilterChain) {
        val httpServletRequest: HttpServletRequest = request as HttpServletRequest

        val uri: String = httpServletRequest.requestURI
        // 静态请求直接跳出
        if (!uri.contains(DOT_IDENTIFY)) {
            if (logger.isDebugEnabled) {
                logger.debug(">>>>>>>>XssFilter[${httpServletRequest.requestURI}]<<<<<<<<")
            }

            if (this.handleExcludeURL(httpServletRequest)) {
                filterChain.doFilter(request, response)
                return
            }

            val xssRequest = XssHttpServletRequestWrapper(request, IS_INCLUDE_RICH_TEXT)
            filterChain.doFilter(xssRequest, response)
        } else {
            filterChain.doFilter(request, response)
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        if (logger.isDebugEnabled) {
            logger.debug(">>>>>>>>XssFilter Init<<<<<<<<")
        }
        // 过滤富文本
        val isIncludeRichText: String = filterConfig.getInitParameter("isIncludeRichText")
        if (StringUtils.isNotBlank(isIncludeRichText)) {
            IS_INCLUDE_RICH_TEXT = BooleanUtils.toBoolean(isIncludeRichText) 
        }
        // 排除
        val temp: String = filterConfig.getInitParameter("excludes")
        val url: Array<String> = temp.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i: Int in url.indices) {
            excludes.add(url[i])
        }

    }

    // -------------------------------------------------------------------------------------------------

    override fun destroy() {}

    // -------------------------------------------------------------------------------------------------

    /**
     * 过滤 url
     *
     * @param request  HttpServletRequest
     *
     * @return Boolean
     */
    private fun handleExcludeURL(request: HttpServletRequest): Boolean {
        if (excludes.isEmpty()) {
            return false
        }
        // url
        val url: String = request.servletPath
        var p: Pattern
        var m: Matcher
        for (pattern: String in excludes) {
            p = Pattern.compile("^$pattern")
            m = p.matcher(url)
            if (m.find()) {
                return true
            }
        }
        return false
    }

    // -------------------------------------------------------------------------------------------------

}
