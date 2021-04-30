package com.leo.service;

import com.leo.dto.PlayerDTO;
import com.leo.dto.TransactionDTO;
import com.leo.entity.Player;
import com.leo.entity.Transaction;
import com.leo.exception.PlayerNotFoundException;
import com.leo.exception.PlayerAmountIsNotEnoughException;
import com.leo.exception.TransactionIsNotUniqueException;
import com.leo.mapper.PlayerMapper;
import com.leo.mapper.TransactionMapper;
import com.leo.repository.PlayerRepository;
import com.leo.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class WalletService {
    private static final String PLAYER_NOT_FOUND = "Player with provided id:{%s} doesn't exists!";

    private final TransactionRepository transactionRepository;
    private final PlayerRepository playerRepository;

    public List<TransactionDTO> getTransactionsByPlayerId(String playerId) {
        return TransactionMapper.INSTANCE.toTransactionDTOs(playerRepository.findById(playerId)
                .map(Player::getTransactions)
                .orElseThrow(() -> new PlayerNotFoundException(String.format(PLAYER_NOT_FOUND, playerId))));
    }

    public BigDecimal getPlayerBalance(String playerId) {
        return PlayerMapper.INSTANCE.toPlayerDTO(playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(String.format(PLAYER_NOT_FOUND, playerId))))
                .getBalance();
    }

    public PlayerDTO executeTransaction(String playerId, Transaction transaction) {
        if (transactionRepository.existsById(transaction.getId())) {
            throw new TransactionIsNotUniqueException(String.format("Transaction with id:{%s} already processed!", transaction.getId()));
        }
        var player = playerRepository.findById(playerId).
                orElseThrow(() -> new PlayerNotFoundException(String.format("Player with id:{%s} doesn't exists!", playerId)));

        synchronized (player) {
            BigDecimal balance = player.getBalance().add(transaction.getAmount());
            if (balance.compareTo(BigDecimal.ZERO) < 0) {
                throw new PlayerAmountIsNotEnoughException(String.format("Not enough money to complete transaction! " +
                        "Current player balance: {%s}, transaction amount: {%s}", player.getBalance(), transaction.getAmount()));
            }

            player.setBalance(balance);
            player.getTransactions().add(transaction);
            playerRepository.saveAndFlush(player);
        }

        return PlayerMapper.INSTANCE.toPlayerDTO(player);
    }
}
