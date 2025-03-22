package com.sabah.microservice.api_gateway.config;

// import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

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
                             .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                             .build();
                
        
    }

    
    @Bean
    public JwtDecoder jwtDecoder() {
        // Replace "your-issuer-uri" with the issuer URI of your authorization server
        return JwtDecoders.fromIssuerLocation("http://localhost:8181/realms/spring-microservice-security-realm");
    }

    // @Bean
    // public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
    //     return JwtDecoders.fromIssuerLocation(properties.getJwt().getIssuerUri());
    // }

}
