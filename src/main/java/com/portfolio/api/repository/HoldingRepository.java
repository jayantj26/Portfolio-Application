package com.portfolio.api.repository;

import com.portfolio.api.entity.Holding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HoldingRepository extends JpaRepository<Holding,Long> {
    Optional<Holding> findByUserIdAndStockId(long userId, String stockId);

    void deleteByUserIdAndStockId(long userId,String stockId);

    List<Holding> findByUserId(long userId);



}
