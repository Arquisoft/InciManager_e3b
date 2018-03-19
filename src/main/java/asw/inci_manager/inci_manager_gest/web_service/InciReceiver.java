package asw.inci_manager.inci_manager_gest.web_service;

<<<<<<< HEAD

=======
>>>>>>> f137d71c2b7c484f6130656072a86ebed0546e12
import asw.inci_manager.inci_manager_gest.web_service.request.IncidenceREST;
import asw.inci_manager.inci_manager_gest.web_service.responses.RespuestaAddIncidenceREST;
import org.springframework.http.ResponseEntity;

public interface InciReceiver {

    public ResponseEntity<RespuestaAddIncidenceREST> addIncidence(IncidenceREST incidenceREST);
}
