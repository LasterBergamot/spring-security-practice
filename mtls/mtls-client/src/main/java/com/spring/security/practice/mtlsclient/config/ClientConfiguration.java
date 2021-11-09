package com.spring.security.practice.mtlsclient.config;

import java.io.InputStream;
import java.security.KeyStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class ClientConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        KeyStore keyStore;
        HttpComponentsClientHttpRequestFactory requestFactory = null;

        try {
            keyStore = KeyStore.getInstance("jks");
            ClassPathResource classPathResource = new ClassPathResource("ssp-mtls-client.jks");
            InputStream inputStream = classPathResource.getInputStream();
            keyStore.load(inputStream, "ssp-mtls-client".toCharArray());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .loadKeyMaterial(keyStore, "ssp-mtls-client".toCharArray()).build(),
                    NoopHostnameVerifier.INSTANCE);

            HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory)
                    .setMaxConnTotal(5)
                    .setMaxConnPerRoute(5)
                    .build();

            requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            requestFactory.setReadTimeout(10000);
            requestFactory.setConnectTimeout(10000);

            restTemplate.setRequestFactory(requestFactory);
        } catch (Exception e) {
            log.error("Exception occurred while setting up RestTemplate!");
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return restTemplate;
    }
}
