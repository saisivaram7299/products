package com.example.products_project.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Sivaram
 */

@Data
@Builder
public class InventoryDetailsRequestVO {

    private Long supplierId;

    private String productName;

    private boolean checkExpiry;

    private Long pageNumber;

    private Long pageSize;


}
