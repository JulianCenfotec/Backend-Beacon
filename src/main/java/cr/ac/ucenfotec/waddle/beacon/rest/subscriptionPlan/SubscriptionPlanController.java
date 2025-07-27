package cr.ac.ucenfotec.waddle.beacon.rest.subscriptionPlan;


import cr.ac.ucenfotec.waddle.beacon.logic.entity.subscriptionPlan.SubscriptionPlan;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.subscriptionPlan.SubscriptionPlanRepository;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.subscriptionPlan.UserSubscription;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.subscriptionPlan.UserSubscriptionRepository;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.subscriptionPlan.UserSubscriptionStateEnum;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RequestMapping("/subscriptionPlan")
@RestController
public class SubscriptionPlanController {
    @Autowired
    private SubscriptionPlanRepository repository;
    
    @Autowired
    private UserSubscriptionRepository userSuscriptionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    @PreAuthorize("isAuthenticated()")
	public List<SubscriptionPlan> getAllSubscriptionPlans() {
		return repository.findAll();
	}
    
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
	public SubscriptionPlan addSubscriptionPlan(@RequestBody SubscriptionPlan subscriptionPlan) {
		return repository.save(subscriptionPlan);
	}
    
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public SubscriptionPlan getSubscriptionPlanById(@PathVariable("id") Integer id) {
    	return repository.findById(id).orElseThrow(RuntimeException::new);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public SubscriptionPlan updateSubscriptionPlan(@PathVariable("id") Integer id, @RequestBody SubscriptionPlan subscriptionPlan) {
    	return repository.findById(id)
    			.map(existingSubscriptionPlan -> {
					existingSubscriptionPlan.setTitulo(subscriptionPlan.getTitulo());
					existingSubscriptionPlan.setDescripcion(subscriptionPlan.getDescripcion());
					existingSubscriptionPlan.setPrecio(subscriptionPlan.getPrecio());
					existingSubscriptionPlan.setPlazo(subscriptionPlan.getPlazo());
    				return repository.save(existingSubscriptionPlan);
    			})
    			.orElseGet(() -> {
					subscriptionPlan.setId((long) id);
    				return repository.save(subscriptionPlan);
    			});
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
	public void deleteSubscriptionPlan(@PathVariable("id") Integer id) {
		repository.deleteById(id);
	}
    
    @GetMapping("/user/{user_id}")
    @PreAuthorize("isAuthenticated()")
    public List<UserSubscription> getOwnSubscriptions(@PathVariable("user_id") Integer user_id){
    	Optional<User> user = userRepository.findById((long) user_id);
    	if (user.get() == null) {
    		return new ArrayList<UserSubscription>();
    	}
    	
    	return userSuscriptionRepository.getPastSuscriptions(user.get());
    }
    
    @PostMapping("/user/{user_id}/subscription/{subscription_id}")
    @PreAuthorize("isAuthenticated()")
    public UserSubscription postNewSubscription(@PathVariable("user_id") Integer user_id, @PathVariable("subscription_id") Integer subscription_id){
    	Optional<User> user = userRepository.findById((long) user_id);
    	if (user.get() == null) {
    		throw new ApplicationContextException("El usuario no se encontró.");
    	}
    	
    	Optional<SubscriptionPlan> subscription = repository.findById(subscription_id);
    	if (subscription.get() == null) {
    		throw new ApplicationContextException("El suscripción no se encontró.");
    	}
    	
    	UserSubscription userSubscription = new UserSubscription(
    			subscription.get(),
    			user.get(),
    			UserSubscriptionStateEnum.CONFIRMATION
    	);
    	
    	return userSuscriptionRepository.save(userSubscription);
    }
    
    @PatchMapping("/paypal/{usubscription_id}/approved")
    @PreAuthorize("isAuthenticated()")
    public UserSubscription patchApprovedSubscription(@PathVariable("usubscription_id") Integer usub_id, @RequestBody UserSubscription userSubscription){
    	userSubscription.setId((long) usub_id);
    	userSubscription.setEstado(UserSubscriptionStateEnum.APPROVED);
    	return userSuscriptionRepository.save(userSubscription);
    }
    
    @PatchMapping("/paypal/{usubscription_id}/canceled")
    @PreAuthorize("isAuthenticated()")
    public UserSubscription patchCanceledSubscription(@PathVariable("usubscription_id") Integer usub_id, @RequestBody UserSubscription userSubscription){
    	userSubscription.setId((long) usub_id);
    	userSubscription.setEstado(UserSubscriptionStateEnum.CANCELED);
    	
    	Calendar cal = Calendar.getInstance();
    	userSubscription.setEndOfSuscriptionAt(cal.getTime());
    	
    	return userSuscriptionRepository.save(userSubscription);
    }
    
    @PatchMapping("/paypal/{usubscription_id}/error")
    @PreAuthorize("isAuthenticated()")
    public UserSubscription patchInErrorSubscription(@PathVariable("usubscription_id") Integer usub_id, @RequestBody UserSubscription userSubscription){
    	userSubscription.setId((long) usub_id);
    	userSubscription.setEstado(UserSubscriptionStateEnum.INERROR);
    	
    	Calendar cal = Calendar.getInstance();
    	userSubscription.setEndOfSuscriptionAt(cal.getTime());
    	
    	return userSuscriptionRepository.save(userSubscription);
    }
}