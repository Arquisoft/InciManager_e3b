package asw.inci_manager.validators;

import asw.inci_manager.inci_manager_gest.entities.Incidence;
import asw.inci_manager.inci_manager_gest.services.IncidenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class SendIncidenceFormValidator implements Validator {
	@Autowired
	private IncidenceService incidencesService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Incidence.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Incidence i = (Incidence) target;
		if (i.getIncidenceName().length() < 1) {
			errors.rejectValue("dni", "Error.name.length");
		}
		if (i.getDescription().length() < 1) {
			errors.rejectValue("name", "Error.description.length");
		}
		if (i.getLocation().length() < 1) {
			errors.rejectValue("lastName", "Error.location.length");
		}
		if (i.getExpiration() == null) {
			errors.rejectValue("password", "Error.expiration.length");
		}
	}
}
