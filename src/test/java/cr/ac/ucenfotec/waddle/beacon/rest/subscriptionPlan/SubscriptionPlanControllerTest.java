

package cr.ac.ucenfotec.waddle.beacon.rest.subscriptionPlan;


import cr.ac.ucenfotec.waddle.beacon.Application;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.subscriptionPlan.SubscriptionPlan;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.subscriptionPlan.SubscriptionPlanRepository;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.auth.JwtService;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.User;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.user.UserRepository;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.Role;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.RoleEnum;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.rol.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;


import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Collections;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class SubscriptionPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionPlanRepository repository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    private String adminToken;


    @BeforeEach
    public void setUp() {

        Role role = new Role();
        role.setId(1);
        role.setName(RoleEnum.SUPER_ADMIN_ROLE);

        User user = new User();
        user.setId(1L);
        user.setEmail("super.admin@gmail.com");
        user.setPassword("superadmin123");
        user.setRole(role);


        when(roleRepository.findByName(RoleEnum.SUPER_ADMIN_ROLE)).thenReturn(Optional.of(role));
        when(userRepository.findByEmail("super.admin@gmail.com")).thenReturn(Optional.of(user));


        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("SUPER_ADMIN_ROLE")
                .build();


        adminToken = jwtService.generateToken(userDetails);
    }

    @Test
    @WithMockUser
    public void getAllSubscriptionPlansTest() throws Exception {
        SubscriptionPlan plan1 = new SubscriptionPlan();
        plan1.setId(1L);
        plan1.setTitulo("Plan 1");
        plan1.setDescripcion("Description 1");
        plan1.setPrecio(10F);
        plan1.setPlazo(30);

        SubscriptionPlan plan2 = new SubscriptionPlan();
        plan2.setId(2L);
        plan2.setTitulo("Plan 2");
        plan2.setDescripcion("Description 2");
        plan2.setPrecio(20F);
        plan2.setPlazo(60);

        when(repository.findAll()).thenReturn(Arrays.asList(plan1, plan2));

        mockMvc.perform(get("/subscriptionPlan")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("[{'id':1,'titulo':'Plan 1','descripcion':'Description 1','precio':10.0,'plazo':30},{'id':2,'titulo':'Plan 2','descripcion':'Description 2','precio':20.0,'plazo':60}]"));
    }

    @Test
    @WithMockUser
    public void getSubscriptionPlanByIdTest() throws Exception {
        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setId(1L);
        plan.setTitulo("Plan 1");
        plan.setDescripcion("Description 1");
        plan.setPrecio(10F);
        plan.setPlazo(30);

        when(repository.findById(1)).thenReturn(Optional.of(plan));

        mockMvc.perform(get("/subscriptionPlan/1")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{'id':1,'titulo':'Plan 1','descripcion':'Description 1','precio':10.0,'plazo':30}"));
    }



}
