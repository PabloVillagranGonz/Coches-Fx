package com.example.crudcoches.Conexion;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

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
}
