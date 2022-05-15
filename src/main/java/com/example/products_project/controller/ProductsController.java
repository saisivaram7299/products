package com.example.products_project.controller;

import com.example.products_project.model.dto.InventoryDTO;
import com.example.products_project.model.dto.InventoryDetailsRequestVO;
import com.example.products_project.model.entity.InventoryEntity;
import com.example.products_project.model.entity.InventoryInEntity;
import com.example.products_project.service.UploadAndProcessService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Sivaram
 */
@RestController
public class ProductsController {

    @Autowired
    UploadAndProcessService uploadAndProcessService;

    @PostMapping("/document/upload-process")
    @ApiOperation(value = "Upload CSV and Process ", notes = "Upload CSV and Process -> add or update in database")
    public ResponseEntity<List<InventoryDTO>> uploadCsvAndProcess(
            @ApiParam(name = "Multipart HTTP Request", value = "Multipart file with Member documents information") @RequestParam("file") MultipartFile file) throws Exception {


        try {
            List<InventoryDTO> entities =  uploadAndProcessService.parseCsvFile(file);

            return new ResponseEntity<>(entities, HttpStatus.OK);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @PostMapping("/supplier/products-list")
    public List<InventoryEntity> getAllProductsBySuppliers(@RequestBody InventoryDetailsRequestVO inventoryDetailsRequestVO){

        return uploadAndProcessService.fetchProductList(inventoryDetailsRequestVO);

    }




}
