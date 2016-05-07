package it.polimi.gis.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiOperation;
import it.anggen.searchbean.entity.EntitySearchBean;
import it.polimi.gis.model.Marker;

@Controller
@RequestMapping("/marker")
public class MarkerController {

	
	    @ResponseBody
	    @RequestMapping(method = RequestMethod.POST)
	    public ResponseEntity search(
	        @org.springframework.web.bind.annotation.RequestBody
	        Marker[] markerArray) {
	    	
	    	
	    	
	        return ResponseEntity.ok().body(null);
	    }
	
	
}
