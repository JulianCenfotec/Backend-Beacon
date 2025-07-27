package cr.ac.ucenfotec.waddle.beacon.rest.etiqueta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.etiqueta.Etiqueta;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.etiqueta.EtiquetaRepository;

@RestController
@RequestMapping("/etiqueta")
public class EtiquetaController {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Etiqueta> getAllEtiquetas() {
        return etiquetaRepository.findAll();
    }
}