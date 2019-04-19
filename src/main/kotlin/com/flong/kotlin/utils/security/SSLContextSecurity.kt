package com.flong.kotlin.utils.security


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.security.cert.X509Certificate

object SSLContextSecurity {

	/**
	 * 绕过验证
	 * @return
	 */
	fun createIgnoreVerifySSL(sslVersion: String): SSLSocketFactory {
		var sc = SSLContext.getInstance(sslVersion);
		val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
			@Throws(CertificateException::class)
			override fun checkClientTrusted(
					chain: Array<java.security.cert.X509Certificate>, authType: String) {
			}

			@Throws(CertificateException::class)
			override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
			}

			override fun getAcceptedIssuers(): Array<X509Certificate?> {
				return arrayOfNulls(0)
			}
		})

		sc!!.init(null, trustAllCerts, java.security.SecureRandom())

		// Create all-trusting host name verifier
		val allHostsValid = HostnameVerifier { _, _ -> true }
		/***
		 * 如果 hostname in certificate didn't match的话就给一个默认的主机验证
		 */
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		return sc.getSocketFactory();
	}


}