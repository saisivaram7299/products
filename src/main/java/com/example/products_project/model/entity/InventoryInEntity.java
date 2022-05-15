package com.example.products_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Sivaram
 */


@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryInEntity {
    @Id
    @Column(name = "inventory_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;


    @Column(name = "prdt_cd")
    private String productCode;

    @Column(name = "batch")
    private String batch;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "deal")
    private Integer deal;

    @Column(name = "free")
    private Integer free;

    @Column(name = "mrp")
    private Double mrp;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "company")
    private String company;

    @Column(name = "supplier_id")
    private Long supplierId;


}
