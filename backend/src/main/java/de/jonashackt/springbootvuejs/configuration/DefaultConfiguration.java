/*
 * Project: beebird-web
 * File created at 2020/11/12 14:43
 * Copyright (c) 2018 linklogis.com all rights reserved.
 */
package de.jonashackt.springbootvuejs.configuration;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认配置
 * `DefaultConfiguration.java` 为默认的配置文件, 里面的配置应为全局通用配置.
 * <p>
 * 自定义配置
 * 如果在部分场景下有特殊配置需求, 需新建一个配置文件来承载自定义的配置, 并且自定义的配置对象须在 `DefaultConfiguration.java` 中有一个全局通用的默认配置对象.
 * <p>
 * 自定义对象
 * 当第三方类库不满足你当前的需求, 需要自定义类的时候, 需要在 `config/custom/` 路径下创建自己的自定义类.
 *
 * @author xieyao
 */
@Configuration
public class DefaultConfiguration {

//    @Bean
//    public OkHttpClient feignClient() {
//        return new OkHttpClient();
//    }

//    @Bean
//    public ErrorDecoder errorDecoder() {
//        return new FeignErrorDecoder();
//    }


//    @Bean
//    public Decoder feignDecoder() {
//        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
//    }
//
//    public ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
//        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(new PhpMappingJackson2HttpMessageConverter());
//        return new ObjectFactory<HttpMessageConverters>() {
//            @Override
//            public HttpMessageConverters getObject() throws BeansException {
//                return httpMessageConverters;
//            }
//        };
//    }
//
//    public class PhpMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
//        PhpMappingJackson2HttpMessageConverter(){
//            List<MediaType> mediaTypes = new ArrayList<>();
//            mediaTypes.add(MediaType.valueOf(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")); //关键
//            setSupportedMediaTypes(mediaTypes);
//        }
//    }

    @Bean
    public Decoder feignDecoder() {
        HttpMessageConverter fastJsonConverter = createFastJsonConverter();
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(fastJsonConverter);
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    @Bean
    public Encoder feignEncoder() {
        HttpMessageConverter fastJsonConverter = createFastJsonConverter();
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(fastJsonConverter);
        return new SpringEncoder(objectFactory);
    }

//    @Bean
//    public Logger feignLogger() {
//        return new Slf4jLogger();
//    }

    private HttpMessageConverter createFastJsonConverter() {

        // 创建fastJson消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        // 由于fastjson1.2.28后，增加了对Content-type验证，故添加多种MediaType
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);

        // 创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);

        return fastConverter;
    }
}
