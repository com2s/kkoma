package com.ssafy.kkoma.api.product.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.location.dto.request.CreateLocationRequest;
import com.ssafy.kkoma.api.product.dto.ProductCreateRequest;
import com.ssafy.kkoma.api.product.dto.ProductWishResponse;
import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.api.product.dto.response.ChatProductResponse;
import com.ssafy.kkoma.api.product.dto.response.SearchProductResponse;
import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.domain.location.entity.Location;

import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.api.product.dto.ProductDetailResponse;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.WishList;
import com.ssafy.kkoma.domain.product.repository.CategoryRepository;
import com.ssafy.kkoma.factory.AreaFactory;
import com.ssafy.kkoma.factory.CategoryFactory;
import com.ssafy.kkoma.factory.ChatRoomFactory;
import com.ssafy.kkoma.factory.LocationFactory;
import com.ssafy.kkoma.factory.MemberFactory;
import com.ssafy.kkoma.factory.ProductFactory;
import com.ssafy.kkoma.factory.WishListFactory;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ProductServiceTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private MemberFactory memberFactory;

	@Autowired
	private ProductFactory productFactory;

	@Autowired
	private CategoryFactory categoryFactory;

	@Autowired
	private ChatRoomFactory chatRoomFactory;

	@Autowired
	private WishListFactory wishListFactory;

	@Autowired
	private AreaFactory areaFactory;

	@Autowired
	private LocationFactory locationFactory;

	private static final String TITLE = "TITLE";
	private static final String IMAGE_URL = "IMAGE_URL";
	private static final String NAME = "NAME";

	@Test
	@Transactional
	public void 글_목록_전체_조회하기() throws Exception {
	    // given
		List<Product> products = new ArrayList<>();
		List<ProductSummary> productSummariesBefore = productService.getProducts();
		int sizeBefore = productSummariesBefore.size();
		Area area = areaFactory.createArea();

		for (int i = 0; i < 10; i++) {
			Location location = locationFactory.createLocation();
	    	products.add(productRepository.save(Product.builder().title(TITLE).thumbnailImage(IMAGE_URL).location(location).build()));
		}

	    // when
		List<ProductSummary> productSummaries = productService.getProducts();

		// then
		assertEquals(sizeBefore + 10, productSummaries.size());
	}

	@Test
	@Transactional
	public void 글_키워드_검색() throws Exception {
		// given
		Area area = areaFactory.createArea();
		Member member = memberFactory.createMember();
		member.setPreferredPlaceRegionCode(12L);

		for (int i = 0; i < 2; i++) {
			Location location = locationFactory.createLocation();
			productRepository.save(Product.builder().title(TITLE).thumbnailImage(IMAGE_URL).description("keyword").location(location).build());
		}

		SearchProductRequest searchProductRequest = SearchProductRequest.builder()
			.keyword("key")
			.build();
		Pageable pageable = PageRequest.of(0,10);

		// when
		SearchProductResponse searchProductResponse = productService.searchProduct(member.getId(), searchProductRequest, pageable);

		// then
		assertEquals(2, searchProductResponse.getContent().size());
	}

	@Test
	@Transactional
	public void 글_상세_조회하기_성공() throws Exception {

	    // given
		Category category = categoryFactory.createCategory("유모차");
		Member member = memberFactory.createMember();
		Product product = productFactory.createProduct(member, category, 10000);

		// when
		ProductDetailResponse productDetailResponse = productService.getProduct(product.getId(), member.getId());

	    // then
		assertEquals("TITLE", productDetailResponse.getTitle());
	}

	@Test
	@Disabled
	@Transactional
	public void 글_상세_조회하기_시_조회수_증가() throws Exception{

		// given
		Category category = categoryFactory.createCategory("유모차");
		Member member = memberFactory.createMember();
		Product product = productFactory.createProduct(member, category, 10000);

		// when
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					productService.addViewCount(product.getId());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();

		// then
		assertEquals(100, updatedProduct.getViewCount());
	}

    @Test
	@Transactional
    void 거래_글_생성() {
		// given
		Area area = areaFactory.createArea();
		Category category = categoryRepository.save(Category.builder().name("유모차").build());
		Member member = memberRepository.save(Member.builder().name(NAME).memberType(MemberType.KAKAO).role(Role.USER).build());
		Location location = locationFactory.createLocation();

		ProductCreateRequest productCreateRequest = ProductCreateRequest.builder()
				.title("TITLE")
				.productImages(List.of("...", "..."))
				.description("...")
				.price(10000)
				.categoryId(category.getId())
				.createLocationRequest(CreateLocationRequest.fromEntity(location))
				.build();

		// when
		ProductDetailResponse productDetailResponse = productService.createProduct(member.getId(), productCreateRequest);

		// then
		assertEquals("TITLE", productDetailResponse.getTitle());
    }

	@Test
	@Transactional
	void 이미_찜_상태인_글을_찜하면_예외를_던진다() {
		Member member = memberFactory.createMember();
		Product product = productFactory.createProduct(member);

		productService.wishProduct(product.getId(), member.getId());

		assertThrows(
				BusinessException.class,
				() -> productService.wishProduct(product.getId(), member.getId())
		);

	}

	@Test
	@Disabled
	@Transactional
	void 이미_찜_비활성_상태인_글을_찜_비활성화하면_예외를_던진다() {
		Member member = memberFactory.createMember();
		Product product = productFactory.createProduct(member);

		productService.unwishProduct(product.getId(), member.getId());

		assertThrows(
				BusinessException.class,
				() -> productService.unwishProduct(product.getId(), member.getId())
		);

	}

	@Test
	@Transactional
	public void 나의_모든_찜_목록을_조회한다() throws Exception{
	    // given
	    Member seller = memberFactory.createMember();
		Member buyer = memberFactory.createMember();

		Pageable pageable = PageRequest.of(0, 10);

		BasePageResponse<WishList, ProductSummary> beforeWishList = productService.getMyWishProducts(buyer.getId(), pageable);

	    // when
		for (int i = 0; i < 15; i++) {
			Product product = productFactory.createProduct(seller);
			wishListFactory.createWishList(buyer, product);
		}

	    // then
		BasePageResponse<WishList, ProductSummary> afterWishList = productService.getMyWishProducts(buyer.getId(), pageable);

		assertEquals(beforeWishList.getTotalElements() + 15, afterWishList.getTotalElements());
		assertEquals(beforeWishList.getSize() + 10, afterWishList.getSize() + 10);
	}


	@Test
	@Transactional
	public void 채팅방에서_거래_글_정보를_조회한다() throws Exception{
	    // given
		Member member = memberFactory.createMember();
		Category category = categoryFactory.createCategory();
		Product product = productFactory.createProduct(member, category,1000);

	    // when
		ChatProductResponse chatProduct = productService.getChatProduct(
			product.getChatRoom().getId()
		);

		// then
		assertEquals(1000, chatProduct.getPrice());
	}

	@Test
	@Transactional
	void 찜_하기() {
		Member member = memberFactory.createMember();
		Product product = productFactory.createProduct(member);
		Long beforeWish = product.getWishCount();

		ProductWishResponse productWishResponse = productService.wishProduct(product.getId(), member.getId());

        assertTrue(productWishResponse.isWish());
		assertEquals(beforeWish + 1, productWishResponse.getWishCount());
	}

	@Test
	@Transactional
	void 찜_취소_하기() {
		Member member = memberFactory.createMember();
		Product product = productFactory.createProduct(member);
		Long beforeWish = product.getWishCount();

		ProductWishResponse wishResponse = productService.wishProduct(product.getId(), member.getId());
        assertTrue(wishResponse.isWish());
		assertEquals(beforeWish + 1, wishResponse.getWishCount());

		ProductWishResponse unwishResponse = productService.unwishProduct(product.getId(), member.getId());
        assertFalse(unwishResponse.isWish());
		assertEquals(beforeWish, unwishResponse.getWishCount());
	}

}
