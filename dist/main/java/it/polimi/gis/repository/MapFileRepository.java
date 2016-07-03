
package it.polimi.gis.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.polimi.gis.model.MapFile;

@Repository
public interface MapFileRepository
    extends JpaRepository<MapFile, java.lang.Integer>
{


    @Query("select t from MapFile t")
    public List<MapFile> findAll();

    public List<MapFile> findByMapFileId(java.lang.Integer mapFileId);

    public List<MapFile> findByFilePath(java.lang.String filePath);

    @Query("select t from MapFile t where  (:mapFileId is null or cast(:mapFileId as string)=cast(t.mapFileId as string)) and (:myFile is null or :myFile='' or cast(:myFile as string)=t.filePath) ")
    public List<MapFile> findByTestIdAndMyFile(
        @org.springframework.data.repository.query.Param("mapFileId")
        java.lang.Integer testId,
        @org.springframework.data.repository.query.Param("myFile")
        java.lang.String myFile);

}
