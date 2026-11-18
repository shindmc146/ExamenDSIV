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
        r.setPictureURL(req.pictureURL());
        r.setDescription(req.description());
        r.setTripDate(LocalDate.parse(req.tripDate()));
        r.setIsUrgent(req.isUrgent());
        return toResponse(repo.save(r));
    }

    @Override
    public void delete(Integer id) {
        repo.findById(id).orElseThrow(() -> error(HttpStatus.NOT_FOUND, "El reporte no existe"));
        repo.deleteById(id);
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
                r.getTripDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                r.getIsUrgent(),
                r.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString()
        );
     
    
}
    
    private ResponseStatusException error(HttpStatus status, String message) {
        return new ResponseStatusException(status, message);
    }

    
    
}
