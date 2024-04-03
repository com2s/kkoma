package com.ssafy.kkoma.factory;

import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.domain.chat.entity.ChatRoom;
import com.ssafy.kkoma.domain.location.entity.Location;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ChatRoomFactory chatRoomFactory;
    @Autowired
    AreaFactory areaFactory;
    @Autowired
    LocationFactory locationFactory;

    private static String TITLE = "TITLE";
    private static String IMAGE_URL = "https://lh3.googleusercontent.com/wAPeTvxh_EwOisF8kMR2L2eOrIOzjfA5AjE28W5asyfGeH85glwrO6zyqL71dCC26R63chADTO7DLOjnqRoXXOAB8t2f4C3QnU6o0BA";

    public ProductFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Member seller) {
        Area area = areaFactory.createArea();
        Location location = locationFactory.createLocation();

        Product product = Product.builder()
                .title(TITLE)
                .thumbnailImage(IMAGE_URL)
                .location(location)
                .build();
        product.setMember(seller);
        return productRepository.save(product);
    }

    public Product createProduct(Member seller, ProductType type) {
        Product product = Product.builder()
                .title(TITLE)
                .thumbnailImage(IMAGE_URL)
                .status(type)
                .build();
        product.setMember(seller);
        return productRepository.save(product);
    }

    public Product createProduct(Member seller, Category category, int price) {
        Area area = areaFactory.createArea();
        Location location = locationFactory.createLocation();

        Product product = Product.builder()
                .title(TITLE)
                .category(category)
                .thumbnailImage(IMAGE_URL)
                .location(location)
                .price(price)
                .build();
        product.setMember(seller);

        ChatRoom chatRoom = chatRoomFactory.createChatRoom();
        product.setChatRoom(chatRoom);

        return productRepository.save(product);
    }

    public Product createProduct(Member member, Category category, int price, ProductType status) {
        Area area = areaFactory.createArea();
        Location location = locationFactory.createLocation();

        Product product = Product.builder()
                .title(TITLE)
                .category(category)
                .thumbnailImage(IMAGE_URL)
                .location(location)
                .price(price)
                .status(status)
                .build();
        product.setMember(member);
        return productRepository.save(product);
    }

    public Product createProduct(Member member, Category category, ChatRoom chatRoom, int price) {
        Area area = areaFactory.createArea();
        Location location = locationFactory.createLocation();

        Product product = Product.builder()
                .title(TITLE)
                .category(category)
                .chatRoom(chatRoom)
                .thumbnailImage(IMAGE_URL)
                .location(location)
                .price(price)
                .build();
        product.setMember(member);
        return productRepository.save(product);
    }

}
