package com.cybr406.account.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource);
    }

    @Autowired
    H2SecurityConfigurer h2SecurityConfigurer;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        h2SecurityConfigurer.configure(http);

        http
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/").permitAll()
                .mvcMatchers(HttpMethod.GET,"/profiles").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
        ;
    }

}
