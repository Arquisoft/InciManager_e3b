package asw.inci_manager;

<<<<<<< HEAD:src/test/java/com/uniovi/inci_manager/InciManagerApplicationTests.java

import asw.InciManagerApplication;
import asw.inci_manager.db_management.model.Incidence;
=======
>>>>>>> f137d71c2b7c484f6130656072a86ebed0546e12:src/test/java/asw/inci_manager/InciManagerApplicationTests.java
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
<<<<<<< HEAD:src/test/java/com/uniovi/inci_manager/InciManagerApplicationTests.java
@SpringBootTest(classes = InciManagerApplication.class)
=======
@SpringBootTest(classes = InciManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
>>>>>>> f137d71c2b7c484f6130656072a86ebed0546e12:src/test/java/asw/inci_manager/InciManagerApplicationTests.java
public class InciManagerApplicationTests {

    @Test
    public void contextLoads() {
        // TODO: testear
    }

    @Test
    public void test1Prueba() {

    }

}
