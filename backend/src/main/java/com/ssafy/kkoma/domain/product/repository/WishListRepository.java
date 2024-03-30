package com.ssafy.kkoma.domain.product.repository;

import com.ssafy.kkoma.domain.product.entity.WishList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    boolean existsByProductIdAndMemberId(Long productId, Long memberId);

    WishList findByProductIdAndMemberId(Long productId, Long memberId);

    Page<WishList> findWishListsByMemberId(Long memberId, Pageable pageable);

}
