package com.ssafy.kkoma.domain.product.repository;

import com.ssafy.kkoma.domain.product.constant.ProductType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.kkoma.domain.product.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    List<Product> findByStatusOrderByCreatedAtDesc(ProductType productType);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Product s where s.id=:id")
    Product findByIdWithPessimisticLock(Long id);

    @Query("select s from Product s where s.category.id=:categoryId AND s.status=:status ORDER BY s.createdAt DESC LIMIT 20")
    List<Product> findByCategoryIdAndStatus(Integer categoryId, ProductType status);

}
