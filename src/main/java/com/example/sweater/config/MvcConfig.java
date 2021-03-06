package com.example.sweater.config;

import com.example.sweater.util.RedirectInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    //with @Value("SpEL") annotation we tell Spring that we want to get variable.
    @Value("${upload.path}") //In this case we will get 'upload.path' from application.properties
    private String uploadPath;

    /*
    A more useful way to consume a REST web service is programmatically. To help you with that task, Spring provides a
    convenient template class called RestTemplate. RestTemplate makes interacting with most RESTful services a one-line
    incantation. And it can even bind that data to custom domain types.
    */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    //We override the addResourceHandlers() to register handlers and locations for img files
    @Override //when we try ty connect to server with path "/img/***" --> server will redirect us to \
    public void addResourceHandlers(ResourceHandlerRegistry registry) {                          //  |
        registry                                                                                //   |
                .addResourceHandler("/img/**")                                     //   V
                .addResourceLocations("file://" + uploadPath + "/");  //  "file://" - store in filesystem + "uploadPath" - absolute path
        registry.addResourceHandler("/static/**").
                addResourceLocations("classpath:/static/"); // "classpath:"- resources will be looking in the tree-project
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RedirectInterceptor());
    }
}
