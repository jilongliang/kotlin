package com.flong.kotlin.modules.mapper

import com.baomidou.mybatisplus.mapper.BaseMapper
import com.flong.kotlin.modules.entity.User
import com.flong.kotlin.modules.query.UserQuery
import com.flong.kotlin.modules.vo.resp.UserRespVo
import com.baomidou.mybatisplus.plugins.Page

interface IUserMapper : BaseMapper<User>{

	//这里的?表示当前是否对象可以为空 @see http://blog.csdn.net/android_gjw/article/details/78436707
	
	fun getUserList(query : UserQuery , page : Page<Any>): List<UserRespVo>?
 
}