package com.kondziu.projects.TastyAppBackend.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private String comment;
    private Date date;
    //username is optional
    private Long userId;
    private String username;
}
