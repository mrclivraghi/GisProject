
package it.polimi.gis.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTDataStoreList;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.decoder.RESTLayerList;
import it.geosolutions.geoserver.rest.decoder.utils.NameLinkElem;
import it.polimi.gis.model.MapFile;
import it.polimi.gis.repository.MapFileRepository;

@Service
public class GeoServerService
{
	@Value("${application.geoserver.restURL}")
    private String restUrl;
    @Value("${application.geoserver.user}")
    private String user;
    @Value("${application.geoserver.password}")
    private String password;
    
    public void loadZipFile(File zipFile)
    {
    	
    	 try {
			GeoServerRESTReader reader = new GeoServerRESTReader(restUrl, user, password);
			GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(restUrl, user, password);
			
			boolean exists = reader.existGeoserver();
			if (exists)
			{
				String fileName=zipFile.getName().replaceAll(".zip", "");
				try {
					
					Boolean published = publisher.publishShp("gisProject",fileName,fileName,zipFile,"EPSG:4326","default_point");
					
				} catch (FileNotFoundException | IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.out.println("Exists");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    public void getFeature(String layerName)
    {
    	
    	 try {
			GeoServerRESTReader reader = new GeoServerRESTReader(restUrl, user, password);
			GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(restUrl, user, password);
			
			boolean exists = reader.existGeoserver();
			if (exists)
			{
					//reader.getLayer("ADBT_00_Omologhi_4326");
					RESTLayerList list= reader.getLayers();
					/*if (list.size()>0)
					{
						NameLinkElem elem=list.get(0);
						RESTDataStoreList datastores=reader.getDatastores("gisProject");
						RESTLayer test;
						RESTDataStore test2;
						test2.
						
					}*/
					
					RESTDataStore ds=reader.getDatastore("", "");
			}
			
			System.out.println("Exists");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    

}
