package com.doctorservice.service;

import com.doctorservice.dto.APIResponse;
import com.doctorservice.dto.DoctorDto;
import com.doctorservice.entity.Area;
import com.doctorservice.entity.City;
import com.doctorservice.entity.Doctor;
import com.doctorservice.entity.State;
import com.doctorservice.exception.CustomException;
import com.doctorservice.repository.AreaRepository;
import com.doctorservice.repository.CityRepository;
import com.doctorservice.repository.DoctorRepository;
import com.doctorservice.repository.StateRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final DoctorRepository doctorRepository;

    private final StateRepository stateRepository;

    private final CityRepository cityRepository;

    private final AreaRepository areaRepository;

    public ProfileService(DoctorRepository doctorRepository, StateRepository stateRepository, CityRepository cityRepository, AreaRepository areaRepository) {
        this.doctorRepository = doctorRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.areaRepository = areaRepository;
    }

    public APIResponse<?> create(DoctorDto doctorDto) {

        APIResponse<Object> response = new APIResponse<>();

        Doctor existingDoctor = doctorRepository.findByContract(doctorDto.getContract());

        if (existingDoctor != null) {
            throw new CustomException("Contract already exists!");
        }

        State state = stateRepository.findByState(doctorDto.getState());
        if(state == null){
            state = new State();
            state.setState(doctorDto.getState());
            state = stateRepository.save(state);
        }

        City city = cityRepository.findByCity(doctorDto.getCity());
        if(city == null){
            city = new City();
            city.setCity(doctorDto.getCity());
            city = cityRepository.save(city);
        }

        Area area = areaRepository.findByArea(doctorDto.getArea());
        if(area == null){
            area = new Area();
            area.setArea(doctorDto.getArea());
            area = areaRepository.save(area);
        }

        Doctor doctor = new Doctor();
        doctor.setName(doctorDto.getName());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setQualification(doctorDto.getQualification());
        doctor.setContract(doctorDto.getContract());
        doctor.setExperience(doctorDto.getExperience());
        doctor.setUrl(doctorDto.getUrl());
        doctor.setAddress(doctorDto.getAddress());

        doctor.setState(state);
        doctor.setCity(city);
        doctor.setArea(area);

        doctorRepository.save(doctor);

        response.setMessage("Doctor created successfully");
        response.setStatus(200);
        response.setData(doctor);
        return response;
    }

    public APIResponse<?> updateDoctor(DoctorDto doctorDto) {

        APIResponse<Object> response = new APIResponse<>();

        // Fetch doctor by old contract
        Doctor doctor = doctorRepository.findByContract(doctorDto.getContract());
        if (doctor == null) {
            throw new CustomException("Doctor not found with contract: " + doctorDto.getContract());
        }

        // ----------------------------------------
        // Update CONTRACT (YES, allowed now)
        // ----------------------------------------
        if (doctorDto.getContract() != null && !doctorDto.getContract().isEmpty()) {

            // Check if new contract already exists with another doctor
            Doctor exist = doctorRepository.findByContract(doctorDto.getContract());
            if (exist != null && !exist.getId().equals(doctor.getId())) {
                throw new CustomException("Contract already in use by another doctor!");
            }

            doctor.setContract(doctorDto.getContract());
        }

        // ----------------------------------------
        // Update NAME
        // ----------------------------------------
        if (doctorDto.getName() != null && !doctorDto.getName().isEmpty()) {
            doctor.setName(doctorDto.getName());
        }

        // Update SPECIALIZATION
        if (doctorDto.getSpecialization() != null && !doctorDto.getSpecialization().isEmpty()) {
            doctor.setSpecialization(doctorDto.getSpecialization());
        }

        // Update QUALIFICATION
        if (doctorDto.getQualification() != null && !doctorDto.getQualification().isEmpty()) {
            doctor.setQualification(doctorDto.getQualification());
        }

        // Update EXPERIENCE
        if (doctorDto.getExperience() != null) {
            doctor.setExperience(doctorDto.getExperience());
        }

        // Update URL
        if (doctorDto.getUrl() != null && !doctorDto.getUrl().isEmpty()) {
            doctor.setUrl(doctorDto.getUrl());
        }

        // Update ADDRESS
        if (doctorDto.getAddress() != null && !doctorDto.getAddress().isEmpty()) {
            doctor.setAddress(doctorDto.getAddress());
        }

        // Update STATE
        if (doctorDto.getState() != null && !doctorDto.getState().isEmpty()) {
            State state = stateRepository.findByState(doctorDto.getState());
            if (state == null) {
                state = new State();
                state.setState(doctorDto.getState());
                state = stateRepository.save(state);
            }
            doctor.setState(state);
        }

        // Update CITY
        if (doctorDto.getCity() != null && !doctorDto.getCity().isEmpty()) {
            City city = cityRepository.findByCity(doctorDto.getCity());
            if (city == null) {
                city = new City();
                city.setCity(doctorDto.getCity());
                city = cityRepository.save(city);
            }
            doctor.setCity(city);
        }

        // Update AREA
        if (doctorDto.getArea() != null && !doctorDto.getArea().isEmpty()) {
            Area area = areaRepository.findByArea(doctorDto.getArea());
            if (area == null) {
                area = new Area();
                area.setArea(doctorDto.getArea());
                area = areaRepository.save(area);
            }
            doctor.setArea(area);
        }

        doctorRepository.save(doctor);

        response.setMessage("Doctor updated successfully");
        response.setStatus(200);
        response.setData(doctor);

        return response;
    }



}
