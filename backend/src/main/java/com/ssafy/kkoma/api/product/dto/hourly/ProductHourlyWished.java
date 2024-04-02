package com.ssafy.kkoma.api.product.dto.hourly;

import lombok.*;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductHourlyWished extends ProductHourlyBase {

    private Long hourlyWishCount; // 지난 1시간 동안 몇 명의 사람들이 찜을 눌렀는가 (현재 13:00이라면, 12:00~12:59 생성된 row만 고려)

}
