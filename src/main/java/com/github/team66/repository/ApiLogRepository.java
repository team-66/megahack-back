package com.github.team66.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.team66.entity.ApiLog;

public interface ApiLogRepository extends JpaRepository<ApiLog, Long> {

	@Query(value = "select count(*) " + 
			"from api_log " + 
			"where date(data_hora) = :data", nativeQuery = true)
	Long requisicoesPorData(LocalDate data);
	
}
