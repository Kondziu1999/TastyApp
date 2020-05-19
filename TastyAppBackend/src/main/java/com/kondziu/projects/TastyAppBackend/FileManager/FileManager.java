package com.kondziu.projects.TastyAppBackend.FileManager;

import java.io.File;

public class FileManager {

    public static void createDirIfNotExists(String path){
        File directory=new File(path);
        if(! directory.exists()){
            boolean xd=directory.mkdirs();
        }
    }
}
