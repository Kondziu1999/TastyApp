package com.kondziu.projects.TastyAppBackend.interceptors.repository;

import com.kondziu.projects.TastyAppBackend.interceptors.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Integer> {
}
