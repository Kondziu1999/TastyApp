package com.kondziu.projects.TastyAppBackend.interceptors.transport;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndpointsQuery {
    private int page;
    private int pageSize;
    private EndpointSortingProperty sortingProperty;
    private SortingDirection sortingDirection;
    private String endpointName;
    private String activityUserId;
}
