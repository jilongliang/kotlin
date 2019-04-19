

package com.flong.kotlin.utils.security

import org.apache.commons.codec.binary.Hex
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


/**
 * 摘要加密工具类(非可逆加密)
 */
object DigestUtils {

    /**
     * 字符编码
     */
    private const val CHARSET_NAME: String = "UTF-8"
    /**
     * md5 标识
     */
    private const val MD5: String = "MD5"
    /**
     * SHA-1
     */
    private const val SHA1: String = "SHA-1"
    /**
     * SHA-256
     */
    private const val SHA256: String = "SHA-256"
    /**
     * SHA-512
     */
    private const val SHA512: String = "SHA-512"
    /**
     * MAC 密钥
     */
    private const val MAC_KEY: String = "3d011361d08724da546bb888901d6930"
    /**
     * HMAC-MD5
     */
    private const val HMAC_MD5: String = "HmacMD5"
    /**
     * HMAC-SHA1
     */
    private const val HMAC_SHA1: String = "HmacSHA1"
    /**
     * HMAC-SHA256
     */
    private const val HMAC_SHA256: String = "HmacSHA256"
    /**
     * HMAC-SHA512
     */
    private const val HMAC_SHA512: String = "HmacSHA512"
    /***
     * 利用 commons-codec 的工具类实现 MD5 加密
     * @param data 需要加密的字符串
     * @return 加密后的报文
     */
    fun getMd5(data: String): String {
        return getMessageDigest(data, MD5)
    }

    // -------------------------------------------------------------------------------------------------

    /***
     * 利用 commons-codec 的工具类实现 SHA-1 加密
     * @param data 需要加密的字符串
     * @return 加密后的报文
     */
    fun getSha1(data: String): String {
        return getMessageDigest(data, SHA1)
    }

    // -------------------------------------------------------------------------------------------------

    /***
     * 利用 commons-codec 的工具类实现 SHA-256 加密
     * @param data 需要加密的字符串
     * @return 加密后的报文
     */
    fun getSha256(data: String): String {
        return getMessageDigest(data, SHA256)
    }

    // -------------------------------------------------------------------------------------------------

    /***
     * 利用 commons-codec 的工具类实现 SHA-512 加密
     * @param data 需要加密的字符串
     * @return 加密后的报文
     */
    fun getSha512(data: String): String {
        return getMessageDigest(data, SHA512)
    }

    // -------------------------------------------------------------------------------------------------

    /***
     * 利用 commons-codec 的工具类实现 HMAC-MD5 加密
     * @param data 需要加密的字符串
     * @return 加密后的报文
     */
    fun getHmacMd5(data: String): String {
        return getMac(data, HMAC_MD5)

    }

    // -------------------------------------------------------------------------------------------------

    /***
     * 利用 commons-codec 的工具类实现 HMAC-SH1 加密
     * @param data 需要加密的字符串
     * @return 加密后的报文
     */
    fun getHmacSha1(data: String): String {
        return getMac(data, HMAC_SHA1)

    }

    // -------------------------------------------------------------------------------------------------

    /***
     * 利用 commons-codec 的工具类实现 HMAC-SH256 加密
     * @param data 需要加密的字符串
     * @return 加密后的报文
     */
    fun getHmacSha256(data: String): String {
        return getMac(data, HMAC_SHA256)

    }

    // -------------------------------------------------------------------------------------------------

    /***
     * 利用 commons-codec 的工具类实现 HMAC-SH512 加密
     * @param data 需要加密的字符串
     * @return 加密后的报文
     */
    fun getHmacSha512(data: String): String {
        return getMac(data, HMAC_SHA512)

    }

    // -------------------------------------------------------------------------------------------------

    /**
     * 利用 commons-codec 的工具类实现加密
     * @param data 需要加密的字符串
     * @param type 加密类型
     * @return 加密后的报文
     */
    private fun getMac(data: String, type: String): String {
        return try {
            val secretKey = SecretKeySpec(MAC_KEY.toByteArray(charset(CHARSET_NAME)), type)
            val mac: Mac = Mac.getInstance(type)
            mac.init(secretKey)
            mac.update(data.toByteArray(charset(CHARSET_NAME))) 
            return Hex.encodeHexString(mac.doFinal())
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * 利用 commons-codec 的工具类实现加密
     * @param data 需要加密的字符串
     * @param type 加密类型
     * @return 加密后的报文
     */
    private fun getMessageDigest(data: String, type: String): String {
        return try {
            val messageDigest: MessageDigest = MessageDigest.getInstance(type)
            val hash: ByteArray = messageDigest.digest(data.toByteArray(charset(CHARSET_NAME)))
            Hex.encodeHexString(hash)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * 实例
     */
    @JvmStatic
    fun main(args: Array<String>) {

        //println(getMd5("123456") + " " + getMd5("123456").length)
        // f51eb1dce6da0772473dfebdab77f26a57153732
        //println(getSha1("ANhui520") + " " + getSha1("ANhui520").length)

        //println(getSha256("123456123456") + " " + getSha256("123456123456").length)

        // 加密
        var lStart = System.currentTimeMillis()
        // d2ca5d6f6d686ad7b8c59ef8fb5b7c91435f157ce7d2941f0baf28f9300460172941e34095f5748c7a158042b8ddf3c2461ef077bc8c64729c84069e663350a7
        println(getSha512("anhui999") + " " + getSha512("anhui999").length)

        var lUseTime = System.currentTimeMillis() - lStart
        println("加密耗时：" + lUseTime + "毫秒")

        //println(getHmacMd5("123456") + " " + getHmacMd5("123456").length)
        //println(getHmacSha1("123456") + " " + getHmacSha1("123456").length)
        //println(getHmacSha256("123456") + " " + getHmacSha256("123456").length)
        //println(getHmacSha512("123456") + " " + getHmacSha512("123456").length)

    }

    // -------------------------------------------------------------------------------------------------

}
