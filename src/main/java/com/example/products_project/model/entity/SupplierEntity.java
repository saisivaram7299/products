package com.example.products_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author Sivaram
 */
@Entity
@Table(name = "suppliers_mst_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierEntity {
    @Id
    @Column(name = "supplier_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long supplierId;

    @Column(name = "supplier_name")
    private String supplierName;
}
