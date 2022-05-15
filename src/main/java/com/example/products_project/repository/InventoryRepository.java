package com.example.products_project.repository;

import com.example.products_project.model.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Sivaram
 */

@Repository

public interface InventoryRepository extends JpaRepository<InventoryEntity , Long>  , JpaSpecificationExecutor<InventoryEntity> {
}
