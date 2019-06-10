package com.flong.kotlin.core.vo


class LiveResp<Any> {

	var code: Int = 200;
	var msg: String = "";
	var data: Any ?= null; //初始化为null

	/**
	 * 响应时间戳
	 */
	var timestamp = System.currentTimeMillis();

	constructor() {
	}

	constructor(data: Any) {
		this.data = data;
	}


}