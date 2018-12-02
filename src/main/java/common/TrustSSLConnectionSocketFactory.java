package common;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by lewis on 2016/7/2.
 */
public class TrustSSLConnectionSocketFactory {

    /**
     * ����SSL��ȫ����
     *
     * @return
     */
    public static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory connectionSocketFactory=null;
        try{
            SSLContext sslcontext = SSLContexts.custom()
            		.loadTrustMaterial(new org.apache.http.ssl.TrustStrategy() {
						
						@Override
						public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
							// TODO Auto-generated method stub
							return true;
						}
					}).build();

            connectionSocketFactory = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
       return connectionSocketFactory;
    }
}
