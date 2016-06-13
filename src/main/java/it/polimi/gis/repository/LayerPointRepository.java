
package it.polimi.gis.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
    

}
