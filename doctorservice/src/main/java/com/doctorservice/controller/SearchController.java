package com.doctorservice.controller;

import com.doctorservice.dto.SearchResultDto;
import com.doctorservice.entity.Doctor;
import com.doctorservice.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
public class SearchController {

    @Autowired
    private DoctorRepository doctorRepository;

    // Example:
    //http://localhost:8080/api/v1/doctor/search?specialization=general&areaName=Agyaram%20Colony

    @GetMapping("/search")
    public List<Doctor> searchDoctors(
            @RequestParam String specialization,
            @RequestParam String areaName
    ) {

        List<Doctor> doctors = doctorRepository.findBySpecializationAndArea(specialization, areaName);

        return doctors;
    }
    }
