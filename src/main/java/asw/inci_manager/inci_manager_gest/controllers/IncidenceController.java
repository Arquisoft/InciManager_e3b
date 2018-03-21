package asw.inci_manager.inci_manager_gest.controllers;

import asw.inci_manager.inci_manager_gest.entities.Incidence;
import asw.inci_manager.inci_manager_gest.request.IncidenceREST;
import asw.inci_manager.inci_manager_gest.responses.RespuestaAddIncidenceREST;
import asw.inci_manager.inci_manager_gest.services.IncidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IncidenceController {

    @Autowired
    IncidenceService incidenceService;

    @RequestMapping(value = "/incidences/add", method = RequestMethod.GET)
    public String addForm(){
        return "incidences/add";
    }
    
    @RequestMapping(value = "/incidences/add", method = RequestMethod.POST)
    public String addIncidence(@ModelAttribute Incidence incidence){
    	incidenceService.send(incidence);
        return "incidences/list";
    }

    @RequestMapping(value = "/incidences/list", method = RequestMethod.GET)
    public String list_incidences(){
        return "incidences/list";
    }



    /**
     * Método para añadir una incidencia que un agente envía.
     *
     * Permito get (al no especificar el método del requestmapping),
     * para asi poder comprobar visualmente la respuesta accediendo a localhost:8080/addIncidence
     *
     * @param incidenceREST
     * @return  respuesta, con fomato "id",
     */
    @RequestMapping(value = "/addIncidence")
    public ResponseEntity<RespuestaAddIncidenceREST> addIncidence(@ModelAttribute IncidenceREST incidenceREST) {
        // TODO: procesar la incidencia que se recibe
        RespuestaAddIncidenceREST res = new RespuestaAddIncidenceREST("id",incidenceREST.getIncidenceName(),"no añadida.");
        return new ResponseEntity<RespuestaAddIncidenceREST>(res, HttpStatus.OK);
    }
}
