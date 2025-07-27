package cr.ac.ucenfotec.waddle.beacon.logic.entity.gasto;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.etiqueta.Etiqueta;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.plan.Plan;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "gasto")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "plan", nullable = true)
    private Plan plan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "etiqueta", nullable = true)
    private Etiqueta etiqueta;

    @Column(name = "deuda", nullable = true)
    private Long deuda;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false)
    private float monto;

    public Gasto() {}

    public Gasto(Plan plan, Etiqueta etiqueta, Long deuda, String nombre, float monto) {
        this.plan = plan;
        this.etiqueta = etiqueta;
        this.deuda = deuda;
        this.nombre = nombre;
        this.monto = monto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Etiqueta getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(Etiqueta etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Long getDeuda() {
        return deuda;
    }

    public void setDeuda(Long deuda) {
        this.deuda = deuda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
}
