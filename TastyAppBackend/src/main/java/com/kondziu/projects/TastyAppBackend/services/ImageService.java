package com.kondziu.projects.TastyAppBackend.services;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;


import com.kondziu.projects.TastyAppBackend.FileManager.FileManager;
import com.kondziu.projects.TastyAppBackend.dto.ImageDto;
import com.kondziu.projects.TastyAppBackend.models.ImageModel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
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
    private final String UPLOADED_FOLDER="C:\\Users\\priva\\Desktop\\TastyApp\\uploads";
    private final String JPG_EXTENSION = "jpg";
    public boolean uploadImage(ImageModel imageModel, Integer userId, Integer recipeId){

        final String dirPath=UPLOADED_FOLDER+"\\users\\"+userId+"\\recipes\\"+recipeId;
        final String filePath = dirPath + "\\"+ imageModel.getUniqueName()+"."+imageModel.getType();
        BufferedImage bufferedImage=null;

        try {
            //get bytes
            byte [] bytes = imageModel.getFile().getBytes();
            //construct path to dir
            Path path = Paths.get(dirPath + "\\" + imageModel.getUniqueName());
            //check whether image has jpg extension, if not convert it to ipg
            if(!imageModel.getType().equals("jpg")){
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                //convert to BufferedImage and pass to converter
                bufferedImage = fileManager.convertToJpg(ImageIO.read(bis));
            }else {
                //else do not change extension
                bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
            }

            //String extension="."+imageModel.getType();
            //
            String extension = "."+JPG_EXTENSION;

            path= path.resolveSibling(path.getFileName() + extension);

            System.out.println(path.toAbsolutePath());
            //create directory if not exists
            fileManager.createDirIfNotExists(dirPath);
            //create output file path
            File output = new File(fileManager.constructPathToImage(userId,recipeId,path.getFileName().toString(),UPLOADED_FOLDER));
            fileManager.saveImageFromBuffer(bufferedImage,JPG_EXTENSION,output);

            //Files.write(path,bytes);

            //compress Image
            fileManager.compressImage(userId,recipeId,path.getFileName().toString(),"jpg",UPLOADED_FOLDER,bytes.length);
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
                            imageModels.add( new ImageDto( path.getFileName().toString(), JPG_EXTENSION, Files.readAllBytes(path)) );
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

