package com.ssafy.kkoma.domain.product.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ssafy.kkoma.domain.product.dto.response.ProductSummary;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.ProductImage;
import com.ssafy.kkoma.domain.product.repository.ProductImageRepository;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;

@SpringBootTest
@EnableJpaAuditing
class ProductServiceTest {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private ProductService productService;

	private static final String TITLE = "TITLE";
	private static final String IMAGE_URL = "IMAGE_URL";

	@Test
	public void 글_목록_전체_조회하기() throws Exception{
	    // given
		List<Product> products = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
	    	products.add(productRepository.save(Product.builder().title(TITLE).thumbnailImage(IMAGE_URL).build()));
		}

	    // when
		List<ProductSummary> productSummaries = productService.getProducts();

		// then
		assertEquals(10, productSummaries.size());
	}

}
