package com.portfolio.api.service.impl;

import com.portfolio.api.dto.HoldingsDto;
import com.portfolio.api.entity.Holding;
import com.portfolio.api.entity.Stock;
import com.portfolio.api.entity.User;
import com.portfolio.api.exception.ResourceNotFoundException;
import com.portfolio.api.repository.HoldingRepository;
import com.portfolio.api.repository.UserRepository;
import com.portfolio.api.service.StockService;
import com.portfolio.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private HoldingRepository holdingRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }
    @Override
    public String saveUser(User user)
    {
        Optional<User> founduser=userRepository.findByUserName(user.getUserName());
        if(founduser.isPresent())
        {
            return "User already exist";
        }
         userRepository.save(user);
        return "User created successfully";
    }
    @Override
    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long userId)
    {
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",String.valueOf(userId)));
    }

    @Override
    public HoldingsDto getPortfolio(long userId)
    {
        User user =getUser(userId);
        List<Holding> holdings= holdingRepository.findByUserId(userId);
        if(holdings.size()==0)return null;
        double totalPortfolioPrice=0;
        double totalBuyPrice=0;
        double totalReturns=0;
         for(Holding holding:holdings)
         {
             String stockId = holding.getStockId();
             Stock stock=stockService.stockDetails(stockId);
             holding.setCurrentPrice(holding.getQuantity()*stock.getClose());
             holding.setReturns(holding.getCurrentPrice()-holding.getTotalBuyPrice());
             holdingRepository.save(holding);
             totalPortfolioPrice+=holding.getCurrentPrice();
             totalBuyPrice+=holding.getTotalBuyPrice();
             totalReturns+=holding.getReturns();
         }
        HoldingsDto holdingsDto=new HoldingsDto();
         holdingsDto.setHoldings(holdings);
         holdingsDto.setTotalPortfolioPrice(totalPortfolioPrice);
         holdingsDto.setTotalBuyPrice(totalBuyPrice);
         holdingsDto.setTotalReturns(totalReturns,calculateTotalReturnsPercentage(totalReturns,totalBuyPrice));


        return holdingsDto;
    }
    private double calculateTotalReturnsPercentage(double totalReturns, double totalBuyPrice)
    {
        if(totalBuyPrice==0)
        {
            return 0;
        }
        return (totalReturns/totalBuyPrice)*100;
    }
}
