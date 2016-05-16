package it.polimi.gis.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import io.swagger.annotations.ApiOperation;
import it.anggen.searchbean.entity.EntitySearchBean;
import it.polimi.gis.model.Marker;
import it.polimi.gis.model.MarkerPair;
import it.polimi.gis.model.Pair;
import it.polimi.gis.repository.PairRepository;

@Controller
@RequestMapping("/map")
public class MapController {

	
	@Autowired
	PairRepository pairRepository;
	
	    @ResponseBody
	    @RequestMapping(method = RequestMethod.POST)
	    public ResponseEntity search(
	        @org.springframework.web.bind.annotation.RequestBody
	        MarkerPair[] markerArray) {
	    	
	    	if (markerArray!=null && markerArray.length>0)
	    	{
	    		for (int i=0; i<markerArray.length; i++)
	    		{
	    			Pair pair = new Pair();
	    			GeometryFactory gf = new GeometryFactory();
	    			Coordinate coord = new Coordinate(markerArray[i].getMarker1().getLng(), markerArray[i].getMarker1().getLat());
	    			pair.setPointA(gf.createPoint(coord));
	    			
	    			coord = new Coordinate(markerArray[i].getMarker2().getLng(), markerArray[i].getMarker2().getLat());
	    			pair.setPointB(gf.createPoint(coord));
	    			
	    			pairRepository.save(pair);
	    		}
	    	}
	    	
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
