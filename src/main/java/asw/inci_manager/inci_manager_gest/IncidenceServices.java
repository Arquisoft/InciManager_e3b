package asw.inci_manager.inci_manager_gest;

import asw.inci_manager.inci_manager_gest.entities.Agent;
import asw.inci_manager.inci_manager_gest.entities.Incidence;

import java.util.Set;

public interface IncidenceServices {
    /**
     * Envía una incidencia a Kafka
     */
    void send(Incidence incidence);
    Set<Incidence> getIncidencesFromAgent(Agent agent);
}
