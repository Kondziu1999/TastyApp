package com.kondziu.projects.TastyAppBackend.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private int expiration;;

    public JwtAuthenticationResponse(String accessToken,int expiration) {
        this.accessToken = accessToken;
        this.expiration=expiration;
    }

}
