package net.lahuta.trdman.repository;

import net.lahuta.trdman.entity.Trade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@RepositoryRestResource(exported = false)
public interface TradeRepository extends JpaRepository<Trade, Long> {

//    @Query("select t from Trade t WHERE t.id = ?1")
//    Optional<Trade> findById(long id);

    @Query(nativeQuery = true, value = "select count(*) from trdbook")
    long countItems();

}
