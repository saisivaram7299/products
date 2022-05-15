package com.example.products_project.service;

import com.example.products_project.model.dto.InventoryDTO;
import com.example.products_project.model.entity.InventoryEntity;
import com.example.products_project.model.entity.InventoryInEntity;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.record.Record;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Sivaram
 */
public interface SaveInventoryDataService {

    List<InventoryDTO> saveInventoryData(List<Record> datalist);

}
