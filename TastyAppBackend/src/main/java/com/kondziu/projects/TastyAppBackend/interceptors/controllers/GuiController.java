package com.kondziu.projects.TastyAppBackend.interceptors.controllers;

import com.kondziu.projects.TastyAppBackend.interceptors.UserLog;
import com.kondziu.projects.TastyAppBackend.interceptors.model.ActivityUserWithOverviewStatistics;
import com.kondziu.projects.TastyAppBackend.interceptors.model.UserWithLogsCount;
import com.kondziu.projects.TastyAppBackend.interceptors.repository.ActivityUserRepository;
import com.kondziu.projects.TastyAppBackend.interceptors.repository.UserLogRepository;
import com.kondziu.projects.TastyAppBackend.interceptors.transport.EndpointNameWithCount;
import com.kondziu.projects.TastyAppBackend.interceptors.transport.EndpointsQuery;
import com.kondziu.projects.TastyAppBackend.interceptors.transport.GetLogsForUserQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/monitoring")
public class GuiController {

    private final ActivityUserRepository activityUserRepository;
    private final UserLogRepository userLogRepository;

    private final EntityManager entityManager;
    public GuiController(ActivityUserRepository activityUserRepository, UserLogRepository userLogRepository, EntityManager entityManager) {

        this.activityUserRepository = activityUserRepository;
        this.userLogRepository = userLogRepository;
        this.entityManager = entityManager;
    }

    @GetMapping("/getUsersWithOverview")
    public ResponseEntity<List<ActivityUserWithOverviewStatistics>> getActivityUsers() {
        Pageable pageable = PageRequest.of(0,10);

        var usersWithLogsCount = userLogRepository.getUserIdsWithMostActivity(pageable);
        var userIds = usersWithLogsCount.stream().map(UserWithLogsCount::getActivityUserId).collect(Collectors.toList());
        var users = activityUserRepository.findAllById(userIds);

        var usersWithActivity = users.stream().map(user -> {
            var data = new ActivityUserWithOverviewStatistics();
            data.setUser(user);
            data.setActivitiesCount(usersWithLogsCount.stream()
                    .filter(x -> Objects.equals(x.getActivityUserId(), user.getId()))
                    .findFirst()
                    .get()
                    .getLogsCount()
            );
            return data;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(usersWithActivity);
    }

    @PostMapping("/getLogsForUser")
    public ResponseEntity<List<UserLog>> getActivityUsers(@RequestBody() GetLogsForUserQuery query) {
        if(query.getSessionId() == null) {
            var logs = userLogRepository.findAllByActivityUserId(query.getUserId(), PageRequest.of(query.getPage(), query.getPageSize()));
            return ResponseEntity.ok(logs);
        } else {
            var logs = userLogRepository.findAllByActivityUserIdAndUserSessionId(query.getUserId(), query.getSessionId(), PageRequest.of(query.getPage(), query.getPageSize()));
            return ResponseEntity.ok(logs);
        }
    }

    @PostMapping("/getEndpointsWithFilter")
    public ResponseEntity<List<EndpointNameWithCount>> getEndpointsWithFilter(@RequestBody()EndpointsQuery query) {
        return ResponseEntity.ok(userLogRepository.findMostPopularEndpointNames(query));
    }

}
