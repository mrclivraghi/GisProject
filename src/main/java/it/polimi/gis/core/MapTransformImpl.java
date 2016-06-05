package it.polimi.gis.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Point;

import it.polimi.gis.model.Pair;
import it.polimi.gis.repository.PairRepository;

@Service
public class MapTransformImpl implements MapTransform {
	
	private DataStore sourceDataStore;
	private DataStore targetDataStore;
	private HashMap parameters;
	private HashMap semantics;
	private ArrayList<Point> sourcePoints;
	private ArrayList<Point> targetPoints;
	private ArrayList<Pair> pairs;
	
	@Autowired
	PairRepository pairRepository;
	

	@Override
	public void setDataStoreSource(DataStore sourceDataStore) {
		this.sourceDataStore=sourceDataStore;

	}

	@Override
	public void setDataStoreTarget(DataStore targetDataStore) {
		this.targetDataStore=targetDataStore;
	}

	@Override
	public void setParameters(HashMap parameters) {
		this.parameters=parameters;

	}

	@Override
	public void setSemantics(HashMap semantics) {
		this.semantics=semantics;

	}

	@Override
	public void setSourcePoints(ArrayList<Point> sourcePoints) {
		this.sourcePoints=sourcePoints;

	}

	@Override
	public void setTargetPoints(ArrayList<Point> targetPoints) {
		this.targetPoints=targetPoints;

	}

	@Override
	public void setPairs(ArrayList<Pair> pairs) {
		this.pairs=pairs;

	}

	@Override
	public ArrayList<Pair> findHomologousPoints() {
		List<Pair> homologousPoints= pairRepository.findByProject("test_omologhi");
		return (ArrayList<Pair>) homologousPoints;
	}

	@Override
	public DataStore transform(ArrayList<Pair> pairs) {
		File fileTest = new File("");
    	File file= new File(fileTest.getAbsolutePath()+"/SW_Ricerca_Punti_Omologhi/FakeResult/result.shp");
    	
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

	@Override
	public Map getStatistics() {
		Map<String,String> stats= new HashMap<String,String>();
		stats.put("Precision", "0.78");
		stats.put("Mean error", "0.12");
		stats.put("Standard deviation", "1.05");
		return stats;
	}

}
