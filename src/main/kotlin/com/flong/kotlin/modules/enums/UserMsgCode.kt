package com.flong.kotlin.modules.enums

/*
 *定义一个枚举类
 */
enum class UserMsgCode() {

    FIND_NOT_USER(1001, "找不到用户信息！"),
    ;


    var code: Int? = null
    var message: String? = null

    constructor(code: Int, message: String) {
        this.code = code
        this.message = message
    }

    //静态
    companion object {
        /**
         * 根据 key 获取 value
         * @param key key
         * @return String
         */
        fun getValueByKey(key: Int?): String? {
            val enums: Array<UserMsgCode> = UserMsgCode.values()
            return enums.indices
                    .firstOrNull { key == enums[it].code }
                    ?.let { enums[it].message }
                    ?: ""
        }

        /**
         * 根据 value 获取 key
         * @param value String
         * @return Int
         */
        fun getKeyByValue(value: String?): Int? {
            val enums: Array<UserMsgCode> = UserMsgCode.values()
            return enums.indices
                    .firstOrNull { value == enums[it].message }
                    ?.let { enums[it].code }
        }

    }

}