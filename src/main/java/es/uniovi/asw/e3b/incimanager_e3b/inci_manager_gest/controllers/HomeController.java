package es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.entities.Agent;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.repositories.AgentRepository;

@Controller
public class HomeController {

    @Autowired
    private AgentRepository agentRepository;

    @RequestMapping("/")
    public String home(){
        return "login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Agent activeUser = agentRepository.findByEmail(email);
        model.addAttribute("user",activeUser.getUsername());
        return "home";
    }

}
