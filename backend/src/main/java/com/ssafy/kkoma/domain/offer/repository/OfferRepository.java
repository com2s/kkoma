package com.ssafy.kkoma.domain.offer.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.kkoma.domain.offer.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

	Optional<List<Offer>> findAllOffersByProductId(Long productId);

}
