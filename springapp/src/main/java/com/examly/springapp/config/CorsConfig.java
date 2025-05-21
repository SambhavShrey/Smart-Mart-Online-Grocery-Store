package com.examly.springapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// configure CORS(corss-origin resource sharing) for sharing resources among different origin
//Allow cors for all endpoints  and permits request form any origin
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                 .allowedOrigins("*")
                 .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                 .allowedHeaders("*")
                 .allowCredentials(false);
    }
    

}

