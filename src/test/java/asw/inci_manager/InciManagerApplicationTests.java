package asw.inci_manager;

import asw.InciManagerApplication;
import asw.inci_manager.db_management.model.Incidence;
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
        Incidence i = new Incidence("agente1", "incidencia 1", "descripción de la incidencia", "45.678, 12.896", null);

        assert i.getUsername().equals("agente1");
        assert i.getIncidenceName().equals("incidencia 1");
        assert i.getDescription().equals("descripción de la incidencia");
        assert i.getLocation().equals("45.678, 12.896");
        assert i.getLabels() == null;
        assert i.getCampos() == null;
        assert i.getExpiration() == null;
    }

}
