package com.portfolio.api.controller;

import com.portfolio.api.entity.Stock;
import com.portfolio.api.exception.ResourceNotFoundException;
import com.portfolio.api.repository.StockRepository;
import com.portfolio.api.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.portfolio.api.inputBean.StockData;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
     public void updateStocks(@RequestBody StockData stockData) throws FileNotFoundException {
        System.out.println(stockData);
        stockService.updateStocks(stockData);
    }

    @GetMapping("/details/{stockId}")
    public ResponseEntity<Stock> stocksDetails(@PathVariable String stockId) throws ResourceNotFoundException
    {
        return new ResponseEntity<Stock>(stockService.stockDetails(stockId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Stock>>getAllStock()
    {
        return new ResponseEntity<List<Stock>>(stockService.getAllStock(),HttpStatus.OK);
    }

}
