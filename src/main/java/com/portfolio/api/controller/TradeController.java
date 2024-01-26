package com.portfolio.api.controller;

import com.portfolio.api.dto.TradeDto;
import com.portfolio.api.entity.Trade;
import com.portfolio.api.service.TradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trade")
public class TradeController {
    private TradeService tradeService;

    public TradeController(TradeService tradeService) {
        super();
        this.tradeService = tradeService;
    }

    @PostMapping()
    public ResponseEntity<String> saveTrade(@RequestBody TradeDto tradeDto)
    {
        return new ResponseEntity<String>(tradeService.saveTrade(tradeDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Trade>> getAllTrade()
    {
        return new ResponseEntity<List<Trade>>(tradeService.getAllTrade(),HttpStatus.OK);
    }
}
