package com.kondziu.projects.TastyAppBackend.interceptors.model;

import com.kondziu.projects.TastyAppBackend.interceptors.UserLog;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "activity_user")
@Entity
public class ActivityUser {
    @Id
    private String id;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<UserLog> userLogs = new ArrayList<>();
}
