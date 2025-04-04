/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leon.pdfreorder.service;

import com.leon.pdfreorder.App;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;

import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.BasicExtractionAlgorithm;

/**
 *
 * @author Leon
 */
public class FileService {

    private static FileService fileService;

    public List<File> selectFiles() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Selecciona uno o mas archivos");
        ExtensionFilter filter = new ExtensionFilter("PDF files", "*.pdf");
        chooser.setSelectedExtensionFilter(filter);
        return chooser.showOpenMultipleDialog(App.stage);
    }

    public void reorderRowsFromTableFile(File file, String destinyPath, int orderIndex) throws IOException {
        PDDocument document = Loader.loadPDF(file);

        ObjectExtractor extractor = new ObjectExtractor(document);
        BasicExtractionAlgorithm algorithm = new BasicExtractionAlgorithm();
        List<List<String>> tableData = new ArrayList<>();

        for (int i = 1; i < document.getNumberOfPages() + 1; i++) {
            Page page = extractor.extract(i);
            //it's a list because there can be several tables in the same page
            List<Table> tables = algorithm.extract(page);

            //for every table add them into the tableData resulted
//            for (Table table : tables) {
//                tableData.addAll(parseTableCollection(table));
//            }
            tableData.addAll(filterTableFromText(tables.getFirst()));
        }
        document.close();

        //sorting rows of the dataTable
        reoderTableByIndex(tableData, orderIndex);

        //generating the new file reordered
        createReorderedFile(destinyPath, tableData);

    }

    public static FileService getInstance() {
        if (fileService == null) {
            fileService = new FileService();
        }
        return fileService;
    }

    private List<List<String>> parseTableCollection(Table table) {
        List<List<String>> tableData = new ArrayList<>();
        //itereates over all the table
        for (List<RectangularTextContainer> row : table.getRows()) {
            List<String> rowData = new ArrayList<>();
            //iterates for every row
            for (RectangularTextContainer cell : row) {
                //adding the content of the current cell to the actual rowData
                rowData.add(cell.getText());
            }
            tableData.add(rowData);
        }
        return tableData;
    }

    private void reoderTableByIndex(List<List<String>> tableData, int orderIndex) {
        tableData.sort(Comparator.comparing(row -> row.get(orderIndex)));
    }

    private void createReorderedFile(String destinyPath, List<List<String>> tableData) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 700);

        for (List<String> row : tableData) {
            contentStream.showText(String.join(" | ", row));
            contentStream.newLineAtOffset(0, -20);
        }

        contentStream.endText();
        contentStream.close();

        document.save(destinyPath);
        document.close();

        System.out.println("new pdf document created at:" + destinyPath);

    }

    private List<List<String>> filterTableFromText(Table table) {
        List<List<String>> parsedTable = parseTableCollection(table);
        //remove the 9 first columns
        for (int i = 0; i < 9; i++) {
            parsedTable.removeFirst();
        }
        //remove the last 2 columns
        parsedTable.removeLast();
        parsedTable.removeLast();

        return parsedTable;
    }

    private Table removeNonTables(List<Table> tables) {
        Table realTable = null;
        int bestScore = 0; //to compare and get the most possible table
        for (Table table : tables) {
            List<List<String>> rows = parseTableCollection(table);
            
            int validRowCount = 0;
            int nonEmptyCellCount = 0;
            
            for(List<String> row: rows){
                long nonEmptyColCount = row.stream().filter(cell -> !cell.isBlank()).count();
                if(nonEmptyColCount >= 4){
                    validRowCount++;
                }
            }
            
                

        }

        return realTable;

    }

}
