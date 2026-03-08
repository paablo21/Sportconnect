package org.example.sportconnect.service;

import org.example.sportconnect.dao.ReservaDAO;
import org.example.sportconnect.model.Reserva;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservaService {

    private final ReservaDAO reservaDAO = new ReservaDAO();

    public void save(Reserva reserva) {
        if (reserva.getFecha().isBefore(LocalDate.now()))
            throw new IllegalArgumentException("No puedes reservar en fechas pasadas.");
        if (!reservaDAO.isDisponible(reserva.getPista().getId(), reserva.getFecha(), reserva.getHoraInicio(), reserva.getHoraFin()))
            throw new IllegalStateException("Esa franja horaria ya está ocupada.");
        reservaDAO.save(reserva);
    }

    public void delete(Long id) {
        reservaDAO.delete(id);
    }

    public List<Reserva> getByUsuario(Long usuarioId) {
        return reservaDAO.getByUsuario(usuarioId);
    }

    public List<Reserva> getAll() {
        return reservaDAO.getAll();
    }

    public boolean isOcupada(Long pistaId, LocalDate fecha, LocalTime hora) {
        return !reservaDAO.isDisponible(pistaId, fecha, hora, hora.plusHours(1));
    }
}