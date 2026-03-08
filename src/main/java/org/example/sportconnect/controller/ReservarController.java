package org.example.sportconnect.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.example.sportconnect.model.Pista;
import org.example.sportconnect.model.Reserva;
import org.example.sportconnect.service.ReservaService;
import org.example.sportconnect.util.SessionManager;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ReservarController implements Initializable {

    @FXML private Label lblNombrePista;
    @FXML private Label lblDeportePista;
    @FXML private Label lblPrecioPista;
    @FXML private Label lblResumenFecha;
    @FXML private Label lblResumenHora;
    @FXML private Label lblEstado;
    @FXML private DatePicker dpFecha;
    @FXML private FlowPane flowHoras;
    @FXML private VBox vboxHoras;
    @FXML private Button btnConfirmar;

    private final ReservaService reservaService = new ReservaService();
    private Pista pistaActual;
    private String horaSeleccionada = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnConfirmar.setDisable(true);
        vboxHoras.setVisible(false);

        dpFecha.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });

        dpFecha.valueProperty().addListener((obs, o, fecha) -> {
            if (fecha != null) {
                lblResumenFecha.setText("📅  " + fecha.toString());
                cargarHoras(fecha);
                vboxHoras.setVisible(true);
                horaSeleccionada = null;
                lblResumenHora.setText("🕐  Selecciona una hora");
                btnConfirmar.setDisable(true);
            }
        });
    }

    public void setPista(Pista pista) {
        this.pistaActual = pista;
        lblNombrePista.setText(pista.getNombre());
        lblDeportePista.setText(pista.getDeporte().getNombre());
        lblPrecioPista.setText(pista.getPrecioHora() + " €/hora");
        dpFecha.setValue(null);
        horaSeleccionada = null;
        lblResumenFecha.setText("📅  Selecciona una fecha");
        lblResumenHora.setText("🕐  Selecciona una hora");
        lblEstado.setText("");
        btnConfirmar.setDisable(true);
        vboxHoras.setVisible(false);
    }

    private void cargarHoras(LocalDate fecha) {
        flowHoras.getChildren().clear();
        for (int h = 8; h <= 21; h++) {
            String hora = String.format("%02d:00", h);
            boolean ocupada = reservaService.isOcupada(pistaActual.getId(), fecha, LocalTime.parse(hora));
            Button btn = new Button(hora);
            if (ocupada) {
                btn.getStyleClass().add("hora-ocupada");
                btn.setDisable(true);
            } else {
                btn.getStyleClass().add("hora-disponible");
                btn.setOnAction(e -> seleccionarHora(hora, btn));
            }
            flowHoras.getChildren().add(btn);
        }
    }

    private void seleccionarHora(String hora, Button btnSeleccionado) {
        flowHoras.getChildren().forEach(node -> {
            if (node instanceof Button b && !b.isDisabled()) {
                b.getStyleClass().setAll("hora-disponible");
            }
        });
        btnSeleccionado.getStyleClass().setAll("hora-seleccionada");
        horaSeleccionada = hora;
        lblResumenHora.setText("🕐  " + hora);
        btnConfirmar.setDisable(false);
    }


    @FXML
    public void confirmarReserva() {
        try {
            LocalTime inicio = LocalTime.parse(horaSeleccionada);
            Reserva reserva = new Reserva(
                    SessionManager.getUsuarioActual(), pistaActual,
                    dpFecha.getValue(), inicio, inicio.plusHours(1)
            );
            reservaService.save(reserva);
            MainController.instancia.mostrarMisReservas();
        } catch (Exception e) {
            lblEstado.getStyleClass().setAll("lbl-error");
            lblEstado.setText(e.getMessage());
        }
    }

    @FXML
    public void volver() {
        MainController.instancia.mostrarInicio();
    }
}