package cr.ac.ucenfotec.waddle.beacon.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.Role;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.RoleEnum;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.RoleRepository;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.UserRepository;

import java.util.Optional;

@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN_ROLE')")
    public User createAdministrator(@RequestBody User newAdminUser) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN_ROLE);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new User();
        user.setName(newAdminUser.getName());
        user.setEmail(newAdminUser.getEmail());
        user.setPassword(passwordEncoder.encode(newAdminUser.getPassword()));
        user.setRole(optionalRole.get());

        return userRepository.save(user);
    }
}
