package org.example.sportconnect.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.example.sportconnect.model.Pista;
import org.example.sportconnect.service.DeporteService;
import org.example.sportconnect.service.PistaService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PistasController implements Initializable {

    @FXML private FlowPane flowPistas;
    @FXML private TextField txtBuscar;
    @FXML private ComboBox<String> cboFiltro;

    private final PistaService pistaService = new PistaService();
    private final DeporteService deporteService = new DeporteService();
    private List<Pista> todasLasPistas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        todasLasPistas = pistaService.getAll();

        // Filtro por deporte
        List<String> deportes = deporteService.getAll()
                .stream().map(d -> d.getNombre()).collect(Collectors.toList());
        deportes.add(0, "Todos");
        cboFiltro.setItems(FXCollections.observableArrayList(deportes));
        cboFiltro.setValue("Todos");

        cboFiltro.valueProperty().addListener((obs, o, v) -> filtrar());
        txtBuscar.textProperty().addListener((obs, o, v) -> filtrar());

        mostrarTarjetas(todasLasPistas);
    }

    private void filtrar() {
        String texto = txtBuscar.getText().toLowerCase();
        String deporte = cboFiltro.getValue();
        List<Pista> filtradas = todasLasPistas.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(texto))
                .filter(p -> deporte == null || deporte.equals("Todos")
                        || p.getDeporte().getNombre().equals(deporte))
                .collect(Collectors.toList());
        mostrarTarjetas(filtradas);
    }

    private void mostrarTarjetas(List<Pista> pistas) {
        flowPistas.getChildren().clear();
        for (Pista p : pistas) {
            flowPistas.getChildren().add(crearTarjeta(p));
        }
    }

    private VBox crearTarjeta(Pista pista) {
        VBox card = new VBox(8);
        card.getStyleClass().add("pista-card");

        Label badge = new Label(pista.getDeporte().getNombre());
        badge.getStyleClass().add("badge");

        Label nombre = new Label(pista.getNombre());
        nombre.getStyleClass().add("pista-nombre");
        nombre.setWrapText(true);

        Label precio = new Label(pista.getPrecioHora() + " €/hora");
        precio.getStyleClass().add("pista-precio");

        Button btnReservar = new Button("Reservar");
        btnReservar.setMaxWidth(Double.MAX_VALUE);
        btnReservar.getStyleClass().add("btn-primary");
        btnReservar.setAccessibleText("Reservar " + pista.getNombre());
        btnReservar.setOnAction(e -> reservar(pista));

        card.getChildren().addAll(badge, nombre, precio, btnReservar);
        return card;
    }

    private void reservar(Pista pista) {
        MainController.instancia.navegarAReservar(pista);
    }
}