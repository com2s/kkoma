package com.ssafy.kkoma.domain.offer.repository;

import com.ssafy.kkoma.domain.offer.entity.OfferDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferDetailRepository extends JpaRepository<OfferDetail, Long> {

    Optional<List<OfferDetail>> findAllOfferDetailsByOfferId(Long offerId);

}
