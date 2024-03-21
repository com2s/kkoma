package com.ssafy.kkoma.api.product.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.kkoma.api.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.product.dto.ProductCreateRequest;
import com.ssafy.kkoma.api.product.dto.ProductDetailResponse;
import com.ssafy.kkoma.api.product.dto.ProductInfoResponse;
import com.ssafy.kkoma.domain.member.entity.Member;

import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.product.constant.MyProductType;
import com.ssafy.kkoma.domain.product.constant.ProductType;

import com.ssafy.kkoma.domain.product.entity.Category;

import com.ssafy.kkoma.domain.product.entity.ProductImage;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductImageService productImageService;
	private final CategoryService categoryService;
	private final MemberService memberService;

	public Product findProductByProductId(Long productId) {
		return productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_EXISTS));
	}

	public List<ProductSummary> getProducts(){
		List<Product> products = productRepository.findAll();

		return products.stream()
			.map(ProductSummary::fromEntity)
			.collect(Collectors.toList());
	}

	public ProductDetailResponse getProduct(Long productId) {
		List<String> productImageUrls = productImageService.getProductImageUrls(productId);
		Product product = findProductByProductId(productId);
		String categoryName = categoryService.getCategoryName(product.getCategory().getId());

		return buildProductDetailResponse(product, productImageUrls, categoryName, product.getMember());
	}

	public ProductInfoResponse getProductInfoResponse(Long productId) {

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_EXISTS));

		return ProductInfoResponse.fromEntity(product, MyProductType.BUY);
	}

	public Product findProductById(Long productId){
		return productRepository.findById(productId)
				.orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_EXISTS));
	}

    public ProductDetailResponse createProduct(Long memberId, ProductCreateRequest productCreateRequest) {
		List<String> productImageUrls = productCreateRequest.getProductImages();
		Member seller = memberService.findMemberByMemberId(memberId);
		Category category = categoryService.findCategoryById(productCreateRequest.getCategoryId());

		Product product = Product.builder()
				.member(seller)
				.category(category)
				.thumbnailImage(productImageUrls.isEmpty() ? null : productImageUrls.get(0))
				.placeDetail("TODO: MVP 개발 이후 location과 placeDetail 저장하는 로직 짜야돼")
				.title(productCreateRequest.getTitle())
				.description(productCreateRequest.getDescription())
				.price(productCreateRequest.getPrice())
				.build();

		Product savedProduct = productRepository.save(product);

		List<ProductImage> productImages = productImageService.createProductImages(productImageUrls, product);
		List<String> savedProductImageUrls = new ArrayList<>();
		for (ProductImage productImage : productImages) {
			savedProductImageUrls.add(productImage.getProductImage());
		}

		return buildProductDetailResponse(savedProduct, savedProductImageUrls, category.getName(), seller);
	}


	private ProductDetailResponse buildProductDetailResponse(
		Product product,
		List<String> productImageUrls,
		String categoryName,
		Member seller
	) {
		MemberSummaryResponse sellerSummaryResponse = MemberSummaryResponse.fromEntity(seller);

		return ProductDetailResponse.builder()
			.id(product.getId())
			.productImages(productImageUrls)
			.title(product.getTitle())
			.description(product.getDescription())
			.categoryName(categoryName)
			.price(product.getPrice())
			.status(product.getStatus())
			.dealPlace(product.getPlaceDetail())
			.elapsedMinutes(Duration.between(product.getCreatedAt(), LocalDateTime.now()).toMinutes())
			.memberSummary(sellerSummaryResponse)
			.wishCount(product.getWishCount())
			.offerCount(product.getOfferCount())
			.viewCount(product.getViewCount())
			.build();
	}

}
