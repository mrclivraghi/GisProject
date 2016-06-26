

import it.GisProjectApplication;
import it.polimi.gis.service.GeoServerService;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=GisProjectApplication.class)
public class GeoServer {

	@Autowired
	GeoServerService geoServerService;
	
	
	public GeoServer() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void createJs()
	{
		File zipFile = new File("D:\\uploadedFile\\GisProject\\mapFile\\DBT_00.zip");
		
		geoServerService.loadZipFile(zipFile);
	}

}
