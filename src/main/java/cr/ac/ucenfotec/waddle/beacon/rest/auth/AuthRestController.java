package cr.ac.ucenfotec.waddle.beacon.rest.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.auth.AuthenticationService;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.auth.JwtService;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.Role;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.RoleEnum;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.RoleRepository;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.LoginResponse;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.UserRepository;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.auth.ChangePasswordRequest;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.auth.PasswordResetService;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
@RequestMapping("/auth")
@RestController
public class AuthRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    private final PasswordResetService passwordResetService;

    public AuthRestController(JwtService jwtService, AuthenticationService authenticationService, PasswordResetService passwordResetService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.passwordResetService = passwordResetService;

        jwtService.printSecretKey();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody User user) {
        User authenticatedUser = authenticationService.authenticate(user);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        Optional<User> foundedUser = userRepository.findByEmail(user.getEmail());

        foundedUser.ifPresent(loginResponse::setAuthUser);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role not found");
        }
        user.setRole(optionalRole.get());
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }


    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Optional<User> userOptional = userRepository.findByEmail(changePasswordRequest.getEmail());
        if (userOptional.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Porfavor volver a iniciar su sesión");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        User user = userOptional.get();

        // Verificar la contraseña actual
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Contraseña actual incorrecta");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Verificar que la nueva contraseña no sea igual a la actual
        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Nueva contraseña no debe ser igual que la contraseña actual");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Verificar que la nueva contraseña y la confirmación coincidan
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "La nueva contraseña no coincide");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Actualizar la contraseña
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);


        passwordResetService.sendPasswordChangeConfirmationEmail(user.getEmail());


        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Password changed successfully");
        return ResponseEntity.ok(successResponse);
    }

}
