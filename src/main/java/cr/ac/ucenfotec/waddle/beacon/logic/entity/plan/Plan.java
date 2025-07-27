package cr.ac.ucenfotec.waddle.beacon.logic.entity.plan;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.gasto.Gasto;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.ingreso.Ingreso;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "plan")
public class Plan {
    public enum Periodo {
        SEMANAL,
        QUINCENAL,
        MENSUAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "plan_anterior_id", nullable = true)
    private Plan anterior;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Periodo periodo;

    @Column(name = "saldo_final", nullable = false)
    private float saldo;

    @Column(nullable = false)
    private float monto;

    @Column(nullable = false)
    private boolean recurrente;
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean sistema;
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean compartida;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Gasto> gastos;

    @JsonManagedReference
    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Ingreso> ingresos;

    public Plan() {}

    public Plan(User usuario, Plan anterior, String titulo, String descripcion, Periodo periodo, float saldo, float monto, boolean recurrente, boolean sistema, boolean compartida, Date createdAt, Date updatedAt, List<Gasto> gastos, List<Ingreso> ingresos) {
        this.usuario = usuario;
        this.anterior = anterior;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.periodo = periodo;
        this.saldo = saldo;
        this.monto = monto;
        this.recurrente = recurrente;
        this.sistema = sistema;
        this.compartida = compartida;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.gastos = gastos;
        this.ingresos = ingresos;
    }
    
    public boolean isSistema() {
        return sistema;
    }

    public void setSistema(boolean sistema) {
        this.sistema = sistema;
    }
    
    public boolean isCompartida() {
        return compartida;
    }

    public void setCompartida(boolean compartida) {
        this.compartida = compartida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Plan getAnterior() {
        return anterior;
    }

    public void setAnterior(Plan anterior) {
        this.anterior = anterior;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public boolean isRecurrente() {
        return recurrente;
    }

    public void setRecurrente(boolean recurrente) {
        this.recurrente = recurrente;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }
}
