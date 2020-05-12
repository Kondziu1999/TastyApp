package com.kondziu.projects.TastyAppBackend.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckCredentialsResponse {

    boolean usernameAvailable;
    boolean emailAvailable;
}
