package com.example.products_project.service.implementation;

import com.example.products_project.mappers.InventoryEntityMapper;
import com.example.products_project.model.dto.InventoryDTO;
import com.example.products_project.model.entity.InventoryInEntity;
import com.example.products_project.model.entity.ProductsEntity;
import com.example.products_project.model.entity.SupplierEntity;
import com.example.products_project.repository.InventoryInRepository;
import com.example.products_project.repository.ProductsRepository;
import com.example.products_project.repository.SuppliersRepository;
import com.example.products_project.service.SaveInventoryDataService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Sivaram
 */

@Service
@Slf4j
public class SaveInventoryDataServiceImpl implements SaveInventoryDataService {

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    SuppliersRepository suppliersRepository;

    @Autowired
    InventoryInRepository inventoryInRepository;

    @Autowired
    InventoryEntityMapper inventoryEntityMapper;

// can implement a thread based save to optimise time but here didn't do to avoid code complexity here

    public List<InventoryDTO> saveInventoryData(List<Record> datalist)  {

        if(!CollectionUtils.isEmpty(datalist)){
//
           List <ProductsEntity> productsEntities =  productsRepository.findAll();
//
           Map<String , String> productCdNameMap = productsEntities.stream().collect(Collectors.toMap(ProductsEntity::getCode, ProductsEntity::getProductName));

           List<SupplierEntity> supplierEntities = suppliersRepository.findAll();

           Map<String , Long> supplierNameIdMap = supplierEntities.stream().collect(Collectors.toMap(SupplierEntity::getSupplierName , SupplierEntity ::getSupplierId));

            Map<Long , String> supplierIdNameMap = supplierEntities.stream().collect(Collectors.toMap(SupplierEntity::getSupplierId , SupplierEntity ::getSupplierName));


            List<InventoryInEntity> inventoryInEntities = new ArrayList<>();

           datalist.forEach((data)->{
               InventoryInEntity entity = InventoryInEntity.builder()
                       .batch(data.getString("batch"))
                       .company(data.getString("company"))
                       .deal( data.getString("deal")!=null && !data.getString("deal").isEmpty() ?Integer.parseInt(data.getString("deal")): 0)
                       .free(data.getString("free")!=null && !data.getString("free").isEmpty() ?Integer.parseInt(data.getString("free")): 0)
                       .mrp(data.getString("mrp")!=null && !data.getString("mrp").isEmpty() ?Double.parseDouble(data.getString("mrp")): 0)
                       .productCode(data.getString("code"))
                       .stock(data.getString("stock")!=null && !data.getString("stock").isEmpty() ?Integer.parseInt(data.getString("stock")): 0)
                       .rate(data.getString("rate")!=null && !data.getString("rate").isEmpty() ?Double.parseDouble(data.getString("rate")): 0)
                       .supplierId(data.getString("supplier")!=null && !data.getString("supplier").isEmpty() ? supplierNameIdMap.get(data.getString("supplier")) : null)
                       .build();
               String expiryDate = data.getString("exp");

               try{
                   Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate);
                   entity.setExpiryDate(date1);
               }
               catch (Exception ex){
                   log.error("fail to parse expiry date with exception :{} " , ex.getMessage());
                   entity.setExpiryDate(null);
               }

               inventoryInEntities.add(entity);

           });

            List <InventoryInEntity> savedList =  (inventoryInRepository.saveAllAndFlush(inventoryInEntities));

            List <InventoryDTO> inventoryDTOS = inventoryEntityMapper.convertEntityToDTO(savedList);

            if(!CollectionUtils.isEmpty(inventoryDTOS)){
                inventoryDTOS.forEach((x)->{
                    if(x!=null && x.getSupplierId()!=null ){
                        x.setSupplierName(supplierIdNameMap.get(x.getSupplierId()));
                    }
                    if(x!=null && x.getProductCode()!=null && !x.getProductCode().isEmpty()){
                        x.setProductName(productCdNameMap.get(x.getProductCode()));
                    }
                });
            }

            return inventoryDTOS;

        }

        return (Collections.emptyList());

    }
}
