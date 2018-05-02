package es.uniovi.asw.e3b.inci_manager;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "es.uniovi.asw.e3b.inci_manager.steps")
public class CucumberTest{
}