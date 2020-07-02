package com.flong.kotlin.modules.controller

import com.flong.kotlin.core.web.BaseController
import com.flong.kotlin.modules.entity.Sales
import com.flong.kotlin.modules.service.SalesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/rest/sales/")
open class SalesController : BaseController() {

    @Autowired lateinit var salesService: SalesService

    /**
     * 查询所有信息
     */
    @GetMapping("/findAll")
    fun findAll(): List<Sales>? {
        return salesService.findAll()
    }

    /**
     * 排序的使用
     */
    @GetMapping("/findSort")
    fun findSort(): List<Sales>? {
        return salesService.findSort()
    }


    /**
     * 大于的使用
     */
    @GetMapping("/findGt")
    fun findGt(): List<Sales>? {
        return salesService.findGt()
    }
    /**
     * 小于的使用
     */
    @GetMapping("/findLt")
    fun findLt(): List<Sales>? {
        return salesService.findLt()
    }


    /**
     * 根据Id去查询一条数据
     */
    @GetMapping("/getOne/{id}")
    fun getOne(@PathVariable("id") id:Long): Sales? {
        return salesService.getOne(id)
    }


    /**
     * 插入一条数据
     */
    @PostMapping("/insert")
    fun insert(): Unit {
        return salesService.addSales()
    }
    /**
     * 删除一条数据
     */
    @PutMapping("/deleteById/{id}")
    fun deleteById(@PathVariable("id") id:String): Unit {
        return salesService.deleteById(id)
    }


    /**
     * 修改一条数据
     * 假设如下的数据，传的JSON体为
     * Content-Type = application/json;charset=UTF-8
     * 
     * {  "id" : "1278573453263409154",  "item" : "苹果",  "quantity" : 10 }
     */
    @PutMapping("/update")
    fun update(@RequestBody sales:Sales): Unit {
        return salesService.update(sales)
    }


}