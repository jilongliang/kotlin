package com.flong.kotlin.modules.entity

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
