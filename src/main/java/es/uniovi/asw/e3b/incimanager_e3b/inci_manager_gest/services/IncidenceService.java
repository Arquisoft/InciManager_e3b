package es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.services;

import com.google.gson.Gson;

import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.IncidenceServices;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.entities.Agent;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.entities.Incidence;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.repositories.IncidenceRepository;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.request.IncidenceREST;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.responses.RespuestaAddIncidenceREST;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.responses.RespuestaFailedREST;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.responses.RespuestaREST;
import es.uniovi.asw.e3b.incimanager_e3b.kafka.producers.KafkaProducer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class IncidenceService implements IncidenceServices {

	private static final Logger logger = Logger.getLogger(IncidenceService.class);

	@Value("${kafka.topic:incidences}")
	private String kafkaTopic;
	
	@Autowired
	private KafkaProducer kafkaProducer;

	@Autowired
	private IncidenceRepository incidenceRepository;

	public void send(Incidence incidence) {		
	SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
		
	List<String> labels = new ArrayList();
	labels.addAll(incidence.getLabels());

		RespuestaAddIncidenceREST response = new RespuestaAddIncidenceREST(incidence.getAgent().getUsername(), incidence.getAgent().getPassword(), "1",
					incidence.getIncidenceName(), incidence.getDescription(), incidence.getLocation(),
					labels, incidence.getFields(), incidence.getStatus(),
					formateador.format(incidence.getExpiration()), incidence.isCacheable());
		kafkaProducer.send(kafkaTopic, new Gson().toJson(response));
		logger.info("Sending incidence \"" + incidence.getIncidenceName() + "\" to topic '" + kafkaTopic + "'");
	}

	public RespuestaREST send(IncidenceREST incidenceREST, Agent agent)
		{			
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");

			if (agent != null && agent.getPassword().equals(incidenceREST.getPassword())&&incidenceREST.isCacheable()) {
								
				RespuestaAddIncidenceREST response = new RespuestaAddIncidenceREST(incidenceREST.getUsername(), incidenceREST.getPassword(), agent.getKind(), 
					incidenceREST.getIncidenceName(), incidenceREST.getDescription(), incidenceREST.getLocation(),
					incidenceREST.getLabels(), incidenceREST.getCampos(), incidenceREST.getStatus(),
					formateador.format(incidenceREST.getExpiration()), incidenceREST.isCacheable());
				
				kafkaProducer.send(kafkaTopic, new Gson().toJson(response));
				logger.info("Sending incidence \"" + incidenceREST.getIncidenceName() + "\" to topic '" + kafkaTopic + "'");
				return response;
			} else if (incidenceREST.isCacheable()){
				logger.info("Wrong authentication, incidence not sending");
				return new RespuestaFailedREST("Wrong authentication, incidence not sending");
			}else {
				logger.info("Not cacheable, incidence not sending");
				return new RespuestaFailedREST("Not cacheable, incidence not sending");
			}

	 	}

	/**
	 * Devuelve las incidencias de un agente pasado por parámetro
	 * 
	 * @param agent
	 *            del que quieres obtener las incidencias
	 * @return lista de incidencias
	 */
	public Set<Incidence> getIncidencesFromAgent(Agent agent) {
		return incidenceRepository.findIncidenceByAgent(agent);
	}

	/**
	 * Recibe una incidencia y la almacena en la base de datos
	 *
	 * @param incidence
	 *            incidencia a guardar en la base de datos
	 */
	public void addIncidence(Incidence incidence) {
		incidenceRepository.save(incidence);
	}

	/**
	 * Retorna una incidencia buscando el id por parámetro
	 * 
	 * @param id,
	 *            incidencia a buscar
	 * @return la incidencia buscada
	 */
	public Incidence getIncidenceById(Long id) {
		return incidenceRepository.findIncidenceById(id);
	}

	/**
	 * Recibe del formulario un String de incidencias separadas por comas y lo
	 * devuelve como un hashset de strings
	 * 
	 * @param label
	 *            String de incidencias separadas por comas
	 * @return HashSet de Strings
	 */
	public Set<String> labelsParser(String label) {
		String[] etiquetas = label.split(",");
		Set<String> labels = new HashSet<String>();
		for (String string : etiquetas) {
			labels.add(string);
		}
		return labels;
	}

	/**
	 * Recibe de formulario un String con la forma campoA : valordelcampoA ;
	 * campoB: valordelcampoB ;campoC : valordelcampoC ;
	 * Luego, lo separa por ";"
	 * Y lo mete en un mapa después de separar por ":"
	 * @param fields String de campos
	 * @return HashMap con el resultado
	 */
	public HashMap<String, String> fielsParser(String fields) {
		HashMap<String, String> mapa = new HashMap<>();
		String[] pares = fields.split(";");
		for (String string : pares) {
			String[] valores = string.split(":");
			if(valores.length==2)
			mapa.put(valores[0], valores[1]);
		}
		return mapa;
	}
}
