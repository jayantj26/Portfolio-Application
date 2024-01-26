package com.portfolio.api.service.impl;

import com.portfolio.api.dto.TradeDto;
import com.portfolio.api.entity.Holding;
import com.portfolio.api.entity.Stock;
import com.portfolio.api.entity.Trade;
import com.portfolio.api.entity.User;
import com.portfolio.api.exception.ResourceNotFoundException;
import com.portfolio.api.repository.HoldingRepository;
import com.portfolio.api.repository.StockRepository;
import com.portfolio.api.repository.TradeRepository;
import com.portfolio.api.repository.UserRepository;
import com.portfolio.api.service.TradeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService{
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private HoldingRepository holdingRepository;

    @Override
    public List<Trade> getAllTrade()
    {
        return tradeRepository.findAll();
    }

    @Transactional
    @Override
    public String saveTrade(TradeDto tradeDto) {
        String tradeType=tradeDto.getTradeType().toUpperCase();
        long userId=tradeDto.getUserId();
        String stockId=tradeDto.getStockId();
        long quantity=tradeDto.getQuantity();

        validateQuantity(quantity);

        Stock stock=getStockEntity(stockId);
        User user=getUserEntity(userId);

        Optional<Holding> holding=holdingRepository.findByUserIdAndStockId(userId, stockId);

        Trade trade=convertDtotoTrade(tradeDto);


            if(holding.isPresent())
            {
                Holding existingHolding=holding.get();
                if(tradeType.equalsIgnoreCase("Buy")) {
                    trade.setTradePrice(stock.getOpen());

                    existingHolding.setQuantity(existingHolding.getQuantity()+quantity);
                    existingHolding.setTotalBuyPrice(existingHolding.getTotalBuyPrice()+(quantity*stock.getOpen()));
                    existingHolding.setCurrentPrice((existingHolding.getQuantity())*(stock.getOpen()));
                    existingHolding.setReturns(existingHolding.getCurrentPrice()-existingHolding.getTotalBuyPrice());

                    holdingRepository.save(existingHolding);
                }
                else {
                    if(quantity>existingHolding.getQuantity()) {
                        return "You don't have enough stocks";
                    }
                    else if(quantity==existingHolding.getQuantity()) {
                        trade.setTradePrice(stock.getClose());
                        holdingRepository.deleteByUserIdAndStockId(userId,stockId);
                    }
                    else{
                            trade.setTradePrice(stock.getClose());
                            double averagePrice=existingHolding.getTotalBuyPrice()/existingHolding.getQuantity();
                            existingHolding.setQuantity(existingHolding.getQuantity()-quantity);
                            existingHolding.setTotalBuyPrice(existingHolding.getTotalBuyPrice()-(quantity*averagePrice));
                            existingHolding.setCurrentPrice((existingHolding.getQuantity())*(stock.getClose()));
                            existingHolding.setReturns(existingHolding.getCurrentPrice()-existingHolding.getTotalBuyPrice());
                            holdingRepository.save(existingHolding);
                        }
                    }


            }
            else {
                if(tradeType.equalsIgnoreCase("buy"))
                {
                    trade.setTradePrice(stock.getOpen());
                    Holding newHolding=new Holding();
                    newHolding.setQuantity(quantity);
                    newHolding.setUserId(userId);
                    newHolding.setStockId(stockId);
                    newHolding.setReturns(0);
                    newHolding.setTotalBuyPrice(quantity*stock.getOpen());
                    newHolding.setCurrentPrice(quantity*stock.getOpen());
                    holdingRepository.save(newHolding);
                }
                else {
                    return "You don't have the Stock in your portfolio";
                }


            }

        tradeRepository.save(trade);
        return "Trade Successful";

    }

    public void validateQuantity(long quantity)
    {
        if(quantity<=0)
        {
            throw new IllegalArgumentException("Quantity should be greater Than 0");
        }
    }

    public Stock getStockEntity(String stockId)
    {
        return stockRepository.findById(stockId).orElseThrow(() ->
                new ResourceNotFoundException("Stock", "stockId", stockId));

    }

    public User getUserEntity(long userId)
    {
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", String.valueOf(userId)));
    }

    public Trade convertDtotoTrade(TradeDto tradeDto)
    {
        Trade trade=new Trade();
        trade.setTradeType(tradeDto.getTradeType());
        trade.setUserId(tradeDto.getUserId());
        trade.setStockId(tradeDto.getStockId());
        trade.setQuantity(tradeDto.getQuantity());
        return trade;
    }




}
