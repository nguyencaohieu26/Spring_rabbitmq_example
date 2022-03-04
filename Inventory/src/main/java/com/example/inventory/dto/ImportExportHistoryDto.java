package com.example.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImportExportHistoryDto {
    private long id;
    private long orderID;
    private long productID;
    private int quantity;
    private BigDecimal totalValue;
    private String type;
}
