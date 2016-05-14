package it.polimi.gis.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiOperation;
import it.anggen.searchbean.entity.EntitySearchBean;
import it.polimi.gis.model.Marker;
import it.polimi.gis.model.MarkerPair;

@Controller
@RequestMapping("/map")
public class MapController {

	
	    @ResponseBody
	    @RequestMapping(method = RequestMethod.POST)
	    public ResponseEntity search(
	        @org.springframework.web.bind.annotation.RequestBody
	        MarkerPair[] markerArray) {
	    	
	    	System.out.println("Lungh"+markerArray.length);
	    	
	    	
	        return ResponseEntity.ok().body(null);
	    }
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/loadMap", method = RequestMethod.POST)
	    private ResponseEntity loadFileMyFile(
	        @RequestParam(value = "file", required = false)
	        MultipartFile file) {

	    	Date now = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
	    	
	    	String destination="D:/uploadedFile/GisProject/"+sdf.format(now)+"/";
	    	String filePath = it.anggen.utils.Utility.saveMultipartFile(file, destination);
	    	return  ResponseEntity.ok().body(filePath);
	    }
	
	
}
