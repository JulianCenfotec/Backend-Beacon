package cr.ac.ucenfotec.waddle.beacon.seeder;

import cr.ac.ucenfotec.waddle.beacon.logic.entity.etiqueta.Etiqueta;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.etiqueta.Etiqueta.Tipo;
import cr.ac.ucenfotec.waddle.beacon.logic.entity.etiqueta.EtiquetaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("etiquetaRepository")
public class EtiquetaSeeder implements CommandLineRunner {
    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @Override
    public void run(String... args) throws Exception {
        for (Tipo tipo : Tipo.values()) {
            if (etiquetaRepository.getEtiquetaSistemaPorTipo(tipo).isEmpty()) {
                Etiqueta etiqueta = new Etiqueta(
                    "#000000",
                    tipo.name(),
                    "Descripción para " + tipo.name(),
                    tipo,
                    true
                );
                etiquetaRepository.save(etiqueta);
            }
        }
    }
}
