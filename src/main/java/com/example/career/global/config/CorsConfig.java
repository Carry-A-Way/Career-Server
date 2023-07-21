package com.example.career.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Configure the allowed methods (e.g., GET, POST, DELETE, etc.)
        config.addAllowedMethod("*");

        // Configure the allowed origins (e.g., "*", "http://example.com")
        config.addAllowedOrigin("*");

        // Configure the allowed headers (e.g., "Authorization", "Content-Type")
        config.addAllowedHeader("*");

        // Allow credentials (cookies, authorization headers, etc.) to be exposed to the client
        config.setAllowCredentials(true);

        // Apply the CORS configuration to all paths
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}