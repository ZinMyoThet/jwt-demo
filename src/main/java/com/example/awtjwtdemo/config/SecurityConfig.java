package com.example.awtjwtdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Value("${application.security.allowed-origins}")
    private List<String> allowOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        http.csrf().disable()
        .cors().and().authorizeRequests()
                .mvcMatchers("/api/**")
                .permitAll();
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        final CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(allowOrigins);
        corsConfiguration.setAllowedMethods(List.of("HEAD","GET","POST","PATCH","DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("Authorization","Cache-Control","Content-Type"));
        final UrlBasedCorsConfigurationSource  source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;

    }

}
