package com.flong.kotlin.core.vo

open class ErrorResp {
	
	var code 		= 99999;
	var msg 	= "";
	var timestamp 	= System.currentTimeMillis();

	constructor(code: Int, msg: String) {
		this.code = code;
		this.msg  = msg;
	}
}