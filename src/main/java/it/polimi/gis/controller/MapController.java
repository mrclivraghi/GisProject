package it.polimi.gis.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import io.swagger.annotations.ApiOperation;
import it.anggen.searchbean.entity.EntitySearchBean;
import it.polimi.gis.core.MapTransform;
import it.polimi.gis.model.LayerPoint;
import it.polimi.gis.model.Marker;
import it.polimi.gis.model.MarkerPair;
import it.polimi.gis.model.Pair;
import it.polimi.gis.repository.LayerPointRepository;
import it.polimi.gis.repository.PairRepository;

@Controller
@RequestMapping("/map")
public class MapController {

	
	@Autowired
	PairRepository pairRepository;
	
	@Autowired
	LayerPointRepository layerPointRepository;
	
	@Autowired
	MapTransform mapTransform;
	
	
	private void getJson(String layerName)
	{
		URL website;
		 StringBuilder response = new StringBuilder();
		try {
			website = new URL("http://localhost:8081/geoserver/gisProject/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=gisProject:"+layerName+"&maxFeatures=50&outputFormat=application%2Fjson");
			URLConnection connection = website.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                    connection.getInputStream()));

	       
	        String inputLine;

	        while ((inputLine = in.readLine()) != null) 
	            response.append(inputLine);

	        in.close();
	        
	        String layerJson=response.toString();
	        
	        ObjectMapper mapper = new ObjectMapper();
	    	try {
				Map<String,Object> mappedObject =(java.util.Map<String, Object>) mapper.readValue(layerJson, Map.class);
				List<Map<String,Object>> featureList=(List<Map<String, Object>>) mappedObject.get("features");
				Integer count=0;
				for (Map<String,Object> feature: featureList)
				{


					Map<String,Object> geometry=(Map<String, Object>) featureList.get(0).get("geometry");
					List<List<List<List<Double>>>> coordinatesList= (List<List<List<List<Double>>>>) geometry.get("coordinates");
					
					for (List<List<List<Double>>> coord1 : coordinatesList)
					{
						for (List<List<Double>> coord2 : coord1)
						{
							for (List<Double> coord3: coord2)
								{
									LayerPoint layerPoint = new LayerPoint();
									layerPoint.setLayerName(layerName);
									GeometryFactory gf = new GeometryFactory();
					    			Coordinate coord = new Coordinate(coord3.get(0), coord3.get(1));
					    			layerPoint.setPoint(gf.createPoint(coord));
					    			layerPointRepository.save(layerPoint);
									
								}
						}
					}
				}
				System.out.println(count);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



	        
	        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	
	    @ResponseBody
	    @RequestMapping(method = RequestMethod.POST)
	    public ResponseEntity search(
	    	@RequestParam("layer1") String layer1,
	    	@RequestParam("layer2") String layer2,
	        @org.springframework.web.bind.annotation.RequestBody
	        MarkerPair[] markerArray) {
	    	
	    	layerPointRepository.deleteLayerPoint();
	    	
	    	getJson(layer1);
	    	getJson(layer2);
	    	
	    		    	
	    	pairRepository.deleteCustomMarkers();
	    	Date now = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
	    	if (markerArray!=null && markerArray.length>0)
	    	{
	    		for (int i=0; i<markerArray.length; i++)
	    		if (markerArray[i].getMarker1()!=null && markerArray[i].getMarker2()!=null)
	    		{
	    			Pair pair = new Pair();
	    			GeometryFactory gf = new GeometryFactory();
	    			//Coordinate coord = new Coordinate(markerArray[i].getMarker1().getLng(), markerArray[i].getMarker1().getLat());
	    			
	    			
	    			List<LayerPoint> closestPoint=layerPointRepository.findClosest(String.valueOf(markerArray[i].getMarker1().getLat()),String.valueOf(markerArray[i].getMarker1().getLng()), layer1);
	    			
	    			
	    			Point[] pointArr= new Point[1];
	    			pointArr[0]=closestPoint.get(0).getPoint();
	    			pair.setPointA(gf.createMultiPoint(pointArr));
	    			markerArray[i].getMarker1().setLat(closestPoint.get(0).getPoint().getY());
	    			markerArray[i].getMarker1().setLng(closestPoint.get(0).getPoint().getX());
	    			
	    			closestPoint=layerPointRepository.findClosest(String.valueOf(markerArray[i].getMarker2().getLat()), String.valueOf(markerArray[i].getMarker2().getLng()), layer1);
	    			
	    			//coord = new Coordinate(markerArray[i].getMarker2().getLng(), markerArray[i].getMarker2().getLat());
	    			pointArr[0]=closestPoint.get(0).getPoint();
	    			markerArray[i].getMarker2().setLat(closestPoint.get(0).getPoint().getY());
	    			markerArray[i].getMarker2().setLng(closestPoint.get(0).getPoint().getX());
	    			
	    			pair.setPointB(gf.createMultiPoint(pointArr));
	    			pair.setProject("project_1");
	    			pairRepository.save(pair);
	    		}
	    	}
	    	
	        return ResponseEntity.ok().body(markerArray);
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
