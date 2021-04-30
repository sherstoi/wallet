package com.leo.mapper;

import com.leo.dto.TransactionDTO;
import com.leo.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDTO toTransactionDTO(Transaction transaction);
    List<TransactionDTO> toTransactionDTOs(List<Transaction> transactions);
}
