package com.example.products_project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Sivaram
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDTO {


    private Long id;


    private String productCode;

    private String productName;

    private String batch;

    private Integer stock;

    private Integer deal;

    private Integer free;

    private Double mrp;

    private Double rate;

    private Date expiryDate;

    private String company;


    private Long supplierId;

    private String supplierName;


}


