package cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "calendario")
public class Calendario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @JsonManagedReference
    @OneToMany(mappedBy = "calendario", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CalendarioPlan> calendarioPlans;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "calendario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CalendarioGasto> calendarioGastosImprevistos;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "calendario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CalendarioIngreso> calendarioIngresosImprevistos;

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

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public List<CalendarioPlan> getCalendarioPlans() {
        return calendarioPlans;
    }

    public void setCalendarioPlans(List<CalendarioPlan> calendarioPlans) {
        this.calendarioPlans = calendarioPlans;
    }
    
	public List<CalendarioGasto> getCalendarioGastosImprevistos() {
		return calendarioGastosImprevistos;
	}
	
	public void setCalendarioGastosImprevistos(List<CalendarioGasto> calendarioGastosImprevistos) {
		this.calendarioGastosImprevistos = calendarioGastosImprevistos;
	}
	
	public List<CalendarioIngreso> getCalendarioIngresosImprevistos() {
		return calendarioIngresosImprevistos;
	}
	
	public void setCalendarioIngresosImprevistos(List<CalendarioIngreso> calendarioIngresosImprevistos) {
		this.calendarioIngresosImprevistos = calendarioIngresosImprevistos;
	}
}
