package cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CalendarioIngresoRepository extends JpaRepository<CalendarioIngreso, Long> {
	@Query("SELECT c FROM CalendarioIngreso c WHERE c.calendario = ?1")
	public List<CalendarioIngreso> FromCalendarioId(Calendario calendario);
	
	// ? Delete relationship between Calendario and Ingreso.
	@Modifying
    @Transactional
	@Query("DELETE FROM CalendarioIngreso c WHERE c.id = ?1")
	public void DeleteRelationship(Long id);
}
