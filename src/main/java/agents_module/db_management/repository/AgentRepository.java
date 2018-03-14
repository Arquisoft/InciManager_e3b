package agents_module.db_management.repository;

import agents_module.db_management.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
	
	/**
	 * Método que devuelve el Participante el cual es buscado por email
	 * en la base de datos
	 * @param email del agente
	 * @return El Participante con dicho email
	 */
	public Agent findByEmail(String email);
	
}
