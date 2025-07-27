package cr.ac.ucenfotec.waddle.beacon.rest.calendario;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calendario")
public class CalendarioController {
    @Autowired
    private CalendarioService calendarioService;

    @GetMapping("/user/{id}")
    @PreAuthorize("isAuthenticated()")
    public List<Calendario> getCalendariosByUserId(@PathVariable("id") Long userId) {
        return calendarioService.getFromUserId(userId);
    }
    
    @PostMapping("/user/{id}")
    @PreAuthorize("isAuthenticated()")
	public void postCalendario(@PathVariable("id") Long userId, @RequestBody Calendario fromCalendar) {
		calendarioService.postCalendario(userId, fromCalendar);
	}

    @PostMapping("/plan")
    @PreAuthorize("isAuthenticated()")
    public CalendarioPlan postPlanToCalendar(@RequestBody CalendarioPlan calendarioPlan) {
        return calendarioService.postPlanToCalendar(calendarioPlan);
    }

    @PatchMapping("/plan")
    @PreAuthorize("isAuthenticated()")
    public void patchPlanMove(@RequestBody CalendarioPlan calendarioPlan) {
        calendarioService.patchPlanMove(calendarioPlan);
    }
    
    @DeleteMapping("/plan/{id}")
    @PreAuthorize("isAuthenticated()")
    public void deleteCalendarioPlan(@PathVariable("id") Long id) {
        calendarioService.deleteCalendarioPlanById(id);
    }
    
    @PostMapping("/gasto")
    @PreAuthorize("isAuthenticated()")
	public CalendarioGasto postGastoToCalendar(@RequestBody CalendarioGasto calendarioGasto) {
		return calendarioService.postGastoImprevistoToCalendar(calendarioGasto);
	}
    
    @PatchMapping("/gasto")
    @PreAuthorize("isAuthenticated()")
	public void patchGastoMove(@RequestBody CalendarioGasto calendarioGasto) {
		calendarioService.patchGastoMove(calendarioGasto);
	}
    
    @DeleteMapping("/gasto/{id}")
    @PreAuthorize("isAuthenticated()")
	public void deleteCalendarioGasto(@PathVariable("id") Long id) {
		calendarioService.deleteCalendarioGastoById(id);
	}
    
    @PostMapping("/ingreso")
    @PreAuthorize("isAuthenticated()")
    public CalendarioIngreso postIngresoToCalendar(@RequestBody CalendarioIngreso calendarioIngreso) {
    	return calendarioService.postIngresoImprevistoToCalendar(calendarioIngreso);
    }
    
    @PatchMapping("/ingreso")
    @PreAuthorize("isAuthenticated()")
	public void patchIngresoMove(@RequestBody CalendarioIngreso calendarioIngreso) {
		calendarioService.patchIngresoMove(calendarioIngreso);
	}
    
    @DeleteMapping("/ingreso/{id}")
    @PreAuthorize("isAuthenticated()")
    public void deleteCalendarioIngreso(@PathVariable("id") Long id) {
    	calendarioService.deleteCalendarioIngresoById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public void deleteCalendario(@PathVariable("id") Long id) {
        calendarioService.deleteCalendarioById(id);
    }
}
