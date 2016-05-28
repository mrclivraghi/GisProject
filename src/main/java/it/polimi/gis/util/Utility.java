package it.polimi.gis.util;

import java.io.File;

import org.geotools.data.DataStore;

public class Utility {

	public static File getShapeFileFromDataStore(DataStore dataStore)
	{
		 String t=dataStore.toString();
		 String fileName=t.substring(t.indexOf("file:")+6, t.indexOf(", charset"));
		  File file = new File(fileName);
		  return file;
	}
	
}
