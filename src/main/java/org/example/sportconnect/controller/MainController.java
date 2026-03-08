package org.example.sportconnect.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.sportconnect.MainApp;
import org.example.sportconnect.util.SessionManager;
import org.example.sportconnect.model.Pista;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private Label lblUsuario;
    @FXML private Label lblRol;
    @FXML private Button btnInicio;
    @FXML private Button btnMisReservas;
    @FXML private Button btnAdmin;
    @FXML private StackPane contenido;

    @FXML private javafx.scene.Node pistas;
    @FXML private javafx.scene.Node misReservas;
    @FXML private javafx.scene.Node admin;
    @FXML private javafx.scene.Node reservar;

    public static MainController instancia;

    @FXML private MisReservasController misReservasController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instancia = this;
        lblUsuario.setText(SessionManager.getUsuarioActual().getNombre());

        if (SessionManager.isAdmin()) {
            lblRol.setText("Administrador");
            btnMisReservas.setVisible(false);
            btnMisReservas.setManaged(false);
            btnInicio.setVisible(false);
            btnInicio.setManaged(false);
        } else {
            lblRol.setText("Usuario");
            btnAdmin.setVisible(false);
            btnAdmin.setManaged(false);
        }

        mostrarAdmin();
    }

    private void resetBotones() {
        btnInicio.getStyleClass().setAll("btn-nav");
        btnMisReservas.getStyleClass().setAll("btn-nav");
        btnAdmin.getStyleClass().setAll("btn-nav");
        pistas.setVisible(false);
        misReservas.setVisible(false);
        admin.setVisible(false);
        reservar.setVisible(false);
    }

    @FXML
    public void mostrarInicio() {
        resetBotones();
        btnInicio.getStyleClass().setAll("btn-nav-activo");
        pistas.setVisible(true);
    }

    @FXML
    public void mostrarMisReservas() {
        resetBotones();
        btnMisReservas.getStyleClass().setAll("btn-nav-activo");
        misReservas.setVisible(true);
        misReservasController.cargarReservas();
    }

    @FXML
    public void mostrarAdmin() {
        resetBotones();
        btnAdmin.getStyleClass().setAll("btn-nav-activo");
        admin.setVisible(true);
    }

    public void mostrarReservar() {
        resetBotones();
        reservar.setVisible(true);
    }

    @FXML
    public void logout() throws Exception {
        SessionManager.logout();
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("fxml/login.fxml"));
        MainApp.primaryStage.setScene(new javafx.scene.Scene(loader.load(), 900, 600));
    }

    @FXML private ReservarController reservarController;

    public void navegarAReservar(Pista pista) {
        mostrarReservar();
        reservarController.setPista(pista);
    }

}