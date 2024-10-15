package com.example.crudcoches.Controller;
import com.example.crudcoches.Clases.Tipo;
import com.example.crudcoches.Conexion.ConnectionDB;
import com.example.crudcoches.DAO.CochesDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.bson.Document;

public class CochesController {

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnNuevo;

    @FXML
    private ListView<String> listCoches;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtModelo;

    private MongoCollection<Document> collection;

    private CochesDAO cochesDAO;


    public void initialize() {
        MongoClient mongoClient = ConnectionDB.conectar(); // Obtener el MongoClient

        // Inicializar el DAO de coches pasando el cliente
        cochesDAO = new CochesDAO(mongoClient.getDatabase("concesionario-coches"));

        // Cargar coches desde la base de datos al iniciar la vista
        cargarCoches();
    }


    private void agregarCoche(){
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();
        String matricula = txtMatricula.getText();

        if (!marca.isEmpty() && !modelo.isEmpty() && !matricula.isEmpty()){
            Document coche = new Document("marca", marca).append("modelo", modelo).append("matricula", matricula);

            collection.insertOne(coche);

            txtMarca.clear();
            txtModelo.clear();
            txtMatricula.clear();

            cargarCoches();
        } else {
            System.out.println("Por favor, rellena todos los campos");
        }
    }
    private void cargarCoches() {
        listCoches.getItems().clear(); // Limpiar la lista antes de recargar

        for (Document doc : collection.find()) {
            String coche = doc.getString("marca") + " - " + doc.getString("modelo") + " - " + doc.getString("matricula");
            listCoches.getItems().add(coche); // Agregar cada coche al ListView
        }
    }


}

