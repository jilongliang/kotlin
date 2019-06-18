package com.flong.kotlin.modules.service

import com.alibaba.fastjson.JSON
import com.baomidou.mybatisplus.mapper.EntityWrapper
import com.baomidou.mybatisplus.mapper.Wrapper
import com.baomidou.mybatisplus.toolkit.IdWorker
import com.flong.kotlin.core.PageUtil
import com.flong.kotlin.core.PageVO
import com.flong.kotlin.core.service.BaseService
import com.flong.kotlin.modules.entity.User
import com.flong.kotlin.modules.mapper.IUserMapper
import com.flong.kotlin.modules.query.UserQuery
import com.flong.kotlin.modules.vo.resp.UserRespVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Date

/**
 * @Description	UserServiceImpl
 * 这里的的：表示为实现 
 * @ClassName	UserServiceImpl.kt
 * @Date		2018年3月11日 下午6:52:08
 * @Author		liangjl
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Service
open class UserService : BaseService<IUserMapper,User,UserQuery>() {


	//Kotlin lateinit 和 by lazy 的区别
	//http://blog.csdn.net/Sherlbon/article/details/72769843
	@Autowired lateinit var userMapper: IUserMapper;

	//根据
	open fun queryAllUser(): List<User>? {
		var wrapper = createWrapper();
		return this.selectList(wrapper);
	}

	
	open fun listPage(query : UserQuery) : PageVO<UserRespVo> ? {
		var page = PageUtil().getPage(query)  ;// 设置分页
		
		var dataList = userMapper.getUserList(query, page!!);//page!!强制告诉编辑器不可能为空
	    
		var json = JSON.toJSONString(dataList);
		println(json)
		return  PageVO(dataList, page);// 获取分页数和总条数
	}
		

	open fun getUserId(userId: Long): User? {
		return get(userId);
	}

	//插入用户
	open fun addUser() {
		var userId = IdWorker.getId();
		var u = User(userId, "liangjl", "123456","1", Date());
		var json = JSON.toJSONString(u);
		println(json)
		add(u);
	}

	fun createWrapper(): Wrapper<User> {
		var wrapper = EntityWrapper<User>();
		wrapper.setEntity(User());//设置实体
		return wrapper;
	}

}