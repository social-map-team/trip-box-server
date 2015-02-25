package com.socialmap.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by yy on 2/22/15.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.socialmap.server.controller")
public class MvcConfig extends WebMvcConfigurerAdapter{
    // Configure the HttpMessageConverters to use for reading or writing to the body
    // of the request or response.
    // If no converters are added, a default list of converters is registered.
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        // add jackson2http message converter
        converters.add(new MappingJackson2HttpMessageConverter());
        // if don't use MappingJackson... return string will use ISO8859-1 encoding
        // and that will lead to Chinese characters mess
        // we can also solve this problem by add
        // new StringHttpMessageConverter("UTF-8")
        converters.add(new ByteArrayHttpMessageConverter());
        // we use ByteArrary... to avoid 406 response when we send an image
        // because MappingJackson... will take it as json, I guess
    }
}
