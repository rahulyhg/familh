package org.demis.familh.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration //Marks this class as configuration
//Specifies which package to scan
@ComponentScan("org.demis.familh")
//Enables Spring's annotations
@EnableWebMvc
public class Config {


    @Bean
    public MappingJackson2JsonView mappingJackson2JsonView() {
        MappingJackson2JsonView mapping = new MappingJackson2JsonView();
        mapping.setContentType("application/json");
        return mapping;
    }
}
