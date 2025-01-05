package com.polarbookshop.catalog_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize  -> authorize
				.requestMatchers("/actuator/**").permitAll()
				.requestMatchers(HttpMethod.GET,"/","/books/**").permitAll()
				.anyRequest().hasRole("employee"))
		    .oauth2ResourceServer(oauth2 -> oauth2
		    	.jwt(Customizer.withDefaults()))
		    .sessionManagement(session -> session
		    	.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		    .csrf(csrf -> csrf
		    	.disable());
		return http.build();
	}
	
	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		var jwtGrantedAuthoritiesConverter  = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLES_");
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

}