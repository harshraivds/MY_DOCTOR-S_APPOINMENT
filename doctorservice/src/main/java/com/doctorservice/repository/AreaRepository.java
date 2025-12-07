package com.doctorservice.repository;

import com.doctorservice.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {

    Area findByArea(String area);
}