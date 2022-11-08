package com.kondziu.projects.TastyAppBackend.interceptors;

import com.kondziu.projects.TastyAppBackend.interceptors.model.ActivityUserDetails;

/**
 * Should be implemented by library user.
 * This is where plugin is looking for user details
 */
public interface ActivityUserDetailsProvider {
    ActivityUserDetails getAppActivityUserDetails(String userIdString);
}
