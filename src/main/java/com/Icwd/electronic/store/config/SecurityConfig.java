package com.Icwd.electronic.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {


    @Bean
    public UserDetailsService userDetailsService(){

        //User create

        UserDetails normal = User.builder()
                .username("Amit")
                .password(passwordEncoder().encode("patel"))
                .roles("NORMAL")
                .build();


        UserDetails admin = User.builder()
                .username("Abhishek")
                .password(passwordEncoder().encode("patel"))
                .roles("ADMIN")
                .build();


        //InMemoryUserDetailsManager
        return new InMemoryUserDetailsManager(normal, admin);


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }



}
