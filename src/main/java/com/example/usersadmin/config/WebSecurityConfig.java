package com.example.usersadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();
		http.csrf().disable().authorizeRequests()
				.antMatchers("/", "/users/**", "/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
						"/configuration/security", "/swagger-ui.html", "/swagger-ui/**", "/webjars/**",
						"/h2-console/**")
				.permitAll().anyRequest().authenticated().and().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return http.build();
	}

}