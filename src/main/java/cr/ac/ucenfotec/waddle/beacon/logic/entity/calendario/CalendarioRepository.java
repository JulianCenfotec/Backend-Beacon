package cr.ac.ucenfotec.waddle.beacon.logic.entity.calendario;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, Long> {
	@Query("SELECT c FROM Calendario c WHERE c.usuario = ?1")
	public List<Calendario> FromUserId(User user);
}
