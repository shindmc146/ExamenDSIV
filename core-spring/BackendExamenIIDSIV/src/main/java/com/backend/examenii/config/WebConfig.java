/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backend.examenii.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Elias
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
    
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        Path uploadDirectory = Paths.get("uploads")
                .toAbsolutePath()
                .normalize();
        String uploadPath = uploadDirectory.toUri().toString();
        registry.addResourceHandler("/uploads/**").addResourceLocations(uploadPath);
    }
}
