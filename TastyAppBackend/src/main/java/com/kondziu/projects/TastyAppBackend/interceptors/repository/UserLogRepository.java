package com.kondziu.projects.TastyAppBackend.interceptors.repository;

import com.kondziu.projects.TastyAppBackend.interceptors.UserLog;
import com.kondziu.projects.TastyAppBackend.interceptors.model.UserWithLogsCount;
import com.kondziu.projects.TastyAppBackend.interceptors.transport.EndpointNameWithCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Integer>, CustomUserLogRepository {

    @Query("SELECT new com.kondziu.projects.TastyAppBackend.interceptors.model.UserWithLogsCount(log.activityUserId, count (log.activityUserId)) from UserLog as log where log.activityUserId is not null GROUP BY log.activityUserId ORDER BY count (log.activityUserId) ")
    List<UserWithLogsCount> getUserIdsWithMostActivity(Pageable pageable);

    List<UserLog> findAllByActivityUserId(String userId, Pageable pageable);

    List<UserLog> findAllByActivityUserIdAndUserSessionId(String userId, String userSessionId, Pageable pageable);

//    @Query("select new com.kondziu.projects.TastyAppBackend.interceptors.transport.EndpointNameWithCount(log.endpoint, count(log.endpoint)) from UserLog as log where log.endpoint like :ep group by log.endpoint ORDER BY count(log.endpoint) DESC")
//    List<EndpointNameWithCount> findMostPopularEndpointNames(@Param("ep") String ep, Pageable pageable);
//
//    @Query("select new com.kondziu.projects.TastyAppBackend.interceptors.transport.EndpointNameWithCount(log.endpoint, count(log.endpoint)) from UserLog as log where log.endpoint like :ep and log.activityUserId = :uid group by log.endpoint ORDER BY count(log.endpoint) DESC")
//    List<EndpointNameWithCount> findMostPopularEndpointNamesForUser(@Param("ep") String ep, @Param("uid") String uid, Pageable pageable);

}
