package com.example.crudcoches.DAO;

import com.example.crudcoches.Clases.Coches;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CochesDAO {
    private final MongoCollection<Document> collection;

    public CochesDAO(MongoDatabase database) {
        this.collection = database.getCollection("coches");
    }

    public void insertarCoche(Coches coche) {
        Document doc = new Document("matricula", coche.getMatricula())
                .append("marca", coche.getMarca())
                .append("modelo", coche.getModelo());
        collection.insertOne(doc);
    }

    public void eliminarCoche(String matricula) {
        collection.deleteOne(new Document("matricula", matricula));
    }

    public List<Coches> cargarCoches() {
        List<Coches> cochesList = new ArrayList<>();
        for (Document doc : collection.find()) {
            Coches coche = new Coches(doc.getString("matricula"), doc.getString("marca"), doc.getString("modelo"));
            cochesList.add(coche);
        }
        return cochesList;
    }

    public void modificarCoche(String matricula, Coches coche) {
        Document query = new Document("matricula", matricula);
        Document update = new Document("$set", new Document("marca", coche.getMarca())
                .append("modelo", coche.getModelo()));
        collection.updateOne(query, update);
    }
}
