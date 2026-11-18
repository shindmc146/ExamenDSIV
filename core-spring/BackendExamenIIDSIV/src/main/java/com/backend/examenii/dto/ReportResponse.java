/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.backend.examenii.dto;

import java.time.LocalDateTime;

/**
 *
 * @author Elias
 */
public record ReportResponse(
        
        Integer id,
        String companyName,
        String passengerName,
        String pictureURL,
        String type,
        String subject,
        String description,
        String tripDate,
        Boolean isUrgent,
        String createdAt
        
        
        ) {

}