package cr.ac.ucenfotec.waddle.beacon.logic.entity.subscriptionPlan;

import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;

@Repository
@DependsOn("userSubscription")
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Integer> {
	@Query("SELECT us FROM UserSubscription us WHERE us.user = ?1")
    List<UserSubscription> getPastSuscriptions(User id);
}
