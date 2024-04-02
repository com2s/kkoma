package com.ssafy.kkoma.api.recommendation.service;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final DataSource dataSource;
    private final MemberService memberService;
    private final ProductService productService;

    private DataModel dataModel;
    private UserSimilarity userSimilarity;
    private UserNeighborhood userNeighborhood;
    private GenericBooleanPrefUserBasedRecommender userBasedRecommender;
    private ItemSimilarity itemSimilarity;
    private GenericItemBasedRecommender itemBasedRecommender;

    private final static int MAX_NUM_OF_RECOMMENDED_ITEMS = 4;
    private final static int DEFAULT_NUM_OF_RECOMMENDED_ITEMS = 1;


    private void initDataModel() throws SQLException, TasteException {
        dataModel = new MySQLJDBCDataModel(dataSource, "category_preference", "member_id", "category_id", "preference", null);
        userSimilarity = new PearsonCorrelationSimilarity(dataModel);
        itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        userNeighborhood = new NearestNUserNeighborhood(1, userSimilarity, dataModel);
        userBasedRecommender = new GenericBooleanPrefUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
        itemBasedRecommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
    }

    private List<RecommendedItem> combineRecommendations(List<RecommendedItem> src, List<RecommendedItem> newItems) {
        // Todo 사용자 기반 추천과 아이템 기반 추천을 결합하는 로직
        src.addAll(newItems);
        src.sort(Comparator.comparingDouble(RecommendedItem::getValue).reversed());
        return src;
    }

    public List<ProductSummary> recommendProduct(Long memberId, Integer num) throws SQLException, TasteException {

        if (!memberService.existsMemberByMemberId(memberId)) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_EXISTS);
        }

        // set default value
        if (num == null) {
            num = DEFAULT_NUM_OF_RECOMMENDED_ITEMS;
        }
        else if (num > MAX_NUM_OF_RECOMMENDED_ITEMS) {
            num = MAX_NUM_OF_RECOMMENDED_ITEMS;
        }

        initDataModel();

        // todo-siyoon optimize
        List<RecommendedItem> recommendedCategories = userBasedRecommender.recommend(memberId, 3, true);
//        List<RecommendedItem> itemBasedRecommendations = itemBasedRecommender.recommend(member.getId(), howMany, true); // todo-siyoon use

        // todo-siyoon use 'num' as num of result
        List<ProductSummary> productSummaries = new ArrayList<>();
        for (RecommendedItem recommendedCategory : recommendedCategories) {
            Integer categoryId = (int) recommendedCategory.getItemID();
            List<Product> products = productService.findProductForSaleByCategoryId(categoryId);
            Optional<Product> result = products.stream().max(Comparator.comparingDouble(p -> (double) p.getWishCount() / (p.getViewCount() + 1)));

            if (result.isPresent()) {
                Product product = result.get();
                productSummaries.add(ProductSummary.fromEntity(product));
            }

            // todo-siyoon delete
            if (productSummaries.size() == num) {
                break;
            }
        }

        if (productSummaries.isEmpty()) {
            Product product = productService.findProductForSale();
            productSummaries.add(ProductSummary.fromEntity(product));
        }

        return productSummaries;
    }

}
