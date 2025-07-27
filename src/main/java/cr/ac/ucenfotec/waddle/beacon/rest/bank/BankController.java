package cr.ac.ucenfotec.waddle.beacon.rest.bank;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.bank.Bank;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.bank.BankRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bank")
@RestController
public class BankController {
    @Autowired
    private BankRepository repository;
    
    @GetMapping
    @PreAuthorize("isAuthenticated()")
	public List<Bank> getAllBanks() {
		return repository.findAll();
	}
    
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
	public Bank addBank(@RequestBody Bank bank) {
		return repository.save(bank);
	}
    
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Bank getBankById(@PathVariable("id") Integer id) {
    	return repository.findById(id).orElseThrow(RuntimeException::new);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public Bank updateBank(@PathVariable("id") Integer id, @RequestBody Bank bank) {
    	return repository.findById(id)
    			.map(existingBank -> {
					existingBank.setNombre(bank.getNombre());
					existingBank.setTasaAhorro(bank.getTasaAhorro());
					existingBank.setTasaUnificacion(bank.getTasaUnificacion());
					existingBank.setTasaCredito(bank.getTasaCredito());
    				return repository.save(existingBank);
    			})
    			.orElseGet(() -> {
					bank.setId((long) id);
    				return repository.save(bank);
    			});
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
	public void deleteBank(@PathVariable("id") Integer id) {
		repository.deleteById(id);
	}
}