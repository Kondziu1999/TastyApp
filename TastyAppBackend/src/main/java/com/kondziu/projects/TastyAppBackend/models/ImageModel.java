package com.kondziu.projects.TastyAppBackend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MultipartFile file;

}
