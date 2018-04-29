package asw.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import asw.InciManagerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {InciManagerApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumTest {
	
	private static final Logger logger = Logger.getLogger(SeleniumTest.class);
	
	private WebDriver driver;
	@Value("${local.server.port:8091}")
	private int port;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	private int timeout = 9;

	@Before
	public void setUp() {
		driver = new HtmlUnitDriver();	
		baseUrl = "http://localhost:" + port;
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		logger.info("Using base URL: '" + baseUrl + "'");
	}

	// Página raiz disponible:
	@Test
	public void availableRootPageTest() throws Exception {
		driver.navigate().to(baseUrl + "/");
		try {
			assertEquals("InciManager", driver.getTitle());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	//Inicio de sesión con datos válidos.
	@Test
	public void testUnioviTest3() throws Exception {
		driver.navigate().to(baseUrl);
		login("paco@gmail.com", "123456");
		try {
			assertTrue(driver.getCurrentUrl().equals(baseUrl + "/home"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
	
	//Envio de incidencia valida.
	@Test
	public void testSendIncidence() throws Exception {
		driver.navigate().to(baseUrl);
		login("paco@gmail.com", "123456");
		driver.navigate().to(baseUrl+"/incidences/add");
		driver.findElement(By.name("incidenceName")).sendKeys("1");
		driver.findElement(By.name("description")).sendKeys("1");
		driver.findElement(By.name("location")).sendKeys("1");
		driver.findElement(By.name("labels")).sendKeys("1");
		driver.findElement(By.name("others")).sendKeys("1");
		driver.findElement(By.name("fields")).sendKeys("1:2");
		driver.findElement(By.cssSelector("button[class='btn btn-primary btn-block']")).click();
		try {
			assertTrue(driver.getCurrentUrl().equals(baseUrl + "/incidences/list"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
	
	//Envio de incidencia sin nombre.
	@Test
	public void testSendNoNamedIncidence() throws Exception {
		driver.navigate().to(baseUrl);
		login("paco@gmail.com", "123456");
		driver.navigate().to(baseUrl+"/incidences/add");
		driver.findElement(By.name("description")).sendKeys("1");
		driver.findElement(By.name("location")).sendKeys("1");
		driver.findElement(By.name("labels")).sendKeys("1");
		driver.findElement(By.name("others")).sendKeys("1");
		driver.findElement(By.name("fields")).sendKeys("1:2");
		driver.findElement(By.cssSelector("button[class='btn btn-primary btn-block']")).click();
		try {
			assertTrue(driver.getCurrentUrl().equals(baseUrl + "/incidences/error"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
	
	//Envio de incidencia sin descripcion.
	@Test
	public void testSendNoDescriptedIncidence() throws Exception {
		driver.navigate().to(baseUrl);
		login("paco@gmail.com", "123456");
		driver.navigate().to(baseUrl+"/incidences/add");
		driver.findElement(By.name("incidenceName")).sendKeys("1");
		driver.findElement(By.name("location")).sendKeys("1");
		driver.findElement(By.name("labels")).sendKeys("1");
		driver.findElement(By.name("others")).sendKeys("1");
		driver.findElement(By.name("fields")).sendKeys("1:2");
		driver.findElement(By.cssSelector("button[class='btn btn-primary btn-block']")).click();
		try {
			assertTrue(driver.getCurrentUrl().equals(baseUrl + "/incidences/error"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
	
	//Envio de incidencia sin localizacion.
	@Test
	public void testSendNoLocatedIncidence() throws Exception {
		driver.navigate().to(baseUrl);
		login("paco@gmail.com", "123456");
		driver.navigate().to(baseUrl+"/incidences/add");
		driver.findElement(By.name("description")).sendKeys("1");
		driver.findElement(By.name("incidenceName")).sendKeys("1");
		driver.findElement(By.name("labels")).sendKeys("1");
		driver.findElement(By.name("others")).sendKeys("1");
		driver.findElement(By.name("fields")).sendKeys("1:2");
		driver.findElement(By.cssSelector("button[class='btn btn-primary btn-block']")).click();
		try {
			assertTrue(driver.getCurrentUrl().equals(baseUrl + "/incidences/error"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
	
	//Envio de incidencia sin etiquetas.
	@Test
	public void testSendNoLabeledIncidence() throws Exception {
		driver.navigate().to(baseUrl);
		login("paco@gmail.com", "123456");
		driver.navigate().to(baseUrl+"/incidences/add");
		driver.findElement(By.name("description")).sendKeys("1");
		driver.findElement(By.name("location")).sendKeys("1");
		driver.findElement(By.name("incidenceName")).sendKeys("1");
		driver.findElement(By.name("others")).sendKeys("1");
		driver.findElement(By.name("fields")).sendKeys("1:2");
		driver.findElement(By.cssSelector("button[class='btn btn-primary btn-block']")).click();
		try {
			assertTrue(driver.getCurrentUrl().equals(baseUrl + "/incidences/error"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
		
	/**
	 * Método auxiliar para loguearse
	 * 
	 * @param username
	 *            correo del usuario
	 * @param password
	 *            contraseña
	 */
	private void login(String username, String password) {
		esperaHastaQueCargueByName("username");
		rellenarByName("username", username);
		rellenarByName("password", password);
		click("loginButton");
	}

	/**
	 * Método auxiliar para esperar que carguen las páginas. Usa la variable timeout
	 * de 9 segundos en caso de tardar mucho
	 * 
	 * @param string
	 *            ID del elemento que esperas que cargue
	 */
	private WebElement esperaHastaQueCargue(String string) {
		return (new WebDriverWait(driver, timeout)).until(ExpectedConditions.visibilityOfElementLocated(By.id(string)));
	}

	/**
	 * Método auxiliar para hacer click en elementos.
	 * 
	 * @param string
	 *            ID del elemento en el que quieres hacer click
	 */
	private void click(String string) {
		driver.findElement(By.id(string)).click();
	}

	/**
	 * Método auxiliar para rellenar campos de formularios
	 * @param nombre nombre del campo a rellenar
	 * @param texto valor
	 */
	private void rellenarByName(String nombre, String texto) {
		driver.findElement(By.name(nombre)).sendKeys(texto);
	}
	
	/**
	 * Método auxiliar para esperar que carguen las páginas. Usa la variable timeout
	 * de 9 segundos en caso de tardar mucho
	 * 
	 * @param string
	 *            nombre del elemento que esperas que cargue
	 */
	private WebElement esperaHastaQueCargueByName(String string) {
		return (new WebDriverWait(driver, timeout)).until(ExpectedConditions.visibilityOfElementLocated(By.name(string)));
	}

	/**
	 * Método auxiliar para rellenar campos de formularios
	 * 
	 * @param campoID
	 *            ID del campo a rellenar
	 * @param texto
	 *            valor
	 */
	private void rellenar(String campoID, String texto) {
		driver.findElement(By.id(campoID)).sendKeys(texto);
	}

	/**
	 * Aborta si el "texto" no está presente en la página actual
	 * 
	 * @param driver:
	 *            apuntando al navegador abierto actualmente.
	 * @param texto:
	 *            texto a buscar
	 */
	static public void textoPresentePagina(WebDriver driver, String texto) {
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + texto + "')]"));
		assertTrue("Texto " + texto + " no localizado!", list.size() > 0);
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
