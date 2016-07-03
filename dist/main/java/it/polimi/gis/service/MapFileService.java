
package it.polimi.gis.service;

import java.util.List;
import it.polimi.gis.model.MapFile;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface MapFileService {


    public List<MapFile> findById(Integer TestId);

    public List<MapFile> find(MapFile Test);

    public void deleteById(Integer TestId);

    public MapFile insert(MapFile Test);

    public MapFile update(MapFile Test);

    public Page<MapFile> findByPage(
        @PathVariable
        Integer pageNumber);

}
