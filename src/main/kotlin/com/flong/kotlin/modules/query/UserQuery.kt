package com.flong.kotlin.modules.query

import com.flong.kotlin.core.Query

class UserQuery : Query{
		//构造方法
	constructor() : super()
	var userId: Long?= null //用户Id 
	var userName: String? = null //用户名
}