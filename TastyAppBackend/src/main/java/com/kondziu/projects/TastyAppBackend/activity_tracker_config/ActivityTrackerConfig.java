package com.kondziu.projects.TastyAppBackend.activity_tracker_config;

import com.kondziu.projects.TastyAppBackend.interceptors.session_id_extractor.ArtificialRequestAttributeSessionIdExtractor;
import com.kondziu.projects.TastyAppBackend.interceptors.session_id_extractor.SessionIdExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivityTrackerConfig {

    @Value("${activity_tracker_config.artificial_session_id}")
    private String sessionIdHeader;

    @Bean
    public SessionIdExtractor sessionIdExtractor() {
        return new ArtificialRequestAttributeSessionIdExtractor(sessionIdHeader);
    }
}
