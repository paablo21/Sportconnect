package org.example.sportconnect.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.sportconnect.model.Reserva;
import org.example.sportconnect.service.ReservaService;
import org.example.sportconnect.util.SessionManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MisReservasController implements Initializable {

    @FXML private VBox vboxReservas;
    @FXML private Label lblVacio;

    private final ReservaService reservaService = new ReservaService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarReservas();
    }

    public void cargarReservas() {
        vboxReservas.getChildren().clear();
        List<Reserva> reservas = reservaService.getByUsuario(
                SessionManager.getUsuarioActual().getId()
        );

        // Filtrar solo las que no han pasado
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        List<Reserva> activas = reservas.stream()
                .filter(r -> java.time.LocalDateTime.of(r.getFecha(), r.getHoraFin()).isAfter(ahora))
                .collect(java.util.stream.Collectors.toList());

        if (activas.isEmpty()) {
            lblVacio.setVisible(true);
        } else {
            lblVacio.setVisible(false);
            for (Reserva r : activas) {
                vboxReservas.getChildren().add(crearTarjeta(r));
            }
        }
    }

    private HBox crearTarjeta(Reserva reserva) {
        HBox card = new HBox(16);
        card.getStyleClass().add("reserva-card");

        VBox info = new VBox(6);
        HBox.setHgrow(info, Priority.ALWAYS);

        Label nombre = new Label(reserva.getPista().getNombre());
        nombre.getStyleClass().add("pista-nombre");

        Label deporte = new Label(reserva.getPista().getDeporte().getNombre());
        deporte.getStyleClass().add("badge");

        HBox chips = new HBox(12);
        chips.setStyle("-fx-alignment: CENTER_LEFT;");

        Label fecha = new Label("📅 " + reserva.getFecha().toString());
        fecha.getStyleClass().add("chip");

        Label hora = new Label("🕐 " + reserva.getHoraInicio() + " - " + reserva.getHoraFin());
        hora.getStyleClass().add("chip");

        chips.getChildren().addAll(fecha, hora);
        info.getChildren().addAll(nombre, deporte, chips);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.getStyleClass().add("btn-danger");
        btnCancelar.setAccessibleText("Cancelar reserva de " + reserva.getPista().getNombre());
        btnCancelar.setOnAction(e -> cancelar(reserva));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(info, spacer, btnCancelar);
        return card;
    }

    private void cancelar(Reserva reserva) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Cancelar esta reserva?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                reservaService.delete(reserva.getId());
                cargarReservas();
            }
        });
    }
}