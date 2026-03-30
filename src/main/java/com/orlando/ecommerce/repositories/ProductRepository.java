package com.orlando.ecommerce.repositories;

import com.orlando.ecommerce.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT obj FROM Product obj " +
    "WHERE UPPER(obj.name) LIKE UPPER(CONCAT('%', :name ,'%'))")
    Page<Product> search_name(String name, Pageable pageable);

}
