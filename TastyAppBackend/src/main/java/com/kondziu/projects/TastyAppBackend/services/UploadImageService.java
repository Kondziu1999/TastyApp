package com.kondziu.projects.TastyAppBackend.services;

import com.kondziu.projects.TastyAppBackend.FileManager.FileManager;
import com.kondziu.projects.TastyAppBackend.models.ImageModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadImageService {

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


    //TODO compress image, consider using Tinypng
}
