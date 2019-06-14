package com.cqut.czb.bn.entity.dto.paymentRecord;

import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

public class MyX509TrustManager implements X509TrustManager {

	// 检查客户端证书
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	// 检查服务器端证书
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	// 返回受信任的X509证书数组
	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	    return null;
	}

	@Override
	public void checkClientTrusted(
			java.security.cert.X509Certificate[] chain, String authType)
			throws java.security.cert.CertificateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkServerTrusted(
			java.security.cert.X509Certificate[] chain, String authType)
			throws java.security.cert.CertificateException {
		// TODO Auto-generated method stub
		
	}
	} 
