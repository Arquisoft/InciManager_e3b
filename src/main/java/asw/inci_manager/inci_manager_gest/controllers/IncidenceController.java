package asw.inci_manager.inci_manager_gest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import asw.inci_manager.inci_manager_gest.entities.Agent;
import asw.inci_manager.inci_manager_gest.entities.Incidence;
import asw.inci_manager.inci_manager_gest.request.IncidenceREST;
import asw.inci_manager.inci_manager_gest.responses.RespuestaREST;
import asw.inci_manager.inci_manager_gest.services.AgentService;
import asw.inci_manager.inci_manager_gest.services.IncidenceService;

@Controller
public class IncidenceController {

    @Autowired
    private IncidenceService incidenceService;
    @Autowired
    private AgentService agentService;
    

    @RequestMapping(value = "/incidences/add", method = RequestMethod.GET)
    public String addForm() {
        return "incidences/add";
    }
    
    @RequestMapping(value = "/incidences/error", method = RequestMethod.GET)
    public String error(Model model, @ModelAttribute("message") String errmsg) {
    	model.addAttribute("msg", errmsg);
        return "incidences/errormsg";
    }

    @RequestMapping(value = "/incidences/add", method = RequestMethod.POST)
    public String addIncidenceFormulario(@RequestParam(value = "incidenceName") String incidenceName,
    		@RequestParam(value = "description", required=false) String description,
            @RequestParam(value = "location", required=false) String location,
            @RequestParam(value = "labels", required=false) String labels,
            @RequestParam(value = "others", required=false) String others,
            @RequestParam(value = "fields", required=false) String fields,
            RedirectAttributes redirectAttrs) {
    	
    	
    	if(incidenceName=="") {
    		   redirectAttrs.addFlashAttribute("message", "Es necesario dar un nombre a la incidencia");
    		   return "redirect:/incidences/error";
    	}if(description=="") {
 		   redirectAttrs.addFlashAttribute("message", "La descripcion es demasiado breve");
    		return "redirect:/incidences/error";
    	}if(location=="") {
  		   redirectAttrs.addFlashAttribute("message", "Es necesario dar una localizacion a la incidencia");
    		return "redirect:/incidences/error";
    	}if(labels=="") {
   		   redirectAttrs.addFlashAttribute("message", "Es necesario especificar alguna etiqueta para la incidencia");
    		return "redirect:/incidences/error";
    	}
    	
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Agent activeAgent = agentService.getAgentByEmailFlexible(email);
		
		Incidence i = new Incidence(activeAgent, incidenceName, description, location, incidenceService.labelsParser(labels));
		i.setCacheable(true);
		i.setOthers(incidenceService.labelsParser(others));
		i.setFields(incidenceService.fielsParser(fields));		
		
		//Descomentar en caso de querer insertar en la base de datos.
		//incidenceService.addIncidence(i);
		
        incidenceService.send(i);
        System.out.println(i);
        return "redirect:/incidences/list";
    }

    @RequestMapping(value = "/incidences/list", method = RequestMethod.GET)
    public String listIncidences(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Agent agenteLogueado = agentService.getAgentByEmailFlexible(email);
        model.addAttribute("incidenceList", incidenceService.getIncidencesFromAgent(agenteLogueado));
        return "incidences/list";
    }

    @RequestMapping(value = "/incidences/view")
    public String viewIncidence(Model model, @RequestParam(required = false) Long id) {
        if (id == null) {
            return "redirect:/incidences/list";
        } else {
            Incidence incidence = incidenceService.getIncidenceById(id);
            model.addAttribute("incidence", incidence);
            return "/incidences/view";
        }
    }

    /**
     * Método para añadir una incidencia que un agente envía.
     *
     * Permito get (al no especificar el método del requestmapping),
     * para asi poder comprobar visualmente la respuesta accediendo a localhost:8080/addIncidence
     *
     * @param incidenceREST
     * @return respuesta, con fomato "id",
     */
	@RequestMapping(value = "/addIncidence")
	public ResponseEntity<RespuestaREST> addIncidence(@RequestBody IncidenceREST incidenceREST) {
		// TODO: procesar la incidencia que se recibe
		Agent agent = agentService.getAgentByEmailFlexible(incidenceREST.getUsername());
		RespuestaREST res;
		res = incidenceService.send(incidenceREST, agent);
		return new ResponseEntity<RespuestaREST>(res, HttpStatus.OK);
	}
}
