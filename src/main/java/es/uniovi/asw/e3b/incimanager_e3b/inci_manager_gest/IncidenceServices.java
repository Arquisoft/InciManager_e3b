package es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest;

import java.util.Set;

import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.entities.Agent;
import es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.entities.Incidence;

public interface IncidenceServices {
    /**
     * Envía una incidencia a Kafka
     */
    void send(Incidence incidence);
    Set<Incidence> getIncidencesFromAgent(Agent agent);
}
