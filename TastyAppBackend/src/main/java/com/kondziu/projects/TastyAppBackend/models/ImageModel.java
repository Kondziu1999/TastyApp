package com.kondziu.projects.TastyAppBackend.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImageModel {

    private String uniqueName;
    private String type;
    private MultipartFile file;

}
