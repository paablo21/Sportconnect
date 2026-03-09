package org.example.sportconnect.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "pistas")
public class Pista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double precioHora;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deporte_id", nullable = false)
    private Deporte deporte;

    @OneToMany(mappedBy = "pista", cascade = CascadeType.ALL)
    private java.util.List<Reserva> reservas = new java.util.ArrayList<>();

    public Pista() {}

    public Pista(String nombre, Double precioHora, Deporte deporte) {
        this.nombre = nombre;
        this.precioHora = precioHora;
        this.deporte = deporte;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecioHora() { return precioHora; }
    public void setPrecioHora(Double precioHora) { this.precioHora = precioHora; }

    public Deporte getDeporte() { return deporte; }
    public void setDeporte(Deporte deporte) { this.deporte = deporte; }

    public List<Reserva> getReservas() { return reservas; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }

    @Override
    public String toString() { return nombre; }
}