package cr.ac.ucenfotec.waddle.beacon.rest.plan;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.plan.Plan;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.plan.PlanService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanService planService;

    @GetMapping("/user/{id}")
    @PreAuthorize("isAuthenticated()")
    public List<Plan> getPlanUser(@PathVariable("id") Long user_id) {
        return planService.getFromUserId(user_id);
    }
    
    @GetMapping("/system")
    @PreAuthorize("isAuthenticated()")
    public List<Plan> getPlanSystem() {
        return planService.getFromSystem();
    }
    
    @GetMapping("/shared")
    @PreAuthorize("isAuthenticated()")
    public List<Plan> getPlanShared() {
        return planService.getFromShared();
    }
    
    @PatchMapping("/shared/{id}")
    @PreAuthorize("isAuthenticated()")
    public Plan patchPlanShared(@PathVariable("id") Long plan_id) {
        return planService.patchShared(plan_id);
    }
    
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Plan createPlan(@RequestBody Plan plan) {
        return planService.save(plan);
    }
}

