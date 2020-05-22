package com.kondziu.projects.TastyAppBackend.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private String uniqueName;
    private String type;
    private byte[] bytes;
}
