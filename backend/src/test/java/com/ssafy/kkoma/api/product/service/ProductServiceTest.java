package com.ssafy.kkoma.api.product.service;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.location.dto.request.CreateLocationRequest;
import com.ssafy.kkoma.api.product.dto.ProductCreateRequest;
import com.ssafy.kkoma.api.product.dto.ProductDetailResponse;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.product.dto.ProductWishResponse;
import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyViewedResponse;
import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyWishedResponse;
import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.api.product.dto.response.ChatProductResponse;
import com.ssafy.kkoma.api.product.dto.response.SearchProductResponse;
import com.ssafy.kkoma.api.redis.constant.RedisKeyName;
import com.ssafy.kkoma.api.redis.service.RedisService;
import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.domain.location.entity.Location;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.ViewHistory;
import com.ssafy.kkoma.domain.product.entity.WishList;
import com.ssafy.kkoma.domain.product.repository.CategoryRepository;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;
import com.ssafy.kkoma.domain.product.repository.ViewHistoryRepository;
import com.ssafy.kkoma.domain.product.repository.WishListRepository;
import com.ssafy.kkoma.factory.*;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ProductServiceTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private WishListRepository wishListRepository;

	@Autowired
	private ViewHistoryRepository viewHistoryRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private MemberFactory memberFactory;

	@Autowired
	private ProductFactory productFactory;

	@Autowired
	private CategoryFactory categoryFactory;

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

	@Test
	@Transactional
	void 지난_한_시간_동안_찜수_탑4_상품글_조회() {

		// 1) given
		int NUM = 5;
		Member seller = memberFactory.createMember();
		Member buyer;

		List<Product> productList = new ArrayList<>();
		List<Member> buyerList = new ArrayList<>();

		LocalDateTime now = LocalDateTime.now().withMinute(30).withSecond(0).withNano(0);

		for (int i = 1; i <= NUM; i++) {
			Product product = productFactory.createProduct(seller);
			productList.add(product);
		}

		for (int i = 1; i <= NUM; i++) {
			buyer = memberFactory.createMember();
			buyerList.add(buyer);
		}

		// 2) when

		// 상품4(0명) / 상품3(4명) / 상품2(3명) / 상품1(2명) / 상품0(1명)
		for (int m = 0; m < NUM; m++) {
			buyer = buyerList.get(m);
			for (int p = NUM - 2; p >= m; p--) {
				WishList wish = wishListFactory.createWishList(buyer, productList.get(p));
				wish.setCreatedAt(now.minusHours(1));
				wish = wishListRepository.save(wish);
			}
		}

		// 상품4 => 5명 but createdAt 시간이 1시간 전보다 전 시간
		for (int m = 0; m < NUM; m++) {
			buyer = buyerList.get(m);
			WishList wish = wishListFactory.createWishList(buyer, productList.get(NUM - 1));
			wish.setCreatedAt(now.withMinute(0).withSecond(0).withNano(0));
			wish = wishListRepository.save(wish);
		}

		List<ProductHourlyWishedResponse> result = productService.getHourlyMostWishedProducts(4, now);
		log.info("result 개수 {}", result.size());

		// 3) then : 최종 순위 => 상품3(4명) / 상품2(3명) / 상품1(2명) / 상품0(1명)

		long answer = NUM - 1;
		for (ProductHourlyWishedResponse p : result) {
			log.info("지난 정시~정시 1시간 동안 찜 수가 가장 많았던 순서대로: {}개", p.getHourlyWishCount());
			Assertions.assertThat(p.getHourlyWishCount()).isEqualTo(answer);
			answer--;
		}

//		redisService.setValues("hourlyWishedProductList", result);

	}

	@Test
	@Transactional
	void 지난_한_시간_동안_조회수_탑4_상품글_조회() {

		// 1) given
		int NUM = 5;
		Member seller = memberFactory.createMember();
		Member buyer;

		List<Product> productList = new ArrayList<>();
		List<Member> buyerList = new ArrayList<>();

		LocalDateTime now = LocalDateTime.now().withMinute(30).withSecond(0).withNano(0);

		for (int i = 1; i <= NUM; i++) {
			Product product = productFactory.createProduct(seller);
			productList.add(product);
		}

		for (int i = 1; i <= NUM; i++) {
			buyer = memberFactory.createMember();
			buyerList.add(buyer);
		}

		// 2) when

		// 상품4(0명) / 상품3(4명) / 상품2(3명) / 상품1(2명) / 상품0(1명)
		for (int m = 0; m < NUM; m++) {
			buyer = buyerList.get(m);
			for (int p = NUM - 2; p >= m; p--) {
				viewHistoryRepository.save(ViewHistory.builder()
					.member(buyer).product(productList.get(p))
					.viewedAt(now.minusHours(1))
					.build()
				);
			}
		}

		// 상품4 => 5명 but createdAt 시간이 1시간 전보다 전 시간
		for (int m = 0; m < NUM; m++) {
			buyer = buyerList.get(m);
			viewHistoryRepository.save(ViewHistory.builder()
					.member(buyer).product(productList.get(NUM - 1))
					.viewedAt(now.withMinute(0).withSecond(0).withNano(0))
					.build()
			);
		}

		List<ProductHourlyViewedResponse> result = productService.getHourlyMostViewedProducts(4, now);
		log.info("result 개수 {}", result.size());

		// 3) then : 최종 순위 => 상품3(4명) / 상품2(3명) / 상품1(2명) / 상품0(1명)

		long answer = NUM - 1;
		for (ProductHourlyViewedResponse p : result) {
			log.info("지난 정시~정시 1시간 동안 조회수가 가장 많았던 순서대로: {}개", p.getHourlyViewCount());
			Assertions.assertThat(p.getHourlyViewCount()).isEqualTo(answer);
			answer--;
		}

//		redisService.setValues("hourlyViewedProductList", result);

	}

	@Test
	@Disabled
	void 조회수와_찜수_상위_4개_조회_임시() {
		List<ProductHourlyViewedResponse> result1 = productService.getHourlyMostViewedProducts(4, LocalDateTime.now());
		log.info("[조회수] result 개수 {}", result1.size());

		for (ProductHourlyViewedResponse p : result1) {
			log.info("지난 정시~정시 1시간 동안 조회수가 가장 많았던 순서대로: {}개", p.getHourlyViewCount());
		}
		redisService.saveHourlyData(RedisKeyName.hourlyViewedProductList, result1);

		List<ProductHourlyWishedResponse> result2 = productService.getHourlyMostWishedProducts(4, LocalDateTime.now());
		log.info("[찜 수] result 개수 {}", result2.size());

		for (ProductHourlyWishedResponse p : result2) {
			log.info("지난 정시~정시 1시간 동안 찜이 가장 많았던 순서대로: {}개", p.getHourlyWishCount());
		}
		redisService.saveHourlyData(RedisKeyName.hourlyWishedProductList, result2);
	}

}
