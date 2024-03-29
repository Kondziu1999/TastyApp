package com.kondziu.projects.TastyAppBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Cacheable
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private long tokenId;

    @Column(name="confirmation_token")
    @JsonIgnore
    private String resetPasswordToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    @JsonIgnore //to avoid sending back user data
    private User user;

    public ResetPasswordToken(User user) {
        this.user = user;
        this.createdDate = new Date();
        this.resetPasswordToken = UUID.randomUUID().toString();
    }
}
