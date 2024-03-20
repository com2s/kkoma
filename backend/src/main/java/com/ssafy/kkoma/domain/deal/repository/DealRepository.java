package com.ssafy.kkoma.domain.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.kkoma.domain.deal.entity.Deal;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

}
