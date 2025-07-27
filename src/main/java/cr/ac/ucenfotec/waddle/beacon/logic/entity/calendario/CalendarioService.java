package cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario.Calendario;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.gasto.Gasto;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.gasto.GastoRepository;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.ingreso.Ingreso;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.ingreso.IngresoRepository;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.plan.Plan;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.plan.PlanRepository;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.UserRepository;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalendarioService {
    @Autowired
    private CalendarioRepository repository;
    
    @Autowired
    private CalendarioPlanRepository repositoryWithPlan;
    
    @Autowired
    private CalendarioGastoRepository repositoryWithGastoImprevisto;
    
    @Autowired
    private CalendarioIngresoRepository repositoryWithIngresoImprevisto;

    @Autowired
    private UserRepository repositoryFromUser;
    
    @Autowired
    private PlanRepository repositoryFromPlan;
    
    @Autowired
    private GastoRepository repositoryFromGasto;
    
    @Autowired
    private IngresoRepository repositoryFromIngreso;
    
	public void postCalendario(Long userId, Calendario fromCalendar) {
		User usuario = repositoryFromUser.findById(userId).get();
		if (usuario == null) {
			throw new ApplicationContextException("Usuario no encontrado.");
		}
		
		String nombre = fromCalendar.getNombre();
		Calendario calendario = new Calendario();
		calendario.setUsuario(usuario);
		calendario.setNombre(nombre);
		repository.save(calendario);
	}

    public List<Calendario> getFromUserId(Long id) {
    	User usuario = repositoryFromUser.findById(id).get();
    	if (usuario == null) {
    		throw new ApplicationContextException("Usuario no encontrado.");
    	}
    	List<Calendario> calendarios = repository.FromUserId(usuario);
    	
		for (Calendario calendario : calendarios) {
			List<CalendarioPlan> calendarioPlans = repositoryWithPlan.FromCalendarioId(calendario);
			calendario.setCalendarioPlans(calendarioPlans);
			
			List<CalendarioGasto> calendarioGastosImprevistos = repositoryWithGastoImprevisto.FromCalendarioId(calendario);
			calendario.setCalendarioGastosImprevistos(calendarioGastosImprevistos);
			
			List<CalendarioIngreso> calendarioIngresosImprevistos = repositoryWithIngresoImprevisto.FromCalendarioId(calendario);
			calendario.setCalendarioIngresosImprevistos(calendarioIngresosImprevistos);
		}
		
    	return calendarios;
    }

    public CalendarioPlan postPlanToCalendar(CalendarioPlan intermediate) {
    	Calendario fromCalendar = intermediate.getCalendario();
    	Plan fromPlan = intermediate.getPlan();
    	
    	Calendario calendario = repository.findById(fromCalendar.getId()).get();
    	Plan plan = repositoryFromPlan.findById(fromPlan.getId()).get();
    	
		if (calendario == null) {
			throw new ApplicationContextException("Calendario no encontrado.");
		}
		
		if (plan == null) {
			throw new ApplicationContextException("Plan no encontrado.");
		}
		
		intermediate.setCalendario(calendario);
		intermediate.setPlan(plan);
		
		CalendarioPlan created = repositoryWithPlan.save(intermediate);
		return created;
	}
    
    
	public CalendarioGasto postGastoImprevistoToCalendar(CalendarioGasto intermediate) {
    	Calendario fromCalendar = intermediate.getCalendario();
    	
    	Calendario calendario = repository.findById(fromCalendar.getId()).get();
    	if (calendario == null) {
    		throw new ApplicationContextException("Calendario no encontrado.");
    	}
    	
    	Gasto fromGasto = intermediate.getGasto();
    	fromGasto.setEtiqueta(null);
    	fromGasto.setPlan(null);
    	fromGasto.setId(null);
    	
    	intermediate.setCalendario(calendario);
    	intermediate.setGasto(fromGasto);
    	intermediate.setId(null);
    	
    	CalendarioGasto created = repositoryWithGastoImprevisto.save(intermediate);
    	return created;
    }
    
	public CalendarioIngreso postIngresoImprevistoToCalendar(CalendarioIngreso intermediate) {
		Calendario fromCalendar = intermediate.getCalendario();

		Calendario calendario = repository.findById(fromCalendar.getId()).get();
		if (calendario == null) {
			throw new ApplicationContextException("Calendario no encontrado.");
		}

		Ingreso fromIngreso = intermediate.getIngreso();
		fromIngreso.setEtiqueta(null);
		fromIngreso.setPlan(null);
		fromIngreso.setId(null);

		intermediate.setCalendario(calendario);
		intermediate.setIngreso(fromIngreso);
		intermediate.setId(null);

		CalendarioIngreso created = repositoryWithIngresoImprevisto.save(intermediate);
		return created;
	}
    
    

    public void patchPlanMove(CalendarioPlan intermediate) {
    	CalendarioPlan fromIntermediate = repositoryWithPlan.findById(intermediate.getId()).get();
    	if (fromIntermediate == null) {
    		throw new ApplicationContextException("CalendarioPlan no encontrado.");
    	}
    	
    	Calendario fromCalendar = intermediate.getCalendario();
    	Plan fromPlan = intermediate.getPlan();
    	
    	Calendario calendario = repository.findById(fromCalendar.getId()).get();
    	Plan plan = repositoryFromPlan.findById(fromPlan.getId()).get();
    	
		if (calendario == null) {
			throw new ApplicationContextException("Calendario no encontrado.");
		}
		
		if (plan == null) {
			throw new ApplicationContextException("Plan no encontrado.");
		}
		
		fromIntermediate.setFechaInicio(intermediate.getFechaInicio());
		fromIntermediate.setCalendario(calendario);
		fromIntermediate.setPlan(plan);		
    	
    	repositoryWithPlan.save(fromIntermediate);
    }
    
	public void patchGastoMove(CalendarioGasto intermediate) {
    	CalendarioGasto fromIntermediate = repositoryWithGastoImprevisto.findById(intermediate.getId()).get();
    	if (fromIntermediate == null) {
    		throw new ApplicationContextException("CalendarioGasto no encontrado.");
    	}
    	
    	Calendario fromCalendar = intermediate.getCalendario();
    	Gasto fromGasto = intermediate.getGasto();
    	
    	Calendario calendario = repository.findById(fromCalendar.getId()).get();
    	Gasto gasto = repositoryFromGasto.findById(fromGasto.getId()).get();
    	
		if (calendario == null) {
			throw new ApplicationContextException("Calendario no encontrado.");
		}
		
		if (gasto == null) {
			throw new ApplicationContextException("Gasto no encontrado.");
		}
		
		fromIntermediate.setFechaInicio(intermediate.getFechaInicio());
		fromIntermediate.setCalendario(calendario);
		fromIntermediate.setGasto(gasto);

		repositoryWithGastoImprevisto.save(fromIntermediate);
	}

	public void patchIngresoMove(CalendarioIngreso intermediate) {
		CalendarioIngreso fromIntermediate = repositoryWithIngresoImprevisto.findById(intermediate.getId()).get();
		if (fromIntermediate == null) {
			throw new ApplicationContextException("CalendarioIngreso no encontrado.");
		}
		
		Calendario fromCalendar = intermediate.getCalendario();
		Ingreso fromIngreso = intermediate.getIngreso();
		
		Calendario calendario = repository.findById(fromCalendar.getId()).get();
		Ingreso ingreso = repositoryFromIngreso.findById(fromIngreso.getId()).get();
		
		if (calendario == null) {
			throw new ApplicationContextException("Calendario no encontrado.");
		}
		
		if (ingreso == null) {
			throw new ApplicationContextException("Ingreso no encontrado.");
		}
		
		fromIntermediate.setFechaInicio(intermediate.getFechaInicio());
		fromIntermediate.setCalendario(calendario);
		fromIntermediate.setIngreso(ingreso);
		
		repositoryWithIngresoImprevisto.save(fromIntermediate);
	}

    public void deleteCalendarioPlanById(Long id) {
        if (!repositoryWithPlan.existsById(id)) {
            throw new ApplicationContextException("CalendarioPlan no encontrado.");
        }
        
        Optional<CalendarioPlan> persistence = repositoryWithPlan.findById(id);
        CalendarioPlan intermediate = persistence.get();
        repositoryWithPlan.delete(intermediate);
    }
    
	public void deleteCalendarioGastoById(Long id) {
		if (!repositoryWithGastoImprevisto.existsById(id)) {
			throw new ApplicationContextException("CalendarioGasto no encontrado.");
		}
		
		Optional<CalendarioGasto> persistence = repositoryWithGastoImprevisto.findById(id);
		CalendarioGasto intermediate = persistence.get();
		intermediate.setCalendario(null);
	    intermediate.setGasto(null);

		repositoryWithGastoImprevisto.DeleteRelationship(id);
	}
	
	public void deleteCalendarioIngresoById(Long id) {
		if (!repositoryWithIngresoImprevisto.existsById(id)) {
			throw new ApplicationContextException("CalendarioIngreso no encontrado.");
		}
		
		Optional<CalendarioIngreso> persistence = repositoryWithIngresoImprevisto.findById(id);
		CalendarioIngreso intermediate = persistence.get();
		intermediate.setCalendario(null);
	    intermediate.setIngreso(null);

		repositoryWithIngresoImprevisto.DeleteRelationship(id);
	}

    public void deleteCalendarioById(Long id) {
        if (!repository.existsById(id)) {
            throw new ApplicationContextException("Calendario no encontrado.");
        }
        repository.deleteById(id);
    }
}