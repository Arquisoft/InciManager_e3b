package asw.inci_manager.inci_manager_gest.controllers;

import asw.inci_manager.inci_manager_gest.entities.Agent;
import asw.inci_manager.inci_manager_gest.entities.Incidence;
import asw.inci_manager.inci_manager_gest.request.IncidenceREST;
import asw.inci_manager.inci_manager_gest.responses.RespuestaAddIncidenceREST;
import asw.inci_manager.inci_manager_gest.services.AgentService;
import asw.inci_manager.inci_manager_gest.services.IncidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IncidenceController {

    @Autowired
    IncidenceService incidenceService;
    @Autowired
    AgentService agentService;

    @RequestMapping(value = "/incidences/add", method = RequestMethod.GET)
    public String addForm() {
        return "incidences/add";
    }

    @RequestMapping(value = "/incidences/add", method = RequestMethod.POST)
    public String addIncidenceFormulario(@ModelAttribute Incidence incidence) {
        // TODO: Aquí pedir los parametros por RequestParam <- más viable
        // TODO: completar el formulario html con los parámetros que faltan de incidencia.
        // TODO: hacer un parser de la lista de etiquetas, porque la de comentarios y "otros" deberían rellarla los operarios

        incidenceService.send(incidence);

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
    public ResponseEntity<RespuestaAddIncidenceREST> addIncidence(@ModelAttribute IncidenceREST incidenceREST) {
        // TODO: procesar la incidencia que se recibe
        RespuestaAddIncidenceREST res = new RespuestaAddIncidenceREST("id", incidenceREST.getIncidenceName(), "no añadida.");
        return new ResponseEntity<RespuestaAddIncidenceREST>(res, HttpStatus.OK);
    }
}
