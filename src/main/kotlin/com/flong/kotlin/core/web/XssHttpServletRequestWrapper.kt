package com.flong.kotlin.core.web


import javax.servlet.http.HttpServletRequest
import com.flong.kotlin.utils.Jsoup2XssUtils
import javax.servlet.http.HttpServletRequestWrapper

/**
 * 重写 HttpServletRequestWrapper XssHttpServletRequestWrapper
 */
class XssHttpServletRequestWrapper(request: HttpServletRequest, isIncludeRichText: Boolean) : HttpServletRequestWrapper(request) {

	companion object {

		/**
		 * 获取最原始的 request 的静态方法
		 *
		 * @return
		 */
		fun getOrgRequest(req: HttpServletRequest): HttpServletRequest? {
			return if (req is XssHttpServletRequestWrapper) {
				req.orgRequest
			} else req
		}

	}


	private var isIncludeRichText: Boolean = false


	/**
	 * 获取最原始的request
	 *
	 * @return
	 */
	var orgRequest: HttpServletRequest? = null

	// -------------------------------------------------------------------------------------------------

	init {
		orgRequest = request
		this.isIncludeRichText = isIncludeRichText
	}

	// -------------------------------------------------------------------------------------------------

	/**
	 * 覆盖 getParameter方法 将参数名和参数值都做 xss 过滤
	 * 如果需要获得原始的值 则通过 super.getParameterValues(name) 来获取
	 * getParameterNames getParameterValues 和 getParameterMap 也可能需要覆盖
	 */
	override fun getParameter(name: String): String? {
		var nameStr: String = name
		val flag: Boolean = "content" == nameStr || nameStr.endsWith("WithHtml")
		if (flag && !isIncludeRichText) {
			return super.getParameter(nameStr)
		}
		nameStr = Jsoup2XssUtils.clean(nameStr)
		var value: String? = super.getParameter(nameStr)
		if (null != value && value.isNotBlank()) {
			value = Jsoup2XssUtils.clean(value)
		}
		return value
	}

	// -------------------------------------------------------------------------------------------------

	/**
	 * 覆盖 getParameter方法 将参数名和参数值都做 xss 过滤
	 * 如果需要获得原始的值 则通过 super.getParameterValues(name) 来获取
	 * getParameterNames getParameterValues 和 getParameterMap 也可能需要覆盖
	 */
	override fun getParameterValues(name: String): Array<String>? {
		val array: Array<String>? = super.getParameterValues(name)
		if (null != array) {
			for (i: Int in array.indices) {
				array[i] = Jsoup2XssUtils.clean(array[i])
			}
		}
		return array
	}

	// -------------------------------------------------------------------------------------------------

	/**
	 * 覆盖 getHeader 方法 将参数名和参数值都做 xss 过滤
	 * 如果需要获得原始的值 则通过 super.getHeaders(name) 来获取
	 * getHeaderNames 也可能需要覆盖
	 */
	override fun getHeader(name: String): String? {
		var nameStr: String = name
		nameStr = Jsoup2XssUtils.clean(nameStr)
		var value: String? = super.getHeader(nameStr)
		if (null != value && value.isNotBlank()) {
			value = Jsoup2XssUtils.clean(value)
		}
		return value
	}

	// -------------------------------------------------------------------------------------------------

}
