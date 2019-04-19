package com.flong.kotlin.core.exception

interface MsgCode  {

	fun getCode(): Int;

	fun getMessage(): String;

	fun getName(): String;
}