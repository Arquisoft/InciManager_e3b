package es.uniovi.asw.e3b.incimanager_e3b.acceptancetests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"es.uniovi.asw.e3b.incimanager_e3b.steps"}, 
		features = "src/test/resources/features",
		plugin = {"pretty", "html:target/cucumber", "default_summary"})
public class AcceptanceTests {
}