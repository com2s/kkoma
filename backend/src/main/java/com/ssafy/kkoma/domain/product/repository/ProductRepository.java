package com.ssafy.kkoma.domain.product.repository;

import com.ssafy.kkoma.domain.product.constant.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.kkoma.domain.product.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    List<Product> findByStatusOrderByCreatedAtDesc(ProductType productType);

}
