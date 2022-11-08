package com.kondziu.projects.TastyAppBackend.interceptors.repository;

import com.kondziu.projects.TastyAppBackend.interceptors.transport.EndpointNameWithCount;
import com.kondziu.projects.TastyAppBackend.interceptors.transport.EndpointsQuery;

import java.util.List;

public interface CustomUserLogRepository {

    List<EndpointNameWithCount> findMostPopularEndpointNames(EndpointsQuery query);
}
