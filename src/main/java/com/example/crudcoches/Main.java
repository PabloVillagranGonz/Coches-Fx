package com.example.crudcoches;

import com.example.crudcoches.Conexion.ConnectionDB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bson.Document;

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
        launch();
        ConnectionDB conexion = new ConnectionDB();
        conexion.crearBaseYColeccion();
    }
}