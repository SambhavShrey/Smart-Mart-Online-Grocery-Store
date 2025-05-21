
package com.examly.springapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, UserDetailsService userDetailsService, PasswordEncoder encoder, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationEntryPoint=jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder authority)throws Exception{
        authority.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(encoder)
        .and()
        .build();
    }

    @Value("${api.product.path}")
    private String productIdPath;

    @Value("${api.order.path}")
    private String orderIdPath;

    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/register", "/api/login", "/api/registers", "/api/logins").permitAll()
                .requestMatchers(HttpMethod.GET, productIdPath, "/api/products/category/{category}", "/api/products").hasAnyRole(ADMIN, USER)
                .requestMatchers(HttpMethod.GET, "/api/user", "/api/feedback", "/api/orders").hasRole(ADMIN)
                .requestMatchers(HttpMethod.PUT, orderIdPath, "/api/products/edit-product/{productId}",productIdPath).hasRole(ADMIN)
                .requestMatchers(HttpMethod.POST, "/api/products", "/api/products/add-product").hasRole(ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/api/user/{userId}", productIdPath).hasRole(ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/orders/user/{userId}", orderIdPath, "/api/feedback/user/{userId}", "/api/products/user/{userId}").hasRole(USER)
                .requestMatchers(HttpMethod.POST, "/api/feedback", "/api/orders").hasRole(USER)
                .requestMatchers(HttpMethod.DELETE, "/api/feedback/{id}", orderIdPath).hasRole(USER)
                .anyRequest().permitAll())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}





