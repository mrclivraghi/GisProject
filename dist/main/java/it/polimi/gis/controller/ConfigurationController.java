package it.polimi.gis.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

	
	
	  @RequestMapping(method = RequestMethod.GET)
	    public ResponseEntity findProperties(@RequestParam("layerName") String layerName) {
		  List<String> properties=getProperties(layerName);
		  return ResponseEntity.ok().body(properties);
	  }
	
	
	private List<String> getProperties(String layerName)
	{
		URL website;
		 StringBuilder response = new StringBuilder();
		 List<String> propertyList= new ArrayList<>();
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
				for (Map<String,Object> feature: featureList)
				{


					Map<String,Object> geometry=(Map<String, Object>) featureList.get(0).get("properties");
					for (String key: geometry.keySet())
					{
						if (!key.equals("OBJECTID") && !propertyList.contains(key))
							propertyList.add(key);
							
					}
					
					
				}
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
		return propertyList;
        
	}
	
	
	
	
}
