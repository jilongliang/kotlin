### 1、mongodb的低版本bson无法转换类型
* 比如MongoDB数据库表的字段类型为Decimal，实体类用String去定义就会报如下错误
* No converter found capablof converting from type [org.bson.types.Decimal128] to type [java.lang.String]

> 解决方法

### 2、排除自带的MongoDB依赖,添加自定义添加依赖
* spring-boot-starter-data-mongodb有多少个与MongoDB有关系的都排除出去
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
    <version>2.2.2.RELEASE</version>
    <!-- 如果日志使用log4j2，需要排除，-->
    <exclusions>
        <exclusion>
            <artifactId>logback-classic</artifactId>
            <groupId>ch.qos.logback</groupId>
        </exclusion>
        <exclusion>
            <artifactId>spring-boot-starter-logging</artifactId>
            <groupId>org.springframework.boot</groupId>
        </exclusion>
        <exclusion>
            <groupId>org.mongodb</groupId>
            <artifactId>mongodb-driver</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.mongodb</groupId>
            <artifactId>mongodb-driver-core</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.mongodb</groupId>
            <artifactId>bson</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

### 3、自定义高版本的jar依赖

```
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver</artifactId>
    <version>3.12.5</version>
</dependency>
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-core</artifactId>
    <version>3.12.5</version>
</dependency>

<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>bson</artifactId>
    <version>3.12.5</version>
</dependency>
```

### 4、数据源配置解决方法
* [参考文章](https://blog.csdn.net/weixin_44761855/article/details/105239537)处理
* mongodb://用户名:密码@机器IP:端口/数据库名?授权源=授权的名称
``` 
spring.data.mongodb.uri=mongodb://liangjl:123456@192.168.1.231:27017/mongoDB?authSource=admin
```


### 5、实体类
``` 

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*

@Document(collection = "sales")
data class Sales constructor(
        /**
         * id
         */
        @Id
        var id: String? = null,
        /**
         * 条目
         */
        var item: String? = null,
        /**
         * 价钱
         */
        var price: BigDecimal? = null,
        /**
         * 数量
         */
        var quantity: Int? = null,
        /**
         * 时间
         */
        var date: Date? = null
)
```
### 6、SalesService详解
* 查询所有销售订单数据
``` 
@Autowired lateinit var mongoTemplate: MongoTemplate

/**
 * 查询所有销售订单数据
 * @return
 */
fun findAll(): List<Sales> {
    return mongoTemplate.findAll(Sales::class.java)
}
```
* 查询所有销售订单数据,通过价格进行排序
``` 

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
```

* 查询所有销售订单数据,通过价格进行排序
``` 
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
```
* gt 大于函数的使用，查询价格大于6元的销售订单数据
```
    fun findGt(): List<Sales> {
        //查询价格大于6元的销售订单数据
        var criteria = Criteria.where("price").gt(6)
        var query = Query(criteria)
        return mongoTemplate.find(query, Sales::class.java)
    }
```
* 查询价格小于6元的销售订单数据
``` 
    fun findLt(): List<Sales> {
        //查询价格小于6元的销售订单数据
        var criteria = Criteria.where("price").lt(6)
        var query = Query(criteria)
        return mongoTemplate.find(query, Sales::class.java)
    }

```
* 通过Id去查询销售订单数据
``` 
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
```
* 插入数据
``` 
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
```
* 删除数据
```
/**
 * 删除数据
 */
fun deleteById(id: String) {
    val query = Query(Criteria.where("id").`is`(id))
    mongoTemplate.remove(query, Sales::class.java)
}
```
* 修改数据
```
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
```

### 7、SalesController代码
``` 

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
```

### 8、数据库表脚本
* [来自官方](https://docs.mongodb.com/manual/reference/operator/aggregation/group/index.html)
``` 
db.sales.insertMany([
  { "_id" : 1, "item" : "abc", "price" : NumberDecimal("10"), "quantity" : NumberInt("2"), "date" : ISODate("2014-03-01T08:00:00Z") },
  { "_id" : 2, "item" : "jkl", "price" : NumberDecimal("20"), "quantity" : NumberInt("1"), "date" : ISODate("2014-03-01T09:00:00Z") },
  { "_id" : 3, "item" : "xyz", "price" : NumberDecimal("5"), "quantity" : NumberInt( "10"), "date" : ISODate("2014-03-15T09:00:00Z") },
  { "_id" : 4, "item" : "xyz", "price" : NumberDecimal("5"), "quantity" :  NumberInt("20") , "date" : ISODate("2014-04-04T11:21:39.736Z") },
  { "_id" : 5, "item" : "abc", "price" : NumberDecimal("10"), "quantity" : NumberInt("10") , "date" : ISODate("2014-04-04T21:23:13.331Z") },
  { "_id" : 6, "item" : "def", "price" : NumberDecimal("7.5"), "quantity": NumberInt("5" ) , "date" : ISODate("2015-06-04T05:08:13Z") },
  { "_id" : 7, "item" : "def", "price" : NumberDecimal("7.5"), "quantity": NumberInt("10") , "date" : ISODate("2015-09-10T08:43:00Z") },
  { "_id" : 8, "item" : "abc", "price" : NumberDecimal("10"), "quantity" : NumberInt("5" ) , "date" : ISODate("2016-02-06T20:20:13Z") },
])
```


### 9、Docker安装MongoDB
* [参考文章](https://www.jianshu.com/p/6f5ce979ccdc)

