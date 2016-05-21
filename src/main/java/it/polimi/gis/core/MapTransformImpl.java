package it.polimi.gis.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.geotools.data.DataStore;

import com.vividsolutions.jts.geom.Point;

import it.polimi.gis.model.Pair;

public class MapTransformImpl implements MapTransform {
	
	private DataStore sourceDataStore;
	private DataStore targetDataStore;
	private HashMap parameters;
	private HashMap semantics;
	private ArrayList<Point> sourcePoints;
	private ArrayList<Point> targetPoints;
	private ArrayList<Pair> pairs;
	

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataStore transform(ArrayList<Pair> pairs) {
		// TODO Auto-generated method stub
		return null;
	}

}
