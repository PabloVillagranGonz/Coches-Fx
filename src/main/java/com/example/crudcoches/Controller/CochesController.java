package com.example.crudcoches.Controller;
import com.example.crudcoches.Clases.Tipo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
    private ListView<Tipo> listCoches;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtModelo;


    /*public void initialize() {
        listCoches.getItems().addAll("Ranchera", "Deportivo", "Todoterreno", "SUV", "Compacto")
    }*/


}

