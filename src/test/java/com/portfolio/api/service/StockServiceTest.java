package com.portfolio.api.service;

import com.portfolio.api.entity.Stock;
import com.portfolio.api.entity.User;
import com.portfolio.api.exception.ResourceNotFoundException;
import com.portfolio.api.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StockServiceTest {
    @Autowired
    private StockService stockService;
    @MockBean
    private StockRepository stockRepository;

    private Stock existingstock;
    private Stock notExistingStock;

    @BeforeEach
    void setUp() {
        existingstock = Stock.builder().stockId("INF846K01Z12").stockName("NAVINIFTY").build();
        notExistingStock=Stock.builder().stockId("Abcdefg").build();
    }

    @Test
    void stockDetails_whenRightIdProvided() throws ResourceNotFoundException {
        Mockito.when(stockRepository.findById(existingstock.getStockId())).thenReturn(Optional.of(existingstock));
        Stock foundStock = stockService.stockDetails(existingstock.getStockId());
        System.out.println(foundStock.toString());
        System.out.println(existingstock.toString());
        assertEquals(existingstock, foundStock);
    }
    @Test
    void stockDetails_whenWrongIdProvided() throws ResourceNotFoundException {
        when(stockRepository.findById(notExistingStock.getStockId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> stockService.stockDetails(existingstock.getStockId()));
    }





}
