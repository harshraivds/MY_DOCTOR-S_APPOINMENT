package com.doctorservice.controller;

import com.doctorservice.dto.APIResponse;
import com.doctorservice.dto.DoctorDto;
import com.doctorservice.service.ProfileService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/doctor")
// http://localhost:8080/api/v1/doctor/create
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> register(
            @RequestBody DoctorDto doctorDto
    ){
        APIResponse<?> response = profileService.create(doctorDto);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }
}
