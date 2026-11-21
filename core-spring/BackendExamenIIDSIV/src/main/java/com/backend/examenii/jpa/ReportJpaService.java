/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backend.examenii.jpa;

import com.backend.examenii.domain.Report;
import com.backend.examenii.dto.ReportRequest;
import com.backend.examenii.dto.ReportResponse;
import com.backend.examenii.repository.IReportRepository;
import com.backend.examenii.service.IReportService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Elias
 */
@Service
public class ReportJpaService implements IReportService{

    @Autowired
    IReportRepository repo;
    
    
    @Override
    public List<ReportResponse> getAll() {
        List<Report> reports = repo.findAll();
        List<ReportResponse> result = new ArrayList<>();
        
        
        for (Report r: reports){
            result.add(toResponse(r));
        }
        return result;
    }

    @Override
    public ReportResponse getById(Integer id) {
        Report result = repo.findById(id).orElseThrow(() -> error(HttpStatus.NOT_FOUND, "El reporte no existe"));
        return toResponse(result);
    }
    
    @Override
    public ReportResponse create(ReportRequest req) {
        Report r = new Report();
        r.setCompanyName(req.companyName());
        r.setPictureURL(req.pictureURL());
        r.setPassengerName(req.passengerName());
        r.setType(req.type());
        r.setSubject(req.subject());
        r.setDescription(req.description());
        r.setTripDate(LocalDate.parse(req.tripDate()));
        r.setIsUrgent(req.isUrgent());
        return toResponse(repo.save(r));
    }

    @Override
    public ReportResponse update(Integer id, ReportRequest req) {
        System.out.println(req.passengerName());
        Report r = repo.findById(id).orElseThrow(() -> error(HttpStatus.NOT_FOUND, "El reporte no existe"));
        r.setCompanyName(req.companyName());
        r.setPassengerName(req.passengerName());
        r.setType(req.type());
        r.setSubject(req.subject());
        if (!r.getPictureURL().equals(req.pictureURL())){
            deletePhoto(r.getPictureURL());
            r.setPictureURL(req.pictureURL());
        }
        r.setDescription(req.description());
        r.setTripDate(LocalDate.parse(req.tripDate()));
        r.setIsUrgent(req.isUrgent());
        return toResponse(repo.save(r));
    }

    @Override
    public void delete(Integer id) {
        Report r = repo.findById(id).orElseThrow(() -> error(HttpStatus.NOT_FOUND, "El reporte no existe"));
        deletePhoto(r.getPictureURL());
        repo.deleteById(id);
        
    }
    
    private void deletePhoto(String picUrl){
       try{
            Path path = ImageService.UPLOAD_DIRECTORY.resolve(picUrl).normalize();
            if (!path.startsWith(ImageService.UPLOAD_DIRECTORY)){
                return;
            }
            Files.deleteIfExists(path);
        }catch(IOException e){
            System.err.println("Error al borrar la foto "+e.getMessage());
        }  
    }
    
    
    private ReportResponse toResponse(Report r){
        return new ReportResponse(
                r.getId(),
                r.getCompanyName(),
                r.getPassengerName(),
                r.getPictureURL(),
                r.getType(),
                r.getSubject(),
                r.getDescription(),
                r.getTripDate().toString(),
                r.getIsUrgent(),
                r.getCreatedAt().toString()
        );
     
    
}
    
    private ResponseStatusException error(HttpStatus status, String message) {
        return new ResponseStatusException(status, message);
    }

    
    
}
