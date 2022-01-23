package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.config;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.service.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Authentication 怎么用by spring security
 * 两个annotation:
 * 1. @Configuration: spring will use as a source of configuration for the IOC context.
 *    表示spring会用他做IOC的配置context
 * 2. @EnableWebSecurity 让spring 知道这个类是为了配置spring security的
 * 3. WebSecurityConfigurerAdapter 让我们写一个adapter 为了web security 配置。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 1. 分离authenticationService，设定自己的authentication 策略。 separate concern:
     */
    private AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    /**
     * 2. override authentication function
     * 使用我们自己的策略来给access authenticate
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    /**
     * authentication chain
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/signup", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.formLogin()
                .defaultSuccessUrl("/home", true);
        http.logout()
                .logoutSuccessUrl("/login?logout").permitAll();
    }

}