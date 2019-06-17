package com.flong.kotlin.core

import com.baomidou.mybatisplus.plugins.Page
open class PageVO<E>  {

	/**
	 * 总条数
	 */
	var total: Long? = null

	/**
	 * 查询结果
	 */
	var records: Collection<E>? = null

	/**
	 * 当前页
	 */
	var page: Int? = null

	/**
	 * 总页数
	 */
	var totalPage: Long? = null

	/**
	 * 每页显示条数
	 */
	var pageSize: Int? = null


	//init { }

	//多重构造方法
	constructor(records: Collection<E>?, pageVO: PageVO<E>){
		this.records = records;
		this.total = pageVO.total;
		this.totalPage = pageVO.totalPage;
		this.page = pageVO.page;
		this.pageSize = pageVO.pageSize;
		
	}
	//多重构造方法
	constructor(records: Collection<E>?, page: Page<Any>){
		this.records = records;
		this.total = page.getTotal();
		this.totalPage = page.getPages();
		this.page = page.getCurrent();
		this.pageSize = page.getSize();
		
	}
	 

}