package com.example.crudcoches.Conexion;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class ConnectionDB {
    public static MongoClient conectar() {
        try {
            MongoClient conexion = new MongoClient(new MongoClientURI("mongodb://admin:1234@localhost:27017/?authSource=admin"));

            System.out.println("Conectada correctamente a la BD");
            return conexion;
        } catch (Exception e) {
            System.out.println("Conexión fallida: " + e.getMessage());
            return null;
        }
    }

    public static void desconectar(MongoClient con) {
        if (con != null) {
            con.close();
            System.out.println("Desconectada correctamente de la BD");
        }
    }

    public void connectToDatabase () {
        MongoClient mongoClient;
        MongoDatabase database;
        MongoCollection<Document> collection;

        try {
            mongoClient = ConnectionDB.conectar();
            database = mongoClient.getDatabase("concesionario-coches");
            collection = database.getCollection("coches");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}
