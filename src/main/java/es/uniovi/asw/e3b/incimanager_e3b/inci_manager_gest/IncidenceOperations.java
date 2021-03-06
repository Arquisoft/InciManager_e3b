package es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.request.IncidenceREST;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.responses.RespuestaREST;

public interface IncidenceOperations {

    /**
     * Método que envía una incidencia en formato JSON (se procesa en Kafka) y recibe una respuesta
     */
    ResponseEntity<RespuestaREST> addIncidence(IncidenceREST incidenceREST);

    /**
     * Método que recibe las incidencias del agente logueado y las lista.
     */
    String listIncidences(Model model);
}
