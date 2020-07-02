package com.flong.kotlin.modules.controller

import com.flong.kotlin.core.PageVO
import com.flong.kotlin.core.exception.BaseException
import com.flong.kotlin.core.web.BaseController
import com.flong.kotlin.modules.entity.User
import com.flong.kotlin.modules.enums.UserMsgCode
import com.flong.kotlin.modules.query.UserQuery
import com.flong.kotlin.modules.service.UserService
import com.flong.kotlin.modules.vo.resp.UserRespVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody


@RestController
@RequestMapping("/rest")
open class UserController : BaseController() {

    @Autowired private lateinit var userService: UserService


    @RequestMapping("/list1")
    fun list1(): String {
        return "NewFile" //跳转页面
    }

    //添加
    @RequestMapping("/add")
    fun add(): Unit {
        userService.addUser()
    }

    //删除
    @RequestMapping("/deletedById")
    fun deletedById(userId: Long): Unit {
        userService.deleteById(userId);
    }

    //更新
    @RequestMapping("/update")
    fun update(user: User): Unit {
        userService.updateById(user)
    }


    //根据Id查询用户
    @RequestMapping("/getUserId")
    fun getUserId(userId: Long): Any? {
        var user = userService.getUserId(userId);
        if (user == null) {
            var msgCode = UserMsgCode.FIND_NOT_USER;
            throw BaseException(msgCode.code!!, msgCode.message!!)
        }
        return userService.getUserId(userId)
    }

    //查询所有
    @RequestMapping("/queryAllUser")
    fun queryAllUser(): List<User>? {
        return userService.queryAllUser()
    }

    //分页查询
    @RequestMapping("listPage")
    fun listPage(query: UserQuery): PageVO<UserRespVo>? {
        var listPage = userService.listPage(query);
        return listPage;
    }


    @RequestMapping("/getBody")
    fun getBody(@RequestBody jsonText: UserRespVo) {
        println(jsonText.component2())
        println(jsonText.userName)
    }


}