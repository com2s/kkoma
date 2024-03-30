package com.ssafy.kkoma.api.recommendation.dto;

import com.ssafy.kkoma.domain.product.constant.ProductType;
import lombok.Builder;
import lombok.Getter;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

@Getter
@Builder
public class RecommendedProduct implements RecommendedItem {

    private Long id;
    private String thumbnailImage;
    private String title;
    private String dealPlace;
    private int price;
    private ProductType status;
    private Long elapsedMinutes;
    private float value;

    @Override
    public long getItemID() {
        return this.id;
    }

    @Override
    public float getValue() {
        return this.value;
    }

}
