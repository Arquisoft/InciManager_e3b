package agents_module.agents.web_service;

import agents_module.agents.GetAgentInfo;
import agents_module.agents.util.Assert;
import agents_module.agents.web_service.request.PeticionInfoREST;
import agents_module.agents.web_service.responses.RespuestaInfoREST;
import agents_module.agents.web_service.responses.errors.ErrorResponse;
import agents_module.db_management.GetAgent;
import agents_module.db_management.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetAgentInfoRESTController implements GetAgentInfo {

	@Autowired
	private GetAgent getAgent;

	@Override
	@RequestMapping(value = "/user", method = RequestMethod.POST, headers = { "Accept=application/json",
			"Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(@RequestBody(required = true) PeticionInfoREST peticion) {

		Assert.isEmailEmpty(peticion.getLogin());
		Assert.isEmailValid(peticion.getLogin());
		Assert.isPasswordEmpty(peticion.getPassword());

		Agent agent = getAgent.getAgent(peticion.getLogin());

		Assert.isAgentNull(agent);

		Assert.isLoginCorrect(peticion.getPassword(), agent, peticion.getKind()); // comprueba password y kind


		/*
		 * Añadimos la información al modelo, para que se muestre en la pagina
		 * html: datosParticipant
		 */

		return new ResponseEntity<RespuestaInfoREST>(new RespuestaInfoREST(agent), HttpStatus.OK);
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error) {
		return error.getMessageJSONFormat();
	}
}
