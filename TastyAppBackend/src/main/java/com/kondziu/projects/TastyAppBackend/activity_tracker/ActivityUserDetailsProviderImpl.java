package com.kondziu.projects.TastyAppBackend.activity_tracker;

import com.agh.activitytrackerclient.models.ActivityUserDetails;
import com.agh.activitytrackerclient.utils.ActivityUserDetailsProvider;
import com.kondziu.projects.TastyAppBackend.repos.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ActivityUserDetailsProviderImpl implements ActivityUserDetailsProvider {
    private final UserRepository userRepository;

    public ActivityUserDetailsProviderImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ActivityUserDetails getAppActivityUserDetails(String userId) {
        return userRepository
                .findById(Long.parseLong(userId))
                .map(user -> ActivityUserDetails.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .build()
                ).orElseThrow(() -> new RuntimeException("User with id: " + userId + "not found"));
    }
}
