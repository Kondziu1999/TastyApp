package com.kondziu.projects.TastyAppBackend.interceptors.transport;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLogsForUserQuery {
    private String userId;
    private int page;
    private int pageSize;
    private String sessionId;
}
