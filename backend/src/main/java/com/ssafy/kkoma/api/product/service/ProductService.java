package com.ssafy.kkoma.api.product.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.kkoma.api.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.product.dto.ProductCreateRequest;
import com.ssafy.kkoma.api.product.dto.ProductCreateResponse;
import com.ssafy.kkoma.api.product.dto.ProductDetailResponse;
import com.ssafy.kkoma.api.product.dto.ProductInfoResponse;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.product.constant.MyProductType;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.ProductImage;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
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

	public List<ProductSummary> getProducts(){
		List<Product> products = productRepository.findAll();

		return products.stream()
			.map(ProductSummary::fromEntity)
			.collect(Collectors.toList());
	}

	public ProductDetailResponse getProduct(Long productId) {
		List<String> productImages = productImageService.getProductImages(productId);

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_EXISTS));

		String categoryName = categoryService.getCategoryName(product.getCategory().getId());

		MemberSummaryResponse memberSummaryResponse = MemberSummaryResponse.fromEntity(product.getMember());

		return ProductDetailResponse.builder()
				.id(productId)
				.productImages(productImages)
				.title(product.getTitle())
				.description(product.getDescription())
				.categoryName(categoryName)
				.price(product.getPrice())
				.status(product.getStatus())
				.dealPlace(product.getPlaceDetail())
				.elapsedMinutes(Duration.between(product.getCreatedAt(), LocalDateTime.now()).toMinutes())
				.memberSummary(memberSummaryResponse)
				.wishCount(product.getWishCount())
				.offerCount(product.getOfferCount())
				.viewCount(product.getViewCount())
				.build();
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

    public ProductCreateResponse createProduct(Long memberId, ProductCreateRequest productCreateRequest) {
		Product product = Product.builder()
				.title(productCreateRequest.getTitle())
				.description(productCreateRequest.getDescription())
				.category(categoryService.findCategoryById(productCreateRequest.getCategoryId()))
				.price(productCreateRequest.getPrice())
				.build();

		Member member = memberService.findMemberByMemberId(memberId);
		product.setMember(member);

		List<String> productImageUrls = productCreateRequest.getProductImages();
		List<ProductImage> productImages = productImageService.createProductImages(productImageUrls, product);
		Product savedProduct = productRepository.save(product);
		return ProductCreateResponse.fromEntity(savedProduct, productImages);
	}

}
