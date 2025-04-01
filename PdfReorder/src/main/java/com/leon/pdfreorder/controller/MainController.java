/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leon.pdfreorder.controller;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {
    @FXML
    private Button btnLoadFile;

    @FXML
    private Button btnOrderAndDownloadFile;

    @FXML
    private Button btnRemoveFile;

    @FXML
    private TableColumn<?, ?> colFileName;

    @FXML
    private TableView<File> tableFiles;

    @FXML
    void btnEvent(ActionEvent event) {
       
    }
}
