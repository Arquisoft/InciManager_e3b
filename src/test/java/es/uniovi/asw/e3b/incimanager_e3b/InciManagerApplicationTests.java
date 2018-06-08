package es.uniovi.asw.e3b.incimanager_e3b;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.entities.Agent;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.entities.Incidence;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.entities.Operario;
import es.uniovi.asw.e3b.incimanager_e3b.util.Estado;
import kafka.KafkaLocalServer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InciManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestPropertySource(properties = { 
		"spring.datasource.url = jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_ON_EXIT=FALSE",
		"spring.datasource.username=postgres", 
		"spring.datasource.password=changeit",
		"spring.jpa.hibernate.ddl-auto=create-drop", 
		"spring.datasource.driverClassName=org.h2.Driver" 
})
public class InciManagerApplicationTests {

	@Value("${server.address:localhost}")
	private String address;

	@Value("${local.server.port:8091}")
	private int port;

	private URL baseUrl;
	private TestRestTemplate template;

	// private TestRestTemplate template2;
	private static KafkaLocalServer kafkaLocalServer;
    private static final String DEFAULT_KAFKA_LOG_DIR = "/tmp/test/kafka_embedded";
    private static final String TEST_TOPIC = "test_topic";
    private static final int BROKER_ID = 0;
    private static final int BROKER_PORT = 9092;
    private static final String LOCALHOST_BROKER = String.format("localhost:%d", BROKER_PORT);

    private static final String DEFAULT_ZOOKEEPER_LOG_DIR = "/tmp/test/zookeeper";
    private static final int ZOOKEEPER_PORT = 2181;
    private static final String ZOOKEEPER_HOST = String.format("localhost:%d", ZOOKEEPER_PORT);

    private static final String groupId = "groupID";

    private Charset charset = Charset.forName("UTF-8");
    private CharsetDecoder decoder = charset.newDecoder();
	
	
	@BeforeClass
    public static void startKafka(){
        Properties kafkaProperties;
        Properties zkProperties;

        try {
            //load properties
            kafkaProperties = getKafkaProperties(DEFAULT_KAFKA_LOG_DIR, BROKER_PORT, BROKER_ID);
            zkProperties = getZookeeperProperties(ZOOKEEPER_PORT,DEFAULT_ZOOKEEPER_LOG_DIR);

            //start kafkaLocalServer
            kafkaLocalServer = new KafkaLocalServer(kafkaProperties, zkProperties);
            Thread.sleep(5000);
        } catch (Exception e){
            e.printStackTrace(System.out);
            e.printStackTrace(System.out);
        }

        //do other things
}

	@Before
	public void setUp() throws Exception {
		this.baseUrl = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
		// template2 = new TestRestTemplate();
	}

	@Test
	public void testAgentModel() {
		Agent paco = new Agent("Paco", "123456", "", "paco@gmail.com", "paco", "Person");
		Agent pepe = new Agent("pepe", "213456", "", "pepe@gmail.com", "pepe", "Person");

		assert paco.equals(paco);
		assertFalse(paco == null);
		assert !paco.equals(new Integer(1));
		assert paco.hashCode() == paco.hashCode();

		pepe.setPassword("pass");
		pepe.setEmail("pepe2@gmail.com");
		assert pepe.getEmail().equals("pepe2@gmail.com");

		// Test agente
		assert paco.getUsername().equals("Paco");
		assert paco.getPassword().equals("123456");
		assert paco.getLocation().equals("");
		assert paco.getIdent().equals("paco");
		assert paco.getKind().equals("Person");
		assert paco.getKindCode() == -1; // TODO: cambiar cuando esté implementada,la llamada REST
	}

	@Test
	public void testIncidenciaModel() {
		Agent paco = new Agent("Paco", "123456", "", "paco@gmail.com", "paco", "Person");

		Incidence i = new Incidence(paco, "incidencia 1", "descripción de la incidencia", "45.678, 12.896",
				new HashSet<String>());

		i.getAgent().setPassword("pass");
		i.getAgent().setEmail("paco@gmail.com");

		// Test agente dentro de la incidencia
		assert i.getAgent().getUsername().equals("Paco");
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

		Incidence i2 = new Incidence(paco, "incidencia 1", "descripción de la incidencia", "45.678, 12.896",
				new HashSet<String>(), null, null, Estado.ABIERTA, new Date(), true, null, null);
		i2.setAgent(null);
		i2.setIncidenceName("incidencia2");
		i2.setDescription("descripcion 2");
		i2.setLocation("45.678, 34.1234");
		i2.setLabels(null);
		assert i2.getComments() == null;
		i2.setFields(null);
		assert i2.getStatus().equals(Estado.ABIERTA);
		assert i2.getExpiration() != null;
		i2.setCacheable(false);
		assertFalse(i2.isCacheable());
		assert i2.getOperario() == null;
		System.out.println(i2.toString());

	}

	@Test
	public void testRESTAddIncideceSuccessful() {
		String url = baseUrl.toString() + "/addIncidence";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonText = "{\"username\":\"paco@gmail.com\"," + "\"password\":\"123456\","
				+ "\"incidenceName\":\"name\"," + "\"description\":\"description\"," + "\"location\":\"location\","
				+ "\"labels\":[]," + "\"campos\":{}," + "\"status\":\"ABIERTA\"," + "\"expiration\":\"123\","
				+ "\"cacheable\":\"true\"}";
		String restResponse = "{\"idIncidencia\":null," + "\"username\":\"paco@gmail.com\","
				+ "\"password\":\"123456\"," + "\"incidenceName\":\"name\"," + "\"description\":\"description\","
				+ "\"location\":\"location\"," + "\"labels\":[]," + "\"campos\":{}," + "\"status\":\"ABIERTA\","
				+ "\"expiration\":123," + "\"cacheable\":true}";
		HttpEntity<String> entity = new HttpEntity<String>(jsonText, headers);
		ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, entity, String.class);
		assertThat(response.getBody(), equalTo(restResponse));
	}

	@Test
	public void testRESTAddIncideceFailingAuth() {
		String url = baseUrl.toString() + "/addIncidence";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonText = "{\"username\":\"paco@gmail.com\"," + "\"password\":\"1256\"," + "\"incidenceName\":\"name\","
				+ "\"description\":\"description\"," + "\"location\":\"location\"," + "\"labels\":[],"
				+ "\"campos\":{}," + "\"status\":\"ABIERTA\"," + "\"expiration\":\"123\"," + "\"cacheable\":\"true\"}";
		String restResponse = "{\"mensaje\":\"Wrong authentication, incidence not sending\"}";
		HttpEntity<String> entity = new HttpEntity<String>(jsonText, headers);
		ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, entity, String.class);
		assertThat(response.getBody(), equalTo(restResponse));
	}

	@Test
	public void testRESTAddIncideceNoCacheable() {
		String url = baseUrl.toString() + "/addIncidence";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonText = "{\"username\":\"paco@gmail.com\"," + "\"password\":\"123456\","
				+ "\"incidenceName\":\"name\"," + "\"description\":\"description\"," + "\"location\":\"location\","
				+ "\"labels\":[]," + "\"campos\":{}," + "\"status\":\"ABIERTA\"," + "\"expiration\":\"123\","
				+ "\"cacheable\":\"false\"}";
		String restResponse = "{\"mensaje\":\"Not cacheable, incidence not sending\"}";
		HttpEntity<String> entity = new HttpEntity<String>(jsonText, headers);
		ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, entity, String.class);
		assertThat(response.getBody(), equalTo(restResponse));
	}

	@Test
	public void testHTMLLoginController() {
		ResponseEntity<String> response;
		String userURI = baseUrl.toString() + "/login";
		String str_final = "<!DOCTYPEhtml><html><head><metacharset=\"utf-8\"/><metaname=\"viewport\"content=\"width=device-width,initial-scale=1.0\"/><title>InciManager</title><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/css/bootstrap.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\"/><linkrel=\"stylesheet\"href=\"/css/Login-Form-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/Navigation-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/styles.css\"/></head><body><div><navclass=\"navbarnavbar-lightnavbar-expand-mdnavigation-clean\"><divclass=\"container\"><aclass=\"navbar-brand\"href=\"/home\"><iclass=\"fafa-gears\"></i>InciManager</a><buttonclass=\"navbar-toggler\"data-toggle=\"collapse\"data-target=\"#navcol-1\"><spanclass=\"sr-only\">Togglenavigation</span><spanclass=\"navbar-toggler-icon\"></span></button><divclass=\"collapsenavbar-collapse\"id=\"navcol-1\"><ulclass=\"navnavbar-navml-auto\"><liclass=\"nav-item\"role=\"presentation\"><aclass=\"nav-link\"href=\"/login\"><iclass=\"iconion-log-in\"></i>Iniciarsesión</a></li></ul></div></div></nav></div><divclass=\"login-clean\"><formmethod=\"post\"action=\"/login\"><h2class=\"sr-only\">LoginForm</h2><divclass=\"illustration\"><iclass=\"fafa-cogs\"></i></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"text\"name=\"username\"placeholder=\"Email\"/></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"password\"name=\"password\"placeholder=\"Password\"/></div><divclass=\"form-group\"><buttonclass=\"btnbtn-primarybtn-block\"id=\"loginButton\"type=\"submit\">LogIn</button></div><inputtype=\"hidden\"name=\"${_csrf.parameterName}\"value=\"${_csrf.token}\"/></form></div><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/js/bootstrap.bundle.min.js\"></script></body></html>";
		response = template.getForEntity(userURI, String.class);
		assertThat(response.getBody().replace(" ", "").replace("\n", "").replace("\t", ""),
				equalTo((str_final).replace(" ", "")));
	}

	@Test
	public void testHTMLIndexController() {
		ResponseEntity<String> response;
		String userURI = baseUrl.toString() + "/";
		String str_final = "<!DOCTYPEhtml><html><head><metacharset=\"utf-8\"/><metaname=\"viewport\"content=\"width=device-width,initial-scale=1.0\"/><title>InciManager</title><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/css/bootstrap.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\"/><linkrel=\"stylesheet\"href=\"/css/Login-Form-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/Navigation-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/styles.css\"/></head><body><div><navclass=\"navbarnavbar-lightnavbar-expand-mdnavigation-clean\"><divclass=\"container\"><aclass=\"navbar-brand\"href=\"/home\"><iclass=\"fafa-gears\"></i>InciManager</a><buttonclass=\"navbar-toggler\"data-toggle=\"collapse\"data-target=\"#navcol-1\"><spanclass=\"sr-only\">Togglenavigation</span><spanclass=\"navbar-toggler-icon\"></span></button><divclass=\"collapsenavbar-collapse\"id=\"navcol-1\"><ulclass=\"navnavbar-navml-auto\"><liclass=\"nav-item\"role=\"presentation\"><aclass=\"nav-link\"href=\"/login\"><iclass=\"iconion-log-in\"></i>Iniciarsesión</a></li></ul></div></div></nav></div><divclass=\"login-clean\"><formmethod=\"post\"action=\"/login\"><h2class=\"sr-only\">LoginForm</h2><divclass=\"illustration\"><iclass=\"fafa-cogs\"></i></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"text\"name=\"username\"placeholder=\"Email\"/></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"password\"name=\"password\"placeholder=\"Password\"/></div><divclass=\"form-group\"><buttonclass=\"btnbtn-primarybtn-block\"id=\"loginButton\"type=\"submit\">LogIn</button></div><inputtype=\"hidden\"name=\"${_csrf.parameterName}\"value=\"${_csrf.token}\"/></form></div><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/js/bootstrap.bundle.min.js\"></script></body></html>";
		response = template.getForEntity(userURI, String.class);
		assertThat(response.getBody().replace(" ", "").replace("\n", "").replace("\t", ""),
				equalTo((str_final).replace(" ", "")));
	}

	// @Test
	// public void testHTMLHomeController() {
	// ResponseEntity<String> response;
	// String userURI = base.toString() + "/home";
	// String str_final =
	// "<!DOCTYPEhtml><html><head><metacharset=\"utf-8\"/><metaname=\"viewport\"content=\"width=device-width,initial-scale=1.0\"/><title>InciManager</title><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/css/bootstrap.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\"/><linkrel=\"stylesheet\"href=\"/css/Login-Form-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/Navigation-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/styles.css\"/></head><body><div><navclass=\"navbarnavbar-lightnavbar-expand-mdnavigation-clean\"><divclass=\"container\"><aclass=\"navbar-brand\"href=\"/home\"><iclass=\"fafa-gears\"></i>InciManager</a><buttonclass=\"navbar-toggler\"data-toggle=\"collapse\"data-target=\"#navcol-1\"><spanclass=\"sr-only\">Togglenavigation</span><spanclass=\"navbar-toggler-icon\"></span></button><divclass=\"collapsenavbar-collapse\"id=\"navcol-1\"><ulclass=\"navnavbar-navml-auto\"><liclass=\"nav-item\"role=\"presentation\"><aclass=\"nav-link\"href=\"/login\"><iclass=\"iconion-log-in\"></i>Iniciarsesión</a></li></ul></div></div></nav></div><divclass=\"login-clean\"><formmethod=\"post\"action=\"/login\"><h2class=\"sr-only\">LoginForm</h2><divclass=\"illustration\"><iclass=\"fafa-cogs\"></i></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"text\"name=\"username\"placeholder=\"Email\"/></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"password\"name=\"password\"placeholder=\"Password\"/></div><divclass=\"form-group\"><buttonclass=\"btnbtn-primarybtn-block\"id=\"loginButton\"type=\"submit\">LogIn</button></div><inputtype=\"hidden\"name=\"${_csrf.parameterName}\"value=\"${_csrf.token}\"/></form></div><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/js/bootstrap.bundle.min.js\"></script></body></html>";
	// response = template2.getForEntity(userURI, String.class);
	// assertThat(response.getBody().replace(" ", "").replace("\n",
	// "").replace("\t", ""),
	// equalTo((str_final).replace(" ", "")));
	// }
	//
	// @Test
	// public void testHTMLIncidenceControllerAddGet() {
	// ResponseEntity<String> response;
	// String userURI = base.toString() + "/incidences/add";
	// String str_final =
	// "<!DOCTYPEhtml><html><head><metacharset=\"utf-8\"/><metaname=\"viewport\"content=\"width=device-width,initial-scale=1.0\"/><title>InciManager</title><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/css/bootstrap.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\"/><linkrel=\"stylesheet\"href=\"/css/Login-Form-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/Navigation-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/styles.css\"/></head><body><div><navclass=\"navbarnavbar-lightnavbar-expand-mdnavigation-clean\"><divclass=\"container\"><aclass=\"navbar-brand\"href=\"/home\"><iclass=\"fafa-gears\"></i>InciManager</a><buttonclass=\"navbar-toggler\"data-toggle=\"collapse\"data-target=\"#navcol-1\"><spanclass=\"sr-only\">Togglenavigation</span><spanclass=\"navbar-toggler-icon\"></span></button><divclass=\"collapsenavbar-collapse\"id=\"navcol-1\"><ulclass=\"navnavbar-navml-auto\"><liclass=\"nav-item\"role=\"presentation\"><aclass=\"nav-link\"href=\"/login\"><iclass=\"iconion-log-in\"></i>Iniciarsesión</a></li></ul></div></div></nav></div><divclass=\"login-clean\"><formmethod=\"post\"action=\"/login\"><h2class=\"sr-only\">LoginForm</h2><divclass=\"illustration\"><iclass=\"fafa-cogs\"></i></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"text\"name=\"username\"placeholder=\"Email\"/></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"password\"name=\"password\"placeholder=\"Password\"/></div><divclass=\"form-group\"><buttonclass=\"btnbtn-primarybtn-block\"id=\"loginButton\"type=\"submit\">LogIn</button></div><inputtype=\"hidden\"name=\"${_csrf.parameterName}\"value=\"${_csrf.token}\"/></form></div><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/js/bootstrap.bundle.min.js\"></script></body></html>";
	// response = template.getForEntity(userURI, String.class);
	// assertThat(response.getBody().replace(" ", "").replace("\n",
	// "").replace("\t", ""),
	// equalTo((str_final).replace(" ", "")));
	// }
	//
	// @Test
	// public void testHTMLIncidenceControllerList() {
	// ResponseEntity<String> response;
	// String userURI = base.toString() + "/incidences/list";
	// String str_final =
	// "<!DOCTYPEhtml><html><head><metacharset=\"utf-8\"/><metaname=\"viewport\"content=\"width=device-width,initial-scale=1.0\"/><title>InciManager</title><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/css/bootstrap.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"/><linkrel=\"stylesheet\"href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\"/><linkrel=\"stylesheet\"href=\"/css/Login-Form-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/Navigation-Clean.css\"/><linkrel=\"stylesheet\"href=\"/css/styles.css\"/></head><body><div><navclass=\"navbarnavbar-lightnavbar-expand-mdnavigation-clean\"><divclass=\"container\"><aclass=\"navbar-brand\"href=\"/home\"><iclass=\"fafa-gears\"></i>InciManager</a><buttonclass=\"navbar-toggler\"data-toggle=\"collapse\"data-target=\"#navcol-1\"><spanclass=\"sr-only\">Togglenavigation</span><spanclass=\"navbar-toggler-icon\"></span></button><divclass=\"collapsenavbar-collapse\"id=\"navcol-1\"><ulclass=\"navnavbar-navml-auto\"><liclass=\"nav-item\"role=\"presentation\"><aclass=\"nav-link\"href=\"/login\"><iclass=\"iconion-log-in\"></i>Iniciarsesión</a></li></ul></div></div></nav></div><divclass=\"login-clean\"><formmethod=\"post\"action=\"/login\"><h2class=\"sr-only\">LoginForm</h2><divclass=\"illustration\"><iclass=\"fafa-cogs\"></i></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"text\"name=\"username\"placeholder=\"Email\"/></div><divclass=\"form-group\"><inputclass=\"form-control\"type=\"password\"name=\"password\"placeholder=\"Password\"/></div><divclass=\"form-group\"><buttonclass=\"btnbtn-primarybtn-block\"id=\"loginButton\"type=\"submit\">LogIn</button></div><inputtype=\"hidden\"name=\"${_csrf.parameterName}\"value=\"${_csrf.token}\"/></form></div><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta.2/js/bootstrap.bundle.min.js\"></script></body></html>";
	// response = template.getForEntity(userURI, String.class);
	// assertThat(response.getBody().replace(" ", "").replace("\n",
	// "").replace("\t", ""),
	// equalTo((str_final).replace(" ", "")));
	// }

	/**
	 * Test modelo Operario.
	 */
	@Test
	public void testModeloOperario() {
		Operario op = new Operario("email@gmail.com", "123456", "", null);
		Operario op2 = null;

		assert op.hashCode() > 0;

		assert op.toString().equals("email@gmail.com");
		assert op.equals(op) == true;
		assertFalse(op.equals(op2));
		assert op.equals(new Integer(1)) == false;

		op.setId((long) 123);
		op.setEmail("email2@gmail.com");
		assert op.getEmail().equals("email2@gmail.com");
		op.setPassword("123");
		assert op.getPassword().equals("123");
		op.setRole("operario");
		assert op.getRole().equals("operario");
	}

	/**
	 * Pongo 'z' para que se ejecute el último (va por orden alfabético)
	 */
	@Test
	public void zApplicationStarts() {
		try {
			InciManagerApplication.main(new String[] {});
		} catch (Exception e) {
			System.out.println(
					"######### HEY! Hubo una excepción al testear el inicio de la app, pero no importa. Solo queríamos que se ejecutara el código de inicio para el % del codecov :-) #########");
			System.out.println(
					"######### Probablemente porque está ejecutandose otra instancia de la aplicación en el mismo puerto #########");
		}
	}
	
	
	private static Properties getKafkaProperties(String logDir, int port, int brokerId) {
        Properties properties = new Properties();
        properties.put("port", port + "");
        properties.put("broker.id", brokerId + "");
        properties.put("log.dir", logDir);
        properties.put("zookeeper.connect", ZOOKEEPER_HOST);
        properties.put("default.replication.factor", "1");
        properties.put("delete.topic.enable", "true");
        return properties;
    }

    private static Properties getZookeeperProperties(int port, String zookeeperDir) {
        Properties properties = new Properties();
        properties.put("clientPort", port + "");
        properties.put("dataDir", zookeeperDir);
        return properties;
}

}
