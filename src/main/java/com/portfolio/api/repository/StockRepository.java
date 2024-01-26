package com.portfolio.api.repository;

import com.portfolio.api.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,String> {
}
