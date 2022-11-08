package com.kondziu.projects.TastyAppBackend.interceptors.repository;

import com.kondziu.projects.TastyAppBackend.interceptors.model.ActivityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityUserRepository extends JpaRepository<ActivityUser, String> {
//    List<ActivityUser> findAllByIdIn(String[] ids);

//    @Query("select p from ActivityUser p join fetch p.userLogs where p.id in :ids")
//    List<ActivityUser> findAllByIdIn(@Param("ids") String[] ids);
}
