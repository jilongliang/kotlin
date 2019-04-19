package com.flong.kotlin.core.exception

import com.fasterxml.jackson.annotation.JsonIgnore


//message是底层RuntimeException类要重写的变量
class BaseException constructor(var code :Int, val msg: String)  : RuntimeException() {

	@JsonIgnore
    override fun getStackTrace(): Array<StackTraceElement> {
        return super.getStackTrace()
    }

	
}