package com.portfolio.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name="stocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock {
    @Id
    private String stockId;
    private String stockName;
    private double open;
    private double close;
    private double high;
    private double low;
    private double prevClose;

}
