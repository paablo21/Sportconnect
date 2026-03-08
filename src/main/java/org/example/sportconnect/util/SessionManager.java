package org.example.sportconnect.util;

import org.example.sportconnect.model.Usuario;

public class SessionManager {

    private static Usuario usuarioActual;

    public static void login(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static void logout() {
        usuarioActual = null;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static boolean isAdmin() {
        return usuarioActual != null && "ADMIN".equals(usuarioActual.getRol());
    }
}