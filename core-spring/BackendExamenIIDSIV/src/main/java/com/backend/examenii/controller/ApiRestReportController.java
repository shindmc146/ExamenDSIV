/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.backend.examenii.controller;

import com.backend.examenii.domain.Report;
import com.backend.examenii.dto.ReportRequest;
import com.backend.examenii.dto.ReportResponse;
import com.backend.examenii.service.IReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Elias
 */
@RestController
@RequestMapping("/reports")
public class ApiRestReportController {
    @Autowired
    private IReportService service;
    
    @GetMapping
    public List<ReportResponse> list(){
        return service.getAll();
    }
    
    @GetMapping("/{id}")
    public ReportResponse find(@PathVariable Integer id){
        return service.getById(id);
    }
    
    @PostMapping
    public ResponseEntity<ReportResponse> add(@RequestBody ReportRequest req){
        
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    
    }
    
    @PutMapping("/{id}")
    public ReportResponse update(@PathVariable Integer id, @RequestBody ReportRequest req){
        
        return service.update(id,req);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        
        return ResponseEntity.noContent().build();
        
    }
}
