package es.uniovi.asw.e3b.incimanager_e3b.acceptancetests.steps;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import es.uniovi.asw.e3b.incimanager_e3b.InciManagerApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {InciManagerApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class LandingSteps {

	@Autowired
	protected WebApplicationContext context;

	protected MockMvc mvc;
	protected MvcResult result;

	@Value("${local.server.port}")
	protected int port;


	@When("^el usuario invoca /$")
	public void elUsuarioInvoca() throws Throwable {
		Assert.notNull(context, "The context must not be null");
		this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
		result = mvc.perform(get("/")).andReturn();
	}

	@Then("^el usuario recibe el código de estado (\\d+)$")
	public void elUsuarioRecibeElCodigoDeEstado(int status) {
		assertThat(result.getResponse().getStatus(), is(status));
	}

	@Then("^el usuario recibe una página con la cadena \"([^\"]*)\"$")
	public void elUsuarioRecibeUnaPaginaConLaCadena(String str) throws Throwable {
		assertThat(result.getResponse().getContentAsString(), containsString(str));
	}

}