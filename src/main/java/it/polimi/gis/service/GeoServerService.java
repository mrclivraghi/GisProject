
package it.polimi.gis.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    
    

}
