package cr.ac.ucenfotec.waddle.beacon.rest.user;

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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    @WithMockUser
    public void testUpdateUser() throws Exception {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("John");
        existingUser.setLastname("Doe");
        existingUser.setEmail("john.doe@example.com");

        Role userRole = new Role();
        userRole.setName(RoleEnum.USER);
        existingUser.setRole(userRole);

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("Jane");
        updatedUser.setLastname("Doe");
        updatedUser.setEmail("jane.doe@example.com");
        updatedUser.setRole(userRole);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Esto\",\"lastname\":\"Esprueba\",\"email\":\"proba@example.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"name\":\"Jane\",\"lastname\":\"Doe\",\"email\":\"jane.doe@example.com\"}"));
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN_ROLE")
    public void testGetAllUsers() throws Exception {
        Role userRole = new Role();
        userRole.setName(RoleEnum.USER);

        User user1 = new User();
        user1.setId(1L);
        user1.setName("John");
        user1.setLastname("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setRole(userRole);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane");
        user2.setLastname("Smith");
        user2.setEmail("jane.smith@example.com");
        user2.setRole(userRole);

        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'id':1,'name':'John','lastname':'Doe','email':'john.doe@example.com'},{'id':2,'name':'Jane','lastname':'Smith','email':'jane.smith@example.com'}]"));
    }

    @Test
    @WithMockUser
    public void testGetUserById() throws Exception {
        Role userRole = new Role();
        userRole.setName(RoleEnum.USER);

        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
        user.setRole(userRole);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'id':1,'name':'John','lastname':'Doe','email':'john.doe@example.com'}"));
    }
}
