package com.portfolio.api.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class CsvPojo {
    @CsvBindByName(column = "ISIN")
    private String stockId;

    @CsvBindByName(column="SYMBOL")
    private String stockName;

    @CsvBindByName(column="OPEN")
    private double open;

    @CsvBindByName(column="CLOSE")
    private double close;

    @CsvBindByName(column="HIGH")
    private double high;

    @CsvBindByName(column="LOW")
    private double low;

    @CsvBindByName(column="PREVCLOSE")
    private double prevClose;


}
