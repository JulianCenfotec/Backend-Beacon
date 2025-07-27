package cr.ac.ucenfotec.waddle.beacon.logic.entity.etiqueta;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "etiqueta")
public class Etiqueta {
    public enum Tipo {
        IMPREVISTO,
        INVERSION,
        SUELDO,
        AHORRO,
        DEUDA,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 7)
    private String color;

    @Column(nullable = false, length = 20)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo;

    @Column(nullable = false)
    private boolean sistema;

    public Etiqueta() {}

    public Etiqueta(String color, String nombre, String descripcion, Tipo tipo, boolean sistema) {
        this.color = color;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.sistema = sistema;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    public boolean isSistema() {
        return sistema;
    }

    public void setSistema(boolean sistema) {
        this.sistema = sistema;
    }
}
