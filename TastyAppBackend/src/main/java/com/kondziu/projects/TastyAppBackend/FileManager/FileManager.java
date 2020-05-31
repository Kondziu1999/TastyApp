package com.kondziu.projects.TastyAppBackend.FileManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

@Service
public class FileManager {

    @Value("${image.maxSize}")
    private float maxSize;

    public  void createDirIfNotExists(String path){
        File directory=new File(path);
        if(! directory.exists()){
            boolean xd=directory.mkdirs();
        }
    }
    /**
     *  convention uploads/users/{userId}/recipes/{recipeId}
     *
     * @param userId
     * @param recipeId
     * @param filename
     * @param base base path, depends on environment
     */
    public  String constructPathToImage(Integer userId,Integer recipeId, String filename, String base){
        return base + "\\users\\" + userId + "\\recipes\\" + recipeId + "\\" + filename;
    }

    @Async
    public void compressImage(Integer userId,Integer recipeId, String filename,String extension, String base,int sizeInBytes) throws IOException {
        float sizeInKB = sizeInBytes/1024;
        //max size is 100KB
        if(sizeInKB < maxSize) return;
        final float compressionFactor = maxSize/sizeInKB;

        File input = new File(constructPathToImage(userId,recipeId,filename,base));
        BufferedImage image = ImageIO.read(input);

        //c like compressed
        File compressedImageFile = new File(constructPathToImage(userId,recipeId,"c"+filename,base));
        OutputStream os =new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers =  ImageIO.getImageWritersByFormatName(extension);
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(compressionFactor);

        JPEGImageWriteParam jpegImageWriteParam = new JPEGImageWriteParam(null);
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();

        //delete old file
        input.delete();
    }


    public BufferedImage convertToJpg(BufferedImage image){

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

            return result;

    }
    //not async since methods need feedback to display appropriate status code
    public void saveImageFromBuffer(BufferedImage image,String format, File output) {
        try {
            ImageIO.write(image,format,output);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}