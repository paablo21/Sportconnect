package org.example.sportconnect.service;

import org.example.sportconnect.dao.PistaDAO;
import org.example.sportconnect.model.Pista;
import java.util.List;

public class PistaService {

    private final PistaDAO pistaDAO = new PistaDAO();

    public void save(Pista pista) {
        if (pista.getNombre() == null || pista.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio.");
        if (pista.getDeporte() == null)
            throw new IllegalArgumentException("El deporte es obligatorio.");
        pistaDAO.save(pista);
    }

    public void delete(Long id) {
        pistaDAO.delete(id);
    }

    public List<Pista> getAll() {
        return pistaDAO.getAll();
    }
}