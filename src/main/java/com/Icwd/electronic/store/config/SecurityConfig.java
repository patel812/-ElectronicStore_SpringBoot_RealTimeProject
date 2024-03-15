package com.Icwd.electronic.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;


    //Old
//    @Bean
 //   public UserDetailsService userDetailsService() {
        //User create

//        UserDetails normal = User.builder()
//                .username("Amit")
//                .password(passwordEncoder().encode("patel"))
//                .roles("NORMAL")
//                .build();
//
//
//        UserDetails admin = User.builder()
//                .username("Abhishek")
//                .password(passwordEncoder().encode("patel"))
//                .roles("ADMIN")
//                .build();
//
//
//        //InMemoryUserDetailsManager
//        return new InMemoryUserDetailsManager(normal, admin);
//
// }
            public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


               http.
                       csrf()
                       .disable()
                       .cors()
                       .disable()
                       .authorizeHttpRequests()
                       .anyRequest()
                       .authenticated()
                       .and()
                       .httpBasic();


                return http.build();
            }



        @Bean
        public DaoAuthenticationProvider authenticationProvider(){

            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

            daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            return daoAuthenticationProvider;
        }




//
   @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
  }


}
