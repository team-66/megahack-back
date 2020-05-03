package com.github.team66.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.github.team66.entity.ApiLog;
import com.github.team66.repository.ApiLogRepository;

@Service
public class ApiLogService {

	@Autowired
	private ApiLogRepository apiLogRepository;
	
	public void inserir(String item) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String usuario = authentication.getName();
		
		ApiLog apiLog = new ApiLog(usuario, item);
		apiLogRepository.save(apiLog);
	}
	
	public boolean podeAcessar() {
		Long requisicoesPorData = apiLogRepository.requisicoesPorData(LocalDate.now());		
		return (requisicoesPorData < 500);
	}
	
}
