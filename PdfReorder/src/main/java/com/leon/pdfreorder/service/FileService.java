/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.leon.pdfreorder.service;

import com.leon.pdfreorder.App;
import java.io.File;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Leon
 */
public class FileService {
    private static FileService fileService;
    
    public List<File> selectFiles(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Selecciona uno o mas archivos");
        ExtensionFilter filter = new ExtensionFilter("PDF files", "*.pdf");
        chooser.setSelectedExtensionFilter(filter);
        return chooser.showOpenMultipleDialog(App.stage);
    }
    
    public File reorderFile(File file){
        return file;
    }
    
    public void downloadFile(File file){
        
    }
    
    public static FileService getInstance(){
        if(fileService == null){
            fileService = new FileService();
        }
        return fileService;
    }

}
