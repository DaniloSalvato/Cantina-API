package com.pb.ProjetoGrupo2.config.swagger;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080/swagger-ui.html#/")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
