package com.example.sweater.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

//this config is necessary to make @Bean JavaMailSender work
@Configuration
public class MailConfig {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private int port; //!! int, not String !!
    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String enable;
    @Value("${mail.debug}")
    private String debug; //!! String, not Boolean !!


    @Bean
    public JavaMailSender getMailServer() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("spring.mail.properties.mail.smtp.auth",auth);
        properties.setProperty("spring.mail.properties.mail.smtp.starttls.enable",enable);
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug); // this an unnecessary thing we use to look at bug if it will have place in mail sending

        return mailSender;
    }
}
