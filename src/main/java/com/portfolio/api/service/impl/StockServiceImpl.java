package com.portfolio.api.service.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import com.portfolio.api.csv.CsvPojo;
import com.portfolio.api.entity.Stock;
import com.portfolio.api.exception.ResourceNotFoundException;
import com.portfolio.api.inputBean.StockData;
import com.portfolio.api.repository.StockRepository;
import com.portfolio.api.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;
    @Override
    public void updateStocks(StockData stockData) throws FileNotFoundException {
        List<CsvPojo> stocks= new CsvToBeanBuilder<CsvPojo>(new FileReader(stockData.getPath())).withType(CsvPojo.class).build().parse();
        Stock stock=new Stock();
        for(CsvPojo st:stocks)
        {
            stock.setStockId(st.getStockId());
            stock.setStockName(st.getStockName());
            stock.setOpen(st.getOpen());
            stock.setClose(st.getClose());
            stock.setLow(st.getLow());
            stock.setHigh(st.getHigh());
            stock.setPrevClose(st.getPrevClose());
            stockRepository.save(stock);
            stockRepository.flush();
            System.out.println("Inserted Data Successfully : " + st.getStockId());
        }
    }

    @Override
    public Stock stockDetails(String stockId) throws ResourceNotFoundException
    {
        return stockRepository.findById(stockId).orElseThrow(() ->
                new ResourceNotFoundException("Stock", "stockId", stockId));
    }

    @Override
    public List<Stock> getAllStock()
    {
        return stockRepository.findAll();
    }


}
