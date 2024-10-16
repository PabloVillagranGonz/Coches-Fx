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


    public void crearBaseYColeccion() {
        MongoClient mongoClient = conectar();

        if (mongoClient != null) {
            try {
                // Accediendo a la base de datos "concesionario-coches"
                MongoDatabase database = mongoClient.getDatabase("concesionario-coches");

                // Accediendo a la colección "coches"
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
