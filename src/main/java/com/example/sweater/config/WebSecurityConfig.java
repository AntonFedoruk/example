package com.example.sweater.config;

import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // this annotation adds kind of authority to give access to methods
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/registration", "/static","/activate/*").permitAll() //we grant access to all parts on page with "/","/registration" path,
                                                                                      // "/static"(users can have access  to static resources(css)) even if they are not login
                                                                                      // "/activate/*": URl can be /activate/ + 1 segment(in which activation code was putted)
                .anyRequest().authenticated()             //we limited access on other pages
                .and()
                .formLogin()
                .loginPage("/login") //mapping on login page
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    //to get users from DB
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance()); //encode passwords to prevent their explicit storage
        //NoOpPasswordEncoder is useful only for testing
    }
}
/*
The WebSecurityConfig class is annotated with @EnableWebSecurity to enable Spring Security’s web security support and
provide the Spring MVC integration. It also extends WebSecurityConfigurerAdapter and overrides a couple of its methods
 to set some specifics of the web security configuration.

The configure(HttpSecurity) method defines which URL paths should be secured and which should not. Specifically, the /
 and /home paths are configured to not require any authentication. All other paths must be authenticated.

When a user successfully logs in, they are redirected to the previously requested page that required authentication.
There is a custom /login page (which is specified by loginPage()), and everyone is allowed to view it.
*/