package cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarioPlanRepository extends JpaRepository<CalendarioPlan, Long> {
	@Query("SELECT c FROM CalendarioPlan c WHERE c.calendario = ?1")
	public List<CalendarioPlan> FromCalendarioId(Calendario calendario);
}