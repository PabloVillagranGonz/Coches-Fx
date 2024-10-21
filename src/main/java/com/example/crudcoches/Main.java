package com.example.crudcoches;

import com.example.crudcoches.Conexion.ConnectionDB;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("coches.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Coches");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        ConnectionDB conexion = new ConnectionDB();
        conexion.crearBaseYColeccion();
        launch();
    }
}