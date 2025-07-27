package cr.ac.ucenfotec.waddle.beacon.logic.entity.plan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
	@Query("SELECT p FROM Plan p WHERE p.usuario = ?1")
	public List<Plan> FromUserId(User user);
	
	@Query("SELECT p FROM Plan p WHERE p.sistema = true")
	public List<Plan> FromSystem();
	
	@Query("SELECT p FROM Plan p WHERE p.compartida = true")
	public List<Plan> FromShared();
}
