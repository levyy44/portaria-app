package com.empresa.portaria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /** CORS aberto - APENAS para facilitar testes do protótipo. */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOrigins("*").allowedMethods("*");
    }

    /** URLs amigáveis: /admin e /portaria apontam para os HTMLs estáticos. */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin").setViewName("forward:/admin.html");
        registry.addViewController("/portaria").setViewName("forward:/portaria.html");
        registry.addViewController("/").setViewName("redirect:/portaria");
    }
}
