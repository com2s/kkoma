package com.ssafy.kkoma.domain.product.repository;

import com.ssafy.kkoma.domain.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllProductImagesByProductId(Long productId);

}
