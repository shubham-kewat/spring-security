package com.kewat.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http.authorizeHttpRequests(
                auth->auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


//WHENEVER YOU WILL ENABLE THIS THEN U HAVE TO COMMENT THE CUSTOMUSERDETAILS SERVICE
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user = User.withUsername("shubham")
//                .password("{noop}shubham")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
}
