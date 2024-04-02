package com.ssafy.kkoma.api.product.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.kkoma.api.area.service.AreaService;
import com.ssafy.kkoma.api.chat.service.ChatRoomService;
import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.deal.service.DealService;
import com.ssafy.kkoma.api.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.product.dto.ProductCreateRequest;
import com.ssafy.kkoma.api.product.dto.ProductDetailResponse;
import com.ssafy.kkoma.api.product.dto.ProductInfoResponse;
import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.api.product.dto.response.ChatProductResponse;
import com.ssafy.kkoma.api.product.dto.response.SearchProductResponse;
import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.domain.chat.entity.ChatRoom;
import com.ssafy.kkoma.api.product.dto.ProductWishResponse;

import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.deal.repository.DealRepository;
import com.ssafy.kkoma.domain.location.entity.Location;
import com.ssafy.kkoma.domain.location.repository.LocationRepository;
import com.ssafy.kkoma.domain.member.entity.Member;

import com.ssafy.kkoma.domain.product.constant.MyProductType;

import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Category;

import com.ssafy.kkoma.domain.product.entity.ProductImage;
import com.ssafy.kkoma.domain.product.entity.WishList;
import com.ssafy.kkoma.domain.product.repository.WishListRepository;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;
import com.ssafy.kkoma.global.util.complete.AutoCompleteUtils;

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
	private final ChatRoomService chatRoomService;
	private final WishListRepository wishListRepository;
	private final DealRepository dealRepository;
	private final DealService dealService;
	private final LocationRepository locationRepository;
	private final AreaService areaService;

	public Product findProductByProductId(Long productId){
		return productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_EXISTS));
	}

	public List<ProductSummary> getProducts(){
		List<Product> products = productRepository.findAll();
		List<ProductSummary> productSummaries = new ArrayList<>();

		for (Product product : products) {
			ProductSummary productSummary = ProductSummary.fromEntity(product);
			if(product.getLocation() != null) {
				String dealPlace = areaService.findAreaById(product.getLocation().getRegionCode()).getFullArea();
				productSummary.setDealPlace(dealPlace);
			}
			productSummaries.add(productSummary);
		}

		return productSummaries;
	}

	public SearchProductResponse searchProduct(Long memberId, SearchProductRequest searchProductRequest, Pageable pageable) {
		Page<Product> pageList = productRepository.searchProduct(searchProductRequest, pageable);
		return buildSearchProductResponse(memberId, pageList);
	}

	public ProductDetailResponse getProduct(Long productId, Long memberId) {
		Product product = findProductByProductId(productId);
		List<String> productImageUrls = productImageService.getProductImageUrls(productId);
		String categoryName = categoryService.getCategoryName(product.getCategory().getId());
		WishList wishList = wishListRepository.findByProductIdAndMemberId(productId, memberId);
		boolean isWished = true;
		if (wishList == null || !wishList.getIsValid())
			isWished = false;

		Area area = areaService.findAreaById(product.getLocation().getRegionCode());

		return buildProductDetailResponse(product, productImageUrls, categoryName, product.getMember(), isWished, area, product.getLocation().getPlaceDetail());
	}

	public void addViewCount(Long productId) {
		Product product = productRepository.findByIdWithPessimisticLock(productId);
		product.addViewCount();
		productRepository.save(product);
	}

	public ProductInfoResponse getProductInfoResponse(Long productId) {

		Product product = findProductByProductId(productId);
		Deal deal = dealRepository.findByProductOrderBySelectedTimeDesc(product);
		Area area = areaService.findAreaById(product.getLocation().getRegionCode());

		return ProductInfoResponse.fromEntity(
			product,
			MyProductType.BUY,
			deal != null ? deal.getId() : null,
			deal != null ? deal.getSelectedTime() : null,
			area
		);
	}

    public ProductDetailResponse createProduct(Long memberId, ProductCreateRequest productCreateRequest) {
		List<String> productImageUrls = productCreateRequest.getProductImages();
		Member seller = memberService.findMemberByMemberId(memberId);
		Category category = categoryService.findCategoryById(productCreateRequest.getCategoryId());
		ChatRoom chatRoom = chatRoomService.createChatRoom();
		Location location = locationRepository.save(productCreateRequest.getCreateLocationRequest().toEntity());

		Product product = Product.builder()
				.member(seller)
				.thumbnailImage(productImageUrls.isEmpty() ? null : productImageUrls.get(0))
				.location(location)
				.title(productCreateRequest.getTitle())
				.description(productCreateRequest.getDescription())
				.price(productCreateRequest.getPrice())
				.build();

		//product.setMember(seller);
		product.setCategory(category);
		product.setChatRoom(chatRoom);

		Product savedProduct = productRepository.save(product);
		Area area = areaService.findAreaById(savedProduct.getLocation().getRegionCode());

		List<ProductImage> productImages = productImageService.createProductImages(productImageUrls, product);
		List<String> savedProductImageUrls = new ArrayList<>();
		for (ProductImage productImage : productImages) {
			savedProductImageUrls.add(productImage.getProductImage());
		}

		return buildProductDetailResponse(savedProduct, savedProductImageUrls, category.getName(), seller, false,  area, product.getLocation().getPlaceDetail());
	}


	private ProductDetailResponse buildProductDetailResponse(
		Product product,
		List<String> productImageUrls,
		String categoryName,
		Member seller,
		boolean wish,
		Area area,
		String placeDetail
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
			.dealPlace(area.getFullArea() + " " + placeDetail)
			.x(product.getLocation().getX())
			.y(product.getLocation().getY())
			.elapsedMinutes(Duration.between(product.getCreatedAt(), LocalDateTime.now()).toMinutes())
			.memberSummary(sellerSummaryResponse)
			.chatRoomId(product.getChatRoom().getId())
			.wish(wish)
			.wishCount(product.getWishCount())
			.offerCount(product.getOfferCount())
			.viewCount(product.getViewCount())
			.build();
	}

	private SearchProductResponse buildSearchProductResponse(Long memberId, Page<Product> page) {
		List<ProductSummary> content = new ArrayList<>();

		for (Product product : page.getContent()) {
			ProductSummary productSummary = ProductSummary.fromEntity(product);
			if(product.getLocation() != null) {
				String dealPlace = areaService.findAreaById(product.getLocation().getRegionCode()).getFullArea();
				productSummary.setDealPlace(dealPlace);
			}
			content.add(productSummary);
		}

		Member member = memberService.findMemberByMemberId(memberId);

		return SearchProductResponse.builder()
			.content(content)
			.preferredPlaceRegionCode(member.getPreferredPlaceRegionCode())
			.size(page.getSize())
			.page(page.getNumber())
			.numberOfElements(page.getNumberOfElements())
			.totalElements(page.getTotalElements())
			.totalPages(page.getTotalPages())
			.first(page.isFirst())
			.last(page.isLast())
			.empty(page.isEmpty())
			.build();
	}

	public ProductWishResponse wishProduct(Long productId, Long memberId) {
		// 글 조회
		Product product = productRepository.findByIdWithPessimisticLock(productId);
		product.addWishCount();
		Member member = memberService.findMemberByMemberId(memberId);

		WishList wishList = wishListRepository.findByProductIdAndMemberId(productId, memberId);
		if (wishList == null) {
			wishList = WishList.builder()
					.build();
			wishList.setMemberAndProduct(member, product);
 		} else if (wishList.getIsValid()) {
			throw new BusinessException(ErrorCode.WISH_LIST_ALREADY_VALID);
		}

		wishList.setValid(true);
		WishList savedWishList = wishListRepository.save(wishList);
		return ProductWishResponse.fromEntity(savedWishList, product);
	}

	public ProductWishResponse unwishProduct(Long productId, Long memberId) {
		Product product = productRepository.findByIdWithPessimisticLock(productId);

		WishList wishList = wishListRepository.findByProductIdAndMemberId(productId, memberId);
		if (wishList == null) {
			throw new BusinessException(ErrorCode.WISH_LIST_DOES_NOT_EXISTS);
		} else if (!wishList.getIsValid()) {
			throw new BusinessException(ErrorCode.WISH_LIST_ALREADY_NOT_VALID);
		}

		wishList.setValid(false);
		product.subWishCount();
		WishList savedWishList = wishListRepository.save(wishList);
		return ProductWishResponse.fromEntity(savedWishList, product);
	}

	public List<Product> findProductForSaleByCategoryId(Integer categoryId) {
		return productRepository.findByCategoryIdAndStatus(categoryId, ProductType.SALE);
	}
	
	public BasePageResponse<WishList, ProductSummary> getMyWishProducts(Long memberId, Pageable pageable) {
		Page<WishList> wishLists = wishListRepository.findWishListsByMemberIdAndIsValid(memberId, true, pageable);
		List<ProductSummary> content = new ArrayList<>();
		for (WishList wishList: wishLists) {
			String dealPlace = areaService.findAreaById(wishList.getProduct().getLocation().getRegionCode()).getFullArea();
			ProductSummary productSummary = ProductSummary.fromEntity(wishList.getProduct());
			productSummary.setDealPlace(dealPlace);
			content.add(productSummary);
		}
		return new BasePageResponse<>(content, wishLists);
	}

	public List<String> getAutoCompleteKeyword(String keyword) {
		AutoCompleteUtils utils = AutoCompleteUtils.getInstance();
		return utils.getPrefixMap(keyword);
	}

	public ChatProductResponse getChatProduct(Long chatRoomId) {
		ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomId);
		Product product = findProductByProductId(chatRoom.getProduct().getId());

		ChatProductResponse chatProductResponse = ChatProductResponse.fromEntity(product);

		if (product.getStatus().equals(ProductType.PROGRESS)) {
			Deal deal = dealService.findDealByProductId(product.getId());
			chatProductResponse.setBuyerId(deal.getMember().getId());
		}

		return chatProductResponse;
	}

}
