package com.ssafy.kkoma.domain.deal.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.kkoma.domain.deal.entity.Deal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DealRepositoryCustom {

	Page<Deal> findScheduledDeal(LocalDateTime now, Pageable pageable);
}
