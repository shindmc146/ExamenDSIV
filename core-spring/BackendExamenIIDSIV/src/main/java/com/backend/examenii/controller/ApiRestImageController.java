/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.backend.examenii.controller;

import com.backend.examenii.domain.ImageResponse;
import com.backend.examenii.service.IImageService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Elias
 */
@RestController
@RequestMapping("/images")
public class ApiRestImageController {
    @Autowired
    private IImageService service;
    @PostMapping
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("file") MultipartFile photo){
        String fileName = "";
        try {
            if (photo != null && !photo.isEmpty()){
                fileName = service.save(photo);
                
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la imagen");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body( new ImageResponse(fileName));
    }
}
