package com.github.team66.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.team66.entity.LoginLog;
import com.github.team66.repository.LoginLogRepository;

@Service
public class LoginLogService {

	@Autowired
	private LoginLogRepository loginLogRepository;
	
	public void inserir(String usuario, String ip) {
		LoginLog loginLog = new LoginLog(usuario, ip);
		loginLogRepository.save(loginLog);
	}
	
}
