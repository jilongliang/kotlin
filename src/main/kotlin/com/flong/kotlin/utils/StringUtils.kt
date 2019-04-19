package com.flong.kotlin.utils

import java.util.regex.Pattern
import org.apache.commons.lang3.StringUtils
import java.util.regex.Matcher

object StringUtils : StringUtils() {


    /**
     * 下划线标识
     */
    private const val LINE = "_"
    /**
     * 下划线
     */
    private val LINE_PATTERN: Pattern = Pattern.compile("_(\\w)")


    private var humpPattern: Pattern? = null

    // ------------------------------------------------------------------------

    init {
        humpPattern = Pattern.compile("[A-Z]")
    }


    /**
     * 下划线转驼峰
     */
    fun lineToHump(str: String): String {
        val args: String = str.toLowerCase()
        val matcher: Matcher = LINE_PATTERN.matcher(args)
        val sb = StringBuffer()
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase())
        }
        matcher.appendTail(sb)
        return sb.toString()
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * 驼峰转下划线
     */
    fun humpToLine(str: String): String {
        val matcher: Matcher? = humpPattern?.matcher(str)
        val sb = StringBuffer()
        while (null != matcher && matcher.find()) {
            matcher.appendReplacement(sb, LINE + matcher.group(0).toLowerCase())
        }
        matcher?.appendTail(sb)
        return sb.toString()
    }


    /**
     * 首字母大写
     *
     * @param string 字符串
     * @return 首字母大写
     */
    fun firstUpperCase(string: String?): String? {
        return if (null == string) {
            null
        } else string.substring(0, 1).toUpperCase() + string.substring(1)
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * 首字母小写
     *
     * @param string 字符串
     * @return 首字母小写
     */
    fun firstLowerCase(string: String?): String? {
        return if (null == string) {
            null
        } else string.substring(0, 1).toLowerCase() + string.substring(1)
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * 统计指定内容中包含指定字符串的数量
     * @param content 内容
     * @param str2Search 被统计的字符串
     * @return 包含数量
     */
    fun countCharInnerContent(content: CharSequence, str2Search: CharSequence): Int {
        if (content.isEmpty() || str2Search.isEmpty() || str2Search.length > content.length) {
            return 0
        }
        var count = 0
        var index = 0
        val contentStr: String = content.toString()
        val strForSearchStr: String = str2Search.toString()
        index = contentStr.indexOf(strForSearchStr, index)
        while (index > -1) {
            count++
            index += str2Search.length
            index = contentStr.indexOf(strForSearchStr, index)
        }
        return count
    }

	
	//对String类进行扩展一个isEmpty方法，格式:fun String.扩展函数名称
	fun String?.isEmpty() :Boolean {
		return this==null ||this.length==0
		
	}

}
