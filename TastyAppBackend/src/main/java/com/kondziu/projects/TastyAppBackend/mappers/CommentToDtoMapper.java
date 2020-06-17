package com.kondziu.projects.TastyAppBackend.mappers;

import com.kondziu.projects.TastyAppBackend.dto.CommentDto;
import com.kondziu.projects.TastyAppBackend.models.Comment;
import com.kondziu.projects.TastyAppBackend.models.User;
import com.kondziu.projects.TastyAppBackend.repos.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class CommentToDtoMapper {

    private final UserRepository userRepository;

    public CommentToDtoMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CommentDto commentToDto(Comment comment){
        //user must be specified since all comments are deleted cascade
        User user = comment.getUser();
        return CommentDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .comment(comment.getComment())
                .date(comment.getDate())
                .build();
    }
}
