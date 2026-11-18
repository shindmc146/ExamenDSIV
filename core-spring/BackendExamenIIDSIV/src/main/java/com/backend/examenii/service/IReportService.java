/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backend.examenii.service;

import com.backend.examenii.domain.Report;
import com.backend.examenii.dto.ReportRequest;
import com.backend.examenii.dto.ReportResponse;
import java.util.List;

/**
 *
 * @author Elias
 */
public interface IReportService {
    List<ReportResponse> getAll();
    ReportResponse create(ReportRequest req);
    ReportResponse update(Integer id, ReportRequest req);
    ReportResponse getById(Integer id);
    void delete(Integer id);
}
