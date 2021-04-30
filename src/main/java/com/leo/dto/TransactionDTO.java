package com.leo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class TransactionDTO {
    private String id;
    private BigDecimal amount;
}
