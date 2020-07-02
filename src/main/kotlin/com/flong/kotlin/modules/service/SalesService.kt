package com.flong.kotlin.modules.service

import com.baomidou.mybatisplus.toolkit.IdWorker
import com.flong.kotlin.modules.entity.Sales
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*


@Service
open class SalesService {

    @Autowired lateinit var mongoTemplate: MongoTemplate

    /**
     * 查询所有销售订单数据
     * @return
     */
    fun findAll(): List<Sales> {
        return mongoTemplate.findAll(Sales::class.java)
    }

    /**
     * 查询所有销售订单数据,通过价格进行排序
     * SQL 语句中, asc是指定列按升序排列，desc则是指定列按降序排列。
     * @return
     */
    fun findSort(): List<Sales> {
        //按照价格与数量字段升序查询出来，
        var sort = Sort(Sort.Direction.ASC, "price").and(Sort(Sort.Direction.ASC, "quantity"))
         //查询item为abc的数据进行
        var criteria = Criteria.where("item").`is`("abc")
        var query = Query(criteria)
        //限制1000条数据
        return mongoTemplate.find(query.with(sort).limit(1000), Sales::class.java)
    }


    fun findGt(): List<Sales> {
        //查询价格大于6元的销售订单数据
        var criteria = Criteria.where("price").gt(6)
        var query = Query(criteria)
        return mongoTemplate.find(query, Sales::class.java)
    }

    fun findLt(): List<Sales> {
        //查询价格大于6元的销售订单数据
        var criteria = Criteria.where("price").lt(6)
        var query = Query(criteria)
        return mongoTemplate.find(query, Sales::class.java)
    }


    /**
     * 在MongoDB插入脚本的时候是以数字的进行插入是，这里用String去根据Id去查询是查行不到数据
     * 在mongoDB查询是数据变成,所以id可以看做Long或者Double类型入参进行查询
     *
     * fun getOne(id: Long): Sales? {}
     * fun getOne(id: Double): Sales? {}
     *
     * {
     *    "_id" : 1.0,
     *    "item" : "abc",
     *    "price" : NumberDecimal("10"),
     *    "quantity" : 2,
     *    "date" : ISODate("2014-03-01T08:00:00.000Z")
     * }
     */
    fun getOne(id: Long): Sales? {
        val query = Query(Criteria.where("id").`is`(id))
        var sales = mongoTemplate.findOne(query, Sales::class.java)
        return sales
    }

    /**
     * 插入数据
     */
    fun addSales() {
        var id = IdWorker.getId().toString()
        var sale = Sales(id, "葡萄", BigDecimal(20), 1, Date())
        //方法一
        //mongoTemplate.insert(sale)
        //方法二
        mongoTemplate.save(sale)
    }

    /**
     * 删除数据
     */
    fun deleteById(id: String) {
        val query = Query(Criteria.where("id").`is`(id))
        mongoTemplate.remove(query, Sales::class.java)
    }

    /**
     * 修改数据
     */
    fun update(sales: Sales) {

        var query = Query(Criteria.where("id").`is`(sales.id))

        var update = Update().set("item", sales.item).set("quantity", sales.quantity)
        //更新查询返回结果集的第一条
        var first = mongoTemplate.updateFirst(query, update, Sales::class.java)

        println("update:" + first)

        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update, Sales::class.java)
    }
}