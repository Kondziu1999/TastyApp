package com.kondziu.projects.TastyAppBackend.interceptors.transport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointNameWithCount {
    String name;
    Long logsCount;
}
