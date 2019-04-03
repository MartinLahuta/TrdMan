package net.lahuta.trdman.repository;

import net.lahuta.trdman.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query("select p from Position p left join fetch p.positionTradesList")
    List<Position> findAllFetchSplits();

    @Query(nativeQuery = true, value = "select count(*) from position")
    long countPositions();

}
