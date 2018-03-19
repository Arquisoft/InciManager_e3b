package asw.inci_manager.inci_manager_gest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IncidenceController {

    @RequestMapping(value = "/incidences/add", method = RequestMethod.GET)
    public String add(){
        return "incidences/add";
    }

    @RequestMapping(value = "/incidences/list", method = RequestMethod.GET)
    public String list_incidences(){
        return "incidences/list";
    }
}
