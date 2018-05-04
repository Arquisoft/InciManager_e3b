package es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.entities.Agent;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.repositories.AgentRepository;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    public Agent getAgentByEmailFlexible(String email){
        return agentRepository.findByEmailFlexible(email);
    }
}
