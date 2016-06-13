
package it.polimi.gis.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.gis.model.LayerPoint;
import it.polimi.gis.model.MapFile;
import it.polimi.gis.model.Pair;

@Repository
public interface LayerPointRepository
    extends JpaRepository<LayerPoint, java.lang.Integer>
{


    @Query("select l from LayerPoint l")
    public List<LayerPoint> findAll();
    
    @Modifying
    @Transactional
    @Query("delete from LayerPoint l")
    public void deleteLayerPoint();

   
    public List<LayerPoint> findByLayerName(String layerName);
    
    @Query(nativeQuery=true,value="select l.* from layer_point l where l.layer_name=:layerName order by ST_distance(ST_GeomFromText(cast('POINT(' as varchar) || :lng || cast(' ' as varchar) || :lat || cast(' )' as varchar)),l.point) ")
    public List<LayerPoint> findClosest(@Param("latitude") String lat,@Param("lng") String lng,@Param("layerName")String layerName);
    
}
