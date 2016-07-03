
package it.polimi.gis.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.gis.model.MapFile;
import it.polimi.gis.model.Pair;

@Repository
public interface PairRepository
    extends JpaRepository<Pair, java.lang.Integer>
{


    @Query("select t from Pair t")
    public List<Pair> findAll();
    
    @Modifying
    @Transactional
    @Query("delete from Pair p where p.project<>'test_omologhi'")
    public void deleteCustomMarkers();

    @Query("select p from Pair p where p.project<>'test_omologhi'")
    public List<Pair> findCustom();
    
    public List<Pair> findByProject(String project);
    

}
