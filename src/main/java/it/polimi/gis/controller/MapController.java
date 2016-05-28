package it.polimi.gis.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import it.polimi.gis.core.MapTransform;
import it.polimi.gis.model.Marker;
import it.polimi.gis.model.MarkerPair;
import it.polimi.gis.model.Pair;
import it.polimi.gis.repository.PairRepository;

@Controller
@RequestMapping("/map")
public class MapController {

	
	@Autowired
	PairRepository pairRepository;
	
	@Autowired
	MapTransform mapTransform;
	
	    @ResponseBody
	    @RequestMapping(method = RequestMethod.POST)
	    public ResponseEntity search(
	        @org.springframework.web.bind.annotation.RequestBody
	        MarkerPair[] markerArray) {
	    	
	    	pairRepository.deleteCustomMarkers();
	    	Date now = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
	    	if (markerArray!=null && markerArray.length>0)
	    	{
	    		for (int i=0; i<markerArray.length; i++)
	    		{
	    			Pair pair = new Pair();
	    			GeometryFactory gf = new GeometryFactory();
	    			Coordinate coord = new Coordinate(markerArray[i].getMarker1().getLng(), markerArray[i].getMarker1().getLat());
	    			Point[] pointArr= new Point[1];
	    			pointArr[0]=gf.createPoint(coord);
	    			pair.setPointA(gf.createMultiPoint(pointArr));
	    			
	    			coord = new Coordinate(markerArray[i].getMarker2().getLng(), markerArray[i].getMarker2().getLat());
	    			pointArr[0]=gf.createPoint(coord);
	    			pair.setPointB(gf.createMultiPoint(pointArr));
	    			pair.setProject("project_1");
	    			pairRepository.save(pair);
	    		}
	    	}
	    	
	        return ResponseEntity.ok().body(null);
	    }
	    
	    
	    @ResponseBody
	    @RequestMapping(method = RequestMethod.DELETE)
	    public ResponseEntity delete() {
	    	
	    	pairRepository.deleteCustomMarkers();
	        return ResponseEntity.ok().body(null);
	    }
	    
	    
	    
	    @RequestMapping(method = RequestMethod.GET)
	    public ResponseEntity search() {
	    	
	    	List<Pair> pairList = pairRepository.findCustom();
	    	List<MarkerPair> markerPairList = new ArrayList<MarkerPair>();
	    	Integer i=0;
	    	for (Pair pair: pairList)
	    	{
	    		i++;
	    		MarkerPair markerPair = new MarkerPair();
	    		markerPair.setMarkerPairId(i);
	    		Marker marker1 = new Marker();
	    		marker1.setMessage("Marker #"+i);
	    		marker1.setLat(pair.getPointA().getInteriorPoint().getY());
	    		marker1.setLng(pair.getPointA().getInteriorPoint().getX());
	    		

	    		Marker marker2 = new Marker();
	    		marker2.setMessage("Marker #"+i);
	    		marker2.setLat(pair.getPointB().getInteriorPoint().getY());
	    		marker2.setLng(pair.getPointB().getInteriorPoint().getX());
	    		
	    		markerPair.setMarker1(marker1);
	    		markerPair.setMarker2(marker2);
	    		
	    		markerPairList.add(markerPair);
	    		
	    	}
	    	
	    	return ResponseEntity.ok().body(markerPairList);
	    }
	    
	
	
}
