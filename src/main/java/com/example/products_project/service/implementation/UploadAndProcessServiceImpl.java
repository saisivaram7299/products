package com.example.products_project.service.implementation;

import com.example.products_project.model.dto.InventoryDTO;
import com.example.products_project.model.dto.InventoryDetailsRequestVO;
import com.example.products_project.model.entity.InventoryEntity;
import com.example.products_project.model.entity.InventoryInEntity;
import com.example.products_project.model.entity.ProductsEntity;
import com.example.products_project.model.entity.SupplierEntity;
import com.example.products_project.repository.InventoryRepository;
import com.example.products_project.repository.ProductsRepository;
import com.example.products_project.repository.SuppliersRepository;
import com.example.products_project.service.SaveInventoryDataService;
import com.example.products_project.service.UploadAndProcessService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.record.Record;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParser;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Sivaram
 */

@Service
@Slf4j
public class UploadAndProcessServiceImpl implements UploadAndProcessService {

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    SuppliersRepository suppliersRepository;

    @Autowired
    SaveInventoryDataService saveInventoryDataService;

    @Autowired
    InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public List<InventoryDTO> parseCsvFile(MultipartFile file) throws Exception {

        try {
            InputStream inputStream = file.getInputStream();
            CsvParserSettings settings = new CsvParserSettings();
            settings.setHeaderExtractionEnabled(true);
            CsvParser parser = new CsvParser(settings);
            List<Record> dataList = parser.parseAllRecords(inputStream);
            log.info("parsed data size of :{}", dataList.size());
            List<ProductsEntity> productsEntities = new ArrayList<>();
            Set<String> suppliers = new HashSet<>();
            if (!CollectionUtils.isEmpty(dataList)) {
                dataList.forEach((data) -> {
                    if (data != null) {
                        ProductsEntity productsEntity = new ProductsEntity();
                        productsEntity.setCode(data.getString("code"));
                        productsEntity.setProductName(data.getString("name"));
                        productsEntities.add(productsEntity);
                        suppliers.add(data.getString("supplier"));
                    }

                });
                productsRepository.saveAllAndFlush(productsEntities);

                List<SupplierEntity> supplierEntities = suppliersRepository.findAllBySupplierName(suppliers);

                if (supplierEntities.isEmpty()) {
                    suppliers.forEach((supply) -> {
                        if(supply!=null && !supply.isEmpty()){
                            SupplierEntity supplierEntity = SupplierEntity.builder()
                                    .supplierName(supply)
                                    .build();
                            supplierEntities.add(supplierEntity);

                        }

                    });
                    suppliersRepository.saveAllAndFlush(supplierEntities);
                } else {

                    List<SupplierEntity> newSuppliersEntity = new ArrayList<>();

                    Set<String> existingSuppliers = supplierEntities.stream().map(SupplierEntity::getSupplierName).collect(Collectors.toSet());

                    suppliers.forEach(x -> {
                        if (!existingSuppliers.contains(x) && x!=null &&!x.isEmpty()) {
                            SupplierEntity supplierEntity = SupplierEntity.builder()
                                    .supplierName(x).build();
                            newSuppliersEntity.add(supplierEntity);

                        }
                    });

                    suppliersRepository.saveAllAndFlush(newSuppliersEntity);


                }

                return saveInventoryDataService.saveInventoryData(dataList);

            } else {
                throw new Exception("data is empty to insert");
            }
        } catch (Exception ex) {
            log.error("error while parsing the csv with exception : {}", ex.getMessage());

            throw new Exception("error while parsing");
        }


    }

    @Override
    public List<InventoryEntity> fetchProductList(InventoryDetailsRequestVO inventoryDetailsRequestVO) {
        if (inventoryDetailsRequestVO != null) {
            Specification<InventoryEntity> detailSpec = getProductsDetailsSpec(inventoryDetailsRequestVO);

            Page<InventoryEntity> inventoryEntities = inventoryRepository.findAll(detailSpec, PageRequest.of(inventoryDetailsRequestVO.getPageNumber().intValue() - 1, inventoryDetailsRequestVO.getPageSize().intValue()));

            if (!CollectionUtils.isEmpty(inventoryEntities.getContent())) {
                return inventoryEntities.getContent();
            }


        }
        return null;
    }

    private Specification<InventoryEntity> getProductsDetailsSpec(InventoryDetailsRequestVO inventoryDetailsRequestVO) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

//            Join<InventoryEntity, ProductsEntity> inventoryEntityProductsEntityJoin =
//                    root.join("productsEntity", JoinType.INNER);

            Join<InventoryEntity, SupplierEntity> inventoryEntitySupplierEntityJoin = root.join("supplierEntity", JoinType.INNER);


            if (inventoryDetailsRequestVO.getSupplierId() != null) {
                predicates.add(criteriaBuilder.equal(inventoryEntitySupplierEntityJoin.get("supplierId"), inventoryDetailsRequestVO.getSupplierId()));
            }

//            if (inventoryDetailsRequestVO.getProductName() != null) {
//                predicates.add(criteriaBuilder.equal(inventoryEntitySupplierEntityJoin.get("productName"), inventoryDetailsRequestVO.getProductName()));
//
//            }

            if (inventoryDetailsRequestVO.isCheckExpiry()) {
                Date date1 = new Date("dd/MM/yyyy");
                predicates.add(criteriaBuilder.lessThan(root.get("expiryDate"), date1));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
