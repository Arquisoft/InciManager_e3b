package asw.inci_manager.inci_manager_gest;

import asw.inci_manager.inci_manager_gest.request.IncidenceREST;
import asw.inci_manager.inci_manager_gest.responses.RespuestaREST;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

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
