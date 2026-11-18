/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backend.examenii.repository;

import com.backend.examenii.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Elias
 */
public interface IReportRepository extends JpaRepository<Report, Integer>{
    
}
