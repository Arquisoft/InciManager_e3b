package es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.entities.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    /**
     * Método que devuelve el Participante el cual es buscado por email
     * en la base de datos
     * @param email del agente
     * @return El Participante con dicho email
     */
    Agent findByEmail(String email);

    /**
     *
     * @param email
     * @return
     */
    @Query("${sql.Manager.findByEmailFlexible}")
    Agent findByEmailFlexible(String email);
}
