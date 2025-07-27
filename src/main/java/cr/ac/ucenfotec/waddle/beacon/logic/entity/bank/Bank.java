package cr.ac.ucenfotec.waddle.beacon.logic.entity.bank;

import jakarta.persistence.*;

@Entity
@Table(name = "bank")

public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 4)
    private Float tasaAhorro;

    @Column(length = 4)
    private Float tasaUnificacion;

    @Column(length = 4)
    private Float tasaCredito;

    public Bank() {}

    public Bank(String nombre, Float tasaCredito, Float tasaUnificacion, Float tasaAhorro) {
        this.nombre = nombre;
        this.tasaAhorro = tasaAhorro;
        this.tasaUnificacion = tasaUnificacion;
        this.tasaCredito = tasaCredito;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getTasaAhorro() {
        return tasaAhorro;
    }

    public void setTasaAhorro(Float tasaAhorro) {
        this.tasaAhorro = tasaAhorro;
    }

    public Float getTasaUnificacion() {
        return tasaUnificacion;
    }

    public void setTasaUnificacion(Float tasaUnificacion) {
        this.tasaUnificacion = tasaUnificacion;
    }

    public Float getTasaCredito() {
        return tasaCredito;
    }

    public void setTasaCredito(Float tasaCredito) {
        this.tasaCredito = tasaCredito;
    }
}
