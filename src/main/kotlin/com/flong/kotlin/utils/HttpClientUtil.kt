package com.flong.kotlin.utils


import com.alibaba.fastjson.JSONObject
import com.flong.kotlin.utils.security.SSLContextSecurity
import org.apache.http.HttpStatus
import org.apache.http.NameValuePair
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.SSLContextBuilder
import org.apache.http.conn.ssl.TrustStrategy
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.ArrayList
import java.util.HashMap
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


/**
 * object表示这个类是单例模式
 * @Description HttpClientUtil帮助类
 * @Author      liangjilong
 * @Date        2018年12月17日
 */
 object HttpClientUtil {

	
	private val log: Logger = LoggerFactory.getLogger(HttpClientUtil::class.java)
	
	private var JSON_APPLICATION 		= "application/json; charset=utf-8"
	private var CONNECT_TIMEOUT 		= 1000 * 20 
	private var MAX_TIMEOUT 			= 1000 * 20
	private var DEFAULT_ENCODING 		= "UTF-8"
	private var LINE 					= System.getProperty("line.separator")//换行相当于\n
	
	private val requestConfig : RequestConfig
	init {
		// 设置连接池  
		var connMgr = PoolingHttpClientConnectionManager()
		// 设置连接池大小  
		connMgr.maxTotal = 100
		connMgr.setDefaultMaxPerRoute(connMgr.maxTotal)

		var configBuilder = RequestConfig.custom()
		// 设置连接超时  
		configBuilder.setConnectTimeout(MAX_TIMEOUT)
		// 设置读取超时  
		configBuilder.setSocketTimeout(MAX_TIMEOUT)
		// 设置从连接池获取连接实例的超时  
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT)
		// 在提交请求之前 测试连接是否可用  
		configBuilder.setStaleConnectionCheckEnabled(true)
		requestConfig = configBuilder.build()

	}


	/**
	 * @Description 获取参数内容
	 * @Author      liangjilong
	 * @Date        2017年6月6日 上午11:36:50
	 * @param bodyParameters
	 * @throws UnsupportedEncodingException 参数
	 * @return String 返回类型
	 */
	fun getConcatParams(bodyParameters: Map<String, String>, encoding :String?): String? {
		
		var content   = ""
		//?： elvis操作符(猫王）,encoding? 表示if(encoding != null) encoding else 表示为空的时候默认就给 utf-8
		//如果  ?:  左侧表达式非空，elvis 操作符就返回其左侧表达式，否则返回右侧表达式。 请注意:当且仅当左侧为空时，才会对右侧表达式求值。
		var charset   = encoding ?: "utf-8"
		
		var builder   = StringBuilder()
		for (i in bodyParameters.entries) {
			//将参数解析为"name=tom&age=21"的模式
			builder.append(i.key).append("=").append(URLEncoder.encode(i.value.toString(), charset)).append("&")
		}
		if (builder.length > 1) {
			content = builder.substring(0,builder.length -1)
		}
		return content
	}

	
	//拼接参数同等getConcatParams方法,但是不encode编码
	fun paramsToQueryString(bodyParameters: Map<String, String>) :String? {
		return bodyParameters.entries.stream().map({e -> e.key + "=" + e.value}).collect(Collectors.joining("&"))
    }
	//拼接参数同等getConcatParams方法,但是encode编码
	fun paramsToQueryStringUrlencoded(bodyParameters: Map<String, String>,encoding :String?): String? {
		//?： elvis操作符(猫王）,encoding? 表示if(encoding != null) encoding else 表示为空的时候默认就给 utf-8
		var charset   = encoding ?: "utf-8"
		return bodyParameters.entries.stream().map({ e -> e.key + "=" + URLEncoder.encode(e.value, charset) }).collect(Collectors.joining("&"))
	}


	/**
	 * @Description 请求链接返回InputStream
	 * @Author        liangjl
	 * @Date        2018年6月12日 下午8:37:42
	 * @param reqUrl
	 * @return 参数
	 * @return InputStream 返回类型
	 */
	fun createHttp(reqUrl: String): InputStream ? {
		try {
			var url = URL(reqUrl) // 创建URL
			var urlconn = url.openConnection() // 试图连接并取得返回状态码
			urlconn.connect()
			//把Connection转换成HttpURLConnection,使用安全的类型转换，如果尝试转换不成功则返回  null 
			//var httpconn = urlconn as? HttpURLConnection //as?下面if条件写法完全不一样
			//if (httpconn?.responseCode != HttpURLConnection.HTTP_OK) {
			
			var httpconn = urlconn as HttpURLConnection 
			if (httpconn.responseCode != HttpURLConnection.HTTP_OK) {
				log.error("createHttp方法出错,无法连接到")
			} else {
				return urlconn.inputStream
			} 
		} catch (e: Exception) {
			log.error("createHttp方法出错,出错原因为:" + e.message)
		}
		return null
	}
	
	//创建http
	fun createHttp(reqUrl :String ,method: String,bodyParams:  String,
				   headers:Map<String,String>? ,charsetName :String?): String {

		val url  = URL(reqUrl)
		val conn = url.openConnection() as HttpURLConnection
		conn.requestMethod 	= method
		conn.doOutput		= true
		conn.doInput		= true
		conn.useCaches		= false
		conn.connectTimeout = CONNECT_TIMEOUT
		conn.readTimeout 	= CONNECT_TIMEOUT
		setRequestProperty(headers, conn)

		if (bodyParams.isNotEmpty()) {
			if (charsetName != null && !"".equals(charsetName)) {
				IoUtils.writerOutputStream(conn.outputStream, bodyParams, charsetName)
			} else {
				IoUtils.writerOutputStream(conn.outputStream, bodyParams)
			}
		}
		return IoUtils.getInputStream(conn.inputStream, charsetName)
	}
	
	
	/**
     * @Description 建立http请求链接支持SSL请求
     * @Author      liangjilong  
     * @Date        2017年6月6日 上午11:11:56  
     * @param reqUrl
     * @param requestMethod
     * @param outputStr
     * @param headerMap请求头属性，可以为空
     * @param sslVersion  支持https的版本参数(TLSv1, TLSv1.1  TLSv1.2)
     * @param bodyParameters  
     * @return String 返回类型   
 	 */
	fun createHttps(reqUrl: String, requestMethod: String, headerMap: Map<String, String>?,
					sslVersion: String, bodyParameters: String?, encoding: String): String {
		
		var conn :HttpsURLConnection? = null
		try {
			//这行代码必须要在创建URL对象之前，因为先校验SSL的https请求通过才可以访问http
			var ssf  = SSLContextSecurity.createIgnoreVerifySSL(sslVersion)
				
			var url  = URL(reqUrl)
		 
			conn = url.openConnection() as HttpsURLConnection
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			conn.sslSocketFactory 	= ssf
			conn.doOutput			= true
			conn.doInput			= true
			conn.useCaches			= false
			conn.connectTimeout 	= CONNECT_TIMEOUT
			conn.readTimeout 		= CONNECT_TIMEOUT
			
			addRequestProperty(headerMap, conn)
	        
			// 设置请求方式（GET/POST）
			conn.requestMethod  = requestMethod
			// 当设置body请求参数
			if (bodyParameters != null && bodyParameters.isNotEmpty()) {  
	            IoUtils.writerOutputStream(conn.outputStream, bodyParameters)
	        } 

			if(conn.getResponseCode() == HttpStatus.SC_OK){
				// 从输入流读取返回内容
				return IoUtils.getInputStream(conn.inputStream,encoding)
			}else{
				return IoUtils.getInputStream(conn.inputStream,encoding)
			}
		
		} catch (e :Exception)  {
			log.error("https请求异常：{}请求链接" + reqUrl,e)
			return ""//请求系统频繁
		}finally{
			// 释放资源
			if(conn!=null){conn.disconnect()}
		}
	}
	
	
	/**
	 * @Description 支持HttpClient的GET和POST请求,支持http和https协议
	 * @Author	liangjl
	 * @Date		2018年5月7日 下午10:05:28
	 * @param reqUrl 		请求链接
	 * @param requestMethod 请求方法GET/POSt
	 * @param bodyParameters 
	 * @param encoding 编码
	 * @param headerMap 请求头 
	 * @return String 返回类型      
	 */
   fun createHttp(reqUrl:String ,requestMethod :String, bodyParameters:Map<String,String>,
					 encoding :String?,headerMap:Map<String,String>?):String ?{  
   		
		//这里是要转成Java的String,因为kotlin的String是没有忽略大小写这个方法
		//kotlin的==和equals是相同的都是比较字符串的值，而且===三个等号比较的是地址的值.
		var method  = requestMethod as java.lang.String
		
   		if((reqUrl.startsWith("https") || reqUrl.contains("https") )  && method.equalsIgnoreCase("POST")) {
   			
   			return commonHttpClientPost(reqUrl, bodyParameters, encoding, headerMap, createHttpClient());  
   			
   		} else if((reqUrl.startsWith("http") || reqUrl.contains("http") )  && method.equalsIgnoreCase("POST")) {
   			
   			return commonHttpClientPost(reqUrl, bodyParameters, encoding, headerMap, HttpClients.createDefault());
   			
   		}else if((reqUrl.startsWith("https") || reqUrl.contains("https") )  && method.equalsIgnoreCase("GET")) {
   			
   			return commonHttpClientGet(reqUrl, bodyParameters, encoding, headerMap, createHttpClient());
			
   		}else {
   			return commonHttpClientGet(reqUrl, bodyParameters, encoding, headerMap, HttpClients.createDefault());  
   		}
   }
	
	/**
    * @Description 发送Post请求  
    * @Author      liangjilong  
	* @Email       jilongliang@sina.com 
    * @Date        2017年10月31日 上午11:05:40  
    * @param reqUrl 请求Url
    * @param bodyParameters
    * @return 参数  
    * @return String 返回类型
	 */
	fun createHttpPost(reqUrl:String, bodyParameters :List<BasicNameValuePair>):String  {
		var httpClient 	= createHttpClient()
		var httpPost 	= HttpPost(reqUrl)//创建HttpPost
		httpPost.config = requestConfig
		httpPost.entity = UrlEncodedFormEntity(bodyParameters, DEFAULT_ENCODING)//设置entity
		
		var httpResponse= httpClient.execute(httpPost)

		if (httpResponse.statusLine != null && httpResponse.statusLine.statusCode == HttpStatus.SC_OK) {
			var retMsg = EntityUtils.toString(httpResponse.entity, DEFAULT_ENCODING)
			if (retMsg != null && retMsg.isNotEmpty()) {
				return retMsg
			}
		} else {
			return ""
		}
		return ""
	}	
	
	 /**
     * @Description commonHttpClientGet
     *  如：{'userName':'小梁','age':'100'}
     *  
     * @Author      liangjilong  
	 * @Email       jilongliang@sina.com 
     * @Date        2017年8月1日 上午10:35:09  
     * @param reqUrl 请求链接
     * @param bodyParameters 请求参数
     * @param encoding 编码，不穿默认UTF-8
     * @param headerMap 头参数：如 application/x-www-form-urlencoded charset=utf-8
     * @param httpClient
     * @return 参数  
     * @return String 返回类型
     */
	private fun commonHttpClientGet(reqUrl :String, bodyParameters:Map<String, String>?,encoding :String?,
							headers: Map<String, String>?, httpClient:CloseableHttpClient):String? {
		var restMsg: String = ""
		var response: CloseableHttpResponse ?= null
		try {
			//把参数转换成字符串
			var reqParamStr = EntityUtils.toString(setUrlEncodedFormEntity(bodyParameters, encoding))
			var httpGet : HttpGet?
			if (reqParamStr != null && reqParamStr.isNotEmpty()) {
				
				httpGet = HttpGet(reqUrl + "?" + reqParamStr)
				log.info(".commonHttpClientGet,reqUrl:" + reqUrl + "?" + reqParamStr)
				
			} else {
				httpGet = HttpGet(reqUrl)
			}
			/*设置请求头属性和值 */
			if (headers != null && headers.isNotEmpty()) {
			for (key in headers.keys) {
					var value = headers.get(key)
					httpGet.addHeader(key, value)
				}
			}
			httpGet.config = requestConfig
			
			response = httpClient.execute(httpGet)
			var entity = response.entity
			if (entity != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				restMsg = EntityUtils.toString(entity, encoding)
				log.info(".commonHttpClientGet,请求链接为:" + reqUrl + ",Response Content: " + restMsg)
			} else {
				restMsg = ""
				log.error(".ommonHttpClientGet的entity对象为空" + reqUrl)
			}
		} catch (e: IOException) {
			log.error("commonHttpClientGet出现异常，异常信息为:" + e.message)
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity())
				} catch (e: IOException) {
				}
			}
		}
		return restMsg
	}
	
	
	/**
     * @Description commonHttpClientPost
     * @Author      liangjilong  
	 * @Email       jilongliang@sina.com 
     * @Date        2017年8月1日 上午10:34:43  
     * @param reqUrl
     * @param bodyParameters
     * @param contentType
     * @param encoding 编码，不传默认UTF-8
     * @param headerMap
     * @param httpClient
     * @return 参数  
     * @return String 返回类型
	 */
	private fun commonHttpClientPost(reqUrl:String ,bodyParameters: Map<String, String> ?,  encoding:String?,
							 headerMap:Map<String, String>?,  httpClient:CloseableHttpClient):String {
		
		var restMsg : 	String = ""
		var response: 	CloseableHttpResponse? = null
		try {
			var httpPost    = setPostHeader(reqUrl, headerMap)/*设置请求头属性和值 */
			//把参数转换成字符串
			var reqParamStr = EntityUtils.toString(setUrlEncodedFormEntity(bodyParameters, encoding))
			httpPost.config = requestConfig
			setStringEntity(reqParamStr, encoding, httpPost)
			
			response = httpClient.execute(httpPost)
			
			var entity = response.getEntity()
			
			if (entity != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				var buffer = IoUtils.getInputStream(entity.getContent(), encoding)
				log.info(".commonHttpClientPost,reqUrl:" + reqUrl + ",Response content : " + buffer.toString())
				return buffer.toString()//返回  
			} else {
				var buffer = IoUtils.getInputStream(entity.getContent(), encoding)
				log.error(".commonHttpClientPost的entity对象为空,请求链接为：" + reqUrl)
				return buffer.toString()//返回  
			}
		} catch (e: IOException) {
			log.error("commonHttpClientPost出现异常，异常信息为:" + e.message)
		} finally {
			if (response != null) {
				response.close()
			}
			httpClient.close()
		}
		return restMsg
	}  
   
	
	 /**
     * @Description 创建UrlEncodedFormEntity参数对象实体 
     * 相当于处理请求链接的参数如： -->http://www.yorisun.com/?username=yy&age=100
     * @Author      liangjilong  
	 * @Email       jilongliang@sina.com 
     * @Date        2017年8月1日 上午10:34:25  
     * @param bodyParameters
     * @param encoding
     * @param reqParamStr
     * @throws IOException
     * @throws UnsupportedEncodingException 参数  
     * @return String 返回类型
     */
	fun setUrlEncodedFormEntity(bodyParameters :Map<String, String>?,  encoding:String?) :UrlEncodedFormEntity ? {
		
		if(bodyParameters != null && bodyParameters.isNotEmpty()){
			//封装请求参数  
		    var params = ArrayList<NameValuePair>()
			for (entry in bodyParameters.entries) {  
				var key 	= entry.key
				var `val` 	= entry.value
				params.add(BasicNameValuePair(key,  `val`))
			}
			var charset   = encoding ?: "utf-8"
			return  UrlEncodedFormEntity(params,Charset.forName(charset))
		}
		return null
	}  
	/**
	 *设置头属性
	 */
	fun setRequestProperty(headers : Map<String, String>?, conn :HttpURLConnection) {
		
		if (headers != null && headers.isNotEmpty()) {
			for (key in headers.keys) {
				var value = headers.get(key)
				conn.setRequestProperty(key, value)
			}
		}
	}
	
	/**
	 * @Description 添加请求属性
	 * @Author	liangjl
	 * @Date		2018年5月7日 下午9:51:54
	 * @param headerMap
	 * @param conn 参数
	 * @return void 返回类型 
	 */
	fun addRequestProperty(headers :Map<String,String>?,  conn :HttpsURLConnection) {
		/*设置请求头属性和值 */
		if (headers != null && headers.isNotEmpty()) {
			for (key in headers.keys) {
				 var value = headers.get(key)
				 //如：conn.addRequestProperty("Authorization","123456")
				 conn.addRequestProperty(key,value)
			 }
		}
	}
	
	
	
	 /**
	 * @Description 创建Post的头参数处理
	 * @Author	liangjl
	 * @Date		2018年4月25日 上午11:38:37
	 * @param reqUrl
	 * @param headerMap
	 * @return 参数
	 * @return HttpPost 返回类型 
     */
	 fun setPostHeader(reqUrl : String, headers :Map<String, String> ?) :HttpPost {
		var httpPost = HttpPost(reqUrl)  
        
        /*设置请求头属性和值 */
		if(headers != null &&headers.isNotEmpty()){
			 for (key in  headers.keys) {
				 var value = headers.get(key)
				 //如： httpPost.addHeader("Content-Type", "application/json")  
				 httpPost.addHeader(key,value)
			 }
		}
		return httpPost
	}  
	
	 /**
	 * @Description 设置setStringEntity参数
	 * @Author	liangjl
	 * @Date  2018年4月25日 下午12:23:57
	 * @param bodyParameters
	 * @param contentType 
	 * @param encoding
	 * @param httpPost 参数
	 * @return void 返回类型 
     */
	fun setStringEntity(bodyParameters : Any?,  encoding :String?,  httpPost :HttpPost) {
		 if (bodyParameters  != null) {
			 // 解决中文乱码问题
			 var charset 	  = encoding ?: "utf-8"
			 var stringEntity = StringEntity(bodyParameters.toString(), charset)

			 if (encoding != null && encoding.isNotEmpty()) {
				 stringEntity.setContentEncoding(encoding)
			 }
			 httpPost.setEntity(stringEntity)
		 }
	 }
	
    
	
	//创建httpClient
	 fun createHttpClient():CloseableHttpClient {
		 val trustStrategy = object : TrustStrategy {
			 override fun isTrusted(xcert509: Array<java.security.cert.X509Certificate>, arg1: String): Boolean {
				 return true
			 }
		 }
		 var sslcontext = SSLContextBuilder().loadTrustMaterial(null,trustStrategy).build()

		 var versions   = arrayOf( "TLSv1", "TLSv1.1", "TLSv1.2")
		 var sslsf 	    = SSLConnectionSocketFactory(sslcontext,versions, null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
		 return HttpClients.custom().setSSLSocketFactory(sslsf).build()
	}
	 
	
	
	/*private String  String java.lang.String(){
		
	}*/
	
	
	@JvmStatic //main测试
	fun main(args: Array<String>) {
		//var b = mapOf("age" to 23, "userName" to "ljl")
		
		var map3 = HashMap<String,String>()
	
		map3.put("age","23")
		map3.put("userName","寅务")

		//var p = getConcatParams(map3,null)
		//var p = paramsToQueryString(map3)
		//println(p)
		var reqUrl = "http://localhost:8080/rest/getBody"
		var method = "POST"
		var json = JSONObject()
		json.put("passWord","123456")
		json.put("userName","寅务")
		
		var header =   HashMap<String, String>()
		header.put("Content-Type", JSON_APPLICATION)
		
		var retMsg = createHttp(reqUrl,method,json.toJSONString(),header,"utf-8")
		println("retMsg=" +retMsg)

	}

}