package com.example.sweater.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    //with @Value("SpEL") annotation we tell Spring that we want to get variable.
    @Value("${upload.path}") //In this case we will get 'upload.path' from application.properties
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    //We override the addResourceHandlers() to register handlers and locations for img files
    @Override //when we try ty connect to server with path "/img/***" --> server will redirect us to \
    public void addResourceHandlers(ResourceHandlerRegistry registry) {                          //  |
        registry
                .addResourceHandler("/img/**")                                     //  V
                .addResourceLocations("file://" + uploadPath + "/");  //  "file://" - store in filesystem + "uploadPath" - absolute path
        registry.addResourceHandler("/static/**").
                addResourceLocations("classpath:/static/"); // "classpath:"- resources will be looking in the tree-project
    }
}
