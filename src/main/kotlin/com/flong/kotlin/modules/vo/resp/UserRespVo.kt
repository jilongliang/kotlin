package com.flong.kotlin.modules.vo.resp

import com.alibaba.fastjson.annotation.JSONField
import java.io.Serializable
import java.util.Date

/**
 https://github.com/alibaba/fastjson/wiki/Use-Fastjson-in-Kotlin
 */
data class UserRespVo constructor(
	var userId: Long?= null,//用户Id主键,IdWork生成
	var userName: String? = null,//用户名
	var passWord: String? = null,//密码
	@field:JSONField(name="isDeleted")
	var isDeleted: String? = null,//是否删除
	var createTime: Date? = null //创建时间,允许为空,让数据库自动生成即可
	):Serializable{
	
}