

import it.anggen.generation.Generator;
import it.Application;
import it.GisProjectApplication;
import it.anggen.repository.entity.ProjectRepository;
import it.anggen.repository.field.EnumFieldRepository;
import it.polimi.gis.service.GeoServerService;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=GisProjectApplication.class)
public class LoadTestOmologous {

	@Autowired
	GeoServerService geoServerService;
	
	
	public LoadTestOmologous() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void createJs()
	{
		geoServerService.getFeature("");
	}

}
