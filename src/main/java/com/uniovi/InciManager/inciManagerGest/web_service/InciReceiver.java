package com.uniovi.inciManager.inciManagerGest.web_service;

import com.uniovi.inciManager.inciManagerGest.web_service.request.IncidenceREST;
import com.uniovi.inciManager.inciManagerGest.web_service.responses.RespuestaAddIncidenceREST;
import org.springframework.http.ResponseEntity;

public interface InciReceiver {

    public ResponseEntity<RespuestaAddIncidenceREST> addIncidence(IncidenceREST incidenceREST);
}
