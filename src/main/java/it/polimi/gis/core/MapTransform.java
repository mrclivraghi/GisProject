package it.polimi.gis.core;

import com.vividsolutions.jts.geom.Point;
import java.util.ArrayList;
import java.util.HashMap;
import org.geotools.data.DataStore;

public interface MapTransform {

    /**
     * Set the map to transform (mapSource).
     *
     * @param sourceDataStore dataStore associated to the source map
     */
    public void setDataStoreSource(DataStore sourceDataStore);

    /**
     * Set the map used as reference (mapTarget).
     *
     * @param targetDataStore dataStore associated to the target map
     */
    public void setDataStoreTarget(DataStore targetDataStore);

    /**
     * Set the parameters used by the research algorithm: angle (double), sigma
     * (double), iterations (int) and maxDistance (double).
     *
     * @param parameters parameters of the research algorithm
     */
    public void setParameters(HashMap parameters);

    /**
     * Set the semantic correspondences between layers (e.g: "residential
     * building"-"building", "commercial building"-"building", "street"-"parcel"
     * ...).
     *
     * @param semantics semantic correspondences between layers of the two maps
     */
    public void setSemantics(HashMap semantics);

    /**
     * Set the points selected on the map to transform. The first point of the
     * sourceMap list is considered homologous of the first point of targetMap
     * list, the second point is considered homologous of the second point and
     * so on.
     *
     * @param sourcePoints points selected on the source map
     */
    public void setSourcePoints(ArrayList<Point> sourcePoints);

    /**
     * Set the points selected on the map used as reference. The first point of
     * the sourceMap list is considered homologous of the first point of
     * targetMap list, the second point is considered homologous of the second
     * point and so on.
     *
     * @param targetPoints points selected on the target map
     */
    public void setTargetPoints(ArrayList<Point> targetPoints);

    /**
     * Set the pairs of homologous points of the source (S) and target (T) maps:
     * (p1S, p1T), (p2S,p2T), (p3S,p3T), ...
     *
     * @param pairs the homologous points of the source map and the target map
     */
    public void setPairs(ArrayList<Point> pairs);

    /**
     * Return the homologous points computed using the source map
     * (sourceDataStore), the target map (targetDataStore), the starting
     * homologous points, the algorithm parameters and the semantic
     * informations.
     *
     * @return the new list of homologous points
     */
    public ArrayList<Pair> findHomologousPoints();

    /**
     * Transform the source map stored in sourceDataStore using the new list of
     * homologous points computed by the algorithm.
     *
     * @param pairs the list of homologous points
     * @return the transformed map
     */
    public DataStore transform(ArrayList<Pair> pairs);

}
