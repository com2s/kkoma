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
import java.util.*;

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

    private final static int MAX_NUM_OF_RECOMMENDED_ITEMS = 8;
    private final static int DEFAULT_NUM_OF_RECOMMENDED_ITEMS = 4;


    private void initDataModel() throws SQLException, TasteException {
        dataModel = new MySQLJDBCDataModel(dataSource, "category_preference", "member_id", "category_id", "preference", null);
        userSimilarity = new PearsonCorrelationSimilarity(dataModel);
        itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        userNeighborhood = new NearestNUserNeighborhood(3, userSimilarity, dataModel);
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

        Random random = new Random();

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
        List<RecommendedItem> recommendedCategories;

        // todo-siyoon optimize
        recommendedCategories = userBasedRecommender.recommend(memberId, num, true);

        List<ProductSummary> productSummaries = new ArrayList<>();
        for (RecommendedItem recommendedCategory : recommendedCategories) {
            Integer categoryId = (int) recommendedCategory.getItemID();
            List<Product> products = productService.findProductForSaleByCategoryId(memberId, categoryId);

            if (products.isEmpty()) {
                break;
            }

            for (int i = 0; i < num; i++) {
                int randomNumber = random.nextInt(products.size()); // Generate a random integer between 0 and size
                productSummaries.add(ProductSummary.fromEntity(products.get(randomNumber)));
            }
        }

        if (productSummaries.size() < num) {
            recommendedCategories = itemBasedRecommender.recommend(memberId, num, true);
            for (RecommendedItem recommendedCategory : recommendedCategories) {
                Integer categoryId = (int) recommendedCategory.getItemID();
                List<Product> products = productService.findProductForSaleByCategoryId(memberId, categoryId);

                if (products.isEmpty()) {
                    break;
                }

                for (int i = 0; i < num; i++) {
                    int randomNumber = random.nextInt(products.size()); // Generate a random integer between 0 and size
                    productSummaries.add(ProductSummary.fromEntity(products.get(randomNumber)));
                }
            }
        }

        if (productSummaries.size() < num) {
            List<Product> products = productService.findProductForSale();

            if (products.isEmpty()) {
                return new ArrayList<>();
            }

            for (int i = 0; i < num; i++) {
                int randomNumber = random.nextInt(products.size());
                productSummaries.add(ProductSummary.fromEntity(products.get(randomNumber)));
            }
        }

        return productSummaries;
    }

}
