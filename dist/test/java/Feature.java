

import it.GisProjectApplication;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTCoverage;
import it.geosolutions.geoserver.rest.decoder.RESTFeatureType;
import it.geosolutions.geoserver.rest.decoder.RESTFeatureType.Attribute;
import it.geosolutions.geoserver.rest.encoder.feature.FeatureTypeAttribute;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.decoder.RESTLayerList;
import it.geosolutions.geoserver.rest.decoder.RESTResource;
import it.polimi.gis.service.GeoServerService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.measure.unit.SI;

import org.geotools.data.DataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=GisProjectApplication.class)
public class Feature {
	
	@Value("${application.geoserver.restURL}")
    private String restUrl;
    @Value("${application.geoserver.user}")
    private String user;
    @Value("${application.geoserver.password}")
    private String password;

	@Autowired
	GeoServerService geoServerService;
	
	
	public Feature() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void features()
	{
		File fileTest = new File("");
    	File file= new File(fileTest.getAbsolutePath()+"/SW_Ricerca_Punti_Omologhi/MappeConAttribute/OSM_00_4326.shp");
    	
    	DataStore dataStore=null;
    	ShapefileDataStoreFactory f = new ShapefileDataStoreFactory();
    	try {
    		dataStore = f.createDataStore( file.toURL());
    		SimpleFeatureSource featureSource = dataStore.getFeatureSource("OSM_00_4326");
    		featureSource.getBounds();
    		SimpleFeatureCollection featureCollection = featureSource.getFeatures();
    		SimpleFeatureIterator iterator = featureCollection.features();
    	    try {
    	        while( iterator.hasNext() ){
    	            SimpleFeature feature = iterator.next();
    	            // process feature
    	        }
    	    }
    	    finally {
    	        iterator.close();
    	    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void featuresREST()
	{
		
		try {
			GeoServerRESTReader reader = new GeoServerRESTReader(restUrl, user, password);
			GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(restUrl, user, password);
			
			boolean exists = reader.existGeoserver();
			if (exists)
			{
				RESTLayerList layerList= reader.getLayers();
				
				for (int i=0; i<layerList.size(); i++)
				{
					RESTLayer layer= reader.getLayer("OSM_00_4326_attr");
					
					RESTFeatureType type=reader.getFeatureType(layer);
					
					//RESTCoverage coverage=reader.getCoverage(layer);
					
					RESTResource res=reader.getResource(layer);
					
					for (Attribute attr : type.getAttributes()) {
						System.out.println(attr.getName());
					}
					
					List<String> keyList=res.getKeywords();
					/*List<Map<FeatureTypeAttribute,String>> attrList=coverage.getAttributeList();
					
					for (Map<FeatureTypeAttribute,String> attr : attrList) {
						for (String str: attr.values())
						{
							System.out.print(str);
						}
					}*/
				}
				
			}
			
			System.out.println("Exists");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
