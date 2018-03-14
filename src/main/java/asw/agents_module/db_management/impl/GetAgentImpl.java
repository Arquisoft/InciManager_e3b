package asw.agents_module.db_management.impl;

import asw.agents_module.db_management.GetAgent;
import asw.agents_module.db_management.model.Agent;
import asw.agents_module.db_management.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GetAgentImpl implements GetAgent {
	
	private AgentRepository repository;
	
	@Autowired
	public GetAgentImpl(AgentRepository repository) {
		this.repository = repository;
	}
	
	
	/**
	 * Método que devuelve el Participante buscado por email
	 * Hace uso del método findByEmail (mapeador)
	 */
	@Override
	public Agent getAgent(String email) {
		
		return this.repository.findByEmail(email);
	}

}
