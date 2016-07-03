
package it.polimi.gis.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.gis.model.MapFile;
import it.polimi.gis.repository.MapFileRepository;

@Service
public class MapFileServiceImpl
    implements MapFileService
{

    @Autowired
    public MapFileRepository mapFileRepository;
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<MapFile> findById(Integer testId) {
        return mapFileRepository.findByMapFileId(testId);
    }

    @Override
    public Page<MapFile> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "testId");
        return mapFileRepository.findAll(pageRequest);
    }

    @Override
    public List<MapFile> find(MapFile test) {
        return mapFileRepository.findByTestIdAndMyFile(test.getMapFileId(),test.getFilePath());
    }

    @Override
    public void deleteById(Integer testId) {
        mapFileRepository.delete(testId);
        return;
    }

    @Override
    public MapFile insert(MapFile test) {
        return mapFileRepository.save(test);
    }

    @Override
    @Transactional
    public MapFile update(MapFile test) {
        MapFile returnedTest=mapFileRepository.save(test);
         return returnedTest;
    }

}
