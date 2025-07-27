package cr.ac.ucenfotec.waddle.beacon.logic.entity.auth;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateSuccess() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        User storedUser = new User();
        storedUser.setEmail("test@example.com");
        storedUser.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(storedUser));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        User authenticatedUser = authenticationService.authenticate(user);
        assertNotNull(authenticatedUser);
        assertEquals("test@example.com", authenticatedUser.getEmail());
    }

    @Test
    public void testAuthenticateFailure() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        Exception exception = assertThrows(AuthenticationException.class, () -> {
            authenticationService.authenticate(user);
        });

        assertEquals("Invalid credentials", exception.getMessage());
    }
}
