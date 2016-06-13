package it.polimi.gis.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.ServiceInfo;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.shapefile.shp.ShapefileReader;
import org.geotools.data.simple.SimpleFeatureSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import it.polimi.gis.model.MapFile;
import it.polimi.gis.model.Marker;
import it.polimi.gis.model.MarkerPair;
import it.polimi.gis.model.Pair;
import it.polimi.gis.model.bean.AlgorithmBean;
import it.polimi.gis.repository.LayerPointRepository;
import it.polimi.gis.repository.PairRepository;
import it.polimi.gis.service.GeoServerService;
import it.polimi.gis.service.MapFileService;
import it.polimi.gis.util.Utility;

@Controller
@RequestMapping("/algorithm")
public class AlgorithmController {

	
	@Autowired
	PairRepository pairRepository;
	
	@Autowired
	MapTransform mapTransform;
	
	@Autowired
	MapFileService mapFileService;
	
	@Autowired
	GeoServerService geoServerService;
	
	@Autowired
	LayerPointRepository layerPointRepository;
	
	    @ResponseBody
	    @RequestMapping(method = RequestMethod.POST)
	    public ResponseEntity run(
	        @RequestBody AlgorithmBean algorithmBean) {
	    	
	    	String layer1="DBT_00_4326";
	    	String layer2="OSM_00_4326";
	    	
	    	mapTransform.setParameters(createParametersMap(algorithmBean));
	    	mapTransform.setSemantics(createAssociationMap(algorithmBean));
	    	List<Pair> pairList= pairRepository.findByProject("project_1");
	    	ArrayList<Point> sourcePoints= new ArrayList<>();
	    	ArrayList<Point> targetPoints= new ArrayList<>();
	    	for (Pair pair: pairList)
	    	{
	    		sourcePoints.add(pair.getPointA().getInteriorPoint());
	    		targetPoints.add(pair.getPointB().getInteriorPoint());
	    	}
	    	mapTransform.setSourcePoints(sourcePoints);
	    	mapTransform.setTargetPoints(targetPoints);
	    	mapTransform.setPairs((ArrayList<Pair>) pairList);
	    	
	    	DataStore sourceDataStore=loadDataStore(layer1+".shp");
	    	DataStore targetDataStore=loadDataStore(layer2+".shp");
	    	mapTransform.setDataStoreSource(sourceDataStore);
	    	mapTransform.setDataStoreTarget(targetDataStore);
	    	
	    	ArrayList<Pair> homologousList=mapTransform.findHomologousPoints();
	    	//truncate to first 100
	    	
	    	List<MarkerPair> markerPairList = new ArrayList<MarkerPair>();
	    	Integer i=0;
	    	for (Pair pair: homologousList)
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
	    	
	        return ResponseEntity.ok().body(markerPairList.subList(0, markerPairList.size()>100? 100: markerPairList.size()));
	    }
	    
	    
	    @ResponseBody
	    @RequestMapping(method = RequestMethod.POST,value="/result")
	    public ResponseEntity getResult(
	    		 @org.springframework.web.bind.annotation.RequestBody
	 	        MarkerPair[] markerArray) {
	    	
	    	pairRepository.deleteCustomMarkers();
	    	Date now = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
	    	ArrayList<Pair> pairList = new ArrayList<Pair>();
	    	if (markerArray!=null && markerArray.length>0)
	    	{
	    		for (int i=0; i<markerArray.length; i++)
	    			if (markerArray[i].getMarker1()!=null && markerArray[i].getMarker2()!=null)
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
	    			pairList.add(pair);
	    		}
	    	}
	    	
	    	//obtain result
	    	DataStore result = mapTransform.transform(pairList);
	    	File shapeFile = Utility.getShapeFileFromDataStore(result);
	    	String zipFilePath="";
	    	if (shapeFile!=null)
	    	{
	    		byte[] buffer = new byte[1024];
	    		ZipOutputStream zos;
				try {
					zipFilePath=shapeFile.getAbsolutePath().replace(".shp", ".zip");
					File zip= new File(zipFilePath);
					zip.createNewFile();
					zos = new ZipOutputStream(new FileOutputStream(zipFilePath));
					ZipEntry ze= new ZipEntry(shapeFile.getName());
		    		zos.putNextEntry(ze);
		    		
		    		FileInputStream in = new FileInputStream(shapeFile);
		   	   
		    		int len;
		    		while ((len = in.read(buffer)) > 0) {
		    			zos.write(buffer, 0, len);
		    		}
		    		in.close();
		    		zos.closeEntry();
		    		zos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	           
	    		//remember close it
	    		
	    		
	    	    	MapFile newMapFile = new MapFile();
	    	    	newMapFile.setFilePath(shapeFile.getAbsolutePath());
	    	    	newMapFile.setWorkSpace("gisProject");
	    	    	newMapFile.setName(shapeFile.getName().replaceAll(".shp", ""));
	    	    	
	    	    	//mapFileService.insert(newMapFile);
	    	    	
	    	    	geoServerService.loadZipFile(new File(zipFilePath));
	    	    	
	    	}
	    	
	    	return ResponseEntity.ok().body(shapeFile.getName().replace(".shp", ""));
	    	
	    }
	    
	    
	    
	    @ResponseBody
	    @RequestMapping(method = RequestMethod.POST,value="/statistics")
	    public ResponseEntity getStatistics()
	    {
	    	Map stats=mapTransform.getStatistics();
	    	
	    	return ResponseEntity.ok().body(stats);
	    }
	    
	    
	    private HashMap createAssociationMap(AlgorithmBean algorithmBean)
	    {
	    	HashMap associationMap= new HashMap<>();
	    	if (algorithmBean.getAssociationList().length>0)
	    	{
	    		for (int i=0; i<algorithmBean.getAssociationList().length; i++)
	    		{
	    			associationMap.put(algorithmBean.getAssociationList()[i].getName(), algorithmBean.getAssociationList()[i].getClassList());
	    		}
	    	}
	    	
	    	return associationMap;
	    }
	    
	    private HashMap createParametersMap(AlgorithmBean algorithmBean)
	    {
	    	HashMap parameterMap= new HashMap<>();
	    	parameterMap.put("angle", algorithmBean.getParameters().getAngle());
	    	parameterMap.put("sigma", algorithmBean.getParameters().getSigma());
	    	parameterMap.put("maxDistance", algorithmBean.getParameters().getMaxDistance());
	    	parameterMap.put("numIterations", algorithmBean.getParameters().getNumIterations());
	    	
	    	return parameterMap;
	    }
	    
	    private DataStore loadDataStore(String name){
	    	
	    	File fileTest = new File("");
	    	File file= new File(fileTest.getAbsolutePath()+"/SW_Ricerca_Punti_Omologhi/Mappe/"+name);
	    	
	    	DataStore dataStore=null;
	    	ShapefileDataStoreFactory f = new ShapefileDataStoreFactory();
	    	try {
	    		dataStore = f.createDataStore( file.toURL());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	return dataStore;
	    }
	    
	    
	
}
