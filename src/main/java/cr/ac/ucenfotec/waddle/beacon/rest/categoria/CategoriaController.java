package cr.ac.ucenfotec.waddle.beacon.rest.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.categoria.Categoria;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.categoria.CategoriaRepository;

import java.util.List;

@RequestMapping("/categoria")
@RestController
public class CategoriaController {
    @Autowired
    private CategoriaRepository repository;
    
    @GetMapping
    @PreAuthorize("isAuthenticated()")
	public List<Categoria> getAllCategorias() {
		return repository.findAll();
	}
    
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
	public Categoria addCategoria(@RequestBody Categoria categoria) {
		return repository.save(categoria);
	}
    
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Categoria getCategoriaById(@PathVariable("id") Integer id) {
    	return repository.findById(id).orElseThrow(RuntimeException::new);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public Categoria updateCategoria(@PathVariable("id") Integer id, @RequestBody Categoria categoria) {
    	return repository.findById(id)
    			.map(existingCategoria -> {
    				existingCategoria.setNombre(categoria.getNombre());
    				existingCategoria.setDescripcion(categoria.getDescripcion());
    				return repository.save(existingCategoria);
    			})
    			.orElseGet(() -> {
    				categoria.setId((long) id);
    				return repository.save(categoria);
    			});
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
	public void deleteCategoria(@PathVariable("id") Integer id) {
		repository.deleteById(id);
	}
}