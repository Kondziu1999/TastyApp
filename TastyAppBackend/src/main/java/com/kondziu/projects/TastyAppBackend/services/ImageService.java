package com.kondziu.projects.TastyAppBackend.services;
import java.io.File;
import java.nio.file.Files;


import com.kondziu.projects.TastyAppBackend.FileManager.FileManager;
import com.kondziu.projects.TastyAppBackend.dto.ImageDto;
import com.kondziu.projects.TastyAppBackend.models.ImageModel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

import java.io.IOException;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class ImageService {
    private FileManager fileManager;

    public ImageService(FileManager fileManager){
        this.fileManager=fileManager;
    }
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
            fileManager.createDirIfNotExists(dirPath);

            Files.write(path,bytes);
            fileManager.compressImage(userId,recipeId,path.getFileName().toString(),UPLOADED_FOLDER,bytes.length);
            return true;

        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
    public ImageDto getImage(Integer userId, Integer recipeId,String filename){
        String filePath = fileManager.constructPathToImage(userId,recipeId,filename,UPLOADED_FOLDER);
        Path path = Path.of(filePath);

        ImageDto imageDto = new ImageDto();
        try{
            byte[] bytes = Files.readAllBytes(path);
            imageDto.setBytes(bytes);
            imageDto.setType( com.google.common.io.Files.getFileExtension(filename) );
            imageDto.setUniqueName(filename);
            return imageDto;
        }catch (IOException e){
            log.warn("file doesn't exists : " + filePath);
            return imageDto;
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
            log.warn("Invalid destination directory path !");
            //if dir do not exists
            return Collections.emptyList();
        }
    }

    public List<String> getImagesNames(Integer userId,Integer recipeId){
        String sourceDir=UPLOADED_FOLDER+"\\users\\"+userId+"\\recipes\\"+recipeId;
        List<String> imagesNames = new ArrayList<>();

        try(Stream<Path> paths = Files.walk(Paths.get(sourceDir))){
            paths.filter(Files::isRegularFile)
                    .forEach(path -> imagesNames.add(path.getFileName().toString()));
        } catch (IOException e) {
            log.warn("Invalid destination directory path !");
        }
        return imagesNames;
    }


    //TODO compress image, consider using Tinypng
}
