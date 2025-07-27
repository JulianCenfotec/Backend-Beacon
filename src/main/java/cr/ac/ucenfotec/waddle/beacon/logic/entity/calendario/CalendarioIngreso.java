package cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.ingreso.Ingreso;





import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OnDelete;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
@Table(name = "calendario_ingreso")
public class CalendarioIngreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "calendario_id", referencedColumnName = "id")
    private Calendario calendario;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ingreso_id", referencedColumnName = "id")
    private Ingreso ingreso;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public Ingreso getIngreso() {
        return ingreso;
    }

    public void setIngreso(Ingreso ingreso) {
        this.ingreso = ingreso;
    }
    
	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
}
