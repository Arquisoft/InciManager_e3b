package asw.inci_manager.inci_manager_gest.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import asw.inci_manager.inci_manager_gest.entities.Incidence;
import asw.inci_manager.kafka_manager.producers.KafkaProducer;


@Service
public class IncidenceService {

	private static final Logger logger = Logger.getLogger(IncidenceService.class);
	
	@Autowired
    KafkaProducer kafkaProducer;
	
	public void send(Incidence incidence)
	{
		// ToDO: Incorporar un campo topic dinámico o incluirlo como propertie:
		kafkaProducer.send("topic", new Gson().toJson(incidence));
		logger.info("Sending incidence \"" + incidence.getIncidenceName() + "\" to topic '" + "topic" +"'");
	}
}
