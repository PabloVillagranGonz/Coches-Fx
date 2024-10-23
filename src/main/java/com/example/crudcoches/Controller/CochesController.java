package com.example.crudcoches.Controller;

import com.example.crudcoches.Clases.Coches;
import com.example.crudcoches.Conexion.ConnectionDB;
import com.example.crudcoches.DAO.CochesDAO;
import com.mongodb.client.MongoClient;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.List;
import java.util.Optional;

public class CochesController {

    @FXML
    private Button btnBorrar;
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
    private TableColumn<Coches, String> colTipo;

    @FXML
    private ComboBox<String> fxTipo;

    @FXML
    private Button idSalir;

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
            String[]lista= {"Todoterreno","Familiar","Suv","Deportivo", "Mini"};
            fxTipo.getItems().addAll(lista);

            // Configuramos las columnas de la tabla

            colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
            colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
            colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
            colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

            cargarCoches();

        } else {
            // Mensaje de error si no se pudo conectar a la base de datos
            System.out.println("No se pudo establecer la conexión a la base de datos.");
        }
    }

    private void cargarCoches() {
        coches = cochesDAO.cargarCoches(); // Cargamos los cochesDAO
        idTablaCoches.getItems().clear(); // Limpiamos la tabla antes de seguir
        idTablaCoches.getItems().addAll(coches); // Añadimos todos los coches a la tabla
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
                txtMarca.clear();
                txtModelo.clear();
                txtMatricula.clear();
                fxTipo.getSelectionModel().clearSelection();

                cargarCoches();
            } else { // Mostramos mensaje de error que el coche con dicha matricula no ha sido encontrado
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
        String nuevaMarca = txtMarca.getText();
        String nuevoModelo = txtModelo.getText();
        String tipo = fxTipo.getSelectionModel().getSelectedItem();

        if (!matricula.isEmpty() && !nuevaMarca.isEmpty() && !nuevoModelo.isEmpty() && !tipo.isEmpty()) {
            Coches cocheModificado = new Coches(matricula, nuevaMarca, nuevoModelo, tipo);
            boolean encontrado = cochesDAO.modificarCoche(matricula, cocheModificado);

            if (encontrado) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Coche modificado");
                alerta.setHeaderText(null);
                alerta.setContentText("El coche ha sido modificado correctamente.");
                alerta.showAndWait();
                txtMatricula.clear();
                txtMarca.clear();
                txtModelo.clear();
                fxTipo.getSelectionModel().clearSelection();
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
            alerta.setContentText("Por favor, rellene todos los campos requeridos.");
            alerta.showAndWait();
        }
    }

    @FXML
    void onClicNuevo(ActionEvent event) {
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();
        String matricula = txtMatricula.getText();
        String tipo = fxTipo.getSelectionModel().getSelectedItem();


        if (!marca.isEmpty() && !modelo.isEmpty() && !matricula.isEmpty() && tipo != null) {
            if (!cochesDAO.comprobarMatricula(matricula)) {

                Coches nuevoCoche = new Coches(matricula, marca, modelo, tipo);

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
                fxTipo.getSelectionModel().clearSelection();

                cargarCoches();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Matricula existente");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, cambie de matricula, esa ya existe.");
                alert.showAndWait();
            }
        } else {
            // Alertamos que hay campos vacios
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, rellena todos los campos.");
            alert.showAndWait();
            }
    }

    @FXML
    void onFinClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de cierre");
        alert.setHeaderText("¿Estás seguro de que deseas cerrar la aplicación?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void onClicBorrar(ActionEvent actionEvent) {
        // Limpiamos los campos de texto
        txtMarca.clear();
        txtModelo.clear();
        txtMatricula.clear();
        fxTipo.getSelectionModel().clearSelection();

        // Mostramos alerta de confirmacion
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Datos Borrados");
        alert.setHeaderText(null);
        alert.setContentText("Los campos han sido borrados.");
        alert.showAndWait();
    }

    public void onClickedMouse(javafx.scene.input.MouseEvent mouseEvent) {
        Coches coche =idTablaCoches.getSelectionModel().getSelectedItem();
        if (coche != null) {
            txtMatricula.setText(coche.matricula);
            txtModelo.setText(coche.modelo);
            txtMarca.setText(coche.marca);
            fxTipo.getSelectionModel().select(coche.getTipo());
        } else {
            System.out.println("No se ha seleccionado ningún coche.");
        }
    }
}
