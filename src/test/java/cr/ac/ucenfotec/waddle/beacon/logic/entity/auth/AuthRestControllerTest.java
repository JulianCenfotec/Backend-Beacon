package cr.ac.ucenfotec.waddle.beacon.logic.entity.auth;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.auth.AuthenticationService;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.auth.JwtService;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.Role;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.RoleEnum;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.RoleRepository;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class AuthRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @Test
    public void testRegisterUser() throws Exception {
        User user = new User();
        user.setEmail("newuser@example.com");
        user.setPassword("newpassword");

        Role userRole = new Role();
        userRole.setName(RoleEnum.USER);
        user.setRole(userRole);

        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName(RoleEnum.USER)).thenReturn(Optional.of(userRole));
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"newuser@example.com\",\"password\":\"newpassword\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"email\":\"newuser@example.com\"}"));
    }
}
