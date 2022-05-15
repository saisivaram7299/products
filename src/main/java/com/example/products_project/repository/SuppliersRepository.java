package com.example.products_project.repository;

import com.example.products_project.model.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Sivaram
 */
@Repository
public interface SuppliersRepository  extends JpaRepository<SupplierEntity , Long> {

    @Query("select se  from SupplierEntity se where se.supplierName in :suppliers")
    List<SupplierEntity> findAllBySupplierName(@Param("suppliers") Set<String> suppliers);
}
