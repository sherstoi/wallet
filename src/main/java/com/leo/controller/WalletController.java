package com.leo.controller;

import com.leo.dto.PlayerDTO;
import com.leo.dto.TransactionDTO;
import com.leo.entity.Transaction;
import com.leo.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/wallet/player/{playerId}/balance")
    public BigDecimal getPlayerBalance(@PathVariable String playerId) {
        return walletService.getPlayerBalance(playerId);
    }

    @GetMapping("/wallet/player/{playerId}/transaction")
    public List<TransactionDTO> getHistoryTransactionsPerPlayer(@PathVariable String playerId) {
        return walletService.getTransactionsByPlayerId(playerId);
    }

    @PostMapping("/wallet/player/{playerId}")
    public PlayerDTO makeTransaction(@PathVariable String playerId, @RequestBody Transaction transaction) {
        return walletService.executeTransaction(playerId, transaction);
    }
}
