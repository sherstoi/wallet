package com.leo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    private String id;
    private BigDecimal balance;
    private String name;
    @OneToMany(targetEntity = Transaction.class, cascade = {CascadeType.ALL})
    private List<Transaction> transactions;
}
