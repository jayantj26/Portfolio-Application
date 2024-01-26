package com.portfolio.api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="holdings")
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private String stockId;
    private long quantity;
    @Column(columnDefinition = "DECIMAL(10,2)")
    private double totalBuyPrice;
    @Column(columnDefinition = "DECIMAL(10,2)")
    private double currentPrice;
    @Column(columnDefinition ="DECIMAL(10,2)")
    private double returns;


}
