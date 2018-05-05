package es.uniovi.asw.e3b.incimanager_e3b;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InciManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InciManagerapplicationTestLogueado {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private Filter springSecurityFilterChain;

	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(springSecurityFilterChain).build();
	}

	@Test
	public void testHomeLogueado() throws Exception {
		mvc.perform(get("/"));
	}

	@Test
	public void testHomeMockup() throws Exception {
		mvc.perform(get("/home"));
	}

	@Test
	public void testLoginMockup() throws Exception {
		mvc.perform(get("/login"));
	}

}
