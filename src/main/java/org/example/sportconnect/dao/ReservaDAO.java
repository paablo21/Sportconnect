package org.example.sportconnect.dao;

import org.example.sportconnect.model.Reserva;
import org.example.sportconnect.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservaDAO {

    public void save(Reserva reserva) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(reserva);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Reserva reserva = session.get(Reserva.class, id);
            if (reserva != null) session.remove(reserva);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Reserva> getByUsuario(Long usuarioId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Reserva r WHERE r.usuario.id = :id ORDER BY r.fecha DESC",
                    Reserva.class
            ).setParameter("id", usuarioId).list();
        }
    }

    public boolean isDisponible(Long pistaId, LocalDate fecha, LocalTime inicio, LocalTime fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(r) FROM Reserva r WHERE r.pista.id = :pistaId " +
                                    "AND r.fecha = :fecha AND r.horaInicio < :fin AND r.horaFin > :inicio",
                            Long.class
                    ).setParameter("pistaId", pistaId)
                    .setParameter("fecha", fecha)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .getSingleResult();
            return count == 0;
        }
    }

    public List<Reserva> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Reserva", Reserva.class).list();
        }
    }
}