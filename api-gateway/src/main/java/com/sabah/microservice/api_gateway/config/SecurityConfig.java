package com.sabah.microservice.api_gateway.config;

import java.util.List;

// import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.JwtDecoders;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {


    private final String[] freeResourceURLS = { "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", 
    "/swagger-resources/**", "/api-docs/**","/aggregate/**" };

    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpsSecurity) throws Exception {
        return httpsSecurity.authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(freeResourceURLS)
                            .permitAll()
                            .anyRequest()
                            .authenticated())
                            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                             .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                             .build();
                
        
    }



    // this was added because the thing given in the application.properties was wrong
    // after correcting that this is not needed anymore
    // @Bean
    // public JwtDecoder jwtDecoder() {
    //     // Replace "your-issuer-uri" with the issuer URI of your authorization server
    //     return JwtDecoders.fromIssuerLocation("http://localhost:8181/realms/spring-microservice-security-realm");
    // }

    // @Bean
    // public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
    //     return JwtDecoders.fromIssuerLocation(properties.getJwt().getIssuerUri());
    // }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
