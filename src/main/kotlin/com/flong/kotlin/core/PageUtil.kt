package com.flong.kotlin.core

import com.baomidou.mybatisplus.plugins.Page

open class PageUtil {

  /**
   * 取mybatis-plus分页对象
   */
  open fun getPage(query : Query):Page<Any>? {
      return Page(query.page, query.pageSize)
  }

 
}