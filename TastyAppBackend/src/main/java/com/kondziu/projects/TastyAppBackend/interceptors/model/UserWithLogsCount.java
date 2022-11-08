package com.kondziu.projects.TastyAppBackend.interceptors.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithLogsCount {
    String activityUserId;
    Long logsCount;
}
