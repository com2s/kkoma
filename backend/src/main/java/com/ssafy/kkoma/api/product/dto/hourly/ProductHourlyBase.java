package com.ssafy.kkoma.api.product.dto.hourly;

import com.ssafy.kkoma.domain.product.constant.ProductType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder // 부모 객체를 상속 받는 자식 객체를 만들 때, 부모 객체의 필드값도 지정 가능
@Getter
@Setter
public class ProductHourlyBase {

    private Long id;

    private String thumbnailImage;
    private String title;
    private int price;

    private ProductType status;

    private Long wishCount;
    private Long viewCount;
    private Long offerCount;

    private LocalDateTime createdAt;

}
