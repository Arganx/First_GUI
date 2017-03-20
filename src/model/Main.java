package model;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../view/gui.fxml"));
        this.primaryStage.setTitle("Best shop");
        this.primaryStage.setScene(new Scene(root, 700, 400));
        this.primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }



}
