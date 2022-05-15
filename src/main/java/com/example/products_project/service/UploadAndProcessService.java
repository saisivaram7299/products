package com.example.products_project.service;

import com.example.products_project.model.dto.InventoryDTO;
import com.example.products_project.model.dto.InventoryDetailsRequestVO;
import com.example.products_project.model.entity.InventoryEntity;
import com.example.products_project.model.entity.InventoryInEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Sivaram
 */
public interface UploadAndProcessService {

    List<InventoryDTO> parseCsvFile(MultipartFile file) throws Exception;

    List<InventoryEntity> fetchProductList(InventoryDetailsRequestVO inventoryDetailsRequestVO);



}
