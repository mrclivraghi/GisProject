
package it.polimi.gis.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.polimi.gis.model.MapFile;
import it.polimi.gis.model.Pair;

@Repository
public interface PairRepository
    extends JpaRepository<Pair, java.lang.Integer>
{


    @Query("select t from Pair t")
    public List<Pair> findAll();


}
