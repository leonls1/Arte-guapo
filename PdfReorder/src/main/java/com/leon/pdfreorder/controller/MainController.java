/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leon.pdfreorder.controller;

import com.leon.pdfreorder.service.FileService;
import com.leon.pdfreorder.utils.other.PopUp;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MainController implements Initializable{

    @FXML
    private Button btnLoadFile;

    @FXML
    private Button btnOrderAndDownloadFile;

    @FXML
    private Button btnRemoveFile;

    @FXML
    private TableColumn<File,String> colFileName;

    @FXML
    private TableView<File> tableFiles;

    private FileService fileService;
    private ObservableList<File> files;
    private File fileSelected;
    private PopUp popup;
    private String columnName = "nombre producto";

    @FXML
    void btnEvent(ActionEvent event) {
        Object evt = event.getSource();
        switch (((Node) evt).getId()) {
            case "btnLoadFile" ->
                loadFile();
            case "btnOrderAndDownloadFile" ->
                reorderFileAndDownload();
            case "btnRemoveFile" ->
                removeFile();
        }
    }

    @FXML
    void mouseEvent(MouseEvent event) {
        if (event.getSource() == tableFiles) {
            fileSelected = tableFiles.getSelectionModel().getSelectedItem();
        }
    }
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        initilizeComponents();
    }
//------------------------------------------------------------
    private void loadFile() {
        files = FXCollections.observableArrayList(fileService.selectFiles());
        files.forEach(System.out::println);
        tableFiles.setItems(files);
    }

    private void reorderFileAndDownload() {
        if (popup.confirmMessage("Reordenar archivo", "", "reordenar y descargar archivo \n por la columna:" + columnName)) {
            fileService.downloadFile(fileService.reorderFile(fileSelected));
        }
    }

    private void removeFile() {
        Optional.of(fileSelected).ifPresent(files::remove);
    }

    private void initilizeComponents() {
        popup = PopUp.getPopInstance();
        fileService = FileService.getInstance();
        tableFiles.setItems(files);
        colFileName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    }



}
