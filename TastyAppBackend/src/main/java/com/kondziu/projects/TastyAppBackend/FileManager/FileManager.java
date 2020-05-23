package com.kondziu.projects.TastyAppBackend.FileManager;

import java.io.File;

public class FileManager {

    public static void createDirIfNotExists(String path){
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
    public static String constructPathToImage(Integer userId,Integer recipeId, String filename, String base){
        return base + "\\users\\" + userId + "\\recipes\\" + recipeId + "\\" + filename;
    }

}
