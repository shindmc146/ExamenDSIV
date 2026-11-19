/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backend.examenii.jpa;

import com.backend.examenii.service.IImageService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Elias
 */
@Service
public class ImageService implements IImageService{
    public static final Path UPLOAD_DIRECTORY = Paths.get("uploads","reports");

    @Override
    public String save(MultipartFile file) throws IOException {
        Files.createDirectories(UPLOAD_DIRECTORY);
        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")){
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID()+extension;
        Path filePath = UPLOAD_DIRECTORY.resolve(fileName);
        System.out.println("Antes de copy "+Files.exists(filePath));
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
        
    }
    
}
