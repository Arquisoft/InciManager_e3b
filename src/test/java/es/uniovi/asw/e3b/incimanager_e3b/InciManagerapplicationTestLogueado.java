package es.uniovi.asw.e3b.incimanager_e3b;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Properties;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
public class InciManagerapplicationTestLogueado {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private Filter springSecurityFilterChain;

	private MockMvc mvc;
	
	@SuppressWarnings("unused")
	private static KafkaLocalServer kafkaLocalServer;
    private static final String DEFAULT_KAFKA_LOG_DIR = "/tmp/test/kafka_embedded";
    @SuppressWarnings("unused")
    private static final String TEST_TOPIC = "test_topic";
    private static final int BROKER_ID = 0;
    private static final int BROKER_PORT = 9092;
    @SuppressWarnings("unused")
    private static final String LOCALHOST_BROKER = String.format("localhost:%d", BROKER_PORT);

    private static final String DEFAULT_ZOOKEEPER_LOG_DIR = "/tmp/test/zookeeper";
    private static final int ZOOKEEPER_PORT = 2181;
    private static final String ZOOKEEPER_HOST = String.format("localhost:%d", ZOOKEEPER_PORT);
    @SuppressWarnings("unused")
    private static final String groupId = "groupID";

    
    private Charset charset = Charset.forName("UTF-8");
    @SuppressWarnings("unused")
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
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(springSecurityFilterChain).build();
	}

	@Test
	public void testHomeLogueado() throws Exception {
		ResultActions result = mvc.perform(get("/"));
		assert result != null;
	}

	@Test
	public void testHomeMockup() throws Exception {
		ResultActions result = mvc.perform(get("/home"));
		assert result != null;
	}

	@Test
	public void testLoginMockup() throws Exception {
		ResultActions result = mvc.perform(get("/login"));
		assert result != null;
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
