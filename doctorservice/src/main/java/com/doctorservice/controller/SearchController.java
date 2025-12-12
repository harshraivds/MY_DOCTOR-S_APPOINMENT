package com.doctorservice.controller;

import com.doctorservice.dto.SearchResultDto;
import com.doctorservice.entity.Doctor;
import com.doctorservice.entity.DoctorAppointmentSchedule;
import com.doctorservice.entity.TimeSlots;
import com.doctorservice.repository.DoctorRepository;
import com.doctorservice.repository.TimeSlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
public class SearchController {

    private final DoctorRepository doctorRepository;

    private final TimeSlotsRepository timeSlotsRepository;

    public SearchController(DoctorRepository doctorRepository, TimeSlotsRepository timeSlotsRepository) {
        this.doctorRepository = doctorRepository;
        this.timeSlotsRepository = timeSlotsRepository;
    }

    // Example:
    //  http://localhost:8080/api/v1/doctor/search?specialization=general&areaName=Agyaram%20Colony

    @GetMapping("/search")
    public List<Doctor> searchDoctors(
            @RequestParam String specialization,
            @RequestParam String areaName
    ) {

        List<Doctor> doctors = doctorRepository.findBySpecializationAndArea(specialization, areaName);
        LocalDate today = LocalDate.now();
        for(Doctor doctor: doctors){
            List<LocalDate> validDates = new ArrayList<>();
            List<LocalTime> allTimeSlots = new ArrayList<>();
            List<DoctorAppointmentSchedule> schedules = doctor.getAppointmentSchedules();

            for(DoctorAppointmentSchedule schedule:schedules){
                LocalDate scheduleDate = schedule.getDate();
                LocalTime now = LocalTime.now();
                List<TimeSlots> timeSlots = timeSlotsRepository.getAllTimeSlots(schedule.getId());

                for (TimeSlots ts : timeSlots) {
                    LocalTime slotTime = ts.getTime();

                    // If schedule is today → only future times
                    if (scheduleDate.isEqual(today)) {
                        if (slotTime.isAfter(now)) {
                            allTimeSlots.add(slotTime);
                        }
                    }
                    // If schedule is in the future → add all times
                    else if (scheduleDate.isAfter(today)) {
                        allTimeSlots.add(slotTime);
                    }
                }
            }
        }
        return doctors;
    }
