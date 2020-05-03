package com.github.team66.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.team66.entity.LoginLog;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {

}
