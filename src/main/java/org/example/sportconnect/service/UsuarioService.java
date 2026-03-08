package org.example.sportconnect.service;

import org.example.sportconnect.dao.UsuarioDAO;
import org.example.sportconnect.model.Usuario;
import java.util.Optional;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Optional<Usuario> login(String email, String password) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("El email no puede estar vacío.");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        return usuarioDAO.findByEmailAndPassword(email, password);
    }

    public void save(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio.");
        usuarioDAO.save(usuario);
    }

    public java.util.List<Usuario> getAll() {
        return usuarioDAO.getAll();
    }

    public void delete(Long id) {
        usuarioDAO.delete(id);
    }
}