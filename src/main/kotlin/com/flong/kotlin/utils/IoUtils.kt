package com.flong.kotlin.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Arrays
import java.io.InputStreamReader
import java.io.BufferedReader
import java.lang.Byte

object IoUtils{
	
	
	private val log: Logger = LoggerFactory.getLogger(IoUtils::class.java) 
	
	/**
     * 点名称
     */
    private const val DOT: String = "."

    /**
     * 点名称
     */
    private const val SIZE_2MB: Long = 1024 * 1024 * 2
	
	/**
	 * 获取 classpath
	 */
	val classpath: String?
		get() {
			var path: String? = Thread.currentThread().contextClassLoader.getResource("/").path
			if (null != path && 1 != path.indexOf(":")) {
				path = File.separator + path
			}
			return path
		}
	
	
	/**
     * 根据文件名 获取其后缀信息
     *
     * @param fileSize 文件大小
     * @return String
     */
    @JvmStatic
    fun gt2Mb(fileSize: Long): Boolean {
        return fileSize > SIZE_2MB
    }
	
	 /**
     * 根据文件名 获取其后缀信息
     * @param filename 文件名
     * @return String
     */
    @JvmStatic
    fun getSuffixByFilename(filename: String): String {
        return filename.substring(filename.lastIndexOf(DOT) + 1).toLowerCase()
    }

	
	/**
     * 只删除此路径的最末路径下所有文件和文件夹
     * @param folderPath 文件路径
     */
    fun delFolder(folderPath: String) {
        try {
            // 删除完里面所有内容
            delAllFile(folderPath)
            val myFilePath = File(folderPath)
            // 删除空文件夹
            myFilePath.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // -------------------------------------------------------------------------------------------------

    /**
     * 删除指定文件夹下所有文件
     * @param path 文件夹完整绝对路径
     * @return true/false
     */
    private fun delAllFile(path: String): Boolean {
        var flag = false
        val file = File(path)
        if (!file.exists()) {
            return false
        }
        if (!file.isDirectory) {
            return false
        }
        val tempList: Array<String>? = file.list()
        var temp: File
        if (null != tempList) {
            for (aTempList: String in tempList) {
                temp = if (path.endsWith(File.separator)) {
                    File(path + aTempList)
                } else {
                    File(path + File.separator + aTempList)
                }
                if (temp.isFile) {
                    temp.delete()
                }
                if (temp.isDirectory) {
                    // 先删除文件夹里面的文件
                    delAllFile("$path/$aTempList")
                    // 再删除空文件夹
                    delFolder("$path/$aTempList")
                    flag = true
                }
            }
        }
        return flag
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * 创建文件夹
     * @param path 文件夹路径
     * @return 文件夹路径
     */
    @JvmStatic
    fun createPath(path: String): File {
        val file = File(path)
        try {
            if (!file.exists()) {
                file.mkdirs()
            }
        } catch (e: Exception) {
            //throw BaseException(">>>>>>>>创建[$path]文件夹失败<<<<<<<<")
			log.error(">>>>>>>>创建[$path]文件夹失败<<<<<<<<")
        }
        return file
    }
	
	
	//通过闭包返回来实现
	fun writeClo(`in`: InputStream, output: OutputStream) {
		try {
			var read: Int = -1
			/*`in`.use { input ->
				output.use {
					while ({ read = input.read();read }() != -1) {
						it.write(read)
					}
				}
			}*/
		} catch (t: Throwable) {
			t.printStackTrace()
		}
	}

	//通过正常写法来实现
	fun writeDef(`in`: InputStream, output: OutputStream) {
		try {
			var read: Int = `in`.read()
			/*`in`.use { input ->
				output.use {
					while (read != -1) {
						it.write(read)
						read = input.read()
					}
				}
			}*/
		} catch (t: Throwable) {
			t.printStackTrace()
		}
	}
	
	
	
	//通过使用also扩展函数来实现
	fun writeAlso(`in`: InputStream, output: OutputStream) {
		try {
			var read: Int = -1
			/*`in`.use { input ->
				output.use {
					while (input.read().also { read = it } != -1) {
						it.write(read)
					}
				}
			}*/
		} catch (t: Throwable) {
			t.printStackTrace()
		}
	}
	
	
	//拼接参数
	fun parameter(map: Map<String, Array<String>>): String {
		 val stringBuilder = StringBuilder("")
		 for ((key: String, value: Array<String>) in map) {
			 stringBuilder.append("{")
			 stringBuilder.append(key)
			 stringBuilder.append("=")
			 stringBuilder.append(Arrays.toString(value))
			 stringBuilder.append("}")
			 stringBuilder.append(",")
		 }
		 if (1 < stringBuilder.length) {
			 stringBuilder.deleteCharAt(stringBuilder.toString().length - 1)
		 }
		 return stringBuilder.toString()
	 }
	
	
	/*
	 *读取流的信息
	 */
	fun getInputStream(`is` :InputStream, charsetName :String?):String {
		var charset = charsetName ?: "utf-8"
		val br = BufferedReader(InputStreamReader(`is`, charset))
		var line: String? = null
		val sb = StringBuilder()
		for (line in br.readLines()) {
			line?.let { sb.append(line) }
		}
		//关闭流
		if (br != null)   br.close();
		if (`is` != null) `is`.close();
		return sb.toString()
	}
	/**
	 * @Description writerOutputStream
	 * @Author	liangjl
	 * @Date	2018年4月25日 下午12:05:16 参数
	 * @return void 返回类型 
	 */
	fun writerOutputStream(outputStream: OutputStream, bodyParameters: String) {
		var out = DataOutputStream(outputStream);
		out.writeBytes(bodyParameters);
		out.flush();
		out.close();
	}

	fun writerOutputStream(outputStream: OutputStream, bodyParameters: String, encoding: String) {
		var bodyParams = bodyParameters  as java.lang.String
		var out = DataOutputStream(outputStream);
		out.write(bodyParams.getBytes(encoding))
		out.flush();
		out.close();
	}


} 