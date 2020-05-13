package com.kondziu.projects.TastyAppBackend.repos;

import com.kondziu.projects.TastyAppBackend.models.Role;
import com.kondziu.projects.TastyAppBackend.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}