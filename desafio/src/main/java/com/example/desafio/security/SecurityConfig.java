package com.example.desafio.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() // Habilita CORS (usa sua CorsConfig)
                .and()
                .csrf().disable() // Desativa CSRF (para evitar bloqueios em POST com JSON)
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated();
    }
}
