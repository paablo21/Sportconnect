/*package org.example.sportconnect.util;

import org.example.sportconnect.dao.DeporteDAO;
import org.example.sportconnect.dao.PistaDAO;
import org.example.sportconnect.model.Deporte;
import org.example.sportconnect.model.Pista;
import org.example.sportconnect.model.Usuario;

import java.util.List;


public class DataInitializer {

    public static void initIfEmpty() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        DeporteDAO deporteDAO = new DeporteDAO();
        PistaDAO pistaDAO = new PistaDAO();

        // Solo insertar si no hay usuarios
        if (usuarioDAO.getAll().isEmpty()) {
            System.out.println("Base de datos vacía. Insertando datos iniciales...");

            // Usuarios
            usuarioDAO.save(new Usuario("Admin", "SportConnect", "admin@sport.com", "admin123", "ADMIN"));
            usuarioDAO.save(new Usuario("Juan", "García López", "juan@sport.com", "1234", "SOCIO"));
            usuarioDAO.save(new Usuario("María", "Martínez Ruiz", "maria@sport.com", "1234", "SOCIO"));

            // Deportes
            Deporte padel = new Deporte("Pádel");
            Deporte tenis = new Deporte("Tenis");
            Deporte futbol = new Deporte("Fútbol");
            deporteDAO.save(padel);
            deporteDAO.save(tenis);
            deporteDAO.save(futbol);

            // Recargamos para tener los IDs
            List<Deporte> deportes = deporteDAO.getAll();
            Deporte dPadel = deportes.stream().filter(d -> d.getNombre().equals("Pádel")).findFirst().orElse(padel);
            Deporte dTenis = deportes.stream().filter(d -> d.getNombre().equals("Tenis")).findFirst().orElse(tenis);
            Deporte dFutbol = deportes.stream().filter(d -> d.getNombre().equals("Fútbol")).findFirst().orElse(futbol);

            // Pistas
            pistaDAO.save(new Pista("Pista Pádel 1", 12.0, dPadel));
            pistaDAO.save(new Pista("Pista Pádel 2", 12.0, dPadel));
            pistaDAO.save(new Pista("Pista Tenis Central", 15.0, dTenis));
            pistaDAO.save(new Pista("Campo Fútbol 7", 40.0, dFutbol));
            pistaDAO.save(new Pista("Campo Fútbol 11", 60.0, dFutbol));

            System.out.println("Datos iniciales insertados correctamente.");
            System.out.println("Usuario admin: admin@sport.com / admin123");
            System.out.println("Usuario socio: juan@sport.com / 1234");
        }
    }
}
*/