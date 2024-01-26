package com.portfolio.api.dto;

import com.portfolio.api.entity.Holding;
import lombok.Data;

import java.util.List;

@Data
public class HoldingsDto {

    private List<Holding>holdings;
    private double totalPortfolioPrice;
    private double totalBuyPrice;
    private TotalReturns totalReturns;

    @Data
    private class TotalReturns{
        private double totalReturns;
        private double totalReturnsPercentage;
    }

    public void setTotalReturns(double totalReturns,double totalReturnsPercentage)
    {
        if (this.totalReturns == null) {
            this.totalReturns = new TotalReturns();
        }
        this.totalReturns.setTotalReturns(totalReturns);
        this.totalReturns.setTotalReturnsPercentage(totalReturnsPercentage);
    }


}
