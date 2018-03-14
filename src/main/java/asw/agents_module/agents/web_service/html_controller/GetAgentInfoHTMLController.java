package asw.agents_module.agents.web_service.html_controller;

import asw.agents_module.agents.util.Assert;
import asw.agents_module.agents.web_service.responses.errors.ErrorResponse;
import asw.agents_module.db_management.GetAgent;
import asw.agents_module.db_management.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class GetAgentInfoHTMLController {

	@Autowired
	private GetAgent getAgent;

	@RequestMapping(value = "/")
	public String inicalicerLogin(Model model) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String getLogin(HttpSession session, @RequestParam String email, @RequestParam String password,
			Model model) {

		Assert.isEmailEmpty(email);
		Assert.isEmailValid(email);
		Assert.isPasswordEmpty(password);

		Agent agent = getAgent.getAgent(email);

		Assert.isAgentNull(agent);
		Assert.isPasswordCorrect(password, agent);

		session.setAttribute("agent", agent);

		return "datosAgent";

	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponseNotFound(ErrorResponse excep, Model model) {
		model.addAttribute("error", excep.getMessageStringFormat());

		return "error";
	}
}
