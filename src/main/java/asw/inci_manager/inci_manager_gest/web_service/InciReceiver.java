package asw.inci_manager.inci_manager_gest.web_service;


import asw.inci_manager.inci_manager_gest.web_service.request.IncidenceREST;
import asw.inci_manager.inci_manager_gest.web_service.responses.RespuestaAddIncidenceREST;
import org.springframework.http.ResponseEntity;

public interface InciReceiver {

    public ResponseEntity<RespuestaAddIncidenceREST> addIncidence(IncidenceREST incidenceREST);
}
