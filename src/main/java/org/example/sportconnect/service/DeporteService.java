package org.example.sportconnect.service;

import org.example.sportconnect.dao.DeporteDAO;
import org.example.sportconnect.model.Deporte;
import java.util.List;

public class DeporteService {

    private final DeporteDAO deporteDAO = new DeporteDAO();

    public void save(Deporte deporte) {
        if (deporte.getNombre() == null || deporte.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio.");
        deporteDAO.save(deporte);
    }

    public List<Deporte> getAll() {
        return deporteDAO.getAll();
    }
    public void delete(Long id) {
        deporteDAO.delete(id);
    }
}