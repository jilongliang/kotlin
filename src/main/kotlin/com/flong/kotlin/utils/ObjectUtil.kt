package com.flong.kotlin.utils

import org.apache.commons.lang3.StringUtils

import java.math.BigInteger

open class ObjectUtil {

	
	
    /**
     * @param object 关键字要用双引号
     * @return
     */
    fun isNull(`object`: Any?): Boolean {
        return if (null == `object`) true else false
    }

    /**
     * @param object
     * *
     * @return
     */
    fun isNotNull(`object`: Any): Boolean {
        return !isNull(`object`)
    }

    /**
     * @param string
     * *
     * @return
     */
    fun isEmpty(string: String): Boolean {
        return StringUtils.isEmpty(string)
    }

    fun isNotEmpty(string: String?): Boolean {
        return StringUtils.isNotEmpty(string)
    }
	
	fun stringToBytes(hexString: String?): ByteArray? {
		var hexString = hexString
		if (hexString == null || hexString == "") {
			return null
		}
		hexString = hexString.toUpperCase()
		val length = hexString.length / 2
		val hexChars = hexString.toCharArray()
		val d = ByteArray(length)
		for (i in 0..length - 1) {
			val pos = i * 2
			d[i] = (charToByte(hexChars[pos]).toInt() shl 4 or charToByte(hexChars[pos + 1]).toInt()).toByte()
		}
		return d
	}

	/**
	 * Convert char to byte
	 * @param c char
	 * *
	 * @return byte
	 */
	private fun charToByte(c: Char): Byte {
		return "0123456789ABCDEF".indexOf(c).toByte()
	}
	
	companion object{
		/**
	     * 逗号
	     */
	    private const val COMMA_NAME: String = ","
		
		
	    //单例方法，返回对象为ObjectUtil，惰性加载，并且且是线程安全
	    val instance :ObjectUtil by lazy {ObjectUtil()}
	}
	

    /**
     * String 数组 转换成 BigInteger 数组
     *
     * @param array String 数组
     * @return BigInteger 数组
     */
    fun string2BigInteger(array: Array<String>): ArrayList<BigInteger>? {
        val idArray: ArrayList<BigInteger> = arrayListOf()
        var id: BigInteger?
        for (i: Int in array.indices) {
           /* id = array[i].toBigIntegerOrNull()
            if (null != id && BigInteger.ZERO < id) {
                idArray.add(id)
            }*/
        }
        return idArray
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * String 数组 转换成 BigInteger 数组
     *
     * @param array String 数组
     * @return BigInteger 数组
     */
    fun string2BigIntegerArray(array: Array<String>): Array<BigInteger>? {
        val idArray: Array<BigInteger> = Array(array.size, { BigInteger.ZERO })
        var id: BigInteger?
        for (i: Int in array.indices) {
           /* id = array[i].toBigIntegerOrNull()
            if (null != id && BigInteger.ZERO < id) {
                idArray[i] = id
            }*/
        }
        return idArray
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * String 转换成 BigInteger 数组
     * @param str String
     * @return BigInteger 数组
     */
    fun string2BigInteger(str: String): Array<BigInteger>? {

        val strAry: List<String> = str.split(COMMA_NAME)
        val idArray: Array<BigInteger> = Array(strAry.size, { BigInteger.ZERO })
        var id: BigInteger?
        for ((i: Int, strValue: String) in strAry.withIndex()) {
           /* id = strValue.toBigIntegerOrNull()
            if (null != id && BigInteger.ZERO < id) {
                idArray[i] = id
            }*/
        }
        return idArray
    }
}
