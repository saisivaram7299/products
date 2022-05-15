package com.example.products_project.mappers;

import com.example.products_project.model.dto.InventoryDTO;
import com.example.products_project.model.entity.InventoryInEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

/**
 * @author Sivaram
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface InventoryEntityMapper {

    InventoryDTO convertEntityToDTO(InventoryInEntity inventoryInEntity);

    List<InventoryDTO> convertEntityToDTO(List<InventoryInEntity> inventoryInEntities);


}
