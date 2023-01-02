package com.example.algorithimproject_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class HelloController {

    @FXML
    private TableColumn<TableViewer, String> asciiC;

    @FXML
    private TableColumn<TableViewer, Byte> charC;

    @FXML
    private Button chooseFB;

    @FXML
    private Button deCompress;

    @FXML
    private TableColumn<TableViewer, Integer> freqC;

    @FXML
    private TableColumn<TableViewer, String> huffC;

    @FXML
    private TableView<TableViewer> tableData;

    @FXML
    void DeCompress(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file");

        Stage choose = new Stage();
        File selectedFile = fileChooser.showOpenDialog(choose);
        System.out.println(selectedFile.getAbsolutePath());

        Huffman huf = new Huffman();
        huf.deCompressFile(selectedFile.getAbsolutePath());

        asciiC = new TableColumn<>();
        charC = new TableColumn<>();
        freqC = new TableColumn<>();
        huffC = new TableColumn<>();

        asciiC.setCellValueFactory(new PropertyValueFactory<TableViewer, String>("ascii"));
        charC.setCellValueFactory(new PropertyValueFactory<TableViewer, Byte>("data"));
        freqC.setCellValueFactory(new PropertyValueFactory<TableViewer, Integer>("freq"));
        huffC.setCellValueFactory(new PropertyValueFactory<TableViewer, String>("code"));

        ObservableList<TableViewer> tableViewers = huf.getTableViewers();

        for (int i = 0; i < tableViewers.size(); i++) {
            System.out.println(tableViewers.get(i).toString());
        }

        tableData.setItems(tableViewers);

    }

    ObservableList<TableViewer> tableViewers = FXCollections.observableArrayList(
            new TableViewer((byte )65, 100, "1"),
            new TableViewer((byte )66, 102, "0")
    );


    @FXML
    void chooseFile(ActionEvent event) throws IOException, ExecutionException, InterruptedException {

        //HelloApplication.compress();

        asciiC.setCellValueFactory(new PropertyValueFactory<>("ascii"));
        charC.setCellValueFactory(new PropertyValueFactory<>("data"));
        freqC.setCellValueFactory(new PropertyValueFactory<>("freq"));
        huffC.setCellValueFactory(new PropertyValueFactory<>("code"));



        for (int i = 0; i < tableViewers.size(); i++) {
            System.out.println(tableViewers.get(i).toString());
        }

        tableData.setItems(tableViewers);


    }

}
