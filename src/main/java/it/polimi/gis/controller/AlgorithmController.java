package it.polimi.gis.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import it.polimi.gis.model.Marker;
import it.polimi.gis.model.MarkerPair;
import it.polimi.gis.model.Pair;
import it.polimi.gis.model.bean.AlgorithmBean;
import it.polimi.gis.repository.PairRepository;

@Controller
@RequestMapping("/algorithm")
public class AlgorithmController {

	
	@Autowired
	PairRepository pairRepository;
	
	@Autowired
	MapTransform mapTransform;
	
	    @ResponseBody
	    @RequestMapping(method = RequestMethod.POST)
	    public ResponseEntity run(
	        @RequestBody AlgorithmBean algorithmBean) {
	    	
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
	    	
	    	ArrayList<Pair> homologousList=mapTransform.findHomologousPoints();
	        return ResponseEntity.ok().body(homologousList);
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
	    
	    
	
}
