package it.polimi.gis.util;

import java.io.File;
import java.io.IOException;

import org.geotools.data.DataStore;
import org.springframework.web.multipart.MultipartFile;

public class Utility {

	public static File getShapeFileFromDataStore(DataStore dataStore)
	{
		 String t=dataStore.toString();
		 String fileName=t.substring(t.indexOf("file:")+6, t.indexOf(", charset"));
		  File file = new File(fileName);
		  return file;
	}
	
	public static String saveMultipartFile(MultipartFile file, String destination)
	{
		String filePath=null;
		if (file==null || file.isEmpty())
		{
			filePath="";
		} else
		{
			
			try {
				
				File savedFile = new File(destination);
				if (!savedFile.exists())
					savedFile.mkdirs();
				savedFile=new File(destination+file.getOriginalFilename());
				file.transferTo(savedFile);
				filePath=savedFile.getAbsolutePath();
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return filePath;
	}
	
}
