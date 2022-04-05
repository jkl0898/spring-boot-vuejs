package de.jonashackt.springbootvuejs.configuration;

import de.jonashackt.springbootvuejs.interceptor.NotebookInterceptor;
import feign.Client;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public Client client() throws NoSuchAlgorithmException,
            KeyManagementException {

        return new Client.Default(
                new NaiveSSLSocketFactory("kf.lianyirong.com.cn"),
                new NaiveHostnameVerifier("kf.lianyirong.com.cn"));
    }

    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return new NotebookInterceptor();
    }
}