package asw.inci_manager.inci_manager_gest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import asw.inci_manager.inci_manager_gest.entities.Incidence;
import asw.inci_manager.kafka_manager.producers.KafkaProducer;


@Service
public class IncidenceService {

	@Autowired
    KafkaProducer kafkaProducer;
	
	public void send(Incidence incidence)
	{
		kafkaProducer.send("topic", new Gson().toJson(incidence));
	}
}
