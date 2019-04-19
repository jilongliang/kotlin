package com.flong.kotlin.core.service

import com.baomidou.mybatisplus.mapper.BaseMapper
import com.baomidou.mybatisplus.mapper.EntityWrapper
import com.baomidou.mybatisplus.service.impl.ServiceImpl
import com.flong.kotlin.core.Query
import java.io.Serializable
import com.baomidou.mybatisplus.plugins.Page


open class BaseService<M : BaseMapper<T>, T, Q : Query> : ServiceImpl<M, T> {

	//构造方法
	constructor() : super()

	var IN_SIZE: Int = 1000;
	/**
	 * 新增
	 */
	fun add(obj: T): Boolean {
		var affCnt = baseMapper.insert(obj);
		return null != affCnt && affCnt > 0;
	}

	override fun updateById(obj: T): Boolean {
		var affCnt = baseMapper.updateById(obj);
		return null != affCnt && affCnt > 0;
	}


	/**
	 * 删除
	 */
	override fun deleteById(id: Serializable): Boolean {
		var affCnt = baseMapper.deleteById(id);
		return null != affCnt && affCnt > 0;
	}

	 

	/**
	 * ID 取对象,取不到为空
	 */
	fun get(id: Serializable): T {
		return baseMapper.selectById(id);
	}

	fun getMapper(): M {
		return this.baseMapper;
	}

		
	fun buildQuery(query: Q): EntityWrapper<T> {
		return EntityWrapper<T>();
	}

}