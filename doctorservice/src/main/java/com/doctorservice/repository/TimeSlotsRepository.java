package com.doctorservice.repository;

import com.doctorservice.entity.TimeSlots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeSlotsRepository extends JpaRepository<TimeSlots, Long> {

    @Query(value = "Select * from time_slots where appointment_schedule_id = :appointmentScheduleId", nativeQuery = true)
    List<TimeSlots> getAllTimeSlots(@Param("appointmentScheduleId") long appointmentScheduleId);
}
