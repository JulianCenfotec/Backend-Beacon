package cr.ac.ucenfotec.waddle.beacon.rest.user;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.auth.PasswordResetService;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.UserRepository;

import java.security.Key;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetService passwordResetService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public List<User> getAllUsers() {
        return UserRepository.findAll();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserRepository.save(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return UserRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @GetMapping("/filterByName/{name}")
    public List<User> getUserById(@PathVariable String name) {
        return UserRepository.findUsersWithCharacterInName(name);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Optional<User> updateUser(@PathVariable Long id, @RequestBody User user) {

        Optional<User> ModUser = UserRepository.findById(id);

        return ModUser

                .map(existingUser -> {

                    String oldEmail = existingUser.getEmail();
                    String newEmail = user.getEmail();

                    if (user.getName()!=null){
                        existingUser.setName(user.getName());
                    }
                    if (user.getLastname()!=null){
                        existingUser.setLastname(user.getLastname());
                    }

                    if (newEmail != null && !newEmail.equals(oldEmail)) {
                        existingUser.setEmail(newEmail);
                        passwordResetService.sendEmailChangeNotification(oldEmail, newEmail);
                    }
                    if (user.getRole()!=null){
                        existingUser.setRole(user.getRole());
                    }
                    if (user.getDisplayname()!=null){
                        existingUser.setDisplayname(user.getDisplayname());
                    }

                    return UserRepository.save(existingUser);
                });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN_ROLE')")
    public void deleteUser(@PathVariable Long id) {
        UserRepository.deleteById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

}