package net.lahuta.trdman.repository;

import net.lahuta.trdman.entity.PositionTrades;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionTradesRepository extends JpaRepository<PositionTrades, Long> {
}
