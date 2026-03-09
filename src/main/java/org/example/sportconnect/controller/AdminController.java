package org.example.sportconnect.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.sportconnect.model.Deporte;
import org.example.sportconnect.model.Pista;
import org.example.sportconnect.service.DeporteService;
import org.example.sportconnect.service.PistaService;
import org.example.sportconnect.model.Usuario;
import org.example.sportconnect.service.UsuarioService;
import org.example.sportconnect.model.Reserva;
import org.example.sportconnect.service.ReservaService;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    // Deportes
    @FXML private TableView<Deporte> tablaDeportes;
    @FXML private TableColumn<Deporte, Long> colDepId;
    @FXML private TableColumn<Deporte, String> colDepNombre;
    @FXML private TextField txtNuevoDeporte;
    @FXML private Label lblDepEstado;

    // Pistas
    @FXML private TableView<Pista> tablaPistas;
    @FXML private TableColumn<Pista, String> colPistaNombre;
    @FXML private TableColumn<Pista, String> colPistaDeporte;
    @FXML private TableColumn<Pista, Double> colPistaPrecio;
    @FXML private TextField txtNuevaPista;
    @FXML private TextField txtPrecio;
    @FXML private ComboBox<Deporte> cboDeporte;
    @FXML private Label lblPistaEstado;

    // Usuarios
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colUsuNombre;
    @FXML private TableColumn<Usuario, String> colUsuEmail;
    @FXML private TableColumn<Usuario, String> colUsuRol;
    @FXML private TextField txtNuevoNombre;
    @FXML private TextField txtNuevoEmail;
    @FXML private TextField txtNuevoPassword;
    @FXML private ComboBox<String> cboRol;
    @FXML private Label lblUsuEstado;

    // Stats
    @FXML private Label lblTotalPistas;
    @FXML private Label lblTotalUsuarios;
    @FXML private Label lblTotalReservas;

    @FXML private TableView<Reserva> tablaReservas;
    @FXML private TableColumn<Reserva, String> colResUsuario;
    @FXML private TableColumn<Reserva, String> colResPista;
    @FXML private TableColumn<Reserva, String> colResFecha;
    @FXML private TableColumn<Reserva, String> colResHora;

    private final ReservaService reservaService = new ReservaService();

    private final DeporteService deporteService = new DeporteService();
    private final PistaService pistaService = new PistaService();
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colDepId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDepNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        colPistaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPistaDeporte.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDeporte().getNombre())
        );
        colPistaPrecio.setCellValueFactory(new PropertyValueFactory<>("precioHora"));

        colUsuNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colUsuEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colUsuRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        cboRol.setItems(FXCollections.observableArrayList("SOCIO", "ADMIN"));

        cargarDeportes();
        cargarPistas();
        cargarUsuarios();

        lblTotalPistas.setText(String.valueOf(pistaService.getAll().size()));
        lblTotalUsuarios.setText(String.valueOf(usuarioService.getAll().size()));
        lblTotalReservas.setText(String.valueOf(new org.example.sportconnect.service.ReservaService().getAll().size()));

        colResUsuario.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getUsuario().getNombre())
        );
        colResPista.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getPista().getNombre())
        );
        colResFecha.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getFecha().toString())
        );
        colResHora.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getHoraInicio() + " - " + data.getValue().getHoraFin()
                )
        );
        tablaReservas.setItems(FXCollections.observableArrayList(reservaService.getAll()));
    }

    private void cargarDeportes() {
        tablaDeportes.setItems(FXCollections.observableArrayList(deporteService.getAll()));
        cboDeporte.setItems(FXCollections.observableArrayList(deporteService.getAll()));
    }

    private void cargarPistas() {
        tablaPistas.setItems(FXCollections.observableArrayList(pistaService.getAll()));
    }

    private void cargarUsuarios() {
        tablaUsuarios.setItems(FXCollections.observableArrayList(usuarioService.getAll()));
    }

    @FXML
    public void agregarDeporte() {
        try {
            deporteService.save(new Deporte(txtNuevoDeporte.getText().trim()));
            txtNuevoDeporte.clear();
            lblDepEstado.getStyleClass().setAll("lbl-ok");
            lblDepEstado.setText("Deporte añadido.");
            cargarDeportes();
        } catch (Exception e) {
            lblDepEstado.getStyleClass().setAll("lbl-error");
            lblDepEstado.setText(e.getMessage());
        }
    }

    @FXML
    public void agregarPista() {
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            pistaService.save(new Pista(txtNuevaPista.getText().trim(), precio, cboDeporte.getValue()));
            txtNuevaPista.clear();
            txtPrecio.clear();
            cboDeporte.setValue(null);
            lblPistaEstado.getStyleClass().setAll("lbl-ok");
            lblPistaEstado.setText("Pista añadida.");
            cargarPistas();
        } catch (Exception e) {
            lblPistaEstado.getStyleClass().setAll("lbl-error");
            lblPistaEstado.setText(e.getMessage());
        }
    }

    @FXML
    public void agregarUsuario() {
        try {
            Usuario u = new Usuario(
                    txtNuevoNombre.getText().trim(),
                    txtNuevoEmail.getText().trim(),
                    txtNuevoPassword.getText().trim(),
                    cboRol.getValue()
            );
            usuarioService.save(u);
            txtNuevoNombre.clear();
            txtNuevoEmail.clear();
            txtNuevoPassword.clear();
            cboRol.setValue(null);
            lblUsuEstado.getStyleClass().setAll("lbl-ok");
            lblUsuEstado.setText("Usuario añadido.");
            cargarUsuarios();
        } catch (Exception e) {
            lblUsuEstado.getStyleClass().setAll("lbl-error");
            lblUsuEstado.setText(e.getMessage());
        }
    }

    @FXML
    public void eliminarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblUsuEstado.setText("Selecciona un usuario.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar a " + seleccionado.getNombre() + "?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                usuarioService.delete(seleccionado.getId());
                lblUsuEstado.getStyleClass().setAll("lbl-ok");
                lblUsuEstado.setText("Usuario eliminado.");
                cargarUsuarios();
            }
        });
    }

    @FXML
    public void eliminarPista() {
        Pista seleccionada = tablaPistas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            lblPistaEstado.setText("Selecciona una pista.");
            return;
        }
        // Comprobar si tiene reservas
        long reservasDePista = reservaService.getAll().stream()
                .filter(r -> r.getPista().getId().equals(seleccionada.getId()))
                .count();
        if (reservasDePista > 0) {
            lblPistaEstado.getStyleClass().setAll("lbl-error");
            lblPistaEstado.setText("No se puede eliminar, tiene reservas activas.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar la pista " + seleccionada.getNombre() + "?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                pistaService.delete(seleccionada.getId());
                lblPistaEstado.getStyleClass().setAll("lbl-ok");
                lblPistaEstado.setText("Pista eliminada.");
                cargarPistas();
            }
        });
    }

    @FXML
    public void eliminarDeporte() {
        Deporte seleccionado = tablaDeportes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblDepEstado.setText("Selecciona un deporte.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar el deporte " + seleccionado.getNombre() + "?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                deporteService.delete(seleccionado.getId());
                lblDepEstado.getStyleClass().setAll("lbl-ok");
                lblDepEstado.setText("Deporte eliminado.");
                cargarDeportes();
            }
        });
    }

    @FXML
    public void eliminarReserva() {
        Reserva seleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar la reserva de " + seleccionada.getUsuario().getNombre() + "?",
                ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                reservaService.delete(seleccionada.getId());
                tablaReservas.setItems(FXCollections.observableArrayList(reservaService.getAll()));
                lblTotalReservas.setText(String.valueOf(reservaService.getAll().size()));
            }
        });
    }
}