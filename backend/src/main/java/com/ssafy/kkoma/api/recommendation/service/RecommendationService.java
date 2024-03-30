package com.ssafy.kkoma.api.recommendation.service;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.product.service.CategoryService;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.product.entity.Product;
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
    private final MemberRepository memberRepository;

    public List<RecommendedItem> recommendCategory(Long memberId) throws SQLException {

        Member member = memberService.findMemberByMemberId(memberId);
        log.info("dataSource={}", dataSource);
        log.info("dataSource.getConnection={}", dataSource.getConnection());
        try {
            DataModel dataModel = new MySQLJDBCDataModel(dataSource,
                    "category_preference",
                    "member_id",
                    "category_id",
                    "preference",
                    null);

            log.info("dataModel={}", dataModel);
            log.info("dataModel UserID={}", dataModel.getUserIDs());
            log.info("dataModel ItemID={}", dataModel.getItemIDs());
            // user-based recommendation
            UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
            log.info("userSimilarity={}", userSimilarity);
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, userSimilarity, dataModel);
            log.info("userNeighborhood={}", userNeighborhood);
            GenericBooleanPrefUserBasedRecommender userBasedRecommender = new GenericBooleanPrefUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
            log.info("userBasedRecommender={}", userBasedRecommender);
            List<RecommendedItem> userBasedRecommendations = userBasedRecommender.recommend(member.getId(), 1, true); // 최대 10개 추천
            log.info("userBasedRecommendations={}", userBasedRecommendations.size());

            // item-based recommendation
            ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
            log.info("itemSimilarity={}", itemSimilarity);
            GenericItemBasedRecommender itemBasedRecommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
            log.info("itemBasedRecommender={}", itemBasedRecommender);

            log.info("member={}", memberRepository.findById(memberId));
            log.info("member={}", memberRepository.findById(member.getId()));
            List<RecommendedItem> itemBasedRecommendations = itemBasedRecommender.recommend(member.getId(), 1, true); // 최대 10개 추천
            log.info("itemBasedRecommendations={}", itemBasedRecommendations.size());

            // combine
            List<RecommendedItem> recommendations = combineRecommendations(itemBasedRecommendations, userBasedRecommendations);

            return recommendations; // todo naming
        } catch (TasteException e) {
            // todo: move to global exception handler
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<RecommendedItem> combineRecommendations(List<RecommendedItem> src, List<RecommendedItem> newItems) {
        // Todo 사용자 기반 추천과 아이템 기반 추천을 결합하는 로직
        src.addAll(newItems);
        src.sort(Comparator.comparingDouble(RecommendedItem::getValue).reversed());
        return src;
    }

    public List<ProductSummary> recommendProduct(Long memberId) throws SQLException {

        // todo use lambda
        List<ProductSummary> productSummaries = new ArrayList<>();
        List<RecommendedItem> recommendedCategories = recommendCategory(memberId);
        for(RecommendedItem recommendedCategory : recommendedCategories) {
            List<Product> products = productService.findProductForSaleByCategoryId((int) recommendedCategory.getItemID());
            Optional<Product> result = products.stream()
                    .max(Comparator.comparingDouble(p -> (double)p.getWishCount() / p.getViewCount()));
            if (result.isPresent()) {
                Product product = result.get();
                productSummaries.add(ProductSummary.fromEntity(product));
            }
        }

        return productSummaries;
    }
}
