package cr.ac.ucenfotec.waddle.beacon.logic.entity.etiqueta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long> {
	@Query("SELECT e FROM Etiqueta e WHERE e.tipo = ?1 AND e.sistema = true")
	List<Etiqueta> getEtiquetaSistemaPorTipo(Etiqueta.Tipo tipo);
}
