package com.flong.kotlin.core.advice

import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.time.DateUtils
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.InitBinder
import java.beans.PropertyEditorSupport
import java.util.*

/**
 * 全局日期绑定
 */
@ControllerAdvice
class DateBindAdvice {

	@InitBinder
	fun dateBinder(binder: WebDataBinder): Unit {
		binder.registerCustomEditor(Date::class.java, DatePropertyEditor());
	}

}

/**
 * 日期转换器
 */ 
class DatePropertyEditor : PropertyEditorSupport() {
	override fun setAsText(text: String) {
		if (StringUtils.isBlank(text)) {
			return
		}
		var date = DateUtils.parseDate(text, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM")
		setValue(date)
	}
} 