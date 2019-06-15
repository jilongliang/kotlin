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
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.DeleteMapping


@RestController
@RequestMapping("/rest")
@Api("用户模块")
open class UserController : BaseController(){
	
    @Autowired private lateinit var userService: UserService
 
	
	@ApiOperation(value="调整页面")
	@GetMapping("/list1")
    fun list1():  String{
		return "NewFile" //跳转页面
    }
	
    //添加
	@ApiOperation(value="添加用户")
	@PostMapping("/add")
    fun add():  Unit{
		userService.addUser()
    }
	
	//删除
	@ApiOperation(value="删除用户")
	@DeleteMapping("/deletedById")
    fun deletedById(userId : Long):  Unit{
		userService.deleteById(userId);
    }
	
	//更新
	@ApiOperation(value="更新用户")
	@PostMapping("/update")
    fun update(user : User):  Unit{
		userService.updateById(user)
    }
	
	
	//根据Id查询用户
	@ApiOperation(value="根据Id查询用户")
	@GetMapping("/getUserId")
    fun getUserId(userId :Long):Any?{
		var user = userService.getUserId(userId);
		if(user ==null){
			var msgCode = UserMsgCode.FIND_NOT_USER;
			throw BaseException(msgCode.code!! ,msgCode.message!!)
		}
		return userService.getUserId(userId)
    }
	
	//查询所有
	@ApiOperation(value="查询所有用户信息")
	@GetMapping("/queryAllUser")
    fun queryAllUser():List<User>?{
		return userService.queryAllUser()
    }
	
	
	//分页查询
	@ApiOperation(value="用户列表分页",notes="用户列表分页")
	@GetMapping("listPage")
    fun listPage(query :UserQuery) :PageVO<UserRespVo> ? {
    	var listPage = userService.listPage(query);
        return listPage;
    }
	
	
	@PostMapping("/getBody")
    fun getBody(@RequestBody jsonText:UserRespVo){
		println(jsonText.component2())
		println(jsonText.userName)
    }
	
	
}