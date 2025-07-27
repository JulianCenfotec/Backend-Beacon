package cr.ac.ucenfotec.waddle.beacon.logic.entity.plan;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.gasto.*;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.ingreso.*;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.UserRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private IngresoRepository ingresoRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Plan save(Plan plan) {
    	User user = plan.getUsuario();
    	List <Gasto> gastos = plan.getGastos();
    	List <Ingreso> ingresos = plan.getIngresos();
    	
    	User savedUser = userRepository.findById(user.getId()).get();
    	
    	plan.setGastos(new ArrayList<Gasto>());
    	plan.setIngresos(new ArrayList<Ingreso>());
        Plan saved = planRepository.save(plan);

        if (gastos != null) {
        	List<Gasto> generados = new ArrayList<>();
            for (Gasto gasto : gastos) {
                gasto.setPlan(saved);
                Gasto nuevo = gastoRepository.save(gasto);
                generados.add(nuevo);
            }
            saved.setGastos(generados);
        }

        if (ingresos != null) {
        	List<Ingreso> generados = new ArrayList<>();
            for (Ingreso ingreso : ingresos) {
                ingreso.setPlan(saved);
                Ingreso nuevo = ingresoRepository.save(ingreso);
                generados.add(nuevo);
            }
            saved.setIngresos(generados);
        }
        
        saved.setUsuario(savedUser);
        return saved;
    }
    
    @Transactional
    public List<Plan> getFromUserId(Long id){
    	Optional<User> optional = userRepository.findById(id);
    	User user = optional.get();
    	
    	if (user == null) {
    		throw new ApplicationContextException("Usuario no encontrado.");
    	}
    	
    	List<Plan> planes = planRepository.FromUserId(user);
    	return planes; 
    }
    
    @Transactional
    public List<Plan> getFromSystem(){
    	List<Plan> planes = planRepository.FromSystem();
    	return planes; 
    }
    
    @Transactional
    public List<Plan> getFromShared(){
    	List<Plan> planes = planRepository.FromShared();
    	return planes;
    }
    
    @Transactional
    public Plan getFromSharedId(Long id){
    	return null;
    }

    @Transactional
	public Plan patchShared(Long plan_id) {
    	Optional<Plan> found = planRepository.findById(plan_id);
    	Plan plan = found.get();
    	
    	if (plan == null) {
    		throw new ApplicationContextException("Plan no encontrado.");
    	}
    	
    	plan.setCompartida(true);
		return planRepository.save(plan);
	}
}
