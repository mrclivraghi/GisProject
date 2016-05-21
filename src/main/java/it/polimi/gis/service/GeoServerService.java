
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
    
    public void connect(File zipFile)
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
    
    

}