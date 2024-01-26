package com.portfolio.api.service;

import com.portfolio.api.entity.Stock;
import com.portfolio.api.exception.ResourceNotFoundException;
import com.portfolio.api.inputBean.StockData;

import java.io.FileNotFoundException;
import java.util.List;

public interface StockService {
    public void updateStocks(StockData stockData) throws FileNotFoundException;

    public Stock stockDetails(String stockId) throws ResourceNotFoundException;

    public List<Stock>getAllStock();
}
