package com.portfolio.api.service;
import static org.junit.jupiter.api.Assertions.*;
import com.portfolio.api.entity.Trade;
import com.portfolio.api.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TradeServiceTest {
    @Autowired
    private TradeService tradeService;

    @MockBean
    private TradeRepository tradeRepository;

    List<Trade>trades;

    @BeforeEach
    void setup()
    {
        trades=new ArrayList<>();
        trades.add(Trade.builder().userId(1).stockId("INF846K01Z12").quantity(3).tradeType("BUY").build());
        trades.add(Trade.builder().userId(1).stockId("INF846K01Z12").quantity(3).tradeType("SELL").build());
        trades.add(Trade.builder().userId(1).stockId("INF846K01Z12").quantity(3).tradeType("BUY").build());

    }

    @Test
    void getAllTrades()
    {

        Mockito.when(tradeRepository.findAll()).thenReturn(trades);
        List<Trade> foundtrades = tradeService.getAllTrade();
        assertEquals(trades,foundtrades);
    }

}
