package com.example.algorithimproject_2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HelloApplication extends Application {



    ObservableList<TableViewer> tableViewersC;
    ObservableList<TableViewer> tableViewersDC;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));


        AnchorPane root = new AnchorPane();

        Button compressB = new Button("Compress");
        compressB.setPrefSize(109, 30);
        compressB.setLayoutX(20);
        compressB.setLayoutY(57);

        Label compressL = new Label("Compress");
        compressL.setLayoutX(295);
        compressL.setLayoutY(14);
        compressL.setPrefSize(53, 17);

        TextField compressTF = new TextField();
        compressTF.setPrefSize(493, 25);
        compressTF.setLayoutX(136);
        compressTF.setLayoutY(60);

        TableView<TableViewer> compressT = new TableView<>();
        compressT.setPrefSize(493, 530);
        compressT.setLayoutX(75);
        compressT.setLayoutY(125);
        compressT.setEditable(false);
        compressT.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<TableViewer, Byte> dataCC = new TableColumn();
        dataCC.setResizable(false);
        dataCC.setText("Data");
        dataCC.setPrefWidth(123);
        dataCC.setCellValueFactory(new PropertyValueFactory<>("data"));
        TableColumn<TableViewer, Integer> freqCC = new TableColumn();
        freqCC.setResizable(false);
        freqCC.setText("freq");
        freqCC.setPrefWidth(123);
        freqCC.setCellValueFactory(new PropertyValueFactory<>("freq"));
        TableColumn<TableViewer, String> codeCC = new TableColumn();
        codeCC.setResizable(false);
        codeCC.setText("code");
        codeCC.setPrefWidth(123);
        codeCC.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<TableViewer, String> asciiCC = new TableColumn();
        asciiCC.setResizable(false);
        asciiCC.setText("ascii");
        asciiCC.setCellValueFactory(new PropertyValueFactory<>("ascii"));
        asciiCC.setPrefWidth(124);

        compressT.getColumns().addAll(dataCC, freqCC, codeCC, asciiCC);

        Button showCB = new Button("Show statistics");
        showCB.setPrefSize(94, 25);
        showCB.setLayoutX(264);
        showCB.setLayoutY(671);
        showCB.setDisable(true);

        Line line = new Line();
        line.setStartX(7);
        line.setEndX(7);
        line.setStartY(-72);
        line.setEndY(660);
        line.setLayoutX(640);
        line.setLayoutY(60);

        Label deCompL = new Label("De-Compress");
        deCompL.setLayoutX(923);
        deCompL.setLayoutY(14);
        deCompL.setPrefSize(72, 17);

        Button deCompressB = new Button("De-Compress");
        deCompressB.setPrefSize(109, 30);
        deCompressB.setLayoutX(656);
        deCompressB.setLayoutY(57);

        TextField deCompressTF = new TextField();
        deCompressTF.setPrefSize(493, 25);
        deCompressTF.setLayoutX(769);
        deCompressTF.setLayoutY(60);

        TableView<TableViewer> deCompressT = new TableView<>();
        deCompressT.setPrefSize(493, 530);
        deCompressT.setLayoutX(712);
        deCompressT.setLayoutY(126);
        deCompressT.setEditable(false);
        deCompressT.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<TableViewer, Byte> dataDCC = new TableColumn();
        dataDCC.setResizable(false);
        dataDCC.setText("Data");
        dataDCC.setPrefWidth(123);
        dataDCC.setCellValueFactory(new PropertyValueFactory<>("data"));
        TableColumn<TableViewer, Integer> freqDCC = new TableColumn();
        freqDCC.setResizable(false);
        freqDCC.setText("freq");
        freqDCC.setPrefWidth(123);
        freqDCC.setCellValueFactory(new PropertyValueFactory<>("freq"));
        TableColumn<TableViewer, String> codeDCC = new TableColumn();
        codeDCC.setResizable(false);
        codeDCC.setText("code");
        codeDCC.setPrefWidth(123);
        codeDCC.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<TableViewer, String> asciiDCC = new TableColumn();
        asciiDCC.setResizable(false);
        asciiDCC.setText("ascii");
        asciiDCC.setCellValueFactory(new PropertyValueFactory<>("ascii"));
        asciiDCC.setPrefWidth(124);

        deCompressT.getColumns().addAll(dataDCC, freqDCC, codeDCC, asciiDCC);


        Button deShowCB = new Button("Show statistics");
        deShowCB.setPrefSize(94, 25);
        deShowCB.setLayoutX(911);
        deShowCB.setLayoutY(671);
        deShowCB.setDisable(true);


        root.getChildren().addAll(compressL, compressB, compressTF, compressT, showCB, line, deCompL, deCompressB, deCompressTF, deCompressT, deShowCB);

        Scene scene = new Scene(root, 1280, 720);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();




        compressB.setOnAction(e -> {
            try {
                FileChooser fileChooser = new FileChooser();

                fileChooser.setTitle("Choose the file");

                Stage choose = new Stage();
                File selectedFile = fileChooser.showOpenDialog(choose);

                String fileExtension = "";
                int i = selectedFile.getAbsolutePath().lastIndexOf('.');
                if (i > 0) {
                    fileExtension = selectedFile.getAbsolutePath().substring(i+1);
                }
                if (fileExtension.equals("MohammadF")){

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("File should not have 'MohammadF' extension");
                    alert.show();

                } else {
                    Huffman huf = new Huffman();
                    huf.compressFile(selectedFile.getAbsolutePath());
                    showCB.setDisable(false);
                    compressTF.setText(selectedFile.getAbsolutePath());
                    compressT.setItems(huf.getTableViewers());
                }
            } catch (Exception err) {
                System.out.println(err.getMessage());
            }
        });


        deCompressB.setOnAction(e -> {
            try {
                FileChooser fileChooser = new FileChooser();

                fileChooser.setTitle("Choose the file");

                Stage choose = new Stage();
                File selectedFile = fileChooser.showOpenDialog(choose);

                String fileExtension = "";
                int i = selectedFile.getAbsolutePath().lastIndexOf('.');
                if (i > 0) {
                    fileExtension = selectedFile.getAbsolutePath().substring(i+1);
                }
                if (!fileExtension.equals("MohammadF")){

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("File should not have 'MohammadF' extension");
                    alert.show();

                } else {
                    Huffman huf = new Huffman();
                    huf.deCompressFile(selectedFile.getAbsolutePath());
                    deShowCB.setDisable(false);
                    deCompressTF.setText(selectedFile.getAbsolutePath());
                    deCompressT.setItems(huf.getTableViewers());
                }
            } catch (Exception err) {}
        });

    }

    public static void main(String[] args) {
        launch();
    }
}