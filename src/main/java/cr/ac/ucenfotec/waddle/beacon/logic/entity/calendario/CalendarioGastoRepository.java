package cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CalendarioGastoRepository extends JpaRepository<CalendarioGasto, Long> {
	@Query("SELECT c FROM CalendarioGasto c WHERE c.calendario = ?1")
	public List<CalendarioGasto> FromCalendarioId(Calendario calendario);
	
	// ? Delete relationship between Calendario and Gasto.
	@Modifying
    @Transactional
	@Query("DELETE FROM CalendarioGasto c WHERE c.id = ?1")
	public void DeleteRelationship(Long id);
}
