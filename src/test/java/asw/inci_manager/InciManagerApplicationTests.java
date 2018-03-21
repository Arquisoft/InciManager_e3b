package asw.inci_manager;

import asw.InciManagerApplication;
import asw.inci_manager.inci_manager_gest.entities.Agent;
import asw.inci_manager.inci_manager_gest.entities.Incidence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InciManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InciManagerApplicationTests {

    @Test
    public void contextLoads() {
        // TODO: testear
    }

    @Test
    public void testIncidenciaModel() {
        Agent paco =new Agent("Paco","123456","","paco@gmail.com","paco","Person");

        Incidence i = new Incidence(paco, "incidencia 1", "descripción de la incidencia", "45.678, 12.896", "");

        assert i.getAgent().getNombre().equals("Paco");
        assert i.getIncidenceName().equals("incidencia 1");
        assert i.getDescription().equals("descripción de la incidencia");
        assert i.getLocation().equals("45.678, 12.896");
        assert i.getLabels() == "";
        assert i.getCampos() == null;
        assert i.getExpiration() == null;
    }

}
