package es.uniovi.asw.e3b.inci_manager.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import es.uniovi.asw.e3b.InciManagerApplication;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { InciManagerApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration

public class AuthenticationSteps {

	private static final Logger logger = Logger.getLogger(AuthenticationSteps.class);

	@Autowired
	protected WebApplicationContext context;
	private WebDriver driver;
	@Value("${local.server.port:8091}")
	private int port;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	private int implicitTimeout = 10;
	private int explicitTimeout = 30;

	@Before
	public void setUp() {
		driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:" + port;
		logger.debug("BaseURL: '" + baseUrl + "'");
		driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
	}

	@Dado("^un agente registrado en el sistema con el nombre de usuario \"([^\"]*)\" y la contraseña \"([^\"]*)\"$")
	public void unAgenteRegistradoEnElSistemaConElNombreDeUsuarioYLaContrasenia(String username,
			String password) {
		logger.info("Dado un agente registrado en el sistema con el nombre de usuario: '" + username
				+ "' y la contraseña: '" + password + "'");
	}

	@Y("^una vez situado en la página de inicio de sesión \"([^\"]*)\"$")
	public void unaVezSituadoEnLaPaginaDeIncioDeSesion(String loginRelativeUrl) {
		logger.info("Y una vez situado en la página de inicio de sesión: '" + loginRelativeUrl + "'");
		driver.navigate().to(baseUrl + loginRelativeUrl);
		// ToDO: Wait for element
		assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + loginRelativeUrl);
	}

	@Cuando("^introduzco \"([^\"]*)\" y \"([^\"]*)\" en los campos \"([^\"]*)\" y \"([^\"]*)\"$")
	public void introduzcoUsernameYPasswordEnLosCamposUsernameYPassword(String username, String password,
			String usernameElement, String passwordElement) {
		logger.info("Cuando introduzco el nombre de usuario: '" + username + "' y la contraseña: '" + password
				+ "' en los campos: '" + usernameElement + "' y '" + passwordElement + "'");
		new WebDriverWait(driver, explicitTimeout)
				.until(ExpectedConditions.visibilityOfElementLocated(By.name(usernameElement)));
		driver.findElement(By.name(usernameElement)).sendKeys(username);
		driver.findElement(By.name(passwordElement)).sendKeys(password);
	}

	@Cuando("^presiono el botón de confirmación \"([^\"]*)\"$")
	public void presionoElBoton(String buttonElement) {
		logger.info("Y presiono el botón de confirmación: '" + buttonElement + "'");
		driver.findElement(By.id(buttonElement)).click();
	}

	@Entonces("^puedo procesar incidencias desde la página principal \"([^\"]*)\"$")
	public void puedoProcesarIncidenciasDesdeLaPaginaPrincipal(String homeRelativeUrl) {
		logger.info("Entonces puedo procesar incidencias desde la página principal: '" + homeRelativeUrl + "'");
		assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + homeRelativeUrl);
	}

	@Dado("^un agente no registrado en el sistema con el nombre de usuario \"([^\"]*)\" y la contraseña \"([^\"]*)\"$")
	public void unAgenteNoRegistradoEnElSistemaConElNombreDeUsuarioYLaContrasenia(String username,
			String password) {
		logger.info("Dado un agente no registrado en el sistema con el nombre de usuario: '" + username
				+ "' y la contraseña: '" + password + "'");
	}

	@Entonces("^recibo una notificación de error de acceso \"([^\"]*)\"$")
	public void reciboUnaNotificacionDeErrorDeAcceso(String loginRelativeUrlError) {
		logger.info("Entonces recibo una notifiación de error de acceso: '" + loginRelativeUrlError + "'");
		assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + loginRelativeUrlError);
	}

	@Y("^no puedo procesar incidencias desde la página principal \"([^\"]*)\"$")
	public void noPuedoProcesarIncidenciasDesdeLaPaginaPrincipal(String homeRelativeUrl) {
		logger.info("Y no puedo procesar incidencias desde la página principal: '" + homeRelativeUrl + "'");
		driver.navigate().to(baseUrl + homeRelativeUrl);
		assertThat(driver.getCurrentUrl()).isNotEqualTo(baseUrl + homeRelativeUrl);
	}

	@After
	public void tearDown() {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
