package org.example.sportconnect.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.sportconnect.MainApp;
import org.example.sportconnect.model.Usuario;
import org.example.sportconnect.service.UsuarioService;

public class RegistroController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void registrar() {
        try {
            Usuario u = new Usuario(
                    txtNombre.getText().trim(),
                    txtEmail.getText().trim(),
                    txtPassword.getText().trim(),
                    "SOCIO"
            );
            usuarioService.save(u);
            volverLogin();
        } catch (Exception e) {
            lblError.setText(e.getMessage());
        }
    }

    @FXML
    public void volverLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("fxml/login.fxml"));
            MainApp.primaryStage.getScene().setRoot(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}