package com.ssafy.kkoma.factory;

import com.ssafy.kkoma.domain.chat.entity.ChatRoom;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {

    @Autowired
    ProductRepository productRepository;

    private static String TITLE = "TITLE";
    private static String IMAGE_URL = "https://lh3.googleusercontent.com/wAPeTvxh_EwOisF8kMR2L2eOrIOzjfA5AjE28W5asyfGeH85glwrO6zyqL71dCC26R63chADTO7DLOjnqRoXXOAB8t2f4C3QnU6o0BA";

    public ProductFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Member seller) {
        Product product = Product.builder()
                .title(TITLE)
                .thumbnailImage(IMAGE_URL)
                .build();
        product.setMember(seller);
        return productRepository.save(product);
    }

    public Product createProduct(Member seller, Category category, int price) {
        Product product = Product.builder()
                .title(TITLE)
                .category(category)
                .thumbnailImage(IMAGE_URL)
                .price(price)
                .build();
        product.setMember(seller);
        return productRepository.save(product);
    }

    public Product createProduct(Member member, Category category, ChatRoom chatRoom, int price) {
        Product product = Product.builder()
                .title(TITLE)
                .category(category)
                .chatRoom(chatRoom)
                .thumbnailImage(IMAGE_URL)
                .price(price)
                .build();
        product.setMember(member);
        return productRepository.save(product);
    }

}
