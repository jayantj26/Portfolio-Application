package com.portfolio.api.service;

import com.portfolio.api.dto.TradeDto;
import com.portfolio.api.entity.Trade;

import java.util.List;

public interface TradeService {
    public String saveTrade(TradeDto tradeDto);

    public List<Trade>getAllTrade();

}
