package es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.repositories;

import org.springframework.data.repository.CrudRepository;

import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.entities.Agent;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.entities.Incidence;

import java.util.Set;

public interface IncidenceRepository extends CrudRepository<Incidence, Long> {

    Set<Incidence> findIncidenceByAgent(Agent agent);
    Incidence findIncidenceById(Long id);

}
