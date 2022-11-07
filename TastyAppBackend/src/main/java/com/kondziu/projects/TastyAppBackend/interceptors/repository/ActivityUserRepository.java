package com.kondziu.projects.TastyAppBackend.interceptors.repository;

import com.kondziu.projects.TastyAppBackend.interceptors.model.ActivityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityUserRepository extends JpaRepository<ActivityUser, String> {
}
