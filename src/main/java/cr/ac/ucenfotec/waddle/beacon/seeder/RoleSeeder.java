package cr.ac.ucenfotec.waddle.beacon.seeder;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.Role;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.RoleEnum;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.RoleRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    	this.loadRoles();
    }

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] { RoleEnum.SUPER_ADMIN_ROLE, RoleEnum.USER,};
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.SUPER_ADMIN_ROLE, "Super Administrator Role",
                RoleEnum.USER, "Default user role"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();

                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(roleToCreate);
            });
        });
    }
}
