package com.uniovi.inciManager.inciManagerGest.web_service.controllers;


import com.uniovi.inciManager.inciManagerGest.web_service.InciReceiver;
import com.uniovi.inciManager.inciManagerGest.web_service.request.IncidenceREST;
import com.uniovi.inciManager.inciManagerGest.web_service.responses.RespuestaAddIncidenceREST;
import com.uniovi.inciManager.inciManagerGest.web_service.services.InciReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class inciReceiverController implements InciReceiver {

    @Autowired
    InciReceiverService inciReceiverService;

    /**
     * Método para añadir una incidencia que un agente envía.
     *
     * Permito get (al no especificar el método del requestmapping),
     * para asi poder comprobar visualmente la respuesta accediendo a localhost:8080/addIncidence
     *
     * @param incidenceREST
     * @return  respuesta, con fomato "id",
     */
    @RequestMapping(value = "/addIncidence")
    public ResponseEntity<RespuestaAddIncidenceREST> addIncidence(@ModelAttribute IncidenceREST incidenceREST) {
        // TODO: procesar la incidencia que se recibe
        RespuestaAddIncidenceREST res = new RespuestaAddIncidenceREST("id",incidenceREST.getIncidenceName(),"no añadida.");
        return new ResponseEntity<RespuestaAddIncidenceREST>(res, HttpStatus.OK);
    }

}
