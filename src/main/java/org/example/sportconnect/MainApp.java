package org.example.sportconnect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.sportconnect.util.HibernateUtil;

public class MainApp extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Esto fuerza a Hibernate a arrancar y crear las tablas
        HibernateUtil.getSessionFactory();

        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("fxml/login.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        stage.setTitle("SportConnect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}