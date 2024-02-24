package com.Icwd.electronic.store.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    //Making modelMapper object & use in serviceImpl as a object
    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }



}
