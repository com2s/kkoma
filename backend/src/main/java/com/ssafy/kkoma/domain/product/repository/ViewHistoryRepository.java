package com.ssafy.kkoma.domain.product.repository;

import com.ssafy.kkoma.domain.product.entity.ViewHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViewHistoryRepository extends JpaRepository<ViewHistory, Long> {
    List<ViewHistory> findAllViewHistoriesByMemberIdAndProductId(Long memberId, Long productId);
    ViewHistory findViewHistoryByMemberIdAndProductId(Long memberId, Long productId);
    Page<ViewHistory> findByMemberIdOrderByViewedAtDesc(Long memberId, Pageable pageable);

}
