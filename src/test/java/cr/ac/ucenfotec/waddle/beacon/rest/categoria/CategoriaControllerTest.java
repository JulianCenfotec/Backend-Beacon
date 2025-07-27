package cr.ac.ucenfotec.waddle.beacon.rest.categoria;

import cr.ac.ucenfotec.waddle.beacon.Application;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.categoria.Categoria;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.categoria.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaRepository repository;

    @Test
    @WithMockUser
    public void getAllCategoriasTest() throws Exception {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/categoria")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser
    public void getCategoriaByIdTest() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Test Categoria");
        categoria.setDescripcion("Descripcion de Test");

        when(repository.findById(1)).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/categoria/1")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"id\":1,\"nombre\":\"Test Categoria\",\"descripcion\":\"Descripcion de Test\"}"));
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN_ROLE")
    public void updateCategoriaTest() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Test Categoria");
        categoria.setDescripcion("Descripcion de Test");

        when(repository.findById(1)).thenReturn(Optional.of(categoria));
        when(repository.save(categoria)).thenReturn(categoria);

        mockMvc.perform(put("/categoria/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Updated Categoria\",\"descripcion\":\"Updated Descripcion\"}")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"id\":1,\"nombre\":\"Updated Categoria\",\"descripcion\":\"Updated Descripcion\"}"));
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN_ROLE")
    public void deleteCategoriaTest() throws Exception {
        mockMvc.perform(delete("/categoria/1")
                        .accept("application/json"))
                .andExpect(status().isOk());
    }
}
