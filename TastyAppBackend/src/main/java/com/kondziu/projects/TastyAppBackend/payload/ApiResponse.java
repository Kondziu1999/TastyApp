package com.kondziu.projects.TastyAppBackend.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {

    private Boolean success;
    private String message;
}
