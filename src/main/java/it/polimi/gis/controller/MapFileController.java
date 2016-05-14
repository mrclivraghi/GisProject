
package it.polimi.gis.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import it.anggen.security.SecurityService;
import it.anggen.service.log.LogEntryService;
import it.polimi.gis.model.MapFile;
import it.polimi.gis.service.MapFileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mapFile")
public class MapFileController {

    @org.springframework.beans.factory.annotation.Autowired
    private MapFileService mapFileService;
    
    private final static Logger log = LoggerFactory.getLogger(MapFile.class);

    @ApiOperation(value = "Return a page of mapFile", notes = "Return a single page of mapFile", response = MapFile.class, responseContainer = "List")
    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<MapFile> page = mapFileService.findByPage(pageNumber);
        return ResponseEntity.ok().body(page);
    }

    @ApiOperation(value = "Return a list of mapFile", notes = "Return a list of mapFile based on the search bean requested", response = MapFile.class, responseContainer = "List")
    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        MapFile mapFile) {

        List<MapFile> mapFileList;
        mapFileList=mapFileService.find(mapFile);
         log.info("Search: returning {} mapFile.",mapFileList.size());
        return ResponseEntity.ok().body(mapFileList);
    }

    @ApiOperation(value = "Return a the mapFile identified by the given id", notes = "Return a the mapFile identified by the given id", response = MapFile.class, responseContainer = "List")
    @Timed
    @ResponseBody
    @RequestMapping(value = "/{mapFileId}", method = RequestMethod.GET)
    public ResponseEntity getmapFileById(
        @PathVariable
        String mapFileId) {
        List<MapFile> mapFileList=mapFileService.findById(Integer.valueOf(mapFileId));
         log.info("Search: returning {} mapFile.",mapFileList.size());
        return ResponseEntity.ok().body(mapFileList);
    }

    @ApiOperation(value = "Delete the mapFile identified by the given id", notes = "Delete the mapFile identified by the given id")
    @Timed
    @ResponseBody
    @RequestMapping(value = "/{mapFileId}", method = RequestMethod.DELETE)
    public ResponseEntity deletemapFileById(
        @PathVariable
        String mapFileId) {
        mapFileService.deleteById(Integer.valueOf(mapFileId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertmapFile(
        @org.springframework.web.bind.annotation.RequestBody
        MapFile mapFile) {
        MapFile insertedmapFile=mapFileService.insert(mapFile);
        return ResponseEntity.ok().body(insertedmapFile);
    }

    @ApiOperation(value = "Update the mapFile given", notes = "Update the mapFile given ", response = MapFile.class)
    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatemapFile(
        @org.springframework.web.bind.annotation.RequestBody
        MapFile mapFile) {

        MapFile updatedmapFile=mapFileService.update(mapFile);
        return ResponseEntity.ok().body(updatedmapFile);
    }



    @ResponseBody
    @RequestMapping(value = "/loadMap", method = RequestMethod.POST)
    public ResponseEntity loadFileMyFile(
        @RequestParam(value = "file", required = false)
        MultipartFile file) {

    	Date now = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
    	
    	String destination="D:/uploadedFile/GisProject/mapFile/"+sdf.format(now)+"/";
    	String filePath = it.anggen.utils.Utility.saveMultipartFile(file, destination);
    	MapFile newMapFile = new MapFile();
    	newMapFile.setFilePath(filePath);
    	mapFileService.insert(newMapFile);
    	return  ResponseEntity.ok().body(filePath);
    }

}
