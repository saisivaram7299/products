package com.example.products_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Sivaram
 */
@Entity
@Table(name = "products_mst_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsEntity {

    @Id
    @Column(name = "prdt_cd")
    private String Code;

    @Column(name = "prdt_name")
    private String productName;

}
