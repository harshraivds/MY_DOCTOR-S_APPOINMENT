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
}
