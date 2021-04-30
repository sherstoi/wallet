package com.leo.service;

import com.leo.entity.Player;
import com.leo.entity.Transaction;
import com.leo.exception.PlayerAmountIsNotEnoughException;
import com.leo.exception.PlayerNotFoundException;
import com.leo.exception.TransactionIsNotUniqueException;
import com.leo.repository.PlayerRepository;
import com.leo.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class WalletServiceTest {
    private static final String SOME_PLAYER_ID = "12345";
    private static final String SOME_TRANSACTION_ID = "54321";

    @Autowired
    private WalletService walletService;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    void testGetPlayerBalance() {
        Player player = new Player(SOME_PLAYER_ID, BigDecimal.TEN, "", new ArrayList<>());

        when(playerRepository.findById(player.getId()))
                .thenReturn(Optional.of(player));

        assertEquals(BigDecimal.TEN, walletService.getPlayerBalance(player.getId()));
    }

    @Test
    void testGetTransactions() {
        Transaction tx1 = new Transaction(UUID.randomUUID().toString(), BigDecimal.ONE);
        Transaction tx2 = new Transaction(UUID.randomUUID().toString(), BigDecimal.ONE);
        Player player = new Player(UUID.randomUUID().toString(), BigDecimal.TEN, "", List.of(tx1, tx2));

        when(playerRepository.findById(player.getId()))
                .thenReturn(Optional.of(player));

        assertEquals(2, walletService.getTransactionsByPlayerId(player.getId()).size());
    }

    @Test
    void testTransactionMinus() {
        Transaction tx = new Transaction(UUID.randomUUID().toString(), BigDecimal.ONE.multiply(BigDecimal.valueOf(-1)));
        List<Transaction> list = new ArrayList<>();
        list.add(tx);
        Player player = new Player(UUID.randomUUID().toString(), BigDecimal.TEN, "", list);

        when(playerRepository.findById(player.getId()))
                .thenReturn(Optional.of(player));
        when(playerRepository.saveAndFlush(player))
                .thenReturn(player);

        assertEquals(walletService.executeTransaction(player.getId(), tx).getBalance(), BigDecimal.valueOf(9));
    }

    @Test
    void testTransactioPlus() {
        Player player = new Player(UUID.randomUUID().toString(), BigDecimal.TEN, "", new ArrayList<>());
        Transaction tx = new Transaction(UUID.randomUUID().toString(), BigDecimal.ONE);

        when(playerRepository.findById(player.getId()))
                .thenReturn(Optional.of(player));

        when(playerRepository.saveAndFlush(player))
                .thenReturn(player);

        assertEquals(walletService.executeTransaction(player.getId(), tx).getBalance(), BigDecimal.valueOf(11));
    }

    @Test
    void testGetTransactionEmpty() {
        Player player = new Player(UUID.randomUUID().toString(), BigDecimal.TEN, "", new ArrayList<>());

        when(playerRepository.findById(player.getId()))
                .thenReturn(Optional.of(player));

        assertEquals(0, walletService.getTransactionsByPlayerId(player.getId()).size());
    }

    @Test
    void testPlayerNotFoundException() {
        when(playerRepository.findById(SOME_PLAYER_ID))
                .thenReturn(Optional.empty());

        assertThrows(
                PlayerNotFoundException.class,
                () -> walletService.getPlayerBalance(SOME_PLAYER_ID),
                "PlayerNotFoundException"
        );
    }

    @Test
    void testTransactionIsNotUniqueException() {
        when(transactionRepository.existsById(SOME_TRANSACTION_ID))
                .thenReturn(true);

        Transaction tx = new Transaction(SOME_TRANSACTION_ID, BigDecimal.TEN);

        assertThrows(TransactionIsNotUniqueException.class,
                () -> walletService.executeTransaction(UUID.randomUUID().toString(), tx),
                "TransactionIdIsNotUniqueException");
    }

    @Test
    void testPlayerAmountIsNotEnoughException() {
        Transaction tx = new Transaction(SOME_TRANSACTION_ID, BigDecimal.valueOf(-12));
        Player player = new Player(SOME_PLAYER_ID, BigDecimal.valueOf(1L), "", new ArrayList<>());

        when(playerRepository.findById(SOME_PLAYER_ID)).thenReturn(Optional.of(player));

        assertThrows(PlayerAmountIsNotEnoughException.class,
                () -> walletService.executeTransaction(SOME_PLAYER_ID, tx),
                "PlayerAmountIsNotEnoughException");
    }
}
