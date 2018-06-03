package es.uniovi.asw.e3b.incimanager_e3b.inci_manager_gest.responses;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import es.uniovi.asw.e3b.incimanager_e3b.util.Estado;

public class RespuestaAddIncidenceREST implements RespuestaREST{

	private String username; // nombre de usuario del agente
	private String password; // para autenticación en la petición REST
	private String kind;  //Tipo del agente
	private String incidenceName;
	private String description;
	private String location; // formato de la localización: "45.67, 32.86"
	private List<String> labels; // etiquetas de la incidencia
	private HashMap<String, String> campos; // campos con propiedad valor
	private Estado status; // Ver Enum: "Estado". Ej: ABIERTA, EN_PROCESO, CERRADA, ANULADA
	private String expiration; // fecha de caducidad, ej: en caso de los sensores de temperatura
	private boolean cacheable;

	public RespuestaAddIncidenceREST() {
	}

	public RespuestaAddIncidenceREST(String username, String password, String kind, String incidenceName, String description,
			String location, List<String> labels, HashMap<String, String> campos, Estado status, Date expiration,
			boolean cacheable) {
		this.username = username;
		this.password = password;
		this.kind = kind;
		this.incidenceName = incidenceName;
		this.description = description;
		this.location = location;
		this.labels = labels;
		this.campos = campos;
		this.status = status;
		this.expiration = expiration;
		this.cacheable = cacheable;
	}
	
	public void setKind(String kind){
		this.kind = kind;
	}
	
	public String getKind(){
		return kind;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	public String getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIncidenceName() {
		return incidenceName;
	}

	public void setIncidenceName(String incidenceName) {
		this.incidenceName = incidenceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public HashMap<String, String> getCampos() {
		return campos;
	}

	public void setCampos(HashMap<String, String> campos) {
		this.campos = campos;
	}

	public Estado getStatus() {
		return status;
	}

	public void setStatus(Estado status) {
		this.status = status;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
}
