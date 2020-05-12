package com.kondziu.projects.TastyAppBackend.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckCredentialsPayload {
    private String username;
    private String email;
}
