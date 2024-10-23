package com.example.crudcoches.Conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileInputStream;
import java.util.Properties;


public class ConnectionDB {
    public static MongoClient conectar() {
        try {

            Properties configuration = new Properties();

            FileInputStream fis = new FileInputStream("src/main/resources/Configuration/database.properties");
            configuration.load(fis);

            String username = configuration.getProperty("username");
            String password = configuration.getProperty("password");
            String host = configuration.getProperty("host");
            String port = configuration.getProperty("port");
            String author = configuration.getProperty("author");
            MongoClient conexion = MongoClients.create("mongodb://" + username + ":" + password + "@" + host + ":" + port + "/?authSource=" + author);

            System.out.println("Conectado");
            return conexion;
        } catch (
                Exception e) {
            System.out.println("Conexión Fallida");
            System.out.println(e);
            return null;
        }
    }

    public static void desconectar(MongoClient con) {
        if (con != null) {
            con.close();
            System.out.println("Desconectada correctamente de la BD");
        }
    }


    public void crearBaseYColeccion() {
        MongoClient mongoClient = conectar();

        if (mongoClient != null) {
            try {
                // Accedemos/creamos a la base de datos "concesionario-coches"
                MongoDatabase database = mongoClient.getDatabase("concesionario-coches");

                // Accedemos/creamos a la colección "coches"
                MongoCollection<Document> collection = database.getCollection("coches");

                System.out.println("Base de datos 'concesionario-coches' y colección 'coches' creadas exitosamente.");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            } finally {
                desconectar(mongoClient);
            }
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
    }

}
