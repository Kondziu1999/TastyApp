package com.kondziu.projects.TastyAppBackend.services;

import com.kondziu.projects.TastyAppBackend.FileManager.FileManager;
import com.kondziu.projects.TastyAppBackend.dto.ImageDto;
import com.kondziu.projects.TastyAppBackend.models.ImageModel;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ImageService {

    private  final String UPLOADED_FOLDER="C:\\Users\\priva\\Desktop\\TastyApp\\uploads";

    public boolean uploadImage(ImageModel imageModel, Integer userId, Integer recipeId){

        final String dirPath=UPLOADED_FOLDER+"\\users\\"+userId+"\\recipes\\"+recipeId;
        final String filePath = dirPath + "\\"+ imageModel.getUniqueName()+"."+imageModel.getType();
        BufferedImage image=null;
        try {
            byte [] bytes = imageModel.getFile().getBytes();
            Path path = Paths.get(dirPath + "\\" + imageModel.getUniqueName());
            String extension="."+imageModel.getType();

            path= path.resolveSibling(path.getFileName() + extension);

            System.out.println(path.toAbsolutePath());
            FileManager.createDirIfNotExists(dirPath);

            Files.write(path,bytes);

            return true;
//            File initialImage = new File(filePath);
//            image= ImageIO.read(initialImage);
//            ImageIO.read
//
//            ImageIO.write(image,imageModel.getType(),new File(filePath));

        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public List<ImageDto> getImages(Integer userId, Integer recipeId){
        String sourceDir=UPLOADED_FOLDER+"\\users\\"+userId+"\\recipes\\"+recipeId;
        List<ImageDto>  imageModels = new ArrayList<>();

        try(Stream<Path> paths = Files.walk(Paths.get(sourceDir))){
            paths
                .filter(Files::isRegularFile)
                .forEach( path ->{
                try {
                    imageModels.add( new ImageDto( path.getFileName().toString(), "jpg", Files.readAllBytes(path)) );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            return imageModels;

        } catch (IOException e) {
            e.printStackTrace();
            //if dir do not exists
            return Collections.emptyList();
        }
    }


    //TODO compress image, consider using Tinypng
}
