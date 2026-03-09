package org.example.sportconnect.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.sportconnect.MainApp;
import org.example.sportconnect.model.Usuario;
import org.example.sportconnect.service.UsuarioService;
import org.example.sportconnect.util.SessionManager;

import java.util.Optional;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void login() {
        try {
            Optional<Usuario> usuario = usuarioService.login(txtEmail.getText(), txtPassword.getText());

            if (usuario.isPresent()) {
                SessionManager.login(usuario.get());
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("fxml/main.fxml"));
                Scene scene = new Scene(loader.load(), 1000, 650);
                MainApp.primaryStage.setScene(scene);
            } else {
                lblError.setText("Email o contraseña incorrectos.");
            }

        } catch (IllegalArgumentException e) {
            lblError.setText(e.getMessage());
        } catch (Exception e) {
            lblError.setText("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
    }

    @FXML
    public void irARegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("fxml/registro.fxml"));
            MainApp.primaryStage.getScene().setRoot(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}