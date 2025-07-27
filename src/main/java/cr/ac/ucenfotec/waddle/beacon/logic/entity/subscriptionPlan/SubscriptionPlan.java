package cr.ac.ucenfotec.waddle.beacon.logic.entity.subscriptionPlan;
import jakarta.persistence.*;

@Entity
@Table(name = "subscriptionPlan")

public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 255)
    private String descripcion;

    @Column(length = 6)
    private Float precio;

    @Column(length = 3)
    private Integer plazo;

    public SubscriptionPlan() {}

    public SubscriptionPlan(String titulo, String descripcion, Float precio, Integer plazo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.plazo = plazo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }
}
