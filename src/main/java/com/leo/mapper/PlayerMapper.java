package com.leo.mapper;

import com.leo.dto.PlayerDTO;
import com.leo.entity.Player;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ACCESSOR_ONLY, uses = TransactionMapper.class)
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    PlayerDTO toPlayerDTO(Player player);
}
