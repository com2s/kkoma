package com.ssafy.kkoma.domain.offer.repository;

import com.ssafy.kkoma.domain.offer.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

}
