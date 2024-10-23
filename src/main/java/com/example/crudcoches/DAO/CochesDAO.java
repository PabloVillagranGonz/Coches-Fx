package com.example.crudcoches.DAO;

import com.example.crudcoches.Clases.Coches;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CochesDAO {
    private final MongoCollection<Document> collection;

    public MongoCollection<Document> getCollection() {
        return collection;
    }


    public CochesDAO(MongoDatabase database) {
        this.collection = database.getCollection("coches");
    }

    public void insertarCoche(Coches coche) {
        try {
            Document doc = new Document("matricula", coche.getMatricula())
                    .append("marca", coche.getMarca())
                    .append("modelo", coche.getModelo())
                    .append("tipo", coche.getTipo());
            collection.insertOne(doc);
        } catch (Exception e) {
            System.out.println("Error al insertar coche: " + e.getMessage());
        }
    }

    public boolean eliminarCoche(String matricula) {
        long result = collection.deleteOne(new Document("matricula", matricula)).getDeletedCount();
        return result > 0;
    }

    public boolean modificarCoche(String matricula, Coches coche) {
        Document query = new Document("matricula", matricula);
        Document update = new Document("$set", new Document("marca", coche.getMarca())
                .append("modelo", coche.getModelo()).append("tipo", coche.getTipo()));
        long modifiedCount = collection.updateOne(query, update).getModifiedCount();
        return modifiedCount > 0;
    }

    public boolean comprobarMatricula(String matricula) {
        try {
            Document doc = collection.find(new Document("matricula", matricula)).first();
            return doc != null; // Devuelve true si existe, false si no existe
        } catch (Exception e) {
            System.out.println("Error al comprobar matrícula: " + e.getMessage());
            return false;
        }
    }
    public List<Coches> cargarCoches() {
        List<Coches> cochesList = new ArrayList<>();
        try {
            for (Document doc : collection.find()) {
                String matricula = doc.getString("matricula");
                String marca = doc.getString("marca");
                String modelo = doc.getString("modelo");
                String tipo = doc.getString("tipo");


                Coches coche = new Coches(matricula, marca, modelo, tipo);
                cochesList.add(coche);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar coches: " + e.getMessage());
        }
        return cochesList;
    }
}