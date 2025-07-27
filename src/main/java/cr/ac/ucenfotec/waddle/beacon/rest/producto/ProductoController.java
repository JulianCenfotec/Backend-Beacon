package cr.ac.ucenfotec.waddle.beacon.rest.producto;

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

import cr.ac.ucenfotec.waddle.beacon.logic.entity.producto.Producto;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.producto.ProductoRepository;

import java.util.List;

@RequestMapping("/producto")
@RestController
public class ProductoController {
    @Autowired
    private ProductoRepository repository;
    
    @GetMapping
    @PreAuthorize("isAuthenticated()")
	public List<Producto> getAllProductos() {
		return repository.findAll();
	}
    
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
	public Producto addProducto(@RequestBody Producto producto) {
		return repository.save(producto);
	}
    
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Producto getProductoById(@PathVariable("id") Integer id) {
    	return repository.findById(id).orElseThrow(RuntimeException::new);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public Producto updateProducto(@PathVariable("id") Integer id, @RequestBody Producto producto) {
    	return repository.findById(id)
    			.map(existingProducto -> {
    				existingProducto.setNombre(producto.getNombre());
    				existingProducto.setPrecio(producto.getPrecio());
    				existingProducto.setCategoria(producto.getCategoria());
    				existingProducto.setDescripcion(producto.getDescripcion());
    				existingProducto.setCantidadEnStock(producto.getCantidadEnStock());
    				return repository.save(existingProducto);
    			})
    			.orElseGet(() -> {
    				producto.setId((long) id);
    				return repository.save(producto);
    			});
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
	public void deleteProducto(@PathVariable("id") Integer id) {
		repository.deleteById(id);
	}
}