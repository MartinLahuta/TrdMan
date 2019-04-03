package net.lahuta.trdman.repository;

import net.lahuta.trdman.entity.Trade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TradeRepository extends JpaRepository<Trade, Long> {

//    @Query("select t from Trade t WHERE t.id = ?1")
//    Optional<Trade> findById(long id);

    @Query(nativeQuery = true, value = "select count(*) from trdbook")
    long countTrades();

    @Query("select t from Trade t WHERE t.memberId = ?1")
    List<Trade> findAllByMember(String key);

}
