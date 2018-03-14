package asw.agents_module.agents;

import asw.agents_module.agents.web_service.request.PeticionInfoREST;
import asw.agents_module.agents.web_service.responses.RespuestaInfoREST;
import org.springframework.http.ResponseEntity;

public interface GetAgentInfo {

	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(PeticionInfoREST peticion);

}
