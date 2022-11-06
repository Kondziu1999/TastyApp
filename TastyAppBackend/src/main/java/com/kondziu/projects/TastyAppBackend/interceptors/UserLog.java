package com.kondziu.projects.TastyAppBackend.interceptors;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_logs")
@Getter
@Setter
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="user_session_id")
    private String userSessionId;

    @Column(name="acitivity_start")
    private Long activityStart;

    @Column(name="acitivity_end")
    private Long activityEnd;

    @Column(name = "endpoint")
    private String endpoint;
}
