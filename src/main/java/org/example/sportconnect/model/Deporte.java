package org.example.sportconnect.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "deportes")
public class Deporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "deporte", fetch = FetchType.LAZY)
    private List<Pista> pistas = new ArrayList<>();

    public Deporte() {}

    public Deporte(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Pista> getPistas() { return pistas; }
    public void setPistas(List<Pista> pistas) { this.pistas = pistas; }

    @Override
    public String toString() { return nombre; }
}