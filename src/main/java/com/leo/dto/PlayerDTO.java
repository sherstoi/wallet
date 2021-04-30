package com.leo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
public class PlayerDTO {
    private String id;
    private BigDecimal balance;
    private String name;
    private List<TransactionDTO> transactions;
}
