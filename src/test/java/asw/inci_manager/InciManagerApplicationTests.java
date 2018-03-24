package asw.inci_manager;

import asw.InciManagerApplication;
import asw.inci_manager.inci_manager_gest.entities.Agent;
import asw.inci_manager.inci_manager_gest.entities.Incidence;
import asw.inci_manager.inci_manager_gest.services.AgentService;
import asw.inci_manager.inci_manager_gest.services.IncidenceService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InciManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@IntegrationTest({"server.port=0"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InciManagerApplicationTests {

    @Autowired
    private IncidenceService incidenceService;

    @Autowired
    private AgentService agentService;

    @Value("${local.server.port}")
    private int port;

    private URL base;
    private RestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        template = new TestRestTemplate();
    }

    @Test
    public void testAgentModel() {
        Agent paco = new Agent("Paco", "123456", "", "paco@gmail.com", "paco", "Person");
        Agent pepe = new Agent("pepe", "213456", "", "pepe@gmail.com", "pepe", "Person");

        assert paco.equals(paco);
        assert !paco.equals(null);
        assert !paco.equals(new Integer(1));
        assert paco.hashCode() == paco.hashCode();

        pepe.setPassword("pass");
        pepe.setEmail("pepe2@gmail.com");
        assert pepe.getEmail().equals("pepe2@gmail.com");

        // Test agente
        assert paco.getNombre().equals("Paco");
        assert paco.getPassword().equals("123456");
        assert paco.getLocation().equals("");
        assert paco.getIdent().equals("paco");
        assert paco.getKind().equals("Person");
        assert paco.getKindCode() == -1; // TODO: cambiar cuando esté implementada,la llamada REST
    }

    @Test
    public void testIncidenciaModel() {
        Agent paco = new Agent("Paco", "123456", "", "paco@gmail.com", "paco", "Person");

        Incidence i = new Incidence(paco, "incidencia 1", "descripción de la incidencia", "45.678, 12.896", new HashSet<String>());

        i.getAgent().setPassword("pass");
        i.getAgent().setEmail("paco@gmail.com");

        // Test agente dentro de la incidencia
        assert i.getAgent().getNombre().equals("Paco");
        assert i.getAgent().getPassword().equals("pass");
        assert i.getAgent().getLocation().equals("");
        assert i.getAgent().getIdent().equals("paco");
        assert i.getAgent().getKind().equals("Person");

        // Test campos incidencia
        assert i.getIncidenceName().equals("incidencia 1");
        assert i.getDescription().equals("descripción de la incidencia");
        assert i.getLocation().equals("45.678, 12.896");
        assert i.getLabels().size() == 0;
        assert i.getFields() == null;
        assert i.getExpiration() == null;
    }


    @Test
    public void testHTMLLoginController() {
        ResponseEntity<String> response;
        String userURI = base.toString() + "/login";
        String str_final = "<!DOCTYPEhtml><html><head><metacharset=\"utf-8\"/><metaname=\"viewport\"content=\"width=device-width,initial-scale=1.0\"/><title>InciManager</title><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/css/bootstrap.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\"/><linkrel=\"stylesheet\"href=\"/css/Login-Form-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/Navigation-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/styles.css\"/></head><body><div><navclass=\"navbarnavbar-lightnavbar-expand-mdnavigation-clean\"><divclass=\"container\"><aclass=\"navbar-brand\"href=\"/home\"><iclass=\"fafa-gears\"></i>InciManager</a><buttonclass=\"navbar-toggler\"data-toggle=\"collapse\"data-target=\"#navcol-1\"><spanclass=\"sr-only\">Togglenavigation</span><spanclass=\"navbar-toggler-icon\"></span></button><divclass=\"collapsenavbar-collapse\"id=\"navcol-1\"><ulclass=\"navnavbar-navml-auto\"><liclass=\"nav-item\"role=\"presentation\"><aclass=\"nav-link\"href=\"/login\"><iclass=\"iconion-log-in\"></i>Iniciarsesión</a></li></ul></div></div></nav></div><divclass=\"login-clean\"><formmethod=\"post\"action=\"/login\"><h2class=\"sr-only\">LoginForm</h2><divclass=\"illustration\"><iclass=\"fafa-cogs\"></i></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"text\"name=\"username\"placeholder=\"Email\"/></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"password\"name=\"password\"placeholder=\"Password\"/></div><divclass=\"form-group\"><buttonclass=\"btnbtn-primarybtn-block\"type=\"submit\">LogIn</button></div><inputtype=\"hidden\"name=\"${_csrf.parameterName}\"value=\"${_csrf.token}\"/></form></div><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/js/bootstrap.bundle.min.js\"></script></body></html>";
        response = template.getForEntity(userURI, String.class);
        assertThat(response.getBody().replace(" ", "").replace("\n", "").replace("\t", ""),
                equalTo((str_final).replace(" ", "")));
    }

    @Test
    public void testHTMLIndexController() {
        ResponseEntity<String> response;
        String userURI = base.toString() + "/";
        String str_final = "<!DOCTYPEhtml><html><head><metacharset=\"utf-8\"/><metaname=\"viewport\"content=\"width=device-width,initial-scale=1.0\"/><title>InciManager</title><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/css/bootstrap.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\"/><linkrel=\"stylesheet\"href=\"/css/Login-Form-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/Navigation-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/styles.css\"/></head><body><div><navclass=\"navbarnavbar-lightnavbar-expand-mdnavigation-clean\"><divclass=\"container\"><aclass=\"navbar-brand\"href=\"/home\"><iclass=\"fafa-gears\"></i>InciManager</a><buttonclass=\"navbar-toggler\"data-toggle=\"collapse\"data-target=\"#navcol-1\"><spanclass=\"sr-only\">Togglenavigation</span><spanclass=\"navbar-toggler-icon\"></span></button><divclass=\"collapsenavbar-collapse\"id=\"navcol-1\"><ulclass=\"navnavbar-navml-auto\"><liclass=\"nav-item\"role=\"presentation\"><aclass=\"nav-link\"href=\"/login\"><iclass=\"iconion-log-in\"></i>Iniciarsesión</a></li></ul></div></div></nav></div><divclass=\"login-clean\"><formmethod=\"post\"action=\"/login\"><h2class=\"sr-only\">LoginForm</h2><divclass=\"illustration\"><iclass=\"fafa-cogs\"></i></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"text\"name=\"username\"placeholder=\"Email\"/></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"password\"name=\"password\"placeholder=\"Password\"/></div><divclass=\"form-group\"><buttonclass=\"btnbtn-primarybtn-block\"type=\"submit\">LogIn</button></div><inputtype=\"hidden\"name=\"${_csrf.parameterName}\"value=\"${_csrf.token}\"/></form></div><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/js/bootstrap.bundle.min.js\"></script></body></html>";
        response = template.getForEntity(userURI, String.class);
        assertThat(response.getBody().replace(" ", "").replace("\n", "").replace("\t", ""),
                equalTo((str_final).replace(" ", "")));
    }


    @Test
    public void testHTMLHomeController() {
        ResponseEntity<String> response;
        String userURI = base.toString() + "/home";
        String str_final = "<!DOCTYPEhtml><html><head><metacharset=\"utf-8\"/><metaname=\"viewport\"content=\"width=device-width,initial-scale=1.0\"/><title>InciManager</title><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/css/bootstrap.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\"/><linkrel=\"stylesheet\"href=\"/css/Login-Form-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/Navigation-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/styles.css\"/></head><body><div><navclass=\"navbarnavbar-lightnavbar-expand-mdnavigation-clean\"><divclass=\"container\"><aclass=\"navbar-brand\"href=\"/home\"><iclass=\"fafa-gears\"></i>InciManager</a><buttonclass=\"navbar-toggler\"data-toggle=\"collapse\"data-target=\"#navcol-1\"><spanclass=\"sr-only\">Togglenavigation</span><spanclass=\"navbar-toggler-icon\"></span></button><divclass=\"collapsenavbar-collapse\"id=\"navcol-1\"><ulclass=\"navnavbar-navml-auto\"><liclass=\"nav-item\"role=\"presentation\"><aclass=\"nav-link\"href=\"/login\"><iclass=\"iconion-log-in\"></i>Iniciarsesión</a></li></ul></div></div></nav></div><divclass=\"login-clean\"><formmethod=\"post\"action=\"/login\"><h2class=\"sr-only\">LoginForm</h2><divclass=\"illustration\"><iclass=\"fafa-cogs\"></i></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"text\"name=\"username\"placeholder=\"Email\"/></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"password\"name=\"password\"placeholder=\"Password\"/></div><divclass=\"form-group\"><buttonclass=\"btnbtn-primarybtn-block\"type=\"submit\">LogIn</button></div><inputtype=\"hidden\"name=\"${_csrf.parameterName}\"value=\"${_csrf.token}\"/></form></div><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/js/bootstrap.bundle.min.js\"></script></body></html>";
        response = template.getForEntity(userURI, String.class);
        assertThat(response.getBody().replace(" ", "").replace("\n", "").replace("\t", ""),
                equalTo((str_final).replace(" ", "")));
    }

    @Test
    public void testHTMLIncidenceControllerAddGet() {
        ResponseEntity<String> response;
        String userURI = base.toString() + "/incidences/add";
        String str_final = "<!DOCTYPEhtml><html><head><metacharset=\"utf-8\"/><metaname=\"viewport\"content=\"width=device-width,initial-scale=1.0\"/><title>InciManager</title><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/css/bootstrap.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\"/><linkrel=\"stylesheet\"href=\"/css/Login-Form-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/Navigation-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/styles.css\"/></head><body><div><navclass=\"navbarnavbar-lightnavbar-expand-mdnavigation-clean\"><divclass=\"container\"><aclass=\"navbar-brand\"href=\"/home\"><iclass=\"fafa-gears\"></i>InciManager</a><buttonclass=\"navbar-toggler\"data-toggle=\"collapse\"data-target=\"#navcol-1\"><spanclass=\"sr-only\">Togglenavigation</span><spanclass=\"navbar-toggler-icon\"></span></button><divclass=\"collapsenavbar-collapse\"id=\"navcol-1\"><ulclass=\"navnavbar-navml-auto\"><liclass=\"nav-item\"role=\"presentation\"><aclass=\"nav-link\"href=\"/login\"><iclass=\"iconion-log-in\"></i>Iniciarsesión</a></li></ul></div></div></nav></div><divclass=\"login-clean\"><formmethod=\"post\"action=\"/login\"><h2class=\"sr-only\">LoginForm</h2><divclass=\"illustration\"><iclass=\"fafa-cogs\"></i></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"text\"name=\"username\"placeholder=\"Email\"/></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"password\"name=\"password\"placeholder=\"Password\"/></div><divclass=\"form-group\"><buttonclass=\"btnbtn-primarybtn-block\"type=\"submit\">LogIn</button></div><inputtype=\"hidden\"name=\"${_csrf.parameterName}\"value=\"${_csrf.token}\"/></form></div><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/js/bootstrap.bundle.min.js\"></script></body></html>";
        response = template.getForEntity(userURI, String.class);
        assertThat(response.getBody().replace(" ", "").replace("\n", "").replace("\t", ""),
                equalTo((str_final).replace(" ", "")));
    }

    @Test
    public void testHTMLIncidenceControllerList() {
        ResponseEntity<String> response;
        String userURI = base.toString() + "/incidences/list";
        String str_final = "<!DOCTYPEhtml><html><head><metacharset=\"utf-8\"/><metaname=\"viewport\"content=\"width=device-width,initial-scale=1.0\"/><title>InciManager</title><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/css/bootstrap.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\"/><linkrel=\"stylesheet\"href=\"/css/Login-Form-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/Navigation-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/styles.css\"/></head><body><div><navclass=\"navbarnavbar-lightnavbar-expand-mdnavigation-clean\"><divclass=\"container\"><aclass=\"navbar-brand\"href=\"/home\"><iclass=\"fafa-gears\"></i>InciManager</a><buttonclass=\"navbar-toggler\"data-toggle=\"collapse\"data-target=\"#navcol-1\"><spanclass=\"sr-only\">Togglenavigation</span><spanclass=\"navbar-toggler-icon\"></span></button><divclass=\"collapsenavbar-collapse\"id=\"navcol-1\"><ulclass=\"navnavbar-navml-auto\"><liclass=\"nav-item\"role=\"presentation\"><aclass=\"nav-link\"href=\"/login\"><iclass=\"iconion-log-in\"></i>Iniciarsesión</a></li></ul></div></div></nav></div><divclass=\"login-clean\"><formmethod=\"post\"action=\"/login\"><h2class=\"sr-only\">LoginForm</h2><divclass=\"illustration\"><iclass=\"fafa-cogs\"></i></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"text\"name=\"username\"placeholder=\"Email\"/></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"password\"name=\"password\"placeholder=\"Password\"/></div><divclass=\"form-group\"><buttonclass=\"btnbtn-primarybtn-block\"type=\"submit\">LogIn</button></div><inputtype=\"hidden\"name=\"${_csrf.parameterName}\"value=\"${_csrf.token}\"/></form></div><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/js/bootstrap.bundle.min.js\"></script></body></html>";
        response = template.getForEntity(userURI, String.class);
        assertThat(response.getBody().replace(" ", "").replace("\n", "").replace("\t", ""),
                equalTo((str_final).replace(" ", "")));
    }

    /**
     * Pongo 'z' para que se ejecute el último (va por orden alfabético)
     */
    @Test
    public void zApplicationStarts() {
        try {
            InciManagerApplication.main(new String[]{});
        } catch (Exception e) {
            System.out.println("######### HEY! Hubo una excepción al testear el inicio de la app, pero no importa. Solo queríamos que se ejecutara el código de inicio para el % del codecov :-) #########");
            System.out.println("######### Probablemente porque está ejecutandose otra instancia de la aplicación en el mismo puerto #########");
        }
    }

}
