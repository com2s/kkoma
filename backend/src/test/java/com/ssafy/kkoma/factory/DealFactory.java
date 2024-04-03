package com.ssafy.kkoma.factory;

import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.deal.repository.DealRepository;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.CategoryRepository;
import com.ssafy.kkoma.global.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DealFactory {

    @Autowired
    DealRepository dealRepository;

    public Deal createDeal(Member member, Product product) {
        Deal deal = Deal.builder()
                .member(member)
                .product(product)
                .selectedTime(LocalDateTime.now())
                .build();
        return dealRepository.save(deal);
    }

}
