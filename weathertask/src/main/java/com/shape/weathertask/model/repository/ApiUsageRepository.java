package com.shape.weathertask.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ApiUsageRepository extends JpaRepository<ApiUsage, Long> {
    ApiUsage findByApiNameAndDate(String apiName, LocalDate date);
}