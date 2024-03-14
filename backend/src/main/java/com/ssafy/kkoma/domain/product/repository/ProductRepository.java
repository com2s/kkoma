package com.ssafy.kkoma.domain.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.kkoma.domain.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
