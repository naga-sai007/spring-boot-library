package com.virtusa.springbootlibrary.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //protect endpoints at /api/<type>/secure
        http.authorizeHttpRequests(requests->
                        requests
                                .requestMatchers("/api/books/secure/**",
                                        "/api/reviews/secure/**",
                                        "/api/messages/secure/**",
                                        "/api/admin/secure/**")
                                .authenticated()
                                .anyRequest().permitAll())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()));


        //Add CORS FILTERS
        http.cors(Customizer.withDefaults());

        //Add Content negotiation Strategy
        http.setSharedObject(ContentNegotiationStrategy.class,
                new HeaderContentNegotiationStrategy());

        //Force a non-empty response body for '401's to make the response friendly
        Okta.configureResourceServer401ResponseBody(http);

        // we are not using Cookies for session tracking >> disable CSRF
        http.csrf(csrf->csrf.disable());

        return http.build();
    }
}
