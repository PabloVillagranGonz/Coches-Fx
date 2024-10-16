package com.example.crudcoches.Controller;

import com.example.crudcoches.Clases.Coches;
import com.example.crudcoches.Conexion.ConnectionDB;
import com.example.crudcoches.DAO.CochesDAO;
import com.mongodb.MongoClient;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class CochesController {

    @FXML
    private Button btnBorrarCampos;
    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TableView<Coches> idTablaCoches;

    @FXML
    private TableColumn<Coches, String> colMarca;

    @FXML
    private TableColumn<Coches, String> colMatricula;

    @FXML
    private TableColumn<Coches, String> colModelo;

    @FXML
    private ListView<String> listCoches;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtModelo;

    private CochesDAO cochesDAO;

    private List<Coches> coches;

    @FXML
    public void initialize() {
        MongoClient mongoClient = ConnectionDB.conectar(); // Conectamos a la base de datos

        if (mongoClient != null) { // Si es exitosa, seguimos

            cochesDAO = new CochesDAO(mongoClient.getDatabase("concesionario-coches"));

            // Configurar las columnas de la tabla
            colMatricula.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getMatricula()));
            colMarca.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getMarca()));
            colModelo.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getModelo()));

            // Cargar coches al iniciar la vista
            cargarCoches();
        } else {
            // Mensaje de error si no se pudo conectar a la base de datos
            System.out.println("No se pudo establecer la conexión a la base de datos.");
        }
    }
    private void cargarCoches() {
        coches = cochesDAO.cargarCoches(); // Cargar coches desde el DAO
        idTablaCoches.getItems().clear(); // Limpiar la tabla antes de añadir nuevos elementos
        idTablaCoches.getItems().addAll(coches); // Añadir todos los coches a la tabla
    }

    @FXML
    void onCLickBorrarCampos(ActionEvent event) {
        // Limpiar los campos de texto
        txtMarca.clear();
        txtModelo.clear();
        txtMatricula.clear();

        // Opcionalmente, mostrar una alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Datos Borrados");
        alert.setHeaderText(null);
        alert.setContentText("Los campos han sido borrados.");
        alert.showAndWait();
    }

    @FXML
    void onClicEliminar(ActionEvent event) {
        String matricula = txtMatricula.getText();
        if (!matricula.isEmpty()){
            boolean eliminado = cochesDAO.eliminarCoche(matricula);

            if (eliminado){
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Coche eliminado");
                alerta.setHeaderText(null);
                alerta.setContentText("El coche ha sido eliminado correctamente");
                alerta.showAndWait();// Limpiar los campos de texto y actualizar la lista
                txtMatricula.clear();
                cargarCoches();
            } else { // No encontramos el coche con dicha matricula
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error al Eliminar");
                alerta.setHeaderText(null);
                alerta.setContentText("No se encontró un coche con la matrícula especificada.");
                alerta.showAndWait();
            }
        } else { //Mostramos mensaje de error que el campo matricula esta vacio
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campo Vacío");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, rellene el campo matrícula.");
            alerta.showAndWait();
        }
    }


    @FXML
    void onClicModificar(ActionEvent event) {
        String matricula = txtMatricula.getText();
        if (!matricula.isEmpty()){
            boolean encontrado = cochesDAO.modificarCoche(matricula, new Coches());

            if (encontrado){
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Coche modificado");
                alerta.setHeaderText(null);
                alerta.setContentText("El coche ha sido modificado correctamente");
                alerta.showAndWait();
                txtMatricula.clear();
                cargarCoches();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error al modificar");
                alerta.setHeaderText(null);
                alerta.setContentText("No se encontró un coche con la matrícula especificada.");
                alerta.showAndWait();
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campo Vacío");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, rellene el campo matrícula.");
            alerta.showAndWait();
        }
    }

    @FXML
    void onClicNuevo(ActionEvent event) {
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();
        String matricula = txtMatricula.getText();


        if (!marca.isEmpty() && !modelo.isEmpty() && !matricula.isEmpty()) {

            Coches nuevoCoche = new Coches(matricula, marca, modelo);

            cochesDAO.insertarCoche(nuevoCoche);

            // Coche creado
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Coche Añadido");
            alert.setHeaderText(null);
            alert.setContentText("El coche ha sido añadido correctamente.");
            alert.showAndWait();


            txtMarca.clear();
            txtModelo.clear();
            txtMatricula.clear();

            cargarCoches();
        } else {
            // Alerta de campos vacios
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, rellena todos los campos.");
            alert.showAndWait();
            }
    }

}
