package cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.gasto.Gasto;

import java.util.Date;


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

@Entity
@Table(name = "calendario_gasto")
public class CalendarioGasto {
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
    @JoinColumn(name = "gasto_id", referencedColumnName = "id")
    private Gasto gasto;

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

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }
    
	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
}
