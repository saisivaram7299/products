package com.example.products_project.repository;

import com.example.products_project.model.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sivaram
 */

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity , String> {
}
