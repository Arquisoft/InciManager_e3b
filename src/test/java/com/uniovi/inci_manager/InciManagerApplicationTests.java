package com.uniovi.inci_manager;


import asw.InciManagerApplication;
import asw.inci_manager.db_management.model.Incidence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InciManagerApplication.class)
public class InciManagerApplicationTests {

    @Test
    public void contextLoads() {
        // TODO: testear
    }

    @Test
    public void test1Prueba() {
        Incidence incidence = new Incidence("agente1", "incidencia que flipas",
                "bua, ocurrió algo desastroso", "45.6783, 53.6789", null);

    }

}
